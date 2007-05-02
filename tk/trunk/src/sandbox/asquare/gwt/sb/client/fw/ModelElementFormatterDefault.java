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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

public class ModelElementFormatterDefault implements ModelElementFormatter
{
	private String m_elementStyleName;
	
	public ModelElementFormatterDefault(String elementStyleName)
	{
		m_elementStyleName = elementStyleName;
	}
	
	public String getElementStyleName()
	{
		return m_elementStyleName;
	}
	
	public void setElementStyleName(String styleName)
	{
		m_elementStyleName = styleName;
	}
	
	public void formatCell(Element viewElement, Object modelElement, Properties properties)
	{
		DOM.setAttribute(viewElement, "className", buildStyleName(properties));
		String newText = String.valueOf(modelElement);
		if (! DOM.getInnerText(viewElement).equals(newText))
		{
			DOM.setInnerText(viewElement, newText);
		}
	}
	
	protected String buildStyleName(Properties properties)
	{
		String result = (m_elementStyleName != null) ? m_elementStyleName : "";
		if (properties != null)
		{
			if (properties.getBoolean(PROPERTY_SELECTED))
			{
				result +=" " + ModelElementFormatter.STYLENAME_SELECTED;
			}
			if (properties.getBoolean(PROPERTY_ACTIVE))
			{
				result +=" " + ModelElementFormatter.STYLENAME_ACTIVE;
			}
			if (properties.getBoolean(PROPERTY_DISABLED))
			{
				result +=" " + ModelElementFormatter.STYLENAME_DISABLED;
			}
		}
		return result;
	}
}
