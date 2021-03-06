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

/**
 * Defines methods for converting model values to displayable Strings and vice versa.
 *  
 * @see <a href="http://developer.apple.com/documentation/Cocoa/Reference/Foundation/Classes/NSFormatter_Class/Reference/Reference.html">AppKit NSFormatter</a>
 */
public interface StringFormatter
{
	 /*
	  * TODO: add getAttributedString() or getRenderingHints() to enable, for
	  * example, negative values to be rendered in red?
	  */
	 
	 /**
	  * Get the String value to be displayed in a view cell. Strings typically
	  * <em>cannot</em> contain html markup.
	  * 
	  * @param modelElement the model element being displayed
	  * @return the String representation of <code>modelElement</code>
	  */
	String getString(Object modelElement);

    /**
     * Get the String value to be displayed in the cell editor. E.g. a financial
     * application may add a currency symbol for viewing, but the cell editor
     * uses numeric values only.
     * 
     * @param modelElement
     * @return a String, or <code>null</code> to use the
     *         {@link #getString(Object)} value
     */
	String getEditingString(Object modelElement);
}
