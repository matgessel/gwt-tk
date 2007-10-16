/*
 * Copyright 2006 Mat Gessel <mat.gessel@gmail.com>
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
package asquare.gwt.tk.client.ui;

import asquare.gwt.tk.client.util.GwtUtil;
import asquare.gwt.tk.client.util.TableUtil;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * A table-based panel which stacks cells horizontally in columns and permits
 * multiple widgets per cell. Empty cells are supported.
 * {@link #add(Widget) add(Widget)},
 * {@link #insert(Widget, int) insert(Widget, int)} and
 * {@link #remove(Widget) remove(Widget)} behave the same as in
 * {@link com.google.gwt.user.client.ui.HorizontalPanel HorizontalPanel}. That
 * is, the table cell and the widget are treated as one. Other methods have
 * options for cell addition, insertion and deletion.
 * </p>
 */
public class ColumnPanel extends ExposedCellPanel
{
	private final Element m_tr;
	
	/**
	 * Creates an empty panel with no columns. 
	 */
	public ColumnPanel()
	{
	    m_tr = TableUtil.createAppendTr(getBody());
	}
	
	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.ExposedCellPanel#insertCellStructure(int)
	 */
	protected void insertCellStructure(int cellIndex)
	{
		TableUtil.createInsertTd(m_tr, cellIndex);
	}
	
	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.ExposedCellPanel#removeCellStructure(int)
	 */
	protected void removeCellStructure(int cellIndex)
	{
		DOM.removeChild(m_tr, getCellElement(cellIndex));
	}
	
	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.ExposedCellPanel#getCellElement(int)
	 */
	public Element getCellElement(int cellIndex)
	{
		GwtUtil.rangeCheck(0, getCellCount(), cellIndex, false);
		
		return DOM.getChild(m_tr, cellIndex);
	}
}
