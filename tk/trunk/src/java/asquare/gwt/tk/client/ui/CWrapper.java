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
package asquare.gwt.tk.client.ui;

import java.util.List;

import asquare.gwt.tk.client.ui.behavior.Controller;
import asquare.gwt.tk.client.ui.behavior.ControllerSupport;
import asquare.gwt.tk.client.ui.behavior.ControllerSupportDelegate;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

/**
 * Wraps an widget to provide event processing via
 * {@link asquare.gwt.tk.client.ui.behavior.Controller controllers}. The
 * wrapped widget will process events and notify listeners as if it was not
 * wrapped.
 */
public class CWrapper extends EventWrapper implements ControllerSupport
{
	private final ControllerSupportDelegate m_controllerSupport;
	
	/**
	 * Creates a CWrapper around the specified widget. 
	 */
	public CWrapper(Widget widget)
	{
		this(widget, null);
	}
	
	/**
	 * Creates a CWrapper around the specified widget. Pass a
	 * value for <code>controllers</code> if you wish to avoid controller
	 * creation via {@link #createControllers()}. Otherwise pass null. 
	 * The event target widget will be the wrapper.  
	 * 
	 * @param widget the widget to wrap
	 * @param controllers a list of 0 or more controllers, or <code>null</code>
	 * @throws IllegalArgumentException if <code>element</code> is null
	 */
	public CWrapper(Widget widget, List controllers)
	{
		this(widget, controllers, true);
	}
	
	/**
	 * Creates a CWrapper around the specified widget. Pass a value for
	 * <code>controllers</code> if you wish to avoid controller creation via
	 * {@link #createControllers()}. Otherwise pass null.
	 * 
	 * @param widget the widget to wrap
	 * @param controllers a list of 0 or more controllers, or <code>null</code>
	 * @param eventsTargetWrapper <code>true</code> to pass this wrapper
	 *            object to {@link Controller} methods, <code>false</code> to
	 *            pass the wrapped widget
	 * @throws IllegalArgumentException if <code>element</code> is null
	 */
	public CWrapper(Widget widget, List controllers, boolean eventsTargetWrapper)
	{
		initWidget(widget);
		Widget eventTarget = eventsTargetWrapper ? this : widget;
		m_controllerSupport = new ControllerSupportDelegate(eventTarget);
		
		if (controllers == null)
			controllers = createControllers();
		
		setControllers(controllers);
	}
	
	/**
	 * A factory method which gives subclasses the opportunity to override
	 * default controller creation.
	 * 
	 * @return a List with 0 or more controllers, or <code>null</code>
	 */
	protected List createControllers()
	{
		return null;
	}
	
	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.ControllerSupport#getController(java.lang.Class)
	 */
	public Controller getController(Class id)
	{
		return m_controllerSupport.getController(id);
	}

	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.ControllerSupport#addController(asquare.gwt.tk.client.ui.behavior.Controller)
	 */
	public Widget addController(Controller controller)
	{
		m_controllerSupport.addController(controller);
		return this;
	}

	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.ControllerSupport#removeController(asquare.gwt.tk.client.ui.behavior.Controller)
	 */
	public Widget removeController(Controller controller)
	{
		m_controllerSupport.removeController(controller);
		return this;
	}
	
	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.ControllerSupport#setControllers(java.util.List)
	 */
	public void setControllers(List controllers)
	{
		m_controllerSupport.setControllers(controllers);
	}
	
	/*
	 *  (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.UIObject#sinkEvents(int)
	 */
	public void sinkEvents(int eventBits)
	{
		m_controllerSupport.sinkEvents(eventBits);
	}

	/*
	 *  (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.UIObject#unsinkEvents(int)
	 */
	public void unsinkEvents(int eventBits)
	{
		m_controllerSupport.unsinkEvents(eventBits);
	}
	
	/*
	 *  (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Widget#onAttach()
	 */
	protected void onAttach()
	{
		if (isAttached())
			return;
		m_controllerSupport.onAttach();
		super.onAttach();
	}
	
	/*
	 *  (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Widget#onDetach()
	 */
	protected void onDetach()
	{
		if(! isAttached())
			return;
		
		try
		{
			onUnload();
		}
		finally
		{
			super.onDetach();
			m_controllerSupport.onDetach();
		}
	}
	
	/*
	 *  (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 */
	protected void onLoad()
	{
	}
	
	/**
	 * This method is called just before the widget is detached from the
	 * browser's document.
	 */
	protected void onUnload()
	{
	}
	
	/*
	 *  (non-Javadoc)
	 * @see com.google.gwt.user.client.EventListener#onBrowserEvent(com.google.gwt.user.client.Event)
	 */
	public void onBrowserEvent(Event event)
	{
		if ((m_controllerSupport.getLegacyEventBits() & DOM.eventGetType(event)) != 0)
		{
			super.onBrowserEvent(event);
		}
		m_controllerSupport.onBrowserEvent(event);
	}
}
