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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

/**
 * A controller which converts native DOM mouse events to framework
 * {@link MouseEvent}s.
 */
public class MouseController extends ControllerAdaptor
{
	private final MouseHandler m_handler;
	
	public MouseController(Class id, MouseHandler handler)
	{
		this(0, id, handler);
	}
	
	public MouseController(int eventBits, Class id, MouseHandler handler)
	{
		super(eventBits, id);
		m_handler = handler;
	}
	
	public int getEventBits()
	{
		int result = super.getEventBits();
		if (result == 0)
		{
			result = (m_handler != null) ? m_handler.getEventBits() : 0;
		}
		return result;
	}
	
	public void onBrowserEvent(Widget widget, Event event)
	{
		processEvent(widget, event);
	}
	
	protected void processEvent(Widget widget, Event event)
	{
		switch (DOM.eventGetType(event))
		{
			case Event.ONMOUSEDOWN:
				onMouseDown(new MouseEvent(widget, event));
				break;
			
			case Event.ONMOUSEMOVE:
				onMouseMove(new MouseEvent(widget, event));
				break;
			
			case Event.ONMOUSEUP:
				onMouseUp(new MouseEvent(widget, event));
				break;
				
			case Event.ONMOUSEOVER: 
				onMouseOver(new MouseEvent(widget, event));
				break;
				
			case Event.ONMOUSEOUT: 
				onMouseOut(new MouseEvent(widget, event));
				break;
		}
	}
	
	protected void onMouseDown(MouseEvent e)
	{
		if (m_handler != null)
		{
			m_handler.onMouseDown(e);
		}
	}
	
	protected void onMouseMove(MouseEvent e)
	{
		if (m_handler != null)
		{
			m_handler.onMouseMove(e);
		}
	}
	
	protected void onMouseUp(MouseEvent e)
	{
		if (m_handler != null)
		{
			m_handler.onMouseUp(e);
		}
	}
	
	protected void onMouseOver(MouseEvent e)
	{
		if (m_handler != null)
		{
			m_handler.onMouseOver(e);
		}
	}
	
	protected void onMouseOut(MouseEvent e)
	{
		if (m_handler != null)
		{
			m_handler.onMouseOut(e);
		}
	}
}
