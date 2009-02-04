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

import java.util.ArrayList;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

/**
 * A controller which:
 * <ul>
 * <li>converts native DOM mouse events to library {@link EventBase Event}s</li>
 * <li>implements event handler interfaces (i.e. handler adaptor) and provides
 * empty implementations of event methods</li>
 * <li>can delegate events to child handlers</li>
 * </ul>
 * <p>
 * <em>Note: child handlers are typically added/removed <strong>before</strong> the controller 
 * is added to the widget so that events are properly sunk on the widget.<em>
 * <p>
 * TODO: add support for all event types
 */
public class EventControllerBase extends EventInterestAdaptor implements EventHandler
{
	private HandlerCollection m_handlers = null;
	private boolean m_pluggedIn = false;
	private Widget m_pluggedInWidget;
	
	public EventControllerBase()
	{
		this(0, null);
	}
	
	public EventControllerBase(EventHandler handler)
	{
		this(0, handler);
	}
	
	public EventControllerBase(int eventBits)
	{
		this(eventBits, null);
	}
	
	/**
	 * @param eventBits additional events to sink, or to use the event bits
	 *            <code>0</code> from the handlers
	 * @param handler an EventHandler or <code>null</code>
	 */
	public EventControllerBase(int eventBits, EventHandler handler)
	{
		super(eventBits);
		if (handler != null)
		{
			addHandler(handler);
		}
	}
	
	protected final boolean hasHandlers()
	{
		return m_handlers != null && m_handlers.size() > 0;
	}
	
	public int getEventBits()
	{
		int result = super.getEventBits();
		if (hasHandlers())
		{
			result |= m_handlers.getEventBits();
		}
		return result;
	}
	
	public boolean isPluggedIn()
	{
		return m_pluggedIn;
	}
	
	public void addHandler(EventHandler handler)
	{
		if (m_handlers == null)
		{
			m_handlers = new HandlerCollection();
		}
		m_handlers.add(handler);
		if (m_pluggedIn)
		{
			handler.plugIn(m_pluggedInWidget);
		}
	}
	
	public void removeHandler(EventHandler handler)
	{
		if (m_handlers != null)
		{
			if (m_handlers.remove(handler))
			{
				if (m_pluggedIn)
				{
					handler.unplug(m_pluggedInWidget);
				}
			}
		}
	}
	
	public void plugIn(Widget widget)
	{
		if (m_pluggedIn)
			return;
		
		m_pluggedIn = true;
		m_pluggedInWidget = widget;
		if (m_handlers != null)
		{
			for (int i = 0, size = m_handlers.size(); i < size; i++)
			{
				(m_handlers.get(i)).plugIn(m_pluggedInWidget);
			}
		}
	}
	
	public void unplug(Widget widget)
	{
		if (! m_pluggedIn)
			return;
		
		if (m_handlers != null)
		{
			for (int i = 0, size = m_handlers.size(); i < size; i++)
			{
				(m_handlers.get(i)).unplug(m_pluggedInWidget);
			}
		}
		m_pluggedInWidget = null;
		m_pluggedIn = false;
	}
	
	public Widget getPluggedInWidget()
	{
		if (! m_pluggedIn)
			throw new IllegalStateException();
		
		return m_pluggedInWidget;
	}
	
	public void onKeyDown(KeyEvent e)
	{
	}
	
	public void onKeyPress(KeyEvent e)
	{
	}
	
	public void onKeyUp(KeyEvent e)
	{
	}
	
	public void onMouseDown(MouseEvent e)
	{
	}
	
	public void onMouseMove(MouseEvent e)
	{
	}
	
	public void onMouseUp(MouseEvent e)
	{
	}
	
	public void onMouseClick(MouseEvent e)
	{
	}
	
	public void onMouseDoubleClick(MouseEvent e)
	{
	}
	
	public void onMouseOver(MouseEvent e)
	{
	}
	
	public void onMouseOut(MouseEvent e)
	{
	}
	
	public void onFocusGained(FocusEvent e)
	{
	}
	
	public void onFocusLost(FocusEvent e)
	{
	}
	
