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

import java.util.ArrayList;

import asquare.gwt.sb.client.util.Properties;
import asquare.gwt.tk.client.util.GwtUtil;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * A basic cell renderer. By default, the model element for the cell is
 * converted to a String via {@link String#valueOf(Object)} and written to the
 * cell. The default conversion can be overriden by setting a custom formatter.
 * @see #setFormatter(StringFormatter)
 */
public class CellRendererDefault implements CellRendererString
{
	private StyleRuleCollection m_styleRules = null;
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
		if (formatter == null)
			throw new NullPointerException();
		
		m_formatter = formatter;
	}
	
	public void setCSSProperty(String cssProperty, int cssValue)
	{
		setCSSRule(null, cssProperty, cssValue);
	}
	
	public void setCSSProperty(String cssProperty, String cssValue)
	{
		setCSSRule(null, cssProperty, cssValue);
	}
	
	/**
	 * Set a rule to be triggered when renderer boolean property is
	 * <code>true</code>. If the renderer property is not specified the rule
	 * will be applied to all cells.
	 * 
	 * @param propertyName a <em>boolean</em> renderer property name, or
	 *            <code>null</code>
	 * @param cssProperty a css style property name
	 * @param cssValue a css style value, or <code>null</code> to clear the
	 *            previous rule
	 * @throws IllegalArgumentException if <code>cssProperty</code> is
	 *             <code>null</code> or an empty String
	 * @throws IllegalArgumentException if <code>cssValue</code> is
	 *             <code>null</code> or an empty String
	 */
	public void setCSSRule(String propertyName, String cssProperty, int cssValue)
	{
		getStyleRules().setCSSRule(propertyName, cssProperty, cssValue);
	}
	
	public void setCSSRule(String propertyName, String cssProperty, String cssValue)
	{
		getStyleRules().setCSSRule(propertyName, cssProperty, cssValue);
	}
	
	public void clearCssRule(String propertyName, String cssProperty)
	{
		if (m_styleRules != null)
		{
			m_styleRules.clearCssRule(propertyName, cssProperty);
		}
	}
	
	private StyleRuleCollection getStyleRules()
	{
		if (m_styleRules == null)
		{
			m_styleRules = new StyleRuleCollection();
		}
		return m_styleRules;
	}
	
	public void renderCell(Element viewElement, Object modelElement, Properties properties)
	{
		prepareElement(viewElement, modelElement, properties);
		renderContent(viewElement, modelElement, properties);
	}
	
	public void prepareElement(Element viewElement, Object modelElement, Properties properties)
	{
		// set cell class name(s)
		String styleName = buildStyleName(modelElement, properties);
		
		if (! styleName.equals(DOM.getElementProperty(viewElement, "className")))
		{
			DOM.setElementProperty(viewElement, "className", styleName);
		}
		
		// set cell CSS properties
		if (m_styleRules != null)
		{
			for (int i = 0, size = m_styleRules.getSize(); i < size; i++)
			{
				String property = m_styleRules.getRendererProperty(i);
				if (property == null || properties.getBoolean(property))
				{
					Object value = m_styleRules.getCssValue(i);
					if (value instanceof String)
					{
						DOM.setStyleAttribute(viewElement, m_styleRules.getCssProperty(i), (String) value);
					}
					else if (value instanceof Integer)
					{
						DOM.setIntStyleAttribute(viewElement, m_styleRules.getCssProperty(i), ((Integer) value).intValue());
					}
					else
					{
						assert false;
					}
				}
				else
				{
					DOM.setStyleAttribute(viewElement, m_styleRules.getCssProperty(i), "");
				}
			}
		}
	}
	
	protected String buildStyleName(Object modelElement, Properties properties)
	{
		String baseStyleName = getElementBaseStyleName();
		String result = baseStyleName != null ? baseStyleName : "";
		if (properties != null)
		{
			result = appendDependantStyle(result, StyleNames.SELECTED, properties.getBoolean(PROPERTY_SELECTED));
			result = appendDependantStyle(result, StyleNames.HOVER, properties.getBoolean(PROPERTY_HOVER));
			result = appendDependantStyle(result, StyleNames.DISABLED, properties.getBoolean(PROPERTY_DISABLED));
			result = appendDependantStyle(result, StyleNames.ACTIVE, properties.getBoolean(PROPERTY_ACTIVE));
		}
		return result;
	}
	
	protected String appendDependantStyle(String styleIn, String dependentStyleName)
	{
		return appendDependantStyle(styleIn, dependentStyleName, true);
	}
	
	protected String appendDependantStyle(String styleIn, String dependentStyleName, boolean add)
	{
		if (add)
		{
			styleIn += ' ' + (m_elementStyleName == null ? "gwt-nostyle" : m_elementStyleName) + '-' + dependentStyleName;
		}
		return styleIn;
	}
	
	public void renderContent(Element viewElement, Object modelElement, Properties properties)
	{
		final String valueString = getValueString(modelElement, properties);
		final String cellString = getCellString(valueString, modelElement, properties);
		DOM.setInnerHTML(viewElement, cellString);
	}
	
	/**
	 * Template method for decorating text returned by the formatter.
	 * Implementors can use this method to create complex cells.
	 * <p>
	 * This implementation simply returns the input string.
	 * 
	 * @param valueString the string provided by the formatter
	 * @return an HTML snippet describing the entire content of the cell
	 */
	protected String getCellString(String valueString, Object modelElement, Properties properties)
	{
		return valueString;
	}
	
	/**
	 * Calls the formatter.  
	 * 
	 * @return an HTML snippet representing <code>modelElement</code>
	 */
	protected String getValueString(Object modelElement, Properties properties)
	{
		String result = m_formatter.getString(modelElement);
		return (result != null && result.length() > 0) ? result : "&nbsp;";
	}
	
	private static class StyleRuleCollection
	{
		private final ArrayList<String> m_rendererProperties = new ArrayList<String>();
		private final ArrayList<String> m_cssProperties = new ArrayList<String>();
		private final ArrayList<Object> m_values = new ArrayList<Object>();
		
		public String getRendererProperty(int index)
		{
			return m_rendererProperties.get(index);
		}
		
		public String getCssProperty(int index)
		{
			return m_cssProperties.get(index);
		}
		
		/**
		 * @return an Integer or a String
		 */
		public Object getCssValue(int index)
		{
			return m_values.get(index);
		}
		
		public int getSize()
		{
			return m_cssProperties.size();
		}
		
		public void setCSSRule(String propertyName, String cssProperty, int cssValue)
		{
			setCSSRule(propertyName, cssProperty, new Integer(cssValue));
		}
		
		public void setCSSRule(String propertyName, String cssProperty, String cssValue)
		{
			setCSSRule(propertyName, cssProperty, (Object) cssValue);
		}
		
		/**
		 * @param cssValue an Integer or a String or <code>null</code>
		 */
		private void setCSSRule(String propertyName, String cssProperty, Object cssValue)
		{
			if (cssProperty == null || cssProperty.length() == 0)
				throw new IllegalArgumentException();
			
			if (cssValue == null || "".equals(cssValue))
				throw new IllegalArgumentException();
			
			int index = getRuleIndex(propertyName, cssProperty);
			if (index == -1)
			{
				// add new rule
				m_rendererProperties.add(propertyName);
				m_cssProperties.add(cssProperty);
				m_values.add(cssValue);
			}
			else
			{
				// update existing rule
				m_values.set(index, cssValue);
			}
		}
		
		public void clearCssRule(String propertyName, String cssProperty)
		{
			if (cssProperty == null || cssProperty.length() == 0)
				throw new IllegalArgumentException();
			
			int index = getRuleIndex(propertyName, cssProperty);
			if (index != -1)
			{
				// clear existing rule
				m_rendererProperties.remove(index);
				m_cssProperties.remove(index);
				m_values.remove(index);
			}
		}
		
		private int getRuleIndex(String propertyName, String cssProperty)
		{
			for (int i = 0, size = m_cssProperties.size(); i < size; i++)
			{
				if (cssProperty.equals(m_cssProperties.get(i)))
				{
					if (GwtUtil.equals(propertyName, m_rendererProperties.get(i)))
					{
						return i;
					}
				}
			}
			return -1;
		}
		
		public String toString()
		{
			String result = "[";
			for (int i = 0, size = getSize(); i < size; i++)
			{
				result += '[' + String.valueOf(m_rendererProperties.get(i)) + ',';
				result += String.valueOf(m_cssProperties.get(i)) + ',';
				result += String.valueOf(m_values.get(i));
				if (i + 1 == size)
				{
					result += ']';
				}
				else
				{
					result += "],";
				}
			}
			return result + ']';
		}
	}
}
