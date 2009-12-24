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
package asquare.gwt.sb.client.ui;

import java.util.Stack;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

/**
 * A {@link com.google.gwt.user.client.ui.ComplexPanel ComplexPanel} which keeps
 * in internal "cursor" enabling you to construct HTML fragments like writing to
 * a stream. Allows customization of the root element and <code>display</code>
 * style of added children.
 * <p>
 * TODO: 
 * <ul>
 * <li>optional strict checking; prevent tables in spans and such</li>
 * </ul>
 * 
 * <h3>CSS Style Rules</h3>
 * <ul class='css'>
 * <li>.tk-StreamPanel { }</li>
 * </ul>
 */
public class StreamPanel extends ComplexPanel
{
	private Stack<Element> m_elementTreePath = new Stack<Element>();
	private Stack<Widget> m_widgetTreePath = new Stack<Widget>();
	private String m_display = null;
	
	
	/**
	 * Constructs a panel with a <code>DIV</code> element and default children
	 * display property.
	 */
	public StreamPanel()
	{
		this("div", null);
	}
	
	/**
	 * Constructs a panel with the specified element and the default display
	 * style.
	 * 
	 * @param element a html element tag
	 */
	public StreamPanel(String element)
	{
		this(element, null);
	}
	
	/**
	 * Constructs a panel with the specified element and display style.
	 * 
	 * @param element a html element tag
	 * @param display a css <code>display</code> style value to apply
	 *            to added children
	 */
	public StreamPanel(String element, String display)
	{
		setElement(DOM.createElement(element));
		setStyleName("tk-StreamPanel");
		m_elementTreePath.push(getElement());
		m_widgetTreePath.push(this);
		m_display = display;
	}
	
	public void close()
	{
		m_elementTreePath.pop();
		m_widgetTreePath.pop();
	}
	
	public void top()
	{
		while(m_elementTreePath.size() > 1)
		{
			m_elementTreePath.pop();
			m_widgetTreePath.pop();
		}
	}
	
	public Widget currentWidget()
	{
		return m_widgetTreePath.peek();
	}
	
	/**
	 * Appends an element to the current node. 
	 * 
	 * @param type
	 */
	public Element element(String type)
	{
		Element element = DOM.createElement(type);
		if (m_display != null)
			DOM.setStyleAttribute(element, "display", m_display);
		DOM.appendChild(m_elementTreePath.peek(), element);
		return element;
	}
	
	/**
	 * Appends an element to the current node and makes the new element the
	 * current node.
	 * 
	 * @param type
	 */
	public Element elementOpen(String type)
	{
		Element element = element(type);
		m_elementTreePath.push(element);
		m_widgetTreePath.push(null);
		return element;
	}
	
	public Widget widget(Widget w)
	{
		if (m_display != null)
			DOM.setStyleAttribute(w.getElement(), "display", m_display);
		add(w, m_elementTreePath.peek());
		return w;
	}
	
	public Widget widgetOpen(Widget w)
	{
		widget(w);
		m_elementTreePath.push(w.getElement());
		m_widgetTreePath.push(w);
		return w;
	}
	
	public void attr(String attributeName, String value)
	{
		DOM.setElementProperty(m_elementTreePath.peek(), attributeName, value);
	}
	
	public void text(String text)
	{
		Element e = m_elementTreePath.peek();
		DOM.setInnerText(e, DOM.getInnerText(e) + text);
	}
	
	public void html(String html)
	{
		Element e = m_elementTreePath.peek();
		DOM.setInnerHTML(e, DOM.getInnerHTML(e) + html);
	}
	
	public void styleName(String styleName)
	{
		UIObject.setStyleName(m_elementTreePath.peek(), styleName, true);
	}
	
	/**
	 * Sets the display style value which will be applied to subsequently added
	 * children.
	 * 
	 * @param display a css <code>display</code> style value to apply
	 *            to added children
	 */
	public void setDisplay(String display)
	{
		m_display = display;
	}
	
	/**
	 * Not supported 
	 * 
	 * @throws UnsupportedOperationException if called
	 * @deprecated not supported
	 */
    @Override
	public boolean remove(Widget w)
	{
		throw new UnsupportedOperationException();
	}
}
