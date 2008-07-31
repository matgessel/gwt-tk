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
package asquare.gwt.tkdemo.client.ui;

import asquare.gwt.sb.client.fw.*;
import asquare.gwt.tk.client.ui.ExposedCellPanel;

public class TabBodyUpdateController implements ListModelListener
{
	private final ExposedCellPanel m_tabPanel;
	private final ListSelectionModelSingle m_selectionModel;
	private final AppPanelCollection m_panels;
	
	private int m_previouslySelectedIndex = -1;
	
	public TabBodyUpdateController(ListModel model, ExposedCellPanel tabPanel, AppPanelCollection panels)
	{
		m_selectionModel = (ListSelectionModelSingle) model.getSelectionModel();
		m_tabPanel = tabPanel;
		m_panels = panels;
		model.addListener(this);
	}
	
	public void modelChanged(ListModelEvent event)
	{
		int selectedIndex = m_selectionModel.getSelectedIndex();
		if (selectedIndex != m_previouslySelectedIndex)
		{
			m_previouslySelectedIndex = selectedIndex;
			m_tabPanel.clearCell(1);
			if (selectedIndex != -1)
			{
				m_tabPanel.addWidgetTo(m_panels.getWidget(selectedIndex), 1);
			}
		}
	}
}
