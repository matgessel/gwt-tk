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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

public class ControllerWrapper extends ControllerAdaptor implements Controller
{
	private final Controller m_controller;
	
	public ControllerWrapper(int eventBits, Class id, Controller controller)
	{
		super(eventBits & controller.getEventBits(), id);
		m_controller = controller;
	}

	/**
	 * Get the wrapped controller.
	 *  
	 * @return the controller
	 */
	protected Controller getController()
	{
		return m_controller;
	}
	
	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.Controller#plugIn(com.google.gwt.user.client.ui.Widget)
	 */
	public void plugIn(Widget widget)
	{
		m_controller.plugIn(widget);
	}
	
	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.Controller#unplug(com.google.gwt.user.client.ui.Widget)
	 */
	public void unplug(Widget widget)
	{
		m_controller.unplug(widget);
	}
	
	/*
	 * (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.EventDelegateAdaptor#onBrowserEvent(com.google.gwt.user.client.ui.Widget, com.google.gwt.user.client.Event)
	 */
	public void onBrowserEvent(Widget widget, Event event)
	{
		if ((m_controller.getEventBits() & DOM.eventGetType(event)) != 0)
		{
			m_controller.onBrowserEvent(widget, event);
		}
	}
	
	/**
	 * Not supported. 
	 * 
	 * @deprecated
	 * @throws UnsupportedOperationException
	 */
	protected final boolean doBrowserEvent(Widget widget, Event event) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}
}
