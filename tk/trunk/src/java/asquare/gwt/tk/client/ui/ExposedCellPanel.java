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

import java.util.Vector;

import asquare.gwt.tk.client.util.GwtUtil;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.Widget;

/**
 * A table-based panel which {@link #getCellElement(int) exposes} the TD element
 * and supports:
 * <ul>
 * <li>setting styles on the TD</li>
 * <li>multiple widgets per cell</li>
 * <li>empty cells</li>
 * </ul>
 */
public abstract class ExposedCellPanel extends CellPanel implements HasAlignment
{
	/**
	 * Maps the index of each cell to an ordered list of child widgets. 
	 * Mapping may be null if the cell contains no widgets. <br/>
	 */
	private final Vector m_cellMap = new Vector();
	
	private HorizontalAlignmentConstant m_horizontalAlignment = ALIGN_LEFT;
	private VerticalAlignmentConstant m_verticalAlignment = ALIGN_TOP;
	
	/**
	 * Creates an empty panel with no cells. 
	 */
	 ExposedCellPanel()
	{
		DOM.setAttribute(getTable(), "cellSpacing", "0");
	    DOM.setAttribute(getTable(), "cellPadding", "0");
	}
	
	/**
	 * Gets the number of cells in this panel. 
	 */
	public int getCellCount()
	{
		return m_cellMap.size();
	}
	
	/**
	 * Adds a new cell to the panel. 
	 */
	public void addCell()
	{
		insertCell(m_cellMap.size());
	}
	
	/**
	 * Creates a new cell and inserts it at the specified index. Existing cells
	 * with an index >= <code>cellIndex</code> will be shifted over by 1.
	 * 
	 * @param cellIndex the index where the cell will be inserted
	 * @throws IndexOutOfBoundsException if <code>cellIndex</code> is less
	 *             than 0 or greater than the number of cells
	 */
	public void insertCell(int cellIndex)
	{
		GwtUtil.rangeCheck(m_cellMap, cellIndex, true);
		
		m_cellMap.add(cellIndex, null);
		insertCellStructure(cellIndex);
		if (m_horizontalAlignment != null)
		{
			setCellHorizontalAlignment(cellIndex, m_horizontalAlignment);
		}
		if (m_verticalAlignment != null)
		{
			setCellVerticalAlignment(cellIndex, m_verticalAlignment);
		}
	}
	
	/**
	 * A <em>template method</em> which creates a td and inserts it into the
	 * underlying table. If a cell exists at <code>cellIndex</code> it will be
	 * shifted up by 1. Implementors may assume <code>cellIndex</code> is
	 * greater than 0 and less than or equal to the number of cells.
	 * 
	 * @param cellIndex the index at which the td will be inserted.
	 */
	protected abstract void insertCellStructure(int cellIndex);
	
	/**
	 * Removes a cell from the panel, including any child widets.
	 * 
	 * @param cellIndex the index of the cell
	 * @throws IndexOutOfBoundsException if the cell specified by
	 *             <code>cellIndex</code> does not exist
	 */
	public void removeCell(int cellIndex)
	{
		GwtUtil.rangeCheck(m_cellMap, cellIndex, false);
		
		clearCell(cellIndex);
		removeCellStructure(cellIndex);
		m_cellMap.remove(cellIndex);
	}
	
	/**
	 * Removes all widgets and child elements from the cell
	 * 
	 * @param cellIndex the index of the cell
	 * @throws IndexOutOfBoundsException if cellIndex does not specify a valid
	 *             cell
	 */
	public void clearCell(int cellIndex)
	{
		GwtUtil.rangeCheck(m_cellMap, cellIndex, false);
		
		Vector cellWidgets = (Vector) m_cellMap.get(cellIndex);
		if (cellWidgets != null)
		{
			while (! cellWidgets.isEmpty())
			{
				removeWidget((Widget) cellWidgets.lastElement(), cellIndex);
			}
		}
		DOM.setInnerHTML(getCellElement(cellIndex), "");
		m_cellMap.set(cellIndex, null);
	}
	
