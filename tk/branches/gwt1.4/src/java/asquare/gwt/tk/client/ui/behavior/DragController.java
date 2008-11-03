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

public class DragController extends EventController
{
	private boolean m_mouseDown;
	
	public DragController(EventHandler handler)
	{
		this(0, handler);
	}
	
	public DragController(int eventBits)
	{
		this(eventBits, null);
	}
	
	/**
	 * @param eventBits additional events to sink, (pass <code>0</code> to just use the event bits from the handlers)
	 * @param handler an EventHandler or <code>null</code>
	 */
	public DragController(int eventBits, EventHandler handler)
	{
		super(DragController.class, eventBits | MouseEvent.MOUSE_DOWN | MouseEvent.MOUSE_UP, handler);
		addHandler(ControlSurfaceController.getInstance());
	}
	
	public void onMouseDown(MouseEvent e)
	{
		m_mouseDown = true;
		DOM.setCapture(getPluggedInWidget().getElement());
	}
	
	public void onMouseUp(MouseEvent e)
	{
		if (m_mouseDown)
		{
			// do last to prevent reentrancy via ONLOSECAPTURE
			DOM.releaseCapture(getPluggedInWidget().getElement());
		}
		m_mouseDown = false;
	}
}
