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
 * A DragGesture wrapper which which prevents the wrapped gesture's step action
 * from occuring until a specified distance is exceeded. This can be used to
 * prevent accidental manipulation or to separate actions which occur onMouseDown
 * from those that occur onDrag.
 */
public class DragThresholdFilter extends DragGestureWrapper
{
	private final int m_threshold;
	
	private boolean m_started = false;
	private int m_startX, m_startY;
	
	/**
	 * Creates a new DragGesture which prevents . The DragGesture is not activated until the mouse movement
	 * equals the specified distance; this prevents accidental movement.
	 * 
	 * @param distance the distance in screen pixels to move before taking an
	 *            action, or <code>0</code>
	 * @param gesture a delegate object
	 */
	public DragThresholdFilter(DragGesture delegate, int distance)
	{
		super(delegate);
		
		if (distance < 0)
			throw new IllegalArgumentException(String.valueOf(distance));
		
		m_threshold = distance;
	}
	
	private boolean equalsThreshold(int x, int y)
	{
		return Math.abs(x - m_startX) >= m_threshold || Math.abs(y - m_startY) >= m_threshold;
	}
	
	public void start(int x, int y)
	{
		m_startX = x;
		m_startY = y;
		if (m_threshold == 0)
		{
			m_started = true;
			super.start(x, y);
		}
	}
	
	public void step(int x, int y)
	{
		if (! m_started && equalsThreshold(x, y))
		{
			m_started = true;
			super.start(x, y);
		}
		if (m_started)
		{
			super.step(x, y);
		}
	}
	
	public void finish(int x, int y)
	{
		super.finish(x, y);
		m_started = false;
	}
}
