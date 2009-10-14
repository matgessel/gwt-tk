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

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

/**
 * A widget base class which supports pluggable controllers. 
 * 
 * @see asquare.gwt.tk.client.ui.behavior.Controller
 */
public class CWidget extends Widget implements ControllerSupport
{
	private final ControllerSupportDelegate m_controllerSupport;
	
	/**
	 * Creates a CWidget based on the specified element.
	 * 
	 * @throws IllegalArgumentException if <code>element</code> is null
	 */
	public CWidget(Element element)
	{
		this(element, null);
	}
	
	/**
	 * Creates a CWidget based on the specified element. Pass a
	 * value for <code>controllers</code> if you wish to avoid controller
	 * creation via {@link #createControllers()}. Otherwise pass null. 
	 * 
	 * @param element a DOM element
	 * @param controllers a list of 0 or more controllers, or <code>null</code>
	 * @throws IllegalArgumentException if <code>element</code> is null
	 */
	public CWidget(Element element, List<Controller> controllers)
	{
		if (element == null)
			throw new IllegalArgumentException();
		
		setElement(element);
		m_controllerSupport = new ControllerSupportDelegate(this);
		
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
	protected List<Controller> createControllers()
	{
		return null;
	}
	
	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.ControllerSupport#getController(java.lang.Class)
	 */
	public Controller getController(Class<? extends Controller> id)
	{
		return m_controllerSupport.getController(id);
	}

	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.ControllerSupport#addController(asquare.gwt.tk.client.ui.behavior.Controller)
	 */
	public Widget addController(Controller controller)
	{
		return m_controllerSupport.addController(controller);
	}

	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.ControllerSupport#removeController(asquare.gwt.tk.client.ui.behavior.Controller)
	 */
	public Widget removeController(Controller controller)
	{
		return m_controllerSupport.removeController(controller);
	}
	
	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.ControllerSupport#setControllers(java.util.List)
	 */
	public void setControllers(List<Controller> controllers)
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
		super.onAttach();
        m_controllerSupport.onAttach();
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
            m_controllerSupport.onDetach();
		}
		finally
		{
            super.onDetach();
		}
	}
	
	/*
	 *  (non-Javadoc)
	 * @see com.google.gwt.user.client.EventListener#onBrowserEvent(com.google.gwt.user.client.Event)
	 */
	public void onBrowserEvent(Event event)
	{
		m_controllerSupport.onBrowserEvent(event);
	}
}
