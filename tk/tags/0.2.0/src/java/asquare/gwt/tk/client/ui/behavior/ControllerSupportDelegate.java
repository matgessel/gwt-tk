/*
 * Copyright 2006 Mat Gessel <mat.gessel@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package asquare.gwt.tk.client.ui.behavior;

import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

/**
 * A delegate class which manages controller bookkeeping and lifecycle. 
 * Ideally, this fuctionality would be in a superclass like
 * {@link com.google.gwt.user.client.ui.Widget Widget}.
 */
public class ControllerSupportDelegate implements ControllerSupport
{
	private final Widget m_widget;
	
	private List m_controllers;
	private int m_legacyEventBits = 0;
//	private boolean m_processing = false;
	
	/**
	 * Creates a delegate for the specified widget. 
	 */
	public ControllerSupportDelegate(Widget widget)
	{
		m_widget = widget;
	}
	
	private void sinkAllBits()
	{
		int controllerEventBits = 0;
		if (m_controllers != null)
		{
			for (int i = 0, size = m_controllers.size(); i < size; i++)
			{
				controllerEventBits |= ((Controller) m_controllers.get(i)).getEventBits();
			}
		}
		DOM.sinkEvents(m_widget.getElement(), m_legacyEventBits | controllerEventBits);
	}
	
	public void sinkEvents(int eventBits)
	{
		m_legacyEventBits |= eventBits;
		DOM.sinkEvents(m_widget.getElement(), 
				DOM.getEventsSunk(m_widget.getElement()) | m_legacyEventBits);
	}
	
	public void unSinkEvents(int eventBits)
	{
		m_legacyEventBits &= ~eventBits;
		sinkAllBits();
	}
	
	public Controller getController(Class id)
	{
		for (int i = 0, size = m_controllers.size(); i < size; i++)
		{
			if (((Controller) m_controllers.get(i)).getId() == id)
			{
				return (Controller) m_controllers.get(i);
			}
		}
		return null;
	}
	
	/**
	 * @throws IllegalArgumentException if <code>controller</code> is null
	 */
	public Widget addController(Controller controller)
	{
		if (controller == null)
			throw new IllegalArgumentException();
		
		if (m_controllers == null)
		{
			m_controllers = new Vector();
		}
		m_controllers.add(controller);
		DOM.sinkEvents(m_widget.getElement(), 
				DOM.getEventsSunk(m_widget.getElement()) | controller.getEventBits()); // adding bits is easy
		if (m_widget.isAttached())
		{
			controller.plugIn(m_widget);
		}
		return m_widget;
	}
	
	/**
	 * @throws IllegalArgumentException if <code>controller</code> is not present
	 */
	public Widget removeController(Controller controller)
	{
		int index = -1;
		if (m_controllers != null)
		{
			index = m_controllers.indexOf(controller);
		}
		
		if (index == -1)
			throw new IllegalArgumentException();
		
		m_controllers.remove(index);
		if (m_widget.isAttached())
		{
			controller.unplug(m_widget);
		}
		sinkAllBits();
		return m_widget;
	}
	
	public void setControllers(List controllers)
	{
		if (m_controllers != null && m_widget.isAttached())
		{
			for (int i = m_controllers.size() - 1; i >= 0; i--)
			{
				((Controller) m_controllers.get(i)).unplug(m_widget);
			}
		}
		m_controllers = controllers;
		if (m_controllers != null && m_widget.isAttached())
		{
			for (int i = m_controllers.size() - 1; i >= 0; i--)
			{
				((Controller) m_controllers.get(i)).plugIn(m_widget);
			}
		}
		sinkAllBits();
	}
	
	public void onAttach()
	{
		if (m_controllers != null)
		{
			for (int i = 0, size = m_controllers.size(); i < size; i++)
			{
				((Controller) m_controllers.get(i)).plugIn(m_widget);
			}
		}
	}
	
//	/**
//	 * @throws IllegalStateException if the widget is detached while controllers
//	 *             are processing an event. To prevent this from happening, use
//	 *             {@link com.google.gwt.user.client.DeferredCommand DeferredCommand}
//	 *             to remove the widget.
//	 */
	public void onDetach()
	{
//		if (m_processing)
//			throw new IllegalStateException("Detach called while calling onBrowserEvent(Event event)");
//		
		if (m_controllers != null)
		{
			for (int i = 0, size = m_controllers.size(); i < size; i++)
			{
				((Controller) m_controllers.get(i)).unplug(m_widget);
			}
		}
	}
	
	public void onBrowserEvent(Event event)
	{
		if (m_controllers != null)
		{
//			m_processing = true;
			for (int i = 0, size = m_controllers.size(); i < size; i++)
			{
				Controller controller = (Controller) m_controllers.get(i);
				if ((controller.getEventBits() & DOM.eventGetType(event)) != 0)
				{
					controller.onBrowserEvent(m_widget, event);
				}
			}
//			m_processing = false;
		}
	}
}