	/**
	 * A <em>template method</em> which removes a td from the underlying
	 * table. Cells with indexes greater than <code>cellIndex</code> will be
	 * shifted down by 1. Implementors may assume <code>cellIndex</code> is
	 * greater than 0 and less than the number of cells.
	 * 
	 * @param cellIndex the index at which the td will be inserted.
	 */
	protected abstract void removeCellStructure(int cellIndex);
	
	/**
	 * Removes all cells and child widgets from the panel. 
	 */
	public void clear()
	{
		while(! m_cellMap.isEmpty())
		{
			removeCell(m_cellMap.size() - 1);
		}
	}
	
	/**
	 * Gets the number of child widgets added to the panel
	 * 
	 * @return the number of child widgets
	 */
	public int getWidgetCount()
	{
		return getChildren().size();
	}
	
	/**
	 * Gets a at the specified index in the specified cell. <code>wIndex</code>
	 * does not include non-widget child elements.
	 * 
	 * @param cellIndex the index of the cell, starting with 0
	 * @param wIndex the index of the widget in the specified cell, starting at
	 *            0
	 * @return the widget
	 * @throws IndexOutOfBoundsException if the cell specified by
	 *             <code>cellIndex</code> does not exist
	 * @throws IndexOutOfBoundsException if the widget specified by
	 *             <code>wIndex</code> does not exist
	 */
	public Widget getWidgetAt(int cellIndex, int wIndex)
	{
		GwtUtil.rangeCheck(m_cellMap, cellIndex, false);
		
		Vector childWidgets = (Vector) m_cellMap.get(cellIndex);
		
		if (childWidgets == null)
			throw new IndexOutOfBoundsException(Integer.toString(wIndex));
		
		GwtUtil.rangeCheck(childWidgets, wIndex, false);
		
		return (Widget) childWidgets.get(wIndex);
	}
	
	/**
	 * Get the index of the cell which contains the specified widget.
	 * 
	 * @param w a child widget
	 * @return the index or <code>-1</code> if the widget is not a child of
	 *         this panel
	 */
	public int getCellIndexOf(Widget w)
	{
		int result = -1;
		int cell = 0, size = m_cellMap.size();
		while (cell < size)
		{
			Vector cellWidgets = (Vector) m_cellMap.get(cell);
			if (cellWidgets != null && cellWidgets.contains(w))
			{
				result = cell;
				break;
			}
			cell++;
		}
		return result;
	}
	
	/**
	 * Creates a new cell and appends to it the specified widget. 
	 * 
	 * @param w a widget
	 */
	public void add(Widget w)
	{
		addWidget(w, true);
	}
	
	/**
	 * Adds a widget to the panel, optionally creating a new cell. 
	 * 
	 * @param w a widget
	 * @param newCell <code>true</code> to create a new cell,
	 *            <code>false</code> to append to the last cell
	 * @throws IndexOutOfBoundsException if <code>newCell</code> is
	 *             <code>false</code> and no cells exist
	 */
	public void addWidget(Widget w, boolean newCell)
	{
		/*
		 * if w is a child of this panel and the only widget in a cell the cell
		 * will be removed
		 */
		w.removeFromParent();
		
		if (newCell)
		{
			insertCell(m_cellMap.size());
		}
		addWidgetTo(w, m_cellMap.size() - 1);
	}
	
	/**
	 * Adds a widget to the specified cell. <code>w</code> will be appended
	 * after any other widgets in the cell.
	 * 
	 * @param w a widget
	 * @param cellIndex the index of the cell
	 * @throws IndexOutOfBoundsException if the cell specified by
	 *             <code>cellIndex</code> does not exist
	 */
	public void addWidgetTo(Widget w, int cellIndex)
	{
		GwtUtil.rangeCheck(m_cellMap, cellIndex, false);
		
		Vector cellWidgets = (Vector) m_cellMap.get(cellIndex);
		int wIndex = (cellWidgets != null) ? cellWidgets.size() : 0;
		insertWidgetAt(w, cellIndex, wIndex);
	}
	
