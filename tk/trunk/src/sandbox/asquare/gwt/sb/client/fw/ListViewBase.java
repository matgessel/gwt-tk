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
package asquare.gwt.sb.client.fw;

import java.util.List;
import java.util.Vector;

import asquare.gwt.sb.client.util.Properties;
import asquare.gwt.tk.client.ui.CWrapper;
import asquare.gwt.tk.client.ui.behavior.PreventSelectionController;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

public abstract class ListViewBase extends CWrapper implements ListView
{
	private ModelElementFormatter m_formatter;
	
	protected ListViewBase(Widget listImpl, List controllers, ModelElementFormatter formatter)
	{
		super(listImpl, controllers);
		setStyleName(ListView.STYLENAME_LIST);
		m_formatter = ((formatter != null) ? formatter : createFormatter());
	}
	
	protected List createControllers()
	{
		List result = new Vector();
		result.add(PreventSelectionController.getInstance());
		return result;
	}
	
	protected ModelElementFormatter createFormatter()
	{
		ListElementFormatterDefault result = (ListElementFormatterDefault) GWT.create(ListElementFormatterDefault.class);
		result.setElementStyleName(STYLENAME_LIST_ITEM);
		return result;
	}
	
	public ModelElementFormatter getFormatter()
	{
		return m_formatter;
	}
	
	public void setFormatter(ModelElementFormatter formatter)
	{
		m_formatter = formatter;
	}
	
	public void add(Object item, Properties cellProperties)
	{
		insert(getSize(), item, cellProperties);
	}
	
	public void insert(int index, Object item, Properties cellProperties)
	{
		m_formatter.formatCell(insertCellStructure(index), item, cellProperties);
	}
	
	protected abstract Element insertCellStructure(int index);
	
	protected abstract Element getCellElement(int index);
	
	public void formatCell(int index, Object item, Properties cellProperties)
	{
		m_formatter.formatCell(getCellElement(index), item, cellProperties);
	}
}
