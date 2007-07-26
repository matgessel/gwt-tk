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
	
	public TabBarView(ListWidget structure, CellRenderer formatter)
	{
		super(structure, formatter);
		addSpacers();
		setStyleName("tk-TabBar");
	}
	
	private void addSpacers()
	{
		Element first = insertCellStructure(0);
		DOM.setAttribute(first, "className", "preSpacer");
		DOM.setInnerText(first, " "); // force cell to show in IE/Opera quirks mode
		
		Element last = insertCellStructure(super.getSize());
		DOM.setAttribute(last, "className", "postSpacer");
		DOM.setInnerText(last, " "); // force cell to show in IE/Opera quirks mode
	}
	
	public int getIndexOf(Element eventTarget)
	{
		int result = super.getIndexOf(eventTarget);
		return result != -1 ? result - 1 : result;
	}
	
	public void insert(int index, Object item, Properties cellProperties)
	{
		GwtUtil.rangeCheck(1, getSize(), index + 1, true);
		super.insert(index + 1, item, cellProperties);
	}
	
	public void clear()
	{
		// remove all children except spacers
		for (int i = getSize() - 2; i >= 0; i--)
		{
			remove(i);
		}
	}
	
	public void remove(int index)
	{
		GwtUtil.rangeCheck(1, getSize(), index + 1, false);
		super.remove(index + 1);
	}
	
	public int getSize()
	{
		return super.getSize() - 2;
	}
	
	public void renderCell(int index, Object item, Properties cellProperties)
	{
		GwtUtil.rangeCheck(1, getSize(), index + 1, false);
		super.renderCell(index + 1, item, cellProperties);
	}
}
