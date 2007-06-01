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

import asquare.gwt.sb.client.fw.ListModel;
import asquare.gwt.sb.client.fw.ListSelectionModelSingle;
import asquare.gwt.tkdemo.client.ui.AppPanelCollection;

import com.google.gwt.user.client.HistoryListener;

public class TabModelUpdateController implements HistoryListener
{
	private final AppPanelCollection m_panels;
	private final ListModel m_tabBarModel;
	private final ListSelectionModelSingle m_selectionModel;
	
	public TabModelUpdateController(AppPanelCollection panels, ListModel tabBarModel)
	{
		m_panels = panels;
		m_tabBarModel = tabBarModel;
		m_selectionModel = (ListSelectionModelSingle) tabBarModel.getSelectionModel();
	}
	
	public void onHistoryChanged(String historyToken)
	{
		int index = m_panels.getIndexForToken(historyToken);
		if (index != -1)
		{
			m_selectionModel.setSelectedIndex(index);
			m_tabBarModel.update();
		}
	}
}
