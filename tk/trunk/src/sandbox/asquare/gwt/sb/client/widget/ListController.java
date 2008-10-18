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
package asquare.gwt.sb.client.widget;

import java.util.EventListener;

import asquare.gwt.sb.client.fw.IndexedCellId;
import asquare.gwt.sb.client.util.TypedList;
import asquare.gwt.sb.client.widget.CList.*;
import asquare.gwt.tk.client.ui.behavior.EventController;
import asquare.gwt.tk.client.ui.behavior.MouseEvent;

public class ListController extends EventController
{
	private final CList m_list;
	
	private TypedList m_handlers = null;
	
	public ListController(CList list)
	{
		// click is flakey with drags, so use mousedown
		super(ListController.class, MouseEvent.MOUSE_DOWN | MouseEvent.MOUSE_DOUBLECLICK);
		m_list = list;
	}
	
	protected void addHandler(Class cls, EventListener handler)
	{
		if (m_handlers == null)
		{
			m_handlers = new TypedList();
		}
		m_handlers.add(cls, handler);
	}
	
	protected void removeHandler(EventListener handler)
	{
		if (m_handlers != null)
		{
			m_handlers.remove(handler);
		}
	}
	
	public void addClickHandler(ListClickHandler handler)
	{
		addHandler(ListClickHandler.class, handler);
	}
	
	public void removeClickHandler(ListClickHandler handler)
	{
		removeHandler(handler);
	}
	
	public void addDoubleClickHandler(ListDoubleClickHandler handler)
	{
		addHandler(ListDoubleClickHandler.class, handler);
	}
	
	public void removeDoubleClickHandler(ListDoubleClickHandler handler)
	{
		removeHandler(handler);
	}
	
	public void onMouseDown(MouseEvent e)
	{
		IndexedCellId cellId = (IndexedCellId) m_list.getListView().getCellId(e.getTarget());
		if (cellId != null)
		{
			fireClick(cellId.getIndex(), e);
		}
	}
	
	public void onMouseDoubleClick(MouseEvent e)
	{
		IndexedCellId cellId = (IndexedCellId) m_list.getListView().getCellId(e.getTarget());
		if (cellId != null)
		{
			fireDoubleClick(cellId.getIndex(), e);
		}
	}
	
	protected void fireClick(int index, MouseEvent e)
	{
		if (m_handlers != null)
		{
			ListClickEventImpl clickEvent = null;
			for (int i = 0, size = m_handlers.getSize(); i < size; i++)
			{
				if (m_handlers.isType(i, ListClickHandler.class))
				{
					if (clickEvent == null)
					{
						clickEvent = new ListClickEventImpl(m_list, ListEvent.LIST_CLICK, index, e);
					}
					((ListClickHandler) m_handlers.getValue(i)).onListClick(clickEvent);
				}
			}
		}
	}

	protected void fireDoubleClick(int index, MouseEvent e)
	{
		if (m_handlers != null)
		{
			ListClickEventImpl clickEvent = null;
			for (int i = 0, size = m_handlers.getSize(); i < size; i++)
			{
				if (m_handlers.isType(i, ListDoubleClickHandler.class))
				{
					if (clickEvent == null)
					{
						clickEvent = new ListClickEventImpl(m_list, ListEvent.LIST_DOUBLECLICK, index, e);
					}
					((ListDoubleClickHandler) m_handlers.getValue(i)).onListDoubleClick(clickEvent);
				}
			}
		}
	}
}
