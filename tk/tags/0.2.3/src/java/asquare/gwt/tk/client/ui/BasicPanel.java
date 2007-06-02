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

import asquare.gwt.tk.client.util.DomUtil;
import asquare.gwt.tk.client.util.GwtUtil;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.IndexedPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A barebones {@link com.google.gwt.user.client.ui.ComplexPanel ComplexPanel}
 * which allows specification of the root element and <code>display</code> style of
 * added children. 
 * 
 * @see <a
 *      href="http://www.w3.org/TR/CSS21/visuren.html#inline-formatting">Inline
 *      formatting context</a>
 */
public class BasicPanel extends CComplexPanel implements IndexedPanel
{
	/**
	 * The default element (<code>DIV</code>), if no element is specified.
	 */
	public static final String ELEMENT_DEFAULT = "div";
	
	/**
	 * Do not specify a display style for added children. 
	 */
	public static final String DISPLAY_DEFAULT = null;
	
	/**
	 * Specifies <code>display:block</code> style for added children. 
	 */
	public static final String DISPLAY_BLOCK = "block";
	
	/**
	 * Specifies <code>display:inline</code> style for added children. 
	 */
	public static final String DISPLAY_INLINE = "inline";
	
	/**
	 * Specifies <code>display:inherit</code> style for added children.
	 * <p><em>By default the display style is not inherited.</em></p> 
	 */
	public static final String DISPLAY_INHERIT = "inherit";
	
	private String m_childrenDisplay = null;
	
	/**
	 * Constructs a panel based on a <code>DIV</code> element and default children
	 * display property.
	 */
	public BasicPanel()
	{
		this(ELEMENT_DEFAULT, DISPLAY_DEFAULT);
	}
	
	/**
	 * Constructs a panel based on the specified element and the default display
	 * style.
	 * 
	 * @param element a DOM element
	 */
	public BasicPanel(Element element)
	{
		this(element, DISPLAY_DEFAULT);
	}
	
	/**
	 * Constructs a panel based on the specified element and the default display
	 * style.
	 * 
	 * @param element a html element tag
	 */
	public BasicPanel(String element)
	{
		this(element, DISPLAY_DEFAULT);
	}
	
	/**
	 * Constructs a panel based on the specified element and display style.
	 * 
	 * @param element a html element tag
	 * @param childrenDisplay a css <code>display</code> style value to apply
	 *            to added children
	 */
	public BasicPanel(String element, String childrenDisplay)
	{
		this(DOM.createElement(element), childrenDisplay);
	}
	
	/**
	 * Constructs a panel based on the specified element and display style.
	 * 
	 * @param element a DOM element
	 * @param childrenDisplay a css <code>display</code> style value to apply
	 *            to added children
	 */
	public BasicPanel(Element element, String childrenDisplay)
	{
		super(element);
		m_childrenDisplay = childrenDisplay;
	}
	
	/**
	 * Gets the display style value which will be applied to added children. 
	 * 
	 * @return a css <code>display</code> style value or null if none specified
	 */
	public String getChildrenDisplay()
	{
		return m_childrenDisplay;
	}
	
	/**
	 * Sets the display style value which will be applied to added children. 
	 * 
	 * @param childrenDisplay a css <code>display</code> style value to apply
	 *            to added children
	 */
	public void setChildrenDisplay(String childrenDisplay)
	{
		m_childrenDisplay = childrenDisplay;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.IndexedPanel#getWidget(int)
	 */
	public Widget getWidget(int index)
	{
		return getChildren().get(index);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.IndexedPanel#getWidgetIndex(com.google.gwt.user.client.ui.Widget)
	 */
	public int getWidgetIndex(Widget child)
	{
		return getChildren().indexOf(child);
	}
	
	/**
	 * Adds a widget to this panel. If a display style has been specified it will
	 * be applied to the child.
	 */
	public void add(Widget w)
	{
		insert(w, getWidgetCount());
	}
	
	/**
	 * Inserts a widget at the specified index. 
	 * 
	 * @param w a widget
	 * @param beforeIndex the index before which <code>w</code> will be
	 *            inserted
	 * @throws IndexOutOfBoundsException if <code>beforeIndex</code> is less
	 *             than 0 or greater than the current widget count
	 */
	public void insert(Widget w, int beforeIndex)
	{
		GwtUtil.rangeCheck(0, getChildren().size(), beforeIndex, true);
		
		if (m_childrenDisplay != null)
		{
			DomUtil.setStyleAttribute(w, "display", m_childrenDisplay);
		}
		// pass null and insert manually so we can control DOM element order
		insert(w, null, beforeIndex);
		DOM.insertChild(getElement(), w.getElement(), beforeIndex);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.IndexedPanel#remove(int)
	 */
	public boolean remove(int index)
	{
		if (index < 0 || index >= getWidgetCount())
			return false;
		
		return remove(getWidget(index));
	}
	
	/**
	 * Get the number of widgets in this panel. 
	 */
	public int getWidgetCount()
	{
		return getChildren().size();
	}
	
	/**
	 * Sets a unique id for referencing this specific panel. 
	 * 
	 * @param id a unique id
	 * @see DomUtil#setId(com.google.gwt.user.client.ui.UIObject, String)
	 */
	public void setId(String id)
	{
		DomUtil.setId(this, id);
	}
}
