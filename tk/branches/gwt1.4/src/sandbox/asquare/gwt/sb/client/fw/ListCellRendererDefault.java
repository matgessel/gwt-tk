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

public class ListCellRendererDefault extends CellRendererDefault implements ListCellRenderer
{
	public ListCellRendererDefault()
	{
		this(null, null);
	}
	
	public ListCellRendererDefault(String elementBaseStyleName)
	{
		this(elementBaseStyleName, null);
	}
	
	public ListCellRendererDefault(String elementBaseStyleName, StringFormatter formatter)
	{
		super(elementBaseStyleName != null ? elementBaseStyleName : ListView.STYLENAME_LIST_ITEM, formatter);
	}
	
	protected String buildStyleName(Object modelElement, Properties properties)
	{
		String result = super.buildStyleName(modelElement, properties);
		if (properties != null)
		{
			result = appendDependantStyle(result, StyleNames.FIRST, properties.getBoolean(PROPERTY_FIRST));
			result = appendDependantStyle(result, StyleNames.LAST, properties.getBoolean(PROPERTY_LAST));
			result = appendDependantStyle(result, StyleNames.ODD, properties.getBoolean(PROPERTY_ODD));
			result = appendDependantStyle(result, StyleNames.EVEN, properties.getBoolean(PROPERTY_EVEN));
		}
		return result;
	}
}
