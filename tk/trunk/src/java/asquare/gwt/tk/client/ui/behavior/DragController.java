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
 * A controller which interprets drag events and delegates the mouse drag
 * operation to a delegate.
 * 
 * @see asquare.gwt.tk.client.ui.behavior.DragGesture
 */
public class DragController extends MouseController
{
	private MouseEvent m_previousEvent;
	
	/**
	 * Creates a new DragController which delegates to the specified
	 * handler.
	 * 
	 * @param handler a delegate object, or <code>null</code>
	 */
	public DragController(MouseHandler handler)
	{
		super(handler.getEventBits() | Event.ONLOSECAPTURE, DragController.class, handler);
	}
	
	public void onBrowserEvent(Widget widget, Event event)
	{
		super.onBrowserEvent(widget, event);
		if (DOM.eventGetType(event) == Event.ONLOSECAPTURE)
		{
			/*
			 * Firefox loses the mouseup if released outside the client area.
			 * But we do get an onlosecapture event. Not ideal, but it prevents
			 * a invalid state (dragging while mouse is released).
			 * http://code.google.com/p/google-web-toolkit/issues/detail?id=243
			 */ 
			onMouseUp(m_previousEvent);
		}
	}
	
	protected void onMouseDown(MouseEvent e)
	{
		DOM.setCapture(e.getReceiver().getElement());
		m_previousEvent = e;
		super.onMouseDown(e);
	}
	
	protected void onMouseMove(MouseEvent e)
	{
		m_previousEvent = e;
		super.onMouseMove(e);
	}
	
	protected void onMouseUp(MouseEvent e)
	{
		if (m_previousEvent != null)
		{
			// do last to prevent reentrancy via ONLOSECAPTURE
			DOM.releaseCapture(e.getReceiver().getElement());
		}
		super.onMouseUp(e);
		m_previousEvent = null;
	}
}
