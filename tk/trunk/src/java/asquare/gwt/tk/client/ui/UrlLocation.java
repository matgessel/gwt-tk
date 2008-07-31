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

import java.util.ArrayList;
import java.util.Iterator;

import asquare.gwt.tk.client.ui.commands.SelectAllCommand;

import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.*;

/**
 * A URL text box similar to that of a browser location bar. 
 * 
 * <p><em>This widget is a work in progress and therefore is subject to change. </em></p>
 */
public class UrlLocation extends TextBox implements FocusListener, KeyboardListener
{
	private ArrayList m_listeners = new ArrayList();
	
	public UrlLocation()
	{
		addFocusListener(this);
		addKeyboardListener(this);
	}
	
	public void addListener(URLListener listener)
	{
		m_listeners.add(listener);
	}
	
	public void removeListener(URLListener listener)
	{
		m_listeners.remove(listener);
	}
	
	public String getURL()
	{
		String url = getText();
		if (url.indexOf("://") == -1)
		{
			url = "http://" + url;
		}
		return url;
	}
	
	public void setURL(String url)
	{
		setText(url);
	}

	// FocusListener methods
	public void onFocus(Widget sender)
	{
		DeferredCommand.addCommand(new SelectAllCommand(this));
	}

	public void onLostFocus(Widget sender) {}

	// KeyboardListener methods
	public void onKeyPress(Widget sender, char keyCode, int modifiers)
	{
		if (keyCode == KeyboardListener.KEY_ENTER)
		{
			setFocus(false);
			for (Iterator iter = m_listeners.iterator(); iter.hasNext();)
			{
				((URLListener) iter.next()).urlEntered(this);
			}
		}
	}
	
	public void onKeyDown(Widget sender, char keyCode, int modifiers) {}

	public void onKeyUp(Widget sender, char keyCode, int modifiers) {}
}
