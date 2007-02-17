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
	
	private boolean m_dragging = false;
	
	/**
	 * Creates a new DragController which delegates to the specified
	 * DragGesture. 
	 * 
	 * @param gesture a delegate object
	 */
	public DragController(DragGesture gesture)
	{
		super(Event.ONMOUSEDOWN | Event.ONMOUSEMOVE | Event.ONMOUSEUP, DragController.class);
		m_dragGesture = gesture;
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
		}
	}
	
	public void onMouseDown(Widget sender, int x, int y)
	{
//	    Debug.println("DragController.onMouseDown(x=" + x + ",y=" + y + ")");
		m_dragging = true;
		DOM.setCapture(sender.getElement());
		m_dragGesture.start(x, y);
	}
	
	public void onMouseMove(Widget sender, int x, int y)
	{
		if (m_dragging)
		{
//		    Debug.println("DragController.onMouseMove(x=" + x + ",y=" + y + ")");
			m_dragGesture.step(x, y);
		}
	}
	
	public void onMouseUp(Widget sender, int x, int y)
	{
//	    Debug.println("DragController.onMouseUp(x=" + x + ",y=" + y + ")");
		m_dragGesture.step(x, y);
		m_dragGesture.finish(x, y);
		DOM.releaseCapture(sender.getElement());
		m_dragging = false;
	}
}
