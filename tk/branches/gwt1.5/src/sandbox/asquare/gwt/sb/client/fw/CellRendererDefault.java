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

import asquare.gwt.tk.client.util.*;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.UIObject;

/**
 * A basic cell renderer. By default, the model element for the cell is
 * converted to a String via {@link String#valueOf(Object)} and written to the
 * cell. The default conversion can be overriden by setting a custom formatter.
 * @see #setFormatter(StringFormatter)
 */
public class CellRendererDefault implements CellRendererString
{
	private final RuleCollection m_dependentStyleNames = new RuleCollection();
	
	private RuleCollection m_styleRules = null;
	private String m_elementStyleName;
	private StringFormatter m_formatter;
	private boolean m_escapeMarkup = true;
	
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
		setElementBaseStyleName(elementBaseStyleName);
		setFormatter((formatter != null) ? formatter : createFormatter());
		addDependentStyleNameRule(CellProperties.SELECTED, true, StyleNames.SELECTED);
		addDependentStyleNameRule(CellProperties.DISABLED, true, StyleNames.DISABLED);
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
		m_elementStyleName = (styleName != null) ? styleName : "gwt-nostyle";
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
	
	public boolean isEscapeMarkup()
    {
        return m_escapeMarkup;
    }

    /**
     * Instructs the renderer to escape HTML markup in values to be rendered.
     * Default is <code>true</code>. Specifying <code>false</code> will enable
     * HTML script injection if the data being rendererd is not from a trusted
     * source.
     * 
     * @param escape <code>false</code> to allow HTML markup to be rendered in
     *            cells
     * @see #escapeMarkup(String)
     */
	public void setEscapeMarkup(boolean escape)
    {
        m_escapeMarkup = escape;
    }
	
	/**
	 * Configures the renderer to add the specified dependent style name if
	 * <code>propertyName</code> equals <code>propertyValue</code>.
	 * 
	 * @param propertyName a renderer cell property
	 * @param propertyValue the value to test against
	 * @param styleSuffix a string to form the suffix of the new style name
	 * @throws IllegalArgumentException if <code>propertyName</code> is
	 *             <code>null</code> or an empty String
	 * @throws IllegalArgumentException if <code>styleSuffix</code> is
	 *             <code>null</code> or an empty String
	 * @see CellProperties
	 * @see UIObject#addStyleDependentName(String)
	 */
	public void addDependentStyleNameRule(String propertyName, boolean propertyValue, String styleSuffix)
	{
		m_dependentStyleNames.setRule(propertyName, styleSuffix, propertyValue);
	}
	
	public void addDependentStyleNameRule(String propertyName, int propertyValue, String styleSuffix)
	{
		m_dependentStyleNames.setRule(propertyName, styleSuffix, propertyValue);
	}
	
