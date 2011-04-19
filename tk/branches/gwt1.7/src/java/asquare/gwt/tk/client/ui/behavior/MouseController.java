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
package asquare.gwt.tk.client.ui.behavior;


/**
 * A controller which converts native DOM mouse events to framework
 * {@link MouseEvent}s.
 * @deprecated use {@link EventController}
 */
public class MouseController// extends ControllerAdaptor
{
//	private MouseHandlerCollection m_handlers = new MouseHandlerCollection();
//	
//	public MouseController(Class id)
//	{
//		this(id, 0, null);
//	}
//	
//	public MouseController(Class id, MouseHandler handler)
//	{
//		this(id, 0, handler);
//	}
//	
//	public MouseController(Class id, int eventBits)
//	{
//		this(id, eventBits, null);
//	}
//	
//	/**
//	 * @param eventBits <code>0</code>, or a positive integer to override
//	 *            handler's event bits
//	 * @param handler a MouseHandler or <code>null</code>
//	 */
//	public MouseController(Class id, int eventBits, MouseHandler handler)
//	{
//		super(id, eventBits);
//		if (handler != null)
//		{
//			addHandler(handler);
//		}
//	}
//	
//	protected boolean hasHandlers()
//	{
//		return m_handlers != null && m_handlers.size() > 0;
//	}
//	
//	public int getEventBits()
//	{
//		int result = super.getEventBits();
//		if (result == 0 && hasHandlers())
//		{
//			result = m_handlers.getEventBits();
//		}
//		return result;
//	}
//	
//	public void addHandler(MouseHandler handler)
//	{
//		if (m_handlers == null)
//		{
//			m_handlers = new MouseHandlerCollection();
//		}
//		m_handlers.add(handler);
//	}
//	
//	public void onBrowserEvent(Widget widget, Event event)
//	{
//		processEvent(widget, event);
//	}
//	
//	protected void processEvent(Widget widget, Event event)
//	{
//		int eventType = DOM.eventGetType(event);
//		switch (eventType)
//		{
//			case Event.ONMOUSEDOWN:
//				onMouseDown(new MouseEventImpl(widget, event, eventType, false));
//				break;
//			
//			case Event.ONMOUSEMOVE:
//				onMouseMove(new MouseEventImpl(widget, event, eventType, false));
//				break;
//			
//			case Event.ONMOUSEUP:
//				onMouseUp(new MouseEventImpl(widget, event, eventType, false));
//				break;
//				
//			case Event.ONMOUSEOVER: 
//				onMouseOver(new MouseEventImpl(widget, event, eventType, false));
//				break;
//				
//			case Event.ONMOUSEOUT: 
//				onMouseOut(new MouseEventImpl(widget, event, eventType, false));
//				break;
//		}
//	}
//	
//	protected void onMouseDown(MouseEvent e)
//	{
//		if (hasHandlers())
//		{
//			for (int i = 0, size = m_handlers.size(); i < size; i++)
//			{	
//				MouseHandler handler = (MouseHandler) m_handlers.get(i);
//				if ((handler.getEventBits() & Event.ONMOUSEDOWN) != 0)
//				{
//					handler.onMouseDown(e);
//				}
//			}
//		}
//	}
//	
//	protected void onMouseMove(MouseEvent e)
//	{
//		if (hasHandlers())
//		{
//			for (int i = 0, size = m_handlers.size(); i < size; i++)
//			{	
//				MouseHandler handler = (MouseHandler) m_handlers.get(i);
//				if ((handler.getEventBits() & Event.ONMOUSEMOVE) != 0)
//				{
//					handler.onMouseMove(e);
//				}
//			}
//		}
//	}
//	
//	protected void onMouseUp(MouseEvent e)
//	{
//		if (hasHandlers())
//		{
//			for (int i = 0, size = m_handlers.size(); i < size; i++)
//			{	
//				MouseHandler handler = (MouseHandler) m_handlers.get(i);
//				if ((handler.getEventBits() & Event.ONMOUSEUP) != 0)
//				{
//					handler.onMouseUp(e);
//				}
//			}
//		}
//	}
//	
//	protected void onMouseOver(MouseEvent e)
//	{
//		if (hasHandlers())
//		{
//			for (int i = 0, size = m_handlers.size(); i < size; i++)
//			{	
//				MouseHandler handler = (MouseHandler) m_handlers.get(i);
//				if ((handler.getEventBits() & Event.ONMOUSEOVER) != 0)
//				{
//					handler.onMouseOver(e);
//				}
//			}
//		}
//	}
//	
//	protected void onMouseOut(MouseEvent e)
//	{
//		if (hasHandlers())
//		{
//			for (int i = 0, size = m_handlers.size(); i < size; i++)
//			{	
//				MouseHandler handler = (MouseHandler) m_handlers.get(i);
//				if ((handler.getEventBits() & Event.ONMOUSEOUT) != 0)
//				{
//					handler.onMouseOut(e);
//				}
//			}
//		}
//	}
//	
//	private static class MouseHandlerCollection extends ArrayList
//	{
//		private static final long serialVersionUID = 1L;
//
//		// -1 means cache is invalid
//		private int m_eventBitsCache = 0;
//		
//		public void add(MouseHandler handler)
//		{
//			super.add(handler);
//			m_eventBitsCache |= handler.getEventBits();
//		}
//		
//		public void remove(MouseHandler handler)
//		{
//			m_eventBitsCache = -1;
//			super.remove(handler);
//		}
//		
//		public int getEventBits()
//		{
//			if (m_eventBitsCache == -1)
//			{
//				int m_eventBitsCache = 0;
//				for (int i = 0, size = size(); i < size; i++)
//				{
//					m_eventBitsCache |= ((MouseHandler) get(i)).getEventBits();
//				}
//			}
//			return m_eventBitsCache;
//		}
//	}
}
