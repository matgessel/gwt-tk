/*
 * Copyright 2007 Mat Gessel <mat.gessel@gmail.com>
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
 * wrapped widget will continue to process events and notify listeners as if it
 * was not wrapped.
 * <p>
 * Usage notes:
 * <ul>
 * <li>{@link #initWidget(Widget)} must be called before you can access/modify
 * controllers</li>
 * </ul>
 */
public class CComposite extends EventWrapper implements ControllerSupport
{
	private final boolean m_eventsTargetWrapper;
	
	private ControllerSupportDelegate m_controllerSupport = null;
	
	public CComposite()
	{
		this(true);
	}
	
	/**
	 * @param eventsTargetWrapper <code>true</code> to pass this wrapper
	 *            object to {@link Controller} methods, <code>false</code> to
	 *            pass the wrapped widget
	 */
	public CComposite(boolean eventsTargetWrapper)
	{
		m_eventsTargetWrapper = eventsTargetWrapper;
	}
	
	private ControllerSupportDelegate getControllerSupport()
	{
		if (m_controllerSupport == null)
			throw new IllegalStateException("CComposite.initWidget() not called");
		
		return m_controllerSupport;
	}
	
	/*
	 * (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.EventWrapper#initWidget(com.google.gwt.user.client.ui.Widget)
	 */
	protected void initWidget(Widget widget)
	{
		if (widget == null)
			throw new IllegalArgumentException();
		
		super.initWidget(widget);
		Widget eventTarget = m_eventsTargetWrapper ? this : widget;
		m_controllerSupport = new ControllerSupportDelegate(eventTarget);
		setControllers(createControllers());
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
		return getControllerSupport().getController(id);
	}

	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.ControllerSupport#addController(asquare.gwt.tk.client.ui.behavior.Controller)
	 */
	public Widget addController(Controller controller)
	{
		getControllerSupport().addController(controller);
		return this;
	}

	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.ControllerSupport#removeController(asquare.gwt.tk.client.ui.behavior.Controller)
	 */
	public Widget removeController(Controller controller)
	{
		getControllerSupport().removeController(controller);
		return this;
	}
	
	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.ControllerSupport#setControllers(java.util.List)
	 */
	public void setControllers(List controllers)
	{
		getControllerSupport().setControllers(controllers);
	}
	
	/*
	 *  (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.UIObject#sinkEvents(int)
	 */
	public void sinkEvents(int eventBits)
	{
		getControllerSupport().sinkEvents(eventBits);
	}

	/*
	 *  (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.UIObject#unsinkEvents(int)
	 */
	public void unsinkEvents(int eventBits)
	{
		getControllerSupport().unsinkEvents(eventBits);
	}
	
	/*
	 *  (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Widget#onAttach()
	 */
	protected void onAttach()
	{
		if (isAttached())
			return;
		
		getControllerSupport().onAttach();
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
			super.onDetach();
		}
		finally
		{
			getControllerSupport().onDetach();
		}
	}
	
	/*
	 *  (non-Javadoc)
	 * @see com.google.gwt.user.client.EventListener#onBrowserEvent(com.google.gwt.user.client.Event)
	 */
	public void onBrowserEvent(Event event)
	{
		/*
		 * CComposite intercepts the widget's events. Here we ensure the wrapped
		 * widget gets any events it has sunk.
		 */
		if ((getControllerSupport().getLegacyEventBits() & DOM.eventGetType(event)) != 0)
		{
			super.onBrowserEvent(event);
		}
		getControllerSupport().onBrowserEvent(event);
	}
}
