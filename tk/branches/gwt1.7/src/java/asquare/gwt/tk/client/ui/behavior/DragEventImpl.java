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

public class DragEventImpl extends MouseEventImpl implements DragEvent
{
	private static final long serialVersionUID = 1L;
	
	private final int m_deltaX, m_deltaY;
	private final int m_cumulativeX, m_cumulativeY;
	
	public DragEventImpl(MouseEventImpl mouseMove, MouseEventImpl mouseDown, MouseEventImpl previousEvent)
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
			m_cumulativeX = mouseMove.getAbsoluteX() - mouseDown.getAbsoluteX();
			m_cumulativeY = mouseMove.getAbsoluteY() - mouseDown.getAbsoluteY();
		}
		else
		{
			m_deltaX = m_deltaY = 0;
			m_cumulativeX = m_cumulativeY = 0;
		}
	}

	public int getDeltaX()
	{
		return m_deltaX;
	}

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
	
	@Override
	protected String getPrettyName()
	{
		return "MouseDrag";
	}
	
	@Override
	protected String dumpProperties()
	{
		return "delta(" + m_deltaX + ',' + m_deltaY + "),cumulative(" + m_cumulativeX + ',' + m_cumulativeY + ")," + super.dumpProperties();
	}
}
