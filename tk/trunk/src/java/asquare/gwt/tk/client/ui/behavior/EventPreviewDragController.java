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

/**
 * A controller which interprets drag events and delegates the mouse drag
 * operation to a delegate.
 * 
 * @see asquare.gwt.tk.client.ui.behavior.DragGesture
 */
public class EventPreviewDragController extends EventPreviewMouseController
{
	private boolean m_mouseDown = true;
	
	/**
	 * Creates a new EventPreviewDragController which delegates to the specified
	 * handler.
	 * 
	 * @param handler a delegate object
	 */
	public EventPreviewDragController(MouseHandler handler)
	{
		super(Event.ONMOUSEDOWN | handler.getEventBits(), DragController.class, handler);
	}
	
	protected void onMouseDown(MouseEvent e)
	{
		DOM.addEventPreview(this);
		m_mouseDown = true;
		super.onMouseDown(e);
	}
	
	protected void onMouseUp(MouseEvent e)
	{
		if (m_mouseDown)
		{
			m_mouseDown = false;
			super.onMouseUp(e);
			DOM.removeEventPreview(this);
		}
	}
}
