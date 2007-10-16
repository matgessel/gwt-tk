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
 * Contains information used by drag operations to position elements in the UI.
 * Different drag operations require different coordinates, so we pass all
 * coordinates to operations using this object. Also encapsulates coordinate
 * calulations.
 */
/*
 * Extends MouseEvent so that both types may be used polymorphically. For
 * example, a slider will process both mouse down and mouse drag (but not mouse
 * move).
 */
public class DragEvent extends MouseEvent
{
	private static final long serialVersionUID = -60876731058183754L;
	
	private final int m_deltaX, m_deltaY;
	private final int m_cumulativeX, m_cumulativeY;
	
	/**
	 * @param receiver the widget which is receiving the mouse event
	 * @param mouseEvent a <code>mousedown</code>, <code>mousemove</code> or <code>mouseup</code> event
	 * @param previousEvent
	 */
	public DragEvent(MouseEvent mouseMove, MouseEvent mouseDown, MouseEvent previousEvent)
	{
		super(mouseMove);
		
		if (previousEvent != null)
		{
			/*
			 * Calculate the delta relative to the last event, adjusting for a
			 * possibly moving coordinate space
			 */
			m_deltaX = mouseMove.getWidgetLeft() - previousEvent.getWidgetLeft() + mouseMove.getWidgetX() - previousEvent.getWidgetX();
			m_deltaY = mouseMove.getWidgetTop() - previousEvent.getWidgetTop() + mouseMove.getWidgetY() - previousEvent.getWidgetY();
			m_cumulativeX = mouseMove.getWidgetX() - mouseDown.getWidgetX();
			m_cumulativeY = mouseMove.getWidgetY() - mouseDown.getWidgetY();
		}
		else
		{
			m_deltaX = m_deltaY = 0;
			m_cumulativeX = m_cumulativeY = 0;
		}
	}

	/**
	 * Get the distance moved horizontally, relative to the last event. Returns
	 * a useful value even if the document is scrolled or the widget has moved
	 * since the last event.
	 */
	public int getDeltaX()
	{
		return m_deltaX;
	}

	/**
	 * Get the distance moved vertically, relative to the last event. Returns
	 * a useful value even if the document is scrolled or the widget has moved
	 * since the last event.
	 */
	public int getDeltaY()
	{
		return m_deltaY;
	}

	public int getCumulativeX()
	{
		return m_cumulativeX;
	}

	public int getCumulativeY()
	{
		return m_cumulativeY;
	}
}
