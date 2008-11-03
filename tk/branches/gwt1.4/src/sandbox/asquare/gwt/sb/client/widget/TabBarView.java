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
import asquare.gwt.sb.client.util.Properties;
import asquare.gwt.tk.client.util.GwtUtil;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * <h3>CSS Style Rules</h3>
 * <ul class='css'>
 * <li>.preSpacer { the initial spacer in the bar}</li>
 * <li>.postSpacer { traling spacer in the bar}</li>
 * </ul>
 */
public class TabBarView extends ListViewDefault
{
	public TabBarView()
	{
		this(new ListWidgetHTable(), null);
	}
	
	public TabBarView(ListWidget structure, CellRenderer renderer)
	{
		super(structure, renderer);
		addSpacers();
		setStyleName("tk-TabBar");
	}
	
	private void addSpacers()
	{
		Element first = insertCellStructure(0);
		DOM.setElementProperty(first, "className", "preSpacer");
		DOM.setInnerText(first, " "); // force cell to show in IE/Opera quirks mode
		
		Element last = insertCellStructure(super.getSize());
		DOM.setElementProperty(last, "className", "postSpacer");
		DOM.setInnerText(last, " "); // force cell to show in IE/Opera quirks mode
	}
	
	public Element getCellRootElement(CellId cellId)
	{
		IndexedCellId cellId0 = (IndexedCellId) cellId;
		return super.getCellRootElement(new IndexedCellIdImpl(cellId0.getIndex() + 1));
	}
	
	public Element getCellRootElement(Element element)
	{
		int index = getIndexOf(element);
		return (index >= 0) ? getCellRootElement(new IndexedCellIdImpl(index)) : null;
	}
	
	public int getIndexOf(Element eventTarget)
	{
		int result = super.getIndexOf(eventTarget) - 1;
		return (result >= 0 && result < getSize()) ? result : -1;
	}
	
	public void insert(IndexedCellId cellId, Object item, Properties cellProperties)
	{
		cellId.setIndex(cellId.getIndex() + 1);
		GwtUtil.rangeCheck(1, getSize(), cellId.getIndex(), true);
		super.insert(cellId, item, cellProperties);
	}
	
	public void clear()
	{
		// remove all children except spacers
		IndexedCellId cellId = new IndexedCellIdImpl();
		for (int index = getSize() - 2; index >= 0; index--)
		{
			cellId.setIndex(index);
			remove(cellId);
		}
	}
	
	public void remove(IndexedCellId cellId)
	{
		cellId.setIndex(cellId.getIndex() + 1);
		GwtUtil.rangeCheck(1, getSize(), cellId.getIndex(), false);
		super.remove(cellId);
	}
	
	public int getSize()
	{
		return super.getSize() - 2;
	}
	
	public void renderCell(CellId cellId, Object item, Properties cellProperties)
	{
		int index = ((IndexedCellId) cellId).getIndex();
		GwtUtil.rangeCheck(1, getSize(), index + 1, false);
		super.renderCell(cellId, item, cellProperties);
	}
}