	/**
	 * Inserts a new cell at the specified index and appends the widget to the
	 * cell.
	 * 
	 * @param w a widget
	 * @param cellIndex the index of the cell
	 * @throws IndexOutOfBoundsException if <code>cellIndex</code> is less
	 *             than 0 or greater than the number of cells
	 */
	public void insert(Widget w, int cellIndex)
	{
		/*
		 * if w is a child of this panel and the only widget in a cell the cell
		 * will be removed
		 */
		w.removeFromParent();
		
		insertCell(cellIndex);
		insertWidgetAt(w, cellIndex, 0);
	}
	
	/**
	 * Inserts a widget into a an existing cell. Any widgets with indexes
	 * greater than or equal to <code>wIndex</code> will be shifted over.
	 * 
	 * @param w a widget
	 * @param cellIndex the index of the cell
	 * @param wIndex the index of the widget before which <code>w</code> will
	 *            be inserted
	 * @throws IndexOutOfBoundsException if the cell specified by
	 *             <code>cellIndex</code> does not exist
	 * @throws IndexOutOfBoundsException if <code>wIndex</code> is less than 0
	 *             or greater than the number of widgets in the specified cell
	 */
	public void insertWidgetAt(Widget w, int cellIndex, int wIndex)
	{
		/*
		 * The cell will be removed if w is a child of this panel and w is the
		 * only widget in the cell
		 */
		w.removeFromParent();
		
		GwtUtil.rangeCheck(m_cellMap, cellIndex, false);
		
		Vector cellWidgets = (Vector) m_cellMap.get(cellIndex);
		
		if (cellWidgets == null)
		{
			if (wIndex != 0)
				throw new IndexOutOfBoundsException(Integer.toString(wIndex));

			cellWidgets = new Vector();
			m_cellMap.set(cellIndex, cellWidgets); // pre: cellIndex has been inserted
		}
		
		GwtUtil.rangeCheck(cellWidgets, wIndex, true);
		
		cellWidgets.insertElementAt(w, wIndex);
		
		/*
		 * Pass null and append the element manually so that we can specify
		 * insertion order. Also note that the order of the "children"
		 * WidgetCollection is not maintained.
		 */
		insert(w, null, getChildren().size());
		Element td = getCellElement(cellIndex);
		DOM.insertChild(td, w.getElement(), wIndex);
	}
	
	/**
	 * Removes a widget from the panel. 
	 * Automatically removes the cell if it becomes empty. 
	 * 
	 * @param w a child widget
	 * @return false if <code>w</code> is not a child of this panel
	 */
	public boolean remove(Widget w)
	{
		return remove(w, true);
	}
	
	/**
	 * Removes a widget from the panel, optionally removing the cell if it
	 * becomes empty.
	 * 
	 * @param w a child widget
	 * @param removeEmptyCell <code>true</code> to remove the cell if it
	 *            becomes empty
	 * @return false if <code>w</code> is not a child of this panel
	 */
	public boolean remove(Widget w, boolean removeEmptyCell)
	{
		if (! getChildren().contains(w))
			return false;
		
		int cellIndex = getCellIndexOf(w);
		assert cellIndex >= 0;
		
		removeWidget(w, cellIndex);
		Vector childWidgets = (Vector) m_cellMap.get(cellIndex);
		if (removeEmptyCell && childWidgets.size() == 0)
		{
			removeCell(cellIndex);
		}
		return true;
	}
	
	/*
	 * Removes the widget from the cellMap & DOM tree and clears out its
	 * listener
	 */
	private void removeWidget(Widget w, int cellIndex)
	{
		Vector childWidgets = (Vector) m_cellMap.get(cellIndex);
		
		super.remove(w);
		boolean present = (childWidgets).remove(w);
		assert present;
	}
	
