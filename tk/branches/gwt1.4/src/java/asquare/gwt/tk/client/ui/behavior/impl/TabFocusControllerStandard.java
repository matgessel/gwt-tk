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

import asquare.gwt.tk.client.ui.behavior.*;

import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HasFocus;
import com.google.gwt.user.client.ui.Widget;

public class TabFocusControllerStandard extends EventController implements TabFocusController, FocusListener, FocusModelListener
{
	private FocusModel m_focusModel = null;
	private HasFocus m_currentlyFocused = null;
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
			m_focusModel.addListener(this);
			for (int i = 0, size = m_focusModel.getSize(); i < size; i++)
			{
				m_focusModel.getWidgetAt(i).addFocusListener(this);
			}
		}
	}
	
	private void unwireFocusListeners()
	{
		if (m_focusModel != null)
		{
			for (int i = 0, size = m_focusModel.getSize(); i < size; i++)
			{
				m_focusModel.getWidgetAt(i).removeFocusListener(this);
			}
			m_focusModel.removeListener(this);
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
	
	// FocusListener methods
	public void onFocus(Widget sender)
	{
//		Debug.println(" TabFocusControllerStandard.onFocus(" + GWT.getTypeName(sender) + ")");
		if (m_blurEvalCommand != null)
		{
			m_blurEvalCommand.cancel();
		}
		if (m_currentlyFocused != sender)
		{
			m_currentlyFocused = (HasFocus) sender;
			m_focusModel.setFocusWidget((HasFocus) sender);
		}
	}
	
	public void onLostFocus(Widget sender)
	{
//		Debug.println(" TabFocusControllerStandard.onLostFocus(" + GWT.getTypeName(sender) + ")");
		if (m_blurEvalCommand != null)
		{
			m_blurEvalCommand.cancel();
		}
		m_blurEvalCommand = new BlurEvalCommand(sender);
		DeferredCommand.addCommand(m_blurEvalCommand);
	}
	
	// FocusModelListener
	public void widgetsAdded(FocusModel model, HasFocus[] added)
	{
		for (int i = 0; i < added.length; i++)
		{
			added[i].addFocusListener(this);
		}
	}
	
	public void widgetsRemoved(FocusModel model, HasFocus[] removed)
	{
		for (int i = 0; i < removed.length; i++)
		{
			removed[i].removeFocusListener(this);
		}
	}
	
	public void focusChanged(FocusModel model, HasFocus previous, HasFocus current)
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
		private final Widget m_pendingBlurWidget;
		
		private boolean m_cancelled = false;
		
		public BlurEvalCommand(Widget blurWidget)
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