	protected EventBase convertEvent(Widget widget, Event event, boolean previewPhase)
	{
		EventBase result;
		int eventType = DOM.eventGetType(event);
		if (MouseEventImpl.isMouseEvent(eventType))
		{
			result = new MouseEventImpl(widget, event, eventType, previewPhase);
		}
		else if (KeyEventImpl.isKeyEvent(eventType))
		{
			result = new KeyEventImpl(widget, event, eventType, previewPhase);
		}
		else if (FocusEventImpl.isFocusEvent(eventType))
		{
			result = new FocusEventImpl(widget, event, eventType, previewPhase);
		}
		else
		{
			throw new IllegalArgumentException("TODO: handle event type=" + DOM.eventGetType(event));
		}
		return result;
	}

	protected void processEvent(EventBase event)
	{
		if (event instanceof MouseEvent)
		{
			processMouseEvent((MouseEvent) event);
		}
		else if (event instanceof KeyEvent)
		{
			processKeyEvent((KeyEvent) event);
		}
		else if (event instanceof FocusEvent)
		{
			processFocusEvent((FocusEvent) event);
		}
	}
	
	public void processMouseEvent(MouseEvent mouseEvent)
	{
		switch (mouseEvent.getType())
		{
			case MouseEvent.MOUSE_DOWN:
				processMouseDown(mouseEvent);
				break;
			
			case MouseEvent.MOUSE_MOVE:
				processMouseMove(mouseEvent);
				break;
			
			case MouseEvent.MOUSE_UP:
				processMouseUp(mouseEvent);
				break;
				
			case MouseEvent.MOUSE_OVER: 
				processMouseOver(mouseEvent);
				break;
				
			case MouseEvent.MOUSE_OUT: 
				processMouseOut(mouseEvent);
				break;
				
			case MouseEvent.MOUSE_CLICK:
				processMouseClick(mouseEvent);
				break;
				
			case MouseEvent.MOUSE_DOUBLECLICK:
				processMouseDoubleClick(mouseEvent);
				break;
		}
	}
	
	public void processKeyEvent(KeyEvent keyEvent)
	{
		switch (keyEvent.getType())
		{
			case KeyEvent.KEY_DOWN:
				processKeyDown(keyEvent);
				break;
			
			case KeyEvent.KEY_PRESSED:
				processKeyPress(keyEvent);
				break;
			
			case KeyEvent.KEY_UP:
				processKeyUp(keyEvent);
				break;
		}
	}
	
	public void processFocusEvent(FocusEvent focusEvent)
	{
		switch (focusEvent.getType())
		{
			case FocusEvent.FOCUS_GAINED:
				processFocusGained(focusEvent);
				break;
			
			case FocusEvent.FOCUS_LOST:
				processFocusLost(focusEvent);
				break;
		}
	}
	
	public void processMouseDown(MouseEvent e)
	{
		onMouseDown(e);
		
		if (! hasHandlers())
			return;
		
		for (int i = 0, size = m_handlers.size(); i < size; i++)
		{	
			EventHandler handler = m_handlers.get(i);
			if ((handler.getEventBits() & MouseEvent.MOUSE_DOWN) != 0)
			{
				handler.processMouseDown(e);
			}
		}
	}
	
	public void processMouseMove(MouseEvent e)
	{
		onMouseMove(e);
		
		if (! hasHandlers())
			return;
		
		for (int i = 0, size = m_handlers.size(); i < size; i++)
		{	
			EventHandler handler = m_handlers.get(i);
			if ((handler.getEventBits() & MouseEvent.MOUSE_MOVE) != 0)
			{
				handler.processMouseMove(e);
			}
		}
	}
	
	public void processMouseUp(MouseEvent e)
	{
		onMouseUp(e);
		
		if (! hasHandlers())
			return;
		
		for (int i = 0, size = m_handlers.size(); i < size; i++)
		{	
			EventHandler handler = m_handlers.get(i);
			if ((handler.getEventBits() & MouseEvent.MOUSE_UP) != 0)
			{
				handler.processMouseUp(e);
			}
		}
	}
	
	public void processMouseClick(MouseEvent e)
	{
		onMouseClick(e);
		
		if (! hasHandlers())
			return;
		
		for (int i = 0, size = m_handlers.size(); i < size; i++)
		{	
			EventHandler handler = m_handlers.get(i);
			if ((handler.getEventBits() & MouseEvent.MOUSE_CLICK) != 0)
			{
				handler.processMouseClick(e);
			}
		}
	}
	
