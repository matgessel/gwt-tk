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
import asquare.gwt.tk.client.ui.behavior.AdjustObjectGesture.Positionable;
import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Adds controller support to popup panels. 
 */
public class CPopupPanel extends PopupPanel implements ControllerSupport, Positionable
{
	private final ControllerSupportDelegate m_controllerSupport = new ControllerSupportDelegate(this);
	private final EventPreviewDelegate m_eventPreview = (EventPreviewDelegate) GWT.create(EventPreviewDelegate.class);
	
	/**
	 * Creates a popup panel with auto hide disabled. 
	 */
	public CPopupPanel()
	{
		this(false);
	}
	
	/**
	 * Creates a popup panel. 
	 * @param autoHide true to hide the popup when the user clicks outside of it
	 */
	public CPopupPanel(boolean autoHide)
	{
		super(autoHide);
		setControllers(createControllers());
	}
	
	/**
	 * A factory method which gives a subclass the opportunity to override default 
	 * controller creation.
	 * 
	 * @return a List with 0 or more controllers, or <code>null</code>
	 */
	protected List<Controller> createControllers()
	{
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.ControllerSupport#addController(asquare.gwt.tk.client.ui.behavior.Controller)
	 */
	public Widget addController(Controller controller)
	{
		return m_controllerSupport.addController(controller);
	}
	
	/*
	 * (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.ControllerSupport#getController(java.lang.Class)
	 */
	public Controller getController(Class<? extends Controller> id)
	{
		return m_controllerSupport.getController(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.ControllerSupport#removeController(asquare.gwt.tk.client.ui.behavior.Controller)
	 */
	public Widget removeController(Controller controller)
	{
		return m_controllerSupport.removeController(controller);
	}
	
	/*
	 * (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.ControllerSupport#setControllers(java.util.List)
	 */
	public void setControllers(List<Controller> controllers)
	{
		m_controllerSupport.setControllers(controllers);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.UIObject#sinkEvents(int)
	 */
	public void sinkEvents(int eventBits)
	{
		m_controllerSupport.sinkEvents(eventBits);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.UIObject#unsinkEvents(int)
	 */
	public void unsinkEvents(int eventBits)
	{
		m_controllerSupport.unsinkEvents(eventBits);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Panel#onAttach()
	 */
	protected void onAttach()
	{
		if (isAttached())
			return;
		
		m_controllerSupport.onAttach();
		super.onAttach();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Panel#onDetach()
	 */
	protected void onDetach()
	{
		if(! isAttached())
			return;
		
		super.onDetach();
		m_controllerSupport.onDetach();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.PopupPanel#onEventPreview(com.google.gwt.user.client.Event)
	 */
	public boolean onEventPreview(Event event)
	{
		return m_eventPreview.onEventPreview(event, super.onEventPreview(event));
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Widget#onBrowserEvent(com.google.gwt.user.client.Event)
	 */
	public void onBrowserEvent(Event event)
	{
		m_controllerSupport.onBrowserEvent(event);
	}
	
	public int getLeft()
	{
		return getPopupLeft();
	}
	
	public void setLeft(int left)
	{
		DOM.setStyleAttribute(getElement(), "left", left + "px");
	}
	
	public int getTop()
	{
		return getPopupTop();
	}
	
	public void setTop(int top)
	{
		DOM.setStyleAttribute(getElement(), "top", top + "px");
	}
	
	/**
	 * This class basically exists to deal with the Firefox/scroll bug. 
	 */
	protected static class EventPreviewDelegate
	{
		/**
		 * @param event
		 * @param superResult the result of <code>PopupPanel{@link #onEventPreview(Event, boolean)}</code>
		 * @return <code>false</code> to cancel the event
		 */
		public boolean onEventPreview(Event event, boolean superResult)
		{
			return superResult;
		}
	}
	
	protected static class EventPreviewDelegateFF extends EventPreviewDelegate
	{
		public boolean onEventPreview(Event event, boolean superResult)
		{
			if (! DomUtil.isMac() && (DOM.eventGetType(event) & Event.MOUSEEVENTS) != 0)
			{
				/*
				 * The client area is bounded by the scroll bars. If a mouse
				 * event is outside the client area it must be targeted to a
				 * scroll bar. Override default behavior to allow these events.
				 */
				int clientX = DOM.eventGetClientX(event);
				int clientY = DOM.eventGetClientY(event);
				if (clientX > DomUtil.getViewportWidth() || clientY > DomUtil.getViewportHeight())
				{
					return true;
				}
			}
			return super.onEventPreview(event, superResult);
		}
	}
}
