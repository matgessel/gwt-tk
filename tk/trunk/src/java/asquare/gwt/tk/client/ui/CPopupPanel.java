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

import java.util.ArrayList;
import java.util.List;

import asquare.gwt.tk.client.ui.behavior.*;
import asquare.gwt.tk.client.ui.behavior.AdjustObjectGesture.Positionable;
import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Adds controller support to popup panels. 
 */
public class CPopupPanel extends PopupPanel implements ControllerSupport, Positionable
{
	private static final PopupPanelController s_controller = (PopupPanelController) GWT.create(PopupPanelController.class);
	
	private final ControllerSupportDelegate m_controllerSupport = new ControllerSupportDelegate(this);
	
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
		List<Controller> result = new ArrayList<Controller>();
		result.add(s_controller);
		return result;
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
		
		super.onAttach();
        m_controllerSupport.onAttach();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Panel#onDetach()
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
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Widget#onBrowserEvent(com.google.gwt.user.client.Event)
	 */
	public void onBrowserEvent(Event event)
	{
		/*
		 * Ensure the wrapped widget gets any events it has sunk.
		 */
        if ((m_controllerSupport.getBitsForOnBrowserEvent() & event.getTypeInt()) != 0)
        {
            super.onBrowserEvent(event);
        }
		m_controllerSupport.onBrowserEvent(event);
	}
	
	public int getLeft()
	{
		return getPopupLeft();
	}
	
	public void setLeft(int left)
	{
		getElement().getStyle().setPropertyPx("left", left);
	}
	
	public int getTop()
	{
		return getPopupTop();
	}
	
	public void setTop(int top)
	{
		getElement().getStyle().setPropertyPx("top", top);
	}
	
	/**
	 * This class basically exists to deal with the Firefox/scroll bug. 
	 */
	public static class PopupPanelController extends ControllerAdaptor
	{
		public PopupPanelController()
		{
			super(PopupPanelController.class);
		}
		
		public void plugIn(Widget widget)
		{
			// NOOP
		}
		
		public void unplug(Widget widget)
		{
			// NOOP
		}
	}
	
	public static class PopupPanelControllerFF extends PopupPanelController implements NativePreviewHandler
	{
		private HandlerRegistration m_registration;
		
		@Override
		public void plugIn(Widget widget)
		{
			m_registration = Event.addNativePreviewHandler(this);
		}
		
		@Override
		public void unplug(Widget widget)
		{
			m_registration.removeHandler();
		}
		
		public void onPreviewNativeEvent(NativePreviewEvent event)
		{
			if ((event.getTypeInt() & Event.MOUSEEVENTS) != 0)
			if (! DomUtil.isMac())
			{
				/*
				 * The client area is bounded by the scroll bars. If a mouse
				 * event is outside the client area it must be targeted to a
				 * scroll bar. Override default behavior to allow these events.
				 */
				int clientX = event.getNativeEvent().getClientX();
				int clientY = event.getNativeEvent().getClientY();
				if (clientX > DomUtil.getViewportWidth() || clientY > DomUtil.getViewportHeight())
				{
					// overrides PopupPanel's call to NativePreviewEvent.cancel()
					event.consume();
				}
			}
		}
	}
}
