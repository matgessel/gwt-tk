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

import java.util.List;
import java.util.Vector;

import asquare.gwt.sb.client.fw.*;
import asquare.gwt.tk.client.ui.behavior.PreventSelectionController;

public class CList extends CComponent
{
	public CList(ListModel model, ListViewBase view)
	{
		super(model, view);
		new ListUpdateController(model, view);
	}
	
	protected List createControllers()
	{
		List result = new Vector();
		result.add(PreventSelectionController.getInstance());
		return result;
	}
	
	public ListModel getListModel()
	{
		return (ListModel) getModel();
	}
	
	public ListView getListView()
	{
		return (ListView) getView();
	}
}
