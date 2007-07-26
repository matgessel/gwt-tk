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
package asquare.gwt.tkdemo.client;

import asquare.gwt.debug.client.*;
import asquare.gwt.sb.client.fw.*;
import asquare.gwt.sb.client.widget.CTabBar;
import asquare.gwt.tk.client.ui.*;
import asquare.gwt.tk.client.util.*;
import asquare.gwt.tkdemo.client.ui.*;

import com.google.gwt.core.client.*;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;

/**
 * This is the entry point for the demo application. It builds the GUI after 
 * the script loads.
 */
public class Demo implements EntryPoint
{
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad()
	{
	    Debug.enableSilently();
	    
		final AppPanelCollection panels = new DemoPanelCollection();
		
		String initialTabToken = History.getToken();
		int initialIndex = panels.getIndexForToken(initialTabToken);
		if (initialIndex == -1)
		{
			initialIndex = 0;
		}
		
		// use a table for border to work around bugs with 100% child width
		RowPanel outer = new RowPanel();
		DomUtil.setAttribute(outer, "id", "main");
		outer.setWidth("100%");
		
		ExposedCellPanel tabPanel = new ColumnPanel();
		tabPanel.setSize("100%", "100%");
		
		CTabBar tabbar = new CTabBar(new ListWidgetVTable(), new SideTabRenderer3());
		tabbar.setStyleName("DemoTabBar");
		History.addHistoryListener(new TabModelUpdateController(panels, tabbar.getListModel()));
		tabPanel.add(tabbar);
		tabPanel.setCellStyleName("DemoTabPanel-tabBar");
		
		tabPanel.add(new HTML("<h2 style='text-align: center; width: 100%; height: 100%;'>Loading...</h2>"));
		tabPanel.setCellStyleName("DemoTabPanel-tabBody");
		tabPanel.setCellWidth("100%");
		new TabBodyUpdateController(tabbar.getListModel(), tabPanel, panels);
		
		outer.add(tabPanel);
		RootPanel.get().add(outer);
		
		BrowserInfo browserInfo = (BrowserInfo) GWT.create(BrowserInfo.class);
		String compatMode = describeCompatMode();
		Label env = new Label(compatMode + " (" + browserInfo.getUserAgent() + ")");
		env.setStyleName("compatMode");
		outer.add(env);
		
	    new DebugElementDumpInspector().install();
	    
	    /**
		 * Incrementally add tabs to TabBar, allowing the UI to redraw to show
		 * progress.
		 */
	    DeferredCommand.add(new LoadUICommand(panels, tabbar, initialIndex));
	}
	
	/**
	 * Generates a human-readable string representing the CSS rendering mode.
	 * 
	 * @see <a href="http://en.wikipedia.org/wiki/Quirks_mode">quirks mode</a>
	 */
	public native String describeCompatMode() /*-{
		var result = "unknown";
		if ($doc.compatMode == "BackCompat")
		{
			result = "quirks mode";
		}
		else if ($doc.compatMode == "CSS1Compat")
		{
			result = "standards compliance mode";
		}
		return result;
	}-*/;
	
	private static class LoadUICommand implements Command
	{
		private final AppPanelCollection m_panels;
		private final CTabBar m_tabbar;
		private final ListModelDefault m_tabBarmodel;
		private final ListSelectionModelSingle m_selectionModel;
		private final int m_initialIndex;
		
		private int m_index = 0;
		
		public LoadUICommand(AppPanelCollection panels, CTabBar tabbar, int initialIndex)
		{
			m_panels = panels;
			m_tabbar = tabbar;
			m_tabBarmodel = (ListModelDefault) tabbar.getListModel();
			m_selectionModel = (ListSelectionModelSingle) m_tabBarmodel.getSelectionModel();
			m_initialIndex = initialIndex;
		}
		
		public void execute()
		{
			if (m_index < m_panels.getSize())
			{
				m_panels.getWidget(m_index);
				m_tabBarmodel.add(m_panels.getUIString(m_index));
				m_tabBarmodel.update();
				m_index++;
				DeferredCommand.add(this);
			}
			else
			{
				m_selectionModel.setSelectedIndex(m_initialIndex);
				m_tabBarmodel.update();
				m_tabbar.addController(new IndexedViewHoverController(m_tabBarmodel));
				m_tabbar.addController(new TabBarClickControllerHistory(m_panels));
			}
		}
	}
}
