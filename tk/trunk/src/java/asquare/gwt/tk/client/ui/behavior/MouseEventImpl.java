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

import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.Widget;

public class MouseEventImpl extends InputEventImpl implements MouseEvent
{
	private static final long serialVersionUID = 1L;
	
	private final int m_clientX, m_clientY;
	private final int m_absoluteX, m_absoluteY;
	private final int m_widgetLeft, m_widgetTop;
	private final int m_widgetX, m_widgetY;
	
	protected MouseEventImpl(MouseEventImpl e)
	{
		super(e);
		m_clientX = e.m_clientX;
		m_clientY = e.m_clientY;
		m_absoluteX = e.m_absoluteX;
		m_absoluteY = e.m_absoluteY;
		m_widgetLeft = e.m_widgetLeft;
		m_widgetTop = e.m_widgetTop;
		m_widgetX = e.m_widgetX;
		m_widgetY = e.m_widgetY;
	}
	
	/**
	 * @param source the widget which is receiving the mouse event
	 * @param mouseEvent a <code>mousedown</code>, <code>mousemove</code> or <code>mouseup</code> event
	 * @param type the DOM event type
	 * @param previewPhase <code>true</code> if the event was caught in event preview
	 */
	public MouseEventImpl(Widget source, Event mouseEvent, int type, boolean previewPhase)
	{
		super(source, mouseEvent, type, previewPhase);
		Element e = source.getElement();
		m_clientX = DOM.eventGetClientX(mouseEvent);
		m_clientY = DOM.eventGetClientY(mouseEvent);
		m_absoluteX = Window.getScrollLeft() + m_clientX;
		m_absoluteY = Window.getScrollTop() + m_clientY;
		m_widgetLeft = DOM.getAbsoluteLeft(source.getElement());
		m_widgetTop = DOM.getAbsoluteTop(source.getElement());
		
		/*
		 * Translate the coordinates into the source widget's coordinate space
		 * see MouseListenerCollection.fireMouseEvent()
		 */
		m_widgetX = m_absoluteX - m_widgetLeft + DOM.getElementPropertyInt(e, "scrollLeft");
		m_widgetY = m_absoluteY - m_widgetTop + DOM.getElementPropertyInt(e, "scrollTop");
	}
	
	public static boolean isMouseEvent(Event domEvent)
	{
		return isMouseEvent(DOM.eventGetType(domEvent));
	}
	
	public static boolean isMouseEvent(int domEventType)
	{
		return (domEventType & MOUSE_EVENTS) != 0;
	}

	protected int getWidgetLeft()
	{
		return m_widgetLeft;
	}

	protected int getWidgetTop()
	{
		return m_widgetTop;
	}

	public int getClientX()
	{
		return m_clientX;
	}

	public int getClientY()
	{
		return m_clientY;
	}
	
	public int getScreenX()
	{
		return DOM.eventGetScreenX(getDomEvent());
	}
	
	public int getScreenY()
	{
		return DOM.eventGetScreenY(getDomEvent());
	}

	public int getWidgetX()
	{
		return m_widgetX;
	}

	public int getWidgetY()
	{
		return m_widgetY;
	}

	public Element getFrom()
	{
		if (getType() != MOUSE_OVER)
			throw new IllegalStateException();
		
		return DOM.eventGetFromElement(getDomEvent());
	}

	public Element getTo()
	{
		if (getType() != MOUSE_OUT)
			throw new IllegalStateException();
		
		return DOM.eventGetToElement(getDomEvent());
	}

	public int getAbsoluteX()
	{
		return m_absoluteX;
	}

	public int getAbsoluteY()
	{
		return m_absoluteY;
	}

	@Override
	protected String dumpProperties()
	{
		return "client(" + m_clientX + ',' + m_clientY + "),widget(" + m_widgetX + ',' + m_widgetY + ")," + super.dumpProperties();
	}
}
