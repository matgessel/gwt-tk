/*
 * Copyright 2008 Mat Gessel <mat.gessel@gmail.com>
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
package asquare.gwt.sb.client.widget;

import asquare.gwt.sb.client.fw.IndexedCellId;
import asquare.gwt.sb.client.fw.ListView;
import asquare.gwt.tk.client.ui.behavior.EventBase;


public class ListSelectionController extends ListSelectionControllerBase
{
	private final ListView m_view;
	
	public ListSelectionController(CList list)
	{
		super(list.getListModel().getSelectionModel(), list.getListModel());
		m_view = list.getListView();
	}
	
	protected int getIndex(EventBase e)
	{
		IndexedCellId cellId = ((IndexedCellId) m_view.getCellId(e.getTarget()));
		return cellId != null ? cellId.getIndex() : -1;
	}
}
