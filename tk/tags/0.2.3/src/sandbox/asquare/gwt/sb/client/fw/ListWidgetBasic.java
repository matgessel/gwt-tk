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

import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

public class ListWidgetBasic extends ListWidget
{
	public static final String DEFAULT_ELEMENT_LIST = "div";
	public static final String DEFAULT_ELEMENT_ITEM = "div";
	
	private final String m_itemElementType;
	
	public ListWidgetBasic()
	{
		this(DEFAULT_ELEMENT_LIST, DEFAULT_ELEMENT_ITEM);
	}
	
	public ListWidgetBasic(String listElement, String itemElement)
	{
		super(new WidgetImpl(listElement));
		m_itemElementType = itemElement;
	}
	
	public int getIndexOf(Element eventTarget)
	{
		Element e = getElement();
		for (int i = 0, size = DOM.getChildCount(e); i < size; i++)
		{
			if (DOM.isOrHasChild(DOM.getChild(e, i), eventTarget))
			{
				return i;
			}
		}
		return -1;
	}
	
	public Element getCellElement(int index)
	{
		return DOM.getChild(getElement(), index);
	}
	
	public Element insertCellStructure(int index)
	{
		Element child = DOM.createElement(m_itemElementType);
		DOM.insertChild(getElement(), child, index);
		return child;
	}
	
	public void remove(int index)
	{
		Element child = DOM.getChild(getElement(), index);
		DOM.removeChild(getElement(), child);
	}
	
	public void clear()
	{
		DomUtil.clean(getElement());
	}
	
	public int getSize()
	{
		return DOM.getChildCount(getElement());
	}

	private static class WidgetImpl extends Widget
	{
		public WidgetImpl(String listElement)
		{
			setElement(DOM.createElement(listElement));
		}
	}
}
