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

/**
 * This class takes a model element as input and renders the contents of a view 
 * cell.
 * 
 * @see <a href="http://developer.apple.com/documentation/Cocoa/Reference/ApplicationKit/Classes/NSCell_Class/index.html">AppKit NSCell</a>
 * @see <a href="http://java.sun.com/j2se/1.4.2/docs/api/javax/swing/ListCellRenderer.html">Swing ListCellRenderer</a>
 */
public interface CellRenderer
{
	public static final String PROPERTY_FIRST = "first";
	public static final String PROPERTY_LAST = "last";
	public static final String PROPERTY_SELECTED = "selected";
	public static final String PROPERTY_HOVER = "hover";
	public static final String PROPERTY_ACTIVE = "active";
	public static final String PROPERTY_DISABLED = "disabled";
	
	/**
	 * This styles cell element and creates the <em>contents</em> of a cell.
	 * 
	 * @param viewElement the view cell element
	 * @param modelElement an Object representing a value to display
	 * @param properties a dictionary of properties which the formatter uses to
	 *            style the view, or <code>null</code>
	 */
	void renderCell(Element viewElement, Object modelElement, Properties properties);
	
	/**
	 * Styles the cell element. 
	 * 
	 * @param viewElement the view cell element
	 * @param modelElement an Object representing a value to display
	 * @param properties a dictionary of properties which the formatter uses to
	 *            style the view, or <code>null</code>
	 */
	void prepareElement(Element viewElement, Object modelElement, Properties properties);
	
	/**
	 * Creates the cell contents. This is typically implemented by setting the
	 * inner text/html on the cell's element.
	 * 
	 * @param viewElement the view cell element
	 * @param modelElement an Object representing a value to display
	 * @param properties a dictionary of properties which the formatter uses to
	 *            style the view, or <code>null</code>
	 */
	void renderContent(Element viewElement, Object modelElement, Properties properties);
}
