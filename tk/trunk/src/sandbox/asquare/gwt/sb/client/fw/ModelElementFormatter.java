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

public interface ModelElementFormatter
{
	public static final String STYLENAME_SELECTED = "Selected";
	public static final String STYLENAME_ACTIVE = "Active";
	public static final String STYLENAME_DISABLED = "Disabled";
	
	public static final String PROPERTY_SELECTED = "selected";
	public static final String PROPERTY_ACTIVE = "active";
	public static final String PROPERTY_DISABLED = "disabled";
	
	/**
	 * @param viewElement the element which displays the model element
	 * @param modelElement an Object representing the content of the view
	 * @param properties an dictionary of properties which the formatter uses to
	 *            style the view, or <code>null</code>
	 */
	void formatCell(Element viewElement, Object modelElement, Properties properties);
}
