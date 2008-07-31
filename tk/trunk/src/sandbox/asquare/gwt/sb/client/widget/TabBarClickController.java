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
package asquare.gwt.sb.client.widget;

import asquare.gwt.sb.client.fw.*;
import asquare.gwt.tk.client.ui.behavior.ControllerAdaptor;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

public class TabBarClickController extends ControllerAdaptor
{
	private final ListModel m_model;
	private final ListSelectionModelSingle m_selectionModel;
	
	public TabBarClickController(ListModel model)
	{
		super(TabBarClickController.class, Event.ONMOUSEDOWN);
		m_model = model;
		m_selectionModel = (ListSelectionModelSingle) model.getSelectionModel();
	}
	
	public void onBrowserEvent(Widget widget, Event event)
	{
		CellId cellId = ((ListView) widget).getCellId(DOM.eventGetTarget(event));
		if (cellId != null)
		{
			m_selectionModel.setSelectedIndex(((IndexedCellId) cellId).getIndex());
		}
		m_model.update();
	}
}
