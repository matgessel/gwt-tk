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
package asquare.gwt.tests.history.client;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TabPanel;

public class TabController implements ValueChangeHandler<String>, SelectionHandler<Integer>
{
	private final TabCollection m_tabs;
	private final TabPanel m_tabPanel;
	private final String m_windowTitle = Window.getTitle();
	
	private int m_selectedTab = -1;
	
	public TabController(TabCollection tabs, TabPanel tabPanel, String initialTabToken)
	{
		m_tabs = tabs;
		m_tabPanel = tabPanel;
		
		int tabIndex = m_tabs.getIndexForToken(initialTabToken);
		selectTab(tabIndex);
	}
	
	public void selectTab(int tabIndex)
	{
		if (tabIndex != m_selectedTab)
		{
			m_selectedTab = tabIndex; // set first to prevent re-entry
			m_tabPanel.selectTab(tabIndex);
			Window.setTitle(m_windowTitle + " > " + m_tabs.getDescription(tabIndex));
		}
	}
	
	// History
	public void onValueChange(ValueChangeEvent<String> event)
	{
		String historyToken = event.getValue();
		int tabIndex = m_tabs.getIndexForToken(historyToken);
		if (tabIndex < 0)
		{
			tabIndex = 0;
		}
		selectTab(tabIndex);
	}
	
	// Tab
	public void onSelection(SelectionEvent<Integer> event)
	{
		int tabIndex = event.getSelectedItem();
		if (tabIndex != m_selectedTab)
		{
			History.newItem(m_tabs.getToken(tabIndex));
		}
	}
}
