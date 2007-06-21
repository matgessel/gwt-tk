/*
 * Copyright 2006 Mat Gessel <mat.gessel@gmail.com>
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

import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

/**
 * A controller which interprets drag events and delegates the mouse drag
 * operation to a delegate.
 * 
 * @see asquare.gwt.tk.client.ui.behavior.DragGesture
 */
public class DragController extends ControllerAdaptor
{
	private final DragGesture m_dragGesture;
	private final int m_threshold;
	
	private boolean m_mouseDown = false;
	private boolean m_dragging = false;
	private int m_downX, m_downY;
	
	/**
	 * Creates a new DragController which delegates to the specified
	 * DragGesture. 
	 * 
	 * @param gesture a delegate object
	 */
	public DragController(DragGesture gesture)
	{
		this(gesture, 0);
	}
	
	/**
	 * Creates a new DragController which delegates to the specified
	 * DragGesture. The DragGesture is not activated until the mouse movement
	 * equals the specified distance; this prevents accidental movement.
	 * 
	 * @param distance the distance in screen pixels to move before taking an
	 *            action, or <code>0</code>
	 * @param gesture a delegate object
	 */
	public DragController(DragGesture gesture, int distance)
	{
		super(Event.ONMOUSEDOWN | Event.ONMOUSEMOVE | Event.ONMOUSEUP | Event.ONLOSECAPTURE, DragController.class);
		m_dragGesture = gesture;
		m_threshold = distance;
	}
	
	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.EventDelegateAdaptor#doBrowserEvent(com.google.gwt.user.client.ui.Widget, com.google.gwt.user.client.Event)
	 */
	public void onBrowserEvent(Widget widget, Event event)
	{
    	// translate the coordinates into the source widget's coordinate space
		// see MouseListenerCollection.fireMouseEvent()
		int sourceX = DomUtil.eventGetAbsoluteX(event) - DOM.getAbsoluteLeft(widget.getElement());
		int sourceY = DomUtil.eventGetAbsoluteY(event) - DOM.getAbsoluteTop(widget.getElement());
		
		switch(DOM.eventGetType(event))
		{
			case Event.ONMOUSEDOWN: 
				onMouseDown(widget, sourceX, sourceY);
				break;

			case Event.ONMOUSEMOVE: 
				onMouseMove(widget, sourceX, sourceY);
				break;

			case Event.ONMOUSEUP: 
				onMouseUp(widget, sourceX, sourceY);
				break;
				
			/*
			 * Firefox loses the mouseup if released outside the client area.
			 * But we do get an onlosecapture event. Not ideal, but it prevents
			 * a invalid state (dragging while mouse is released).
			 * http://code.google.com/p/google-web-toolkit/issues/detail?id=243
			 */ 
			case Event.ONLOSECAPTURE: 
				onMouseUp(widget, sourceX, sourceY);
				break;
		}
	}
	
	private boolean equalsThreshold(int x, int y)
	{
		return Math.abs(x - m_downX) >= m_threshold || Math.abs(y - m_downY) >= m_threshold;
	}
	
	private void doStartDrag(Widget sender, int x, int y)
	{
		m_dragging = true;
		m_dragGesture.start(x, y);
	}
	
	public void onMouseDown(Widget sender, int x, int y)
	{
//	    Debug.println("DragController.onMouseDown(x=" + x + ",y=" + y + ")");
		DOM.setCapture(sender.getElement());
		m_downX = x;
		m_downY = y;
		m_mouseDown = true;
		if (equalsThreshold(x, y))
		{
			doStartDrag(sender, x, y);
		}
	}
	
	public void onMouseMove(Widget sender, int x, int y)
	{
//	    Debug.println("DragController.onMouseMove(x=" + x + ",y=" + y + ")");
		if (m_mouseDown)
		{
			if (! m_dragging && equalsThreshold(x, y))
			{
				doStartDrag(sender, m_downX, m_downY);
			}
			if (m_dragging)
			{
				m_dragGesture.step(x, y);
			}
		}
	}
	
	public void onMouseUp(Widget sender, int x, int y)
	{
//	    Debug.println("DragController.onMouseUp(x=" + x + ",y=" + y + ")");
		
		// it is possible to get a mouse up without a mouse down
		if (m_mouseDown)
		{
			m_dragGesture.step(x, y);
			m_dragGesture.finish(x, y);
			DOM.releaseCapture(sender.getElement());
			m_dragging = false;
			m_mouseDown = false;
		}
	}
}
