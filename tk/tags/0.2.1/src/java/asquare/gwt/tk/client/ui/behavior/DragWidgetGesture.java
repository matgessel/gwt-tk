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

import com.google.gwt.user.client.ui.Widget;

/**
 * A DragGesture which positions a target widget when the source widget is
 * dragged.
 */
public class DragWidgetGesture implements DragGesture
{
	private final Widget m_target;
	
	private int m_startX, m_startY;
	private int m_startLeft, m_startTop;
	
	/**
	 * @param target the widget to be dragged
	 */
	public DragWidgetGesture(Widget target)
	{
		m_target = target;
	}
	
	protected Widget getDragWidget()
	{
		return m_target;
	}
	
	/**
	 * A <em>template method</em> which sets the position of the target widget
	 * to the specified coordinates. Coordinates are in the document's
	 * coordinate space (i.e. absolute position).
	 * 
	 * @param left the x coordinate in pixels
	 * @param top the y coordinate in pixels
	 */
	protected void setTargetPosition(int left, int top)
	{
		DomUtil.setStyleAttribute(m_target, "position", "absolute");
		DomUtil.setStyleAttribute(m_target, "left", left + "px");
		DomUtil.setStyleAttribute(m_target, "top", top + "px");
	}
	
	// DragGesture methods
	public void start(int x, int y)
	{
//		Debug.println("start(" + x + "," + y + ")");
		m_startLeft = m_target.getAbsoluteLeft();
		m_startTop = m_target.getAbsoluteTop();
		m_startX = x + m_startLeft;
		m_startY = y + m_startTop;
		m_target.addStyleName("Dragging");
	}
	
	public void step(int x, int y)
	{
//		Debug.println("step(" + x + "," + y + ")");
		int absX = x + m_target.getAbsoluteLeft();
		int absY = y + m_target.getAbsoluteTop();
		int deltaX = absX - m_startX;
		int deltaY = absY - m_startY;
//		Debug.println("setAbsolutePosition(" + (m_startLeft + deltaX) + ',' + (m_startTop + deltaY) + ')');
		setTargetPosition(m_startLeft + deltaX, m_startTop + deltaY);
	}
	
	public void finish(int x, int y)
	{
//		Debug.println("finish(" + x + "," + y + ")");
		m_target.removeStyleName("Dragging");
	}
}