	/**
	 * Gets the table <code>td</code> element corresponding to the specified
	 * cell.
	 * 
	 * @param cellIndex the index of the cell
	 * @return the <code>td</code> element of the specified cell
	 * @throws IndexOutOfBoundsException if <code>cellIndex</code> does not
	 *             specify an existing cell
	 */
	public abstract Element getCellElement(int cellIndex);
	
	/**
	 * Gets the style name(s) for the cell specified by
	 * <code>cellIndex</code>.
	 * 
	 * @return the CSS class name(s) (space delimited)
	 * @param cellIndex the index of the cell
	 * @throws IndexOutOfBoundsException if the cell specified by
	 *             <code>cellIndex</code> does not exist
	 */
	public String getCellStyleName(int cellIndex)
	{
		GwtUtil.rangeCheck(m_cellMap, cellIndex, false);
		
		return DOM.getAttribute(getCellElement(cellIndex), "className");
	}
	
	/**
	 * Adds a style name to the last cell. 
	 * 
	 * @param styleName a CSS class name
	 * @throws IllegalStateException if no cells exist
	 */
	public void addCellStyleName(String styleName)
	{
		if (getCellCount() == 0)
			throw new IllegalStateException();
		
		addCellStyleName(getCellCount() - 1, styleName);
	}
	
	/**
	 * Adds a style name to the <code>class</code> attribute of the cell
	 * specified by the <code>cellIndex</code>.
	 * 
	 * @param cellIndex the index of a cell
	 * @param styleName a CSS class name
	 * @throws IndexOutOfBoundsException if the cell specified by
	 *             <code>cellIndex</code> does not exist
	 */
	public void addCellStyleName(int cellIndex, String styleName)
	{
		GwtUtil.rangeCheck(m_cellMap, cellIndex, false);
		
		setStyleName(getCellElement(cellIndex), styleName, true);
	}
	
	/**
	 * Sets the style name for the last cell. Other style names will be
	 * overwritten.
	 * 
	 * @param styleName a CSS class name
	 * @throws IllegalStateException if no cells exist
	 */
	public void setCellStyleName(String styleName)
	{
		if (m_cellMap.size() == 0)
			throw new IllegalStateException();
		
		setCellStyleName(getCellCount() - 1, styleName);
	}
	
	/**
	 * Sets the style name for the cell specified by
	 * <code>cellIndex</code>. Other style names will be
	 * overwritten.
	 * 
	 * @param cellIndex the index of the cell
	 * @param styleName a CSS class name
	 * @throws IndexOutOfBoundsException if the cell specified by
	 *             <code>cellIndex</code> does not exist
	 */
	public void setCellStyleName(int cellIndex, String styleName)
	{
		GwtUtil.rangeCheck(m_cellMap, cellIndex, false);
		
		DOM.setAttribute(getCellElement(cellIndex), "className", styleName);
	}
	
	/**
	 * Sets the width of the last cell. 
	 * 
	 * @param width a CSS measurement
	 */
	public void setCellWidth(String width)
	{
		if (m_cellMap.size() == 0)
			throw new IllegalStateException();
		
		setCellWidth(getCellCount() - 1, width);
	}
	
	/**
	 * Sets the width of the specified cell. 
	 * 
	 * @param cellIndex the index of a cell
	 * @param width a CSS measurement
	 * @throws IndexOutOfBoundsException if the cell specified by
	 *             <code>cellIndex</code> does not exist
	 */
	public void setCellWidth(int cellIndex, String width)
	{
		GwtUtil.rangeCheck(m_cellMap, cellIndex, false);
		
		DOM.setAttribute(getCellElement(cellIndex), "width", width);
	}

	/**
	 * Sets the height of the last cell. 
	 * 
	 * @param height a CSS measurement
	 */
	public void setCellHeight(String height)
	{
		if (m_cellMap.size() == 0)
			throw new IllegalStateException();
		
		setCellHeight(getCellCount() - 1, height);
	}
	
