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
package asquare.gwt.sb.client.fw;

import asquare.gwt.sb.client.util.Action;
import asquare.gwt.sb.client.util.ActionPropertyListener;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;

public class ActionButton extends Button implements ActionPropertyListener
{
	private Action m_action;
	
	public ActionButton()
	{
	}
	
	public ActionButton(Action action)
	{
		setAction(action);
	}
	
	public Action getAction()
	{
		return m_action;
	}
	
	public void setAction(Action action)
	{
		if (m_action != null)
		{
			m_action.removeListener(this);
		}
		m_action = action;
		if (m_action != null)
		{
			m_action.addListener(this);
			actionPropertiesChanged(action);
		}
	}
	
	public void onBrowserEvent(Event event)
	{
		super.onBrowserEvent(event);
		if (DOM.eventGetType(event) == Event.ONCLICK)
		{
			m_action.execute();
		}
	}
	
	// ActionPropertyListener methods
	public void actionPropertiesChanged(Action action)
	{
		setText(action.getUIString());
		setEnabled(action.isEnabled());
	}
}
