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
package asquare.gwt.tkdemo.client.junk;

import java.util.Vector;

import asquare.gwt.tk.client.util.GwtUtil;

import com.google.gwt.user.client.ui.*;

/**
 * A container which displays one of its children when a tab is selected. 
 * Basically, a lazy-attach replacement for the bottom portion of TabPanel.  
 */
public class DemoTabContent extends Composite implements TabListener
{
	private final SimplePanel m_panel = new SimplePanel();
	private final Vector m_panels = new Vector();
	
	public DemoTabContent()
	{
		initWidget(m_panel);
	}
	
	public void add(Widget w)
	{
		m_panels.add(w);
	}
	
	public void showWidget(int index)
	{
		GwtUtil.rangeCheck(-1, m_panels.size() + 1, index, false);
		
		Widget w = null;
		if (index != -1)
		{
			w = (Widget) m_panels.get(index);
		}
		m_panel.setWidget(w);
	}
	
	// TabListener methods
	public void onTabSelected(SourcesTabEvents sender, int tabIndex)
	{
		showWidget(tabIndex);
	}
	
	public boolean onBeforeTabSelected(SourcesTabEvents sender, int tabIndex)
	{
		return true;
	}
}
