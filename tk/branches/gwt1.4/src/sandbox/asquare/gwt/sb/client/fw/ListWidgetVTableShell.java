/*
 * Copyright 2008 Mat Gessel <mat.gessel@gmail.com>
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
import asquare.gwt.tk.client.util.TableUtil;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

public class ListWidgetVTableShell extends ListWidget
{
	private final TableImpl mImpl;
	
	public ListWidgetVTableShell()
	{
		super(new TableImpl());
		mImpl = (TableImpl) getWidget();
	}

    public void setCellPadding(int px)
    {
        TableUtil.setTableCellPadding(getElement(), px);
    }
    
    public void setCellSpacing(int px)
    {
        TableUtil.setTableCellSpacing(getElement(), px);
    }
    
    public void setEmptyCellsVisible(boolean visible)
    {
        if (visible)
        {
        	TableUtil.setEmptyCellsShow(getElement());
        }
        else
        {
        	TableUtil.setEmptyCellsHide(getElement());
        }
    }
    
	public void clear()
	{
		mImpl.clear();
	}

	public Element getCellElement(int index)
	{
		return mImpl.getCellElement(index);
	}

	public Element getCellRootElement(Element eventTarget)
	{
		return mImpl.getCellRootElement(eventTarget);
	}

	public int getIndexOf(Element eventTarget)
	{
		return mImpl.getIndexOf(eventTarget);
	}

	public int getSize()
	{
		return mImpl.getSize();
	}
	
	public Element insertCellStructure(int index)
	{
		return mImpl.insertCellStructure(index);
	}

	public void remove(int index)
	{
		mImpl.remove(index);
	}

	private static class TableImpl extends Widget
	{
		private final Element m_tBody;
		
		public TableImpl()
		{
			Element table = DOM.createTable();
			setElement(table);
			m_tBody = DOM.createTBody();
			DOM.appendChild(table, m_tBody);
		}

		public void clear()
		{
			DomUtil.clean(m_tBody);
		}

		public Element getCellElement(int index)
		{
			return DOM.getChild(m_tBody, index);
		}

		public Element getCellRootElement(Element eventTarget)
		{
	        Element candidate = eventTarget;
	        do
	        {
	            if ("TR".equals(DomUtil.getElementName(candidate)))
	            {
	                Element candidateTBody = DOM.getParent(candidate);
	                if (DOM.compare(m_tBody, candidateTBody))
	                {
	                    return candidate;
	                }
	                else
	                {
	                    // skip the TBody
	                	candidate = candidateTBody;
	                }
	            }
	            candidate = DOM.getParent(candidate);
	        }
	        while (candidate != null && ! DOM.compare(m_tBody, candidate));
	        return null;
	    }

		public int getIndexOf(Element eventTarget)
		{
			Element tr = getCellRootElement(eventTarget);
			if (tr != null)
			{
				return DOM.getChildIndex(m_tBody, tr);
			}
			return -1;
		}

		public int getSize()
		{
			return DOM.getChildCount(m_tBody);
		}

		public Element insertCellStructure(int index)
		{
			Element tr = DOM.createTR();
			DOM.insertChild(m_tBody, tr, index);
			return tr;
		}

		public void remove(int index)
		{
			DOM.removeChild(m_tBody, DOM.getChild(m_tBody, index));
		}
	}
}
