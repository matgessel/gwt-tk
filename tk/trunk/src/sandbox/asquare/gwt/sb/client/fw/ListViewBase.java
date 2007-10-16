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

import asquare.gwt.sb.client.util.Properties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

public abstract class ListViewBase extends ViewBase implements ListView
{
	private CellRenderer m_renderer;
	
	protected ListViewBase(Widget listImpl, CellRenderer cellRenderer)
	{
		super(listImpl);
		setStyleName(ListView.STYLENAME_LIST);
		m_renderer = ((cellRenderer != null) ? cellRenderer : createRenderer());
	}
	
	protected CellRenderer createRenderer()
	{
        CellRendererDefault result = (CellRendererDefault) GWT.create(CellRendererDefault.class);
        result.setElementBaseStyleName(ListView.STYLENAME_LIST_ITEM);
        return result;
	}
	
	public CellRenderer getRenderer()
	{
		return m_renderer;
	}
	
	public void setRenderer(CellRenderer renderer)
	{
		m_renderer = renderer;
	}
	
	public void add(Object item, Properties cellProperties)
	{
		insert(getSize(), item, cellProperties);
	}
	
	public void insert(int index, Object item, Properties cellProperties)
	{
		getRenderer().renderCell(insertCellStructure(index), item, cellProperties);
	}
	
	protected abstract Element insertCellStructure(int index);
	
	protected abstract Element getCellElement(int index);
	
	public void renderCell(int index, Object item, Properties cellProperties)
	{
		getRenderer().renderCell(getCellElement(index), item, cellProperties);
	}
}
