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
package asquare.gwt.tk.demo.client;

import java.util.Vector;

import asquare.gwt.debug.client.Debug;
import asquare.gwt.tk.client.ui.BorderPanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;

public class Demo implements EntryPoint
{
	public void onModuleLoad()
	{
		TabCollection tabs = new TabCollection();
		tabs.add("dropdown", "Drop Down Panel", new DropDownPanelPanel());
		tabs.add("debug", "Debug Utilities", new DebugPanel());
		tabs.add("misc", "Misc", new MiscPanel());
		
		BorderPanel outer = new BorderPanel();
		DOM.setAttribute(outer.getElement(), "id", "main");
		
		final TabPanel tabPanel = new TabPanel();
		tabPanel.setWidth("100%");
		for (int i = 0; i < tabs.size(); i++)
		{
			tabPanel.add(tabs.getWidget(i), tabs.getDescription(i));
		}
		outer.add(tabPanel);
		
		String initialTabToken = History.getToken();
		if (initialTabToken.length() == 0)
		{
			initialTabToken = tabs.getToken(0);
		}
		TabController controller = new TabController(tabs, tabPanel, initialTabToken);
		tabPanel.addTabListener(controller);
		History.addHistoryListener(controller);
		
		RootPanel.get().add(outer);
	    Debug.enableSilently();
	}
	
	static class TabCollection
	{
		private final Vector m_tokens = new Vector();
		private final Vector m_descriptions = new Vector();
		private final Vector m_bodies = new Vector();
		private int m_size = 0;
		
		public void add(String token, String description, Widget body)
		{
			if (token == null || description == null || body == null)
				throw new IllegalArgumentException();
			
			m_tokens.add(token);
			m_descriptions.add(description);
			m_bodies.add(body);
			m_size++;
		}
		
		public Widget getWidget(int i)
		{
			return (Widget) m_bodies.get(i);
		}

		public String getDescription(int i)
		{
			return (String) m_descriptions.get(i);
		}
		
		public String getToken(int i)
		{
			return (String) m_tokens.get(i);
		}
		
		public int getIndexForToken(String token)
		{
			return m_tokens.indexOf(token);
		}

		public int size()
		{
			return m_size;
		}
	}
}
