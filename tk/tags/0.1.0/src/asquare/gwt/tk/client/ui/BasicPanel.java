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

import asquare.gwt.tk.client.Util;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A barebones {@link com.google.gwt.user.client.ui.ComplexPanel ComplexPanel}
 * which allows customization of the root element and <code>display</code> style of
 * added children. Similar to FlowPanel, but does not necessarily set
 * <code>style="display: inline;"</code> on it's childern (the inline style
 * prohibits margins between adjacent inline "lines").
 * 
 * <h3>CSS Style Rules</h3>
 * <ul class='css'>
 * <li>.tk-BasicPanel { }</li>
 * </ul>
 * 
 * @see <a
 *      href="http://www.w3.org/TR/CSS21/visuren.html#inline-formatting">Inline
 *      formatting context</a>
 */
public class BasicPanel extends ComplexPanel
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
	 * Constructs a panel with the default properties
	 */
	public BasicPanel()
	{
		this("div", null);
	}
	
	/**
	 * Constructs a panel with the specified element and the default display
	 * style.
	 * 
	 * @param element a html element tag
	 */
	public BasicPanel(String element)
	{
		this(element, null);
	}
	
	/**
	 * Constructs a panel with the specified element and display style.
	 * 
	 * @param element a html element tag
	 * @param childrenDisplay a css <code>display</code> style value to apply
	 *            to added children
	 */
	public BasicPanel(String element, String childrenDisplay)
	{
		setElement(DOM.createElement(element));
		setStyleName("tk-BasicPanel");
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

	/**
	 * Adds a widget to this panel. If a display style has been specified it will
	 * be applied to the child.
	 */
	public boolean add(Widget w)
	{
		if (!super.add(w))
			return false;

		if (m_childrenDisplay != null)
		{
			DOM.setStyleAttribute(w.getElement(), "display", m_childrenDisplay);
		}
		DOM.appendChild(getElement(), w.getElement());
		return true;
	}
	
	/**
	 * Removes a widget from this panel. 
	 */
	public boolean remove(Widget w)
	{
		if (!super.remove(w))
			return false;
		
		DOM.removeChild(getElement(), w.getElement());
		return true;
	}
	
	/**
	 * Sets a unique id for referencing this specific panel. 
	 * 
	 * @param id a unique id
	 * @see Util#setId(com.google.gwt.user.client.ui.UIObject, String)
	 */
	public void setId(String id)
	{
		Util.setId(this, id);
	}
}
