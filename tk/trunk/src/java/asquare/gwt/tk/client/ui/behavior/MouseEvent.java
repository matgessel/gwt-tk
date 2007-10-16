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

import java.util.EventObject;

import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

/**
 * Contains information used by drag operations to position elements in the UI.
 * Different drag operations require different coordinates, so we pass all
 * coordinates to operations using this object. Also encapsulates coordinate
 * calulations.
 */
public class MouseEvent extends EventObject
{
	private static final long serialVersionUID = 2294143695886173892L;
	
	private final Event m_domEvent;
	private final Element m_target, m_to, m_from;
	private final int m_clientX, m_clientY;
	private final int m_absoluteX, m_absoluteY;
	private final int m_widgetLeft, m_widgetTop;
	private final int m_widgetX, m_widgetY;
	
	protected MouseEvent(MouseEvent e)
	{
		super(e.getSource());
		m_domEvent = e.m_domEvent;
		m_target = e.m_target;
		m_to = e.m_to;
		m_from = e.m_from;
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
	 * @param receiver the widget which is receiving the mouse event
	 * @param mouseEvent a <code>mousedown</code>, <code>mousemove</code> or <code>mouseup</code> event
	 * @param previousEvent
	 */
	public MouseEvent(Widget receiver, Event mouseEvent)
	{
		super(receiver);
		m_domEvent = mouseEvent;
		m_target = DOM.eventGetTarget(mouseEvent);
		m_to = DOM.eventGetToElement(mouseEvent);
		m_from = DOM.eventGetFromElement(mouseEvent);
		m_clientX = DOM.eventGetClientX(mouseEvent);
		m_clientY = DOM.eventGetClientY(mouseEvent);
		m_absoluteX = DomUtil.getViewportScrollX() + m_clientX;
		m_absoluteY = DomUtil.getViewportScrollY() + m_clientY;
		m_widgetLeft = DOM.getAbsoluteLeft(receiver.getElement());
		m_widgetTop = DOM.getAbsoluteTop(receiver.getElement());
		
		/*
		 * Translate the coordinates into the source widget's coordinate space
		 * see MouseListenerCollection.fireMouseEvent()
		 */
		m_widgetX = m_absoluteX - m_widgetLeft;
		m_widgetY = m_absoluteY - m_widgetTop;
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

	public int getWidgetX()
	{
		return m_widgetX;
	}

	public int getWidgetY()
	{
		return m_widgetY;
	}

	public Widget getReceiver()
	{
		return (Widget) getSource();
	}

	public Element getTarget()
	{
		return m_target;
	}

	public Element getFrom()
	{
		return m_from;
	}

	public Element getTo()
	{
		return m_to;
	}

	public int getAbsoluteX()
	{
		return m_absoluteX;
	}

	public int getAbsoluteY()
	{
		return m_absoluteY;
	}

	public Event getDomEvent()
	{
		return m_domEvent;
	}
}
