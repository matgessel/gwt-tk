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

import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.user.client.ui.UIObject;

public abstract class AdjustObjectGesture implements DragGesture
{
	private UIObject m_outer;
	private UIObject m_content;
	
	protected AdjustObjectGesture()
	{
		this(null, null);
	}
	
	/**
	 * Note: <code>outer</code> and <code>content</code> may be the same Widget
	 * 
	 * @param outer the floating container (for left and top)
	 * @param content the content (for width & height)
	 * @param styleName the CSS class to be applied to the widget while resizing, or <code>null</code>
	 */
	protected AdjustObjectGesture(UIObject outer, UIObject content)
	{
		m_outer = outer;
		m_content = content;
	}
	
	public UIObject getDragObject()
	{
		return m_outer;
	}
	
	public void setDragObject(UIObject outer)
	{
		m_outer = outer;
	}
	
	public UIObject getSizeObject()
	{
		return m_content;
	}
	
	public void setSizeObject(UIObject content)
	{
		m_content = content;
	}
	
	/**
	 * @param delta the difference from the original width to the current width
	 * @return <code>true</code> if the width has changed
	 */
	protected boolean setWidth(int delta)
	{
		if (delta != 0)
		{
			int width = m_content.getOffsetWidth();
			m_content.setWidth(width + delta + "px");
			return m_content.getOffsetWidth() != width;
		}
		return false;
	}
	
	/**
	 * @param delta the difference from the original height to the current height
	 * @return <code>true</code> if the height has changed
	 */
	protected boolean setHeight(int delta)
	{
		if (delta != 0)
		{
			int height = m_content.getOffsetHeight();
			m_content.setHeight(height + delta + "px");
			return m_content.getOffsetHeight() != height;
		}
		return false;
	}
	
	/**
	 * @param delta the difference from the original left to the current left
	 */
	protected void setLeft(int delta)
	{
		if (delta != 0)
		{
			int offsetLeft = DomUtil.getIntAttribute(m_outer, "offsetLeft");
			DomUtil.setPixelStyleAttribute(m_outer, "left", offsetLeft + delta);
//			Debug.println("oldLeft: " + offsetLeft + ", delta: " + delta + ", newLeft: " + (offsetLeft + delta));
		}
	}
	
	/**
	 * @param delta the difference from the original top to the current top
	 */
	protected void setTop(int delta)
	{
		if (delta != 0)
		{
			int offsetTop = DomUtil.getIntAttribute(m_outer, "offsetTop");
			DomUtil.setPixelStyleAttribute(m_outer, "top", offsetTop + delta);
		}
	}
	
	public void start(MouseEvent dragEvent)
	{
	}
	
	public void finish()
	{
	}
}