	public void processMouseDoubleClick(MouseEvent e)
	{
		onMouseDoubleClick(e);
		
		if (! hasHandlers())
			return;
		
		for (int i = 0, size = m_handlers.size(); i < size; i++)
		{	
			EventHandler handler = m_handlers.get(i);
			if ((handler.getEventBits() & MouseEvent.MOUSE_DOUBLECLICK) != 0)
			{
				handler.processMouseDoubleClick(e);
			}
		}
	}
	
	public void processMouseOver(MouseEvent e)
	{
		onMouseOver(e);
		
		if (! hasHandlers())
			return;
		
		for (int i = 0, size = m_handlers.size(); i < size; i++)
		{	
			EventHandler handler = m_handlers.get(i);
			if ((handler.getEventBits() & MouseEvent.MOUSE_OVER) != 0)
			{
				handler.processMouseOver(e);
			}
		}
	}
	
	public void processMouseOut(MouseEvent e)
	{
		onMouseOut(e);
		
		if (! hasHandlers())
			return;
		
		for (int i = 0, size = m_handlers.size(); i < size; i++)
		{	
			EventHandler handler = m_handlers.get(i);
			if ((handler.getEventBits() & MouseEvent.MOUSE_OUT) != 0)
			{
				handler.processMouseOut(e);
			}
		}
	}
	
	public void processKeyDown(KeyEvent e)
	{
		onKeyDown(e);
		
		if (! hasHandlers())
			return;
		
		for (int i = 0, size = m_handlers.size(); i < size; i++)
		{	
			EventHandler handler = m_handlers.get(i);
			if ((handler.getEventBits() & KeyEvent.KEY_DOWN) != 0)
			{
				handler.processKeyDown(e);
			}
		}
	}
	
	public void processKeyPress(KeyEvent e)
	{
		onKeyPress(e);
		
		if (! hasHandlers())
			return;
		
		for (int i = 0, size = m_handlers.size(); i < size; i++)
		{	
			EventHandler handler = m_handlers.get(i);
			if ((handler.getEventBits() & KeyEvent.KEY_PRESSED) != 0)
			{
				handler.processKeyPress(e);
			}
		}
	}
	
	public void processKeyUp(KeyEvent e)
	{
		onKeyUp(e);
		
		if (! hasHandlers())
			return;
		
		for (int i = 0, size = m_handlers.size(); i < size; i++)
		{	
			EventHandler handler = m_handlers.get(i);
			if ((handler.getEventBits() & KeyEvent.KEY_UP) != 0)
			{
				handler.processKeyUp(e);
			}
		}
	}
	
	public void processFocusGained(FocusEvent e)
	{
		onFocusGained(e);
		
		if (! hasHandlers())
			return;
		
		for (int i = 0, size = m_handlers.size(); i < size; i++)
		{	
			EventHandler handler = m_handlers.get(i);
			if ((handler.getEventBits() & FocusEvent.FOCUS_GAINED) != 0)
			{
				handler.processFocusGained(e);
			}
		}
	}
	
	public void processFocusLost(FocusEvent e)
	{
		onFocusLost(e);
		
		if (! hasHandlers())
			return;
		
		for (int i = 0, size = m_handlers.size(); i < size; i++)
		{	
			EventHandler handler = m_handlers.get(i);
			if ((handler.getEventBits() & FocusEvent.FOCUS_LOST) != 0)
			{
				handler.processFocusLost(e);
			}
		}
	}
	
	private static class HandlerCollection extends ArrayList<EventHandler>
	{
		private static final long serialVersionUID = 1L;

		// -1 means cache is invalid
		private int m_eventBitsCache = 0;
		
		public boolean add(EventHandler handler)
		{
			boolean result = super.add(handler);
			m_eventBitsCache |= handler.getEventBits();
			return result;
		}
		
		public boolean remove(EventHandler handler)
		{
			m_eventBitsCache = -1;
			return super.remove(handler);
		}
		
		public int getEventBits()
		{
			if (m_eventBitsCache == -1)
			{
				int m_eventBitsCache = 0;
				for (int i = 0, size = size(); i < size; i++)
				{
					m_eventBitsCache |= get(i).getEventBits();
				}
			}
			return m_eventBitsCache;
		}
	}
}
