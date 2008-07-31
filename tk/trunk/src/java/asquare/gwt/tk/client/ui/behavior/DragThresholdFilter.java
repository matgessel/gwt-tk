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
 * prevent accidental manipulation or to pervent the wrapped gesture's start
 * action from being executed until the mouse is moved.
 */
public class DragThresholdFilter extends DragGestureWrapper
{
	private int m_threshold;
	private MouseEvent m_start;
	private boolean m_started = false;
	
	/**
	 * Creates a new DragGesture with the specified threshold.
	 * 
	 * @param distance the distance in screen pixels to move before taking an
	 *            action, or <code>0</code>
	 * @param gesture a delegate object
	 */
	public DragThresholdFilter(int distance, DragGesture delegate)
	{
		super(delegate);
		
		if (distance < 0)
			throw new IllegalArgumentException(String.valueOf(distance));
		
		m_threshold = distance;
	}

	public int getThreshold()
	{
		return m_threshold;
	}

	public void setThreshold(int threshold)
	{
		m_threshold = threshold;
	}
	
	protected boolean equalsThreshold(int deltaX, int deltaY)
	{
		return Math.abs(deltaX) >= m_threshold || Math.abs(deltaY) >= m_threshold;
	}
	
	public void start(MouseEvent e)
	{
		if (m_threshold == 0)
		{
			m_started = true;
			super.start(e);
		}
		else
		{
			m_start = e;
		}
	}
	
	public void step(DragEvent e)
	{
		if (! m_started && equalsThreshold(e.getCumulativeX(), e.getCumulativeY()))
		{
			m_started = true;
			super.start(m_start);
			m_start = null;
		}
		if (m_started)
		{
			super.step(e);
		}
	}
	
	public void finish()
	{
		if (m_started)
		{
			super.finish();
			m_started = false;
			m_start = null;
		}
	}
}
