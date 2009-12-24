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

import asquare.gwt.tk.client.ui.ExposedCellPanel;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

public class ListWidgetTable extends ListWidget
{
	private final ExposedCellPanel m_panel;
	
	public ListWidgetTable(ExposedCellPanel table)
	{
		super(table);
		m_panel = table;
		m_panel.setVerticalAlignment(null);
		m_panel.setHorizontalAlignment(null);
	}
	
    @Override
	public Element getCellRootElement(Element eventTarget)
	{
		for (int i = 0, size = m_panel.getCellCount(); i < size; i++)
		{
			Element candidate = m_panel.getCellElement(i);
			if (DOM.isOrHasChild(candidate, eventTarget))
			{
				return candidate;
			}
		}
		return null;
	}
	
    @Override
	public int getIndexOf(Element eventTarget)
	{
		for (int i = 0, size = m_panel.getCellCount(); i < size; i++)
		{
			if (DOM.isOrHasChild(m_panel.getCellElement(i), eventTarget))
			{
				return i;
			}
		}
		return -1;
	}
	
    @Override
	public void remove(int index)
	{
		m_panel.removeCell(index);
	}
	
    @Override
	public void clear()
	{
		m_panel.clear();
	}
	
    @Override
	public int getSize()
	{
		return m_panel.getCellCount();
	}
	
    @Override
	public Element getCellElement(int index)
	{
		return m_panel.getCellElement(index);
	}
	
    @Override
	public Element insertCellStructure(int index)
	{
		m_panel.insertCell(index);
		return m_panel.getCellElement(index);
	}
}
