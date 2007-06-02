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
package asquare.gwt.tk.client.ui.commands;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.HasFocus;

/**
 * Focuses or blurs a widget. 
 * 
 * @see com.google.gwt.user.client.DeferredCommand#add(com.google.gwt.user.client.Command)
 */
public class FocusCommand implements Command
{
	private final HasFocus m_widget;
	private final boolean m_focus;
	
	/**
	 * Constructs a command which will focus the specified widget. 
	 * 
	 * @param widget a widget which implements {@link HasFocus}
	 */
	public FocusCommand(HasFocus widget)
	{
		m_widget = widget;
		m_focus = true;
	}
	
	/**
	 * Constructs a command which will focus or blur the specified widget. 
	 * 
	 * @param widget a widget which implements {@link HasFocus}
	 * @param focus <code>true</code> to focus <code>widget</code>, <code>false</code> to blur
	 */
	public FocusCommand(HasFocus widget, boolean focus)
	{
		m_widget = widget;
		m_focus = focus;
	}
	
	/*
	 *  (non-Javadoc)
	 * @see com.google.gwt.user.client.Command#execute()
	 */
	public void execute()
	{
		m_widget.setFocus(m_focus);
	}
}
