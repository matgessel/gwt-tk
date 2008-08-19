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

import com.google.gwt.user.client.Element;

public class ListViewDefault extends CompositeCellViewBase implements ListView
{
	private final ListWidget m_structure;
	
	protected ListViewDefault(ListWidget structure, CellRenderer renderer)
	{
		super(renderer);
		m_structure = structure;
		initWidget(structure);
		setStyleName(ListView.STYLENAME_LIST);
	}
	
	protected CellRenderer createRenderer()
	{
        return new ListCellRendererDefault();
	}
	
	public CellId getCellId(Element eventTarget)
	{
		int index = getIndexOf(eventTarget);
		return index != -1 ? new IndexedCellIdImpl(index) : null;
	}
	
	public Element getCellRootElement(Element element)
	{
		return m_structure.getCellRootElement(element);
	}
	
	public Element getCellRootElement(CellId cellId)
	{
		return m_structure.getCellElement(((IndexedCellId) cellId).getIndex());
	}
	
	public void add(Object item, Properties cellProperties)
	{
		insert(new IndexedCellIdImpl(getSize()), item, cellProperties);
	}
	
	public void insert(IndexedCellId cellId, Object item, Properties cellProperties)
	{
		getRenderer(cellId, item, cellProperties).renderCell(insertCellStructure(cellId.getIndex()), item, cellProperties);
	}
	
	protected Element insertCellStructure(int index)
	{
		return m_structure.insertCellStructure(index);
	}
	
	public void clear()
	{
		m_structure.clear();
	}
	
	public int getIndexOf(Element eventTarget)
	{
		return m_structure.getIndexOf(eventTarget);
	}
	
	public int getSize()
	{
		return m_structure.getSize();
	}
	
	public void remove(IndexedCellId cellId)
	{
		m_structure.remove(cellId.getIndex());
	}
}
