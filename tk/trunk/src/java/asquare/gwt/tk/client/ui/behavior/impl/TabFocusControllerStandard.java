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
package asquare.gwt.tk.client.ui.behavior.impl;

import java.util.*;

import asquare.gwt.tk.client.ui.behavior.*;
import asquare.gwt.tk.client.ui.behavior.KeyEvent;
import asquare.gwt.tk.client.ui.behavior.event.FocusModelHandler;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;

public class TabFocusControllerStandard extends EventController implements TabFocusController, FocusHandler, BlurHandler, FocusModelHandler
{
	private final Map<Object, HandlerRegistration> m_registrations = new HashMap<Object, HandlerRegistration>();
	
	private FocusModel m_focusModel = null;
	private Focusable m_currentlyFocused = null;
	private BlurEvalCommand m_blurEvalCommand = null;
	
	public TabFocusControllerStandard()
	{
		super(TabFocusController.class, Event.ONKEYDOWN);
	}
	
	protected FocusModel getModel()
	{
		return m_focusModel;
	}
	
	public void setModel(FocusModel focusModel)
	{
		if (isPluggedIn())
		{
			unwireFocusListeners();
		}
		m_focusModel = focusModel;
		if (isPluggedIn())
		{
			wireFocusListeners();
		}
	}
	
	public void plugIn(Widget widget)
	{
		wireFocusListeners();
		super.plugIn(widget);
	}
	
	public void unplug(Widget widget)
	{
		super.unplug(widget);
		unwireFocusListeners();
	}
	
	private void wireFocusListeners()
	{
		if (m_focusModel != null)
		{
			m_focusModel.addHandler(this);
			for (int i = 0, size = m_focusModel.getSize(); i < size; i++)
			{
				Object widget = m_focusModel.getWidgetAt(i);
				HandlerRegistration registration = ((HasFocusHandlers) widget).addFocusHandler(this);
				m_registrations.put(widget, registration);
			}
		}
	}
	
	private void unwireFocusListeners()
	{
		if (m_focusModel != null)
		{
			Set<Object> widgets = m_registrations.keySet();
			for (Iterator<Object> iterator = widgets.iterator(); iterator.hasNext();)
			{
				Object widget = iterator.next();
				HandlerRegistration registration = m_registrations.get(widget);
				registration.removeHandler();
				iterator.remove();
			}
			m_focusModel.removeHandler(this);
		}
	}
	
	public void onBrowserEvent(Widget widget, Event event)
	{
		if (KeyEventImpl.getKeyCode(event) == KeyEvent.KEYCODE_TAB)
		{
			// cancel tab keydown, thereby overriding focus change
			DOM.eventPreventDefault(event);
			FocusModel model = getModel();
			if (model != null && model.getSize() > 0)
			{
				model.focusNextWidget(! DOM.eventGetShiftKey(event));
			}
		}
	}
	
	public void onFocus(FocusEvent event)
	{
//		Debug.println(" TabFocusControllerStandard.onFocus(" + GWT.getTypeName(sender) + ")");
		Focusable sender = (Focusable) event.getSource();
		if (m_blurEvalCommand != null)
		{
			m_blurEvalCommand.cancel();
		}
		if (m_currentlyFocused != sender)
		{
			m_currentlyFocused = sender;
			m_focusModel.setFocusWidget(sender);
		}
	}
	
	public void onBlur(BlurEvent event)
	{
//		Debug.println(" TabFocusControllerStandard.onLostFocus(" + GWT.getTypeName(sender) + ")");
		if (m_blurEvalCommand != null)
		{
			m_blurEvalCommand.cancel();
		}
		m_blurEvalCommand = new BlurEvalCommand(event.getSource());
		DeferredCommand.addCommand(m_blurEvalCommand);
	}
	
	// FocusModelHandler methods
	public void widgetsAdded(FocusModel model, Focusable[] added)
	{
		for (Object widget : added)
		{
			HandlerRegistration registration = ((HasFocusHandlers) widget).addFocusHandler(this);
			m_registrations.put(widget, registration);
		}
	}
	
	public void widgetsRemoved(FocusModel model, Focusable[] removed)
	{
		for (Object widget : removed)
		{
			HandlerRegistration registration = m_registrations.remove(widget);
			registration.removeHandler();
		}
	}
	
	public void focusChanged(FocusModel model, Focusable previous, Focusable current)
	{
		if (m_currentlyFocused != current)
		{
			m_currentlyFocused = current;
			if (m_currentlyFocused != null)
			{
				m_currentlyFocused.setFocus(true);
			}
		}
	}
	
	/**
	 * Used to filter extranneous focus changes from the same widget. E.g.
	 * focus, blur, focus.
	 */
	private class BlurEvalCommand implements Command
	{
		private final Object m_pendingBlurWidget;
		
		private boolean m_cancelled = false;
		
		public BlurEvalCommand(Object blurWidget)
		{
			m_pendingBlurWidget = blurWidget;
		}
		
		/**
		 * Cancels the command. It will still execute, but will have no effect.
		 */
		public void cancel()
		{
			m_cancelled = true;
			m_blurEvalCommand = null;
		}
		
		public void execute()
		{
			if (! m_cancelled && m_currentlyFocused == m_pendingBlurWidget)
			{
//				Debug.println("BlurEvalCommand.execute(blur widget)");
				m_focusModel.setFocusIndex(-1);
			}
			m_blurEvalCommand = null;
		}
	}
}
