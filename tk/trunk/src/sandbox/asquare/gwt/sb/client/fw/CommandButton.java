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

import asquare.gwt.sb.client.util.UICommand;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;

/**
 * A button which accepts a command for configuration and click action. 
 */
public class CommandButton extends Button
{
	private UICommand m_command;
	
	public CommandButton()
	{
	}
	
	public CommandButton(UICommand command)
	{
		setCommand(command);
	}
	
	public UICommand getCommand()
	{
		return m_command;
	}
	
	public void setCommand(UICommand command)
	{
		m_command = command;
		if (m_command != null)
		{
			setText(command.getUIString());
		}
	}
	
	public void onBrowserEvent(Event event)
	{
		super.onBrowserEvent(event);
		if (DOM.eventGetType(event) == Event.ONCLICK)
		{
			m_command.execute();
		}
	}
}
