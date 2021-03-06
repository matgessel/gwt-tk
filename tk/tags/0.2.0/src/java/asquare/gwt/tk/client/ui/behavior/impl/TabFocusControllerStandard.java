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

import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HasFocus;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Widget;

public class TabFocusControllerStandard extends AbstractTabFocusController
{
	public TabFocusControllerStandard()
	{
		super(Event.ONKEYDOWN);
	}
	
	// EventDelegate methods
	protected boolean doBrowserEvent(Widget widget, Event event)
	{
		if (DomUtil.getKeyCode(event) == KeyboardListener.KEY_TAB)
		{
			if (m_focusModel.getSize() > 0)
			{
				HasFocus next;
				if (DOM.eventGetShiftKey(event))
				{
					next = m_focusModel.getPreviousWidget();
				}
				else
				{
					next = m_focusModel.getNextWidget();
				}
				next.setFocus(true);
			}
			// cancel tab keydown, thereby preventing focus change
			return false;
		}
		return true;
	}
}
