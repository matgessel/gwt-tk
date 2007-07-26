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

import java.util.Vector;

import asquare.gwt.sb.client.util.Properties;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * A basic cell renderer. By default, the model element for the cell is
 * converted to a String via {@link String#valueOf(Object)} and written to the
 * cell. The default conversion can be overriden by setting a custom formatter.
 * @see #setFormatter(StringFormatter)
 */
public class CellRendererDefault implements CellRenderer
{
	private final Vector m_styleAttributeNames = new Vector();
	private final Vector m_styleAttributeValues = new Vector();
//	private final HashMap m_styleAttributes = new HashMap();
	private String m_elementStyleName;
	private StringFormatter m_formatter;
	
	public CellRendererDefault()
	{
		this(null, null);
	}
	
	public CellRendererDefault(String elementBaseStyleName)
	{
		this(elementBaseStyleName, null);
	}
	
	public CellRendererDefault(String elementBaseStyleName, StringFormatter formatter)
	{
		m_elementStyleName = elementBaseStyleName;
		setFormatter((formatter != null) ? formatter : createFormatter());
	}
	
	/**
	 * Template method to create the formatter. 
	 * 
	 * @return <code>new StringFormatterDefault()</code>
	 */
	protected StringFormatter createFormatter()
	{
		return new StringFormatterDefault();
	}
	
	public String getElementBaseStyleName()
	{
		return m_elementStyleName;
	}
	
	public void setElementBaseStyleName(String styleName)
	{
		m_elementStyleName = styleName;
	}

	public StringFormatter getFormatter()
	{
		return m_formatter;
	}

	public void setFormatter(StringFormatter formatter)
	{
		m_formatter = formatter;
	}
	
	public void setCSSProperty(String name, int value)
	{
		setCSSProperty(name, new Integer(value));
	}
	
	public void setCSSProperty(String name, String value)
	{
		setCSSProperty(name, (Object) value);
	}
	
	/**
	 * @param value a Integer or a String
	 */
	private void setCSSProperty(String name, Object value)
	{
		if (name == null)
			throw new IllegalArgumentException();
		
		int index = m_styleAttributeNames.indexOf(name);
		if (value == null || value.equals(""))
		{
			m_styleAttributeNames.remove(index);
			m_styleAttributeValues.remove(index);
		}
		else
		{
			if (index == -1)
			{
				m_styleAttributeNames.add(name);
				m_styleAttributeValues.add(value);
			}
			else
			{
				m_styleAttributeValues.setElementAt(value, index);
			}
		}
	}
	
	public void renderCell(Element viewElement, Object modelElement, Properties properties)
	{
		prepareElement(viewElement, modelElement, properties);
		renderContent(viewElement, modelElement, properties);
	}
	
	public void prepareElement(Element viewElement, Object modelElement, Properties properties)
	{
		// set cell class name(s)
		String styleName = buildStyleName(properties);
		
		if (! styleName.equals(DOM.getAttribute(viewElement, "className")))
		{
			DOM.setAttribute(viewElement, "className", styleName);
		}
		
		// set cell CSS properties
		for (int i = 0, size = m_styleAttributeNames.size(); i < size; i++)
		{
			Object value = m_styleAttributeValues.get(i);
			if (value == null || value instanceof String)
			{
				DOM.setStyleAttribute(viewElement, (String) m_styleAttributeNames.get(i), (String) m_styleAttributeValues.get(i));
			}
			else if (value instanceof Integer)
			{
				DOM.setIntStyleAttribute(viewElement, (String) m_styleAttributeNames.get(i), ((Integer) m_styleAttributeValues.get(i)).intValue());
			}
			else
			{
				assert false;
			}
		}
	}
	
	protected String buildStyleName(Properties properties)
	{
		String baseStyleName = getElementBaseStyleName();
		String result = baseStyleName != null ? baseStyleName : "";
		if (properties != null)
		{
			result = appendDependantStyle(result, StyleNames.FIRST, properties.getBoolean(PROPERTY_FIRST));
			result = appendDependantStyle(result, StyleNames.LAST, properties.getBoolean(PROPERTY_LAST));
			result = appendDependantStyle(result, StyleNames.SELECTED, properties.getBoolean(PROPERTY_SELECTED));
			result = appendDependantStyle(result, StyleNames.HOVER, properties.getBoolean(PROPERTY_HOVER));
			result = appendDependantStyle(result, StyleNames.DISABLED, properties.getBoolean(PROPERTY_DISABLED));
		}
		return result;
	}
	
	protected String appendDependantStyle(String string, String dependentStyleName, boolean add)
	{
		if (add)
		{
			string += ' ' + (m_elementStyleName == null ? "gwt-nostyle" : m_elementStyleName) + '-' + dependentStyleName;
		}
		return string;
	}
	
	public void renderContent(Element viewElement, Object modelElement, Properties properties)
	{
		String newHtml = m_formatter.getHtml(modelElement);
		if (newHtml != null)
		{
			// TODO does this check work? (e.g. auto-generated tbody & td text nodes, possible platform formatting differences)
			if (! DOM.getInnerHTML(viewElement).equals(newHtml))
			{
				DOM.setInnerHTML(viewElement, newHtml);
			}
		}
		else
		{
			String newText = String.valueOf(modelElement);
			if (! DOM.getInnerText(viewElement).equals(newText))
			{
				DOM.setInnerText(viewElement, newText);
			}
		}
	}
}