	public void removeDependentStyleNameRule(String propertyName, String styleSuffix)
	{
		m_dependentStyleNames.clearRule(propertyName, styleSuffix);
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
	 *            <code>null</code> to always apply the rule
	 * @param cssProperty a css style property name
	 * @param cssValue a css style value
	 * @throws IllegalArgumentException if <code>cssProperty</code> is
	 *             <code>null</code> or an empty String
	 * @throws IllegalArgumentException if <code>cssValue</code> is
	 *             <code>null</code> or an empty String
	 */
	public void setCSSRule(String propertyName, String cssProperty, int cssValue)
	{
		getStyleRules().setRule(propertyName, cssProperty, cssValue);
	}
	
	public void setCSSRule(String propertyName, String cssProperty, String cssValue)
	{
		getStyleRules().setRule(propertyName, cssProperty, cssValue);
	}
	
	public void clearCssRule(String propertyName, String cssProperty)
	{
		if (m_styleRules != null)
		{
			m_styleRules.clearRule(propertyName, cssProperty);
		}
	}
	
	private RuleCollection getStyleRules()
	{
		if (m_styleRules == null)
		{
			m_styleRules = new RuleCollection();
		}
		return m_styleRules;
	}
	
	public void renderCell(Element viewElement, Object modelElement, CellProperties properties)
	{
		prepareElement(viewElement, modelElement, properties);
		renderContent(viewElement, modelElement, properties);
	}
	
	public void prepareElement(Element viewElement, Object modelElement, CellProperties properties)
	{
		// set cell class name(s)
		StringBuilder styleName = new StringBuilder();
		buildStyleName(styleName, modelElement, properties);
		
		if (! styleName.equals(DOM.getElementProperty(viewElement, "className")))
		{
			DOM.setElementProperty(viewElement, "className", styleName.toString());
		}
		
		// set cell CSS properties
		if (m_styleRules != null)
		{
			for (int i = 0, size = m_styleRules.getSize(); i < size; i++)
			{
				String property = m_styleRules.getRendererProperty(i);
				if (property == null || properties.getBoolean(property))
				{
					Object value = m_styleRules.getValue(i);
					if (value instanceof String)
					{
						DOM.setStyleAttribute(viewElement, m_styleRules.getName(i), (String) value);
					}
					else if (value instanceof Integer)
					{
						DOM.setIntStyleAttribute(viewElement, m_styleRules.getName(i), ((Integer) value).intValue());
					}
					else
					{
						assert false;
					}
				}
				else
				{
					DOM.setStyleAttribute(viewElement, m_styleRules.getName(i), "");
				}
			}
		}
	}
	
	protected StringBuilder buildStyleName(StringBuilder result, Object modelElement, CellProperties properties)
	{
		String baseStyleName = getElementBaseStyleName();
		result.append(baseStyleName != null ? baseStyleName : "");
		if (properties != null)
		{
			if (! properties.isDisabled())
			{
				appendDependantStyle(result, StyleNames.HOVER, properties.isHover());
				appendDependantStyle(result, StyleNames.ACTIVE, properties.isActive());
			}
			for (int i = 0, size = m_dependentStyleNames.getSize(); i < size; i++)
			{
				String propertyName = m_dependentStyleNames.getRendererProperty(i);
				Object value = m_dependentStyleNames.getValue(i);
				if (value instanceof Boolean)
				{
					appendDependantStyle(result, m_dependentStyleNames.getName(i), properties.getBoolean(propertyName) == (Boolean) value);
				}
				else if (value instanceof Integer)
				{
					appendDependantStyle(result, m_dependentStyleNames.getName(i), properties.getInt(propertyName) == (Integer) value);
				}
				else
				{
					assert false;
				}
			}
		}
		return result;
	}
	
	protected StringBuilder appendDependantStyle(StringBuilder styleIn, String dependentStyleName)
	{
		return appendDependantStyle(styleIn, dependentStyleName, true);
	}
	
	protected StringBuilder appendDependantStyle(StringBuilder styleIn, String dependentStyleName, boolean add)
	{
		if (add)
		{
			styleIn.append(' ');
			styleIn.append(m_elementStyleName);
			styleIn.append('-');
			styleIn.append(dependentStyleName);
		}
		return styleIn;
	}
	
	public void renderContent(Element viewElement, Object modelElement, CellProperties properties)
	{
		final String valueString = getValueString(modelElement, properties);
		final String safeValueString = (m_escapeMarkup) ? escapeMarkup(valueString) : valueString;
		final String nonEmptyValueString = ("".equals(safeValueString)) ? "&nbsp;" : safeValueString;
		final String cellString = getCellString(nonEmptyValueString, modelElement, properties);
		DOM.setInnerHTML(viewElement, cellString);
	}
	
	/**
	 * Calls the formatter.  
	 * 
	 * @return an HTML snippet representing <code>modelElement</code>
	 */
	protected String getValueString(Object modelElement, CellProperties properties)
	{
        return m_formatter.getString(modelElement);
	}

    /**
     * Escapes HTML markup in the string returned by
     * {@link #getValueString(Object, CellProperties)}.
     * 
     * @see #setEscapeMarkup(boolean)
     */
	protected String escapeMarkup(String valueString)
	{
        return DomUtil.escapeMarkup(valueString);
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
    protected String getCellString(String valueString, Object modelElement, CellProperties properties)
    {
        return valueString;
    }
    
	private static class RuleCollection
	{
		private final ArrayList<String> m_rendererProperties = new ArrayList<String>();
		private final ArrayList<String> m_names = new ArrayList<String>();
		private final ArrayList<Object> m_values = new ArrayList<Object>();
		
		public String getRendererProperty(int index)
		{
			return m_rendererProperties.get(index);
		}
		
		public String getName(int index)
		{
			return m_names.get(index);
		}
		
		/**
		 * @return an Integer or a String
		 */
		public Object getValue(int index)
		{
			return m_values.get(index);
		}
		
		public int getSize()
		{
			int result = m_names.size();
			assert m_rendererProperties.size() == result && m_values.size() == result;
			return result;
		}
		
		/**
		 * @param value an Integer or a String or <code>null</code>
		 */
		private void setRule(String propertyName, String name, Object value)
		{
			if (name == null || name.length() == 0)
				throw new IllegalArgumentException();
			
			if (value == null || name.length() == 0)
				throw new IllegalArgumentException();
			
			int index = getRuleIndex(propertyName, name);
			if (index == -1)
			{
				// add new rule
				m_rendererProperties.add(propertyName);
				m_names.add(name);
				m_values.add(value);
			}
			else
			{
				// update existing rule
				m_values.set(index, value);
			}
		}
		
		public void clearRule(String propertyName, String name)
		{
			if (name == null || name.length() == 0)
				throw new IllegalArgumentException();
			
			int index = getRuleIndex(propertyName, name);
			if (index != -1)
			{
				// clear existing rule
				m_rendererProperties.remove(index);
				m_names.remove(index);
				m_values.remove(index);
			}
		}
		
		private int getRuleIndex(String propertyName, String name)
		{
			for (int i = 0, size = m_names.size(); i < size; i++)
			{
				if (name.equals(m_names.get(i)))
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
				result += String.valueOf(m_names.get(i)) + ',';
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