	/**
	 * Sets the height of the specified cell. 
	 * 
	 * @param cellIndex the index of a cell
	 * @param height a CSS measurement
	 * @throws IndexOutOfBoundsException if the cell specified by
	 *             <code>cellIndex</code> does not exist
	 */
	public void setCellHeight(int cellIndex, String height)
	{
		GwtUtil.rangeCheck(m_cellMap, cellIndex, false);
		
		DOM.setAttribute(getCellElement(cellIndex), "height", height);
	}
	
	/**
	 * Sets the horizontal alignment of the last cell. 
	 * 
	 * @param hAlign a constant representing left, center or right alignment
	 * @see HasAlignment
	 */
	public void setCellHorizontalAlignment(HorizontalAlignmentConstant hAlign)
	{
		if (m_cellMap.size() == 0)
			throw new IllegalStateException();
		
		setCellHorizontalAlignment(getCellCount() - 1, hAlign);
	}
	
	/**
	 * Sets the horizontal alignment of the specified cell. 
	 * 
	 * @param cellIndex the index of a cell
	 * @param hAlign a constant representing left, center or right alignment
	 * @throws IndexOutOfBoundsException if the cell specified by
	 *             <code>cellIndex</code> does not exist
	 * @see HasAlignment
	 */
	public void setCellHorizontalAlignment(int cellIndex, HorizontalAlignmentConstant hAlign)
	{
		GwtUtil.rangeCheck(m_cellMap, cellIndex, false);
		
		DOM.setAttribute(getCellElement(cellIndex), "align", hAlign.getTextAlignString());
	}
	
	/**
	 * Sets the vertical alignment of the last cell. 
	 * 
	 * @param vAlign a constant representing top, middle or bottom alignment
	 * @see HasAlignment
	 */
	public void setCellVerticalAlignment(VerticalAlignmentConstant vAlign)
	{
		if (m_cellMap.size() == 0)
			throw new IllegalStateException();
		
		setCellVerticalAlignment(getCellCount() - 1, vAlign);
	}
	
	/**
	 * Sets the vertical alignment of the specified cell. 
	 * 
	 * @param cellIndex the index of a cell
	 * @param vAlign a constant representing top, middle or bottom alignment
	 * @throws IndexOutOfBoundsException if the cell specified by
	 *             <code>cellIndex</code> does not exist
	 * @see HasAlignment
	 */
	public void setCellVerticalAlignment(int cellIndex, VerticalAlignmentConstant vAlign)
	{
		GwtUtil.rangeCheck(m_cellMap, cellIndex, false);
		
		DOM.setStyleAttribute(getCellElement(cellIndex), "verticalAlign", vAlign.getVerticalAlignString());
	}
	
	/**
	 * Gets the default horizontal alignment for newly created cells.
	 * 
	 * @return an alignment constant or null
	 * @see HasAlignment
	 */
	public HorizontalAlignmentConstant getHorizontalAlignment()
	{
		return m_horizontalAlignment;
	}
	
	/**
	 * Sets the default horizontal alignment for newly created cells.
	 * 
	 * @param align an alignment constant or null
	 * @see HasAlignment
	 */
	public void setHorizontalAlignment(HorizontalAlignmentConstant align)
	{
		m_horizontalAlignment = align;
	}
	
	/**
	 * Gets the default vertical alignment for newly created cells.
	 * 
	 * @return an alignment constant or null
	 * @see HasAlignment
	 */
	public VerticalAlignmentConstant getVerticalAlignment()
	{
		return m_verticalAlignment;
	}
	
	/**
	 * Sets the default vertical alignment for newly created cells.
	 * 
	 * @param align an alignment constant or null
	 * @see HasAlignment
	 */
	public void setVerticalAlignment(VerticalAlignmentConstant align)
	{
		m_verticalAlignment = align;
	}
}
