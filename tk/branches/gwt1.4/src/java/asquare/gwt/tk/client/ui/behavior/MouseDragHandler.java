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
 * Converts {@link MouseEvent}s to {@link DragEvent}s and delegates to the
 * specified {@link DragGesture}.
 */
public class MouseDragHandler extends EventController
{
	private DragGesture m_dragGesture;
	private MouseEvent m_mouseDown = null;
	private MouseEvent m_previousEvent = null;
	
	public MouseDragHandler(DragGesture dragGesture)
	{
		super(MouseDragHandler.class, MouseEvent.MOUSE_DOWN | MouseEvent.MOUSE_MOVE | MouseEvent.MOUSE_UP);
		m_dragGesture = dragGesture;
	}

	protected MouseEvent getMouseDown()
	{
		return m_mouseDown;
	}

	protected MouseEvent getPreviousEvent()
	{
		return m_previousEvent;
	}
	
	/**
	 * Get the delegate object which will handle drag operations. 
	 */
	public DragGesture getDragGesture()
	{
		return m_dragGesture;
	}
	
	/**
	 * Set the delegate object which will handle drag operations. 
	 */
	public void setDragGesture(DragGesture dragGesture)
	{
		m_dragGesture = dragGesture;
	}
	
	public void onMouseDown(MouseEvent e)
	{
		e.stopPropagation();
		
		if (m_dragGesture != null)
		{
			m_mouseDown = e;
			m_dragGesture.start(e);
			m_previousEvent = e;
		}
	}
	
	public void onMouseMove(MouseEvent e)
	{
		if (m_mouseDown != null)
		{
			m_dragGesture.step(new DragEventImpl((MouseEventImpl) e, (MouseEventImpl) m_mouseDown, (MouseEventImpl) m_previousEvent));
			m_previousEvent = e;
		}
	}
	
	public void onMouseUp(MouseEvent e)
	{
		// it is possible to get a mouse up without a mouse down
		if (m_mouseDown != null)
		{
            // clear MD first to prevent reentrancy
            m_mouseDown = null;
            m_dragGesture.finish();
			m_previousEvent = null;
		}
	}
}
