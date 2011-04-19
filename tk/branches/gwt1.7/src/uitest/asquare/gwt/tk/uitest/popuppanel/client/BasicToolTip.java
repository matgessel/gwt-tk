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
package asquare.gwt.tk.uitest.popuppanel.client;

import asquare.gwt.tk.client.ui.CWindow;

import com.google.gwt.user.client.ui.Widget;

public class BasicToolTip extends CWindow implements ToolTipModel
{
	private final ToolTipRenderer m_renderer;
	
	private Widget m_toolTipWidget = null;
	private int m_mouseX;
	private int m_mouseY;
	private boolean m_widgetChanged = false;
	private boolean m_mouseChanged = false;
	
	public BasicToolTip()
	{
		this(new ToolTipRendererString());
	}
	
	public BasicToolTip(ToolTipRenderer renderer)
	{
		m_renderer = renderer;
		setStyleName("popup");
	}
	
	public Widget getToolTipWidget()
	{
		return m_toolTipWidget;
	}
	
	public void setToolTipWidget(Widget widget)
	{
		if (m_toolTipWidget != widget)
		{
			m_toolTipWidget = widget;
			m_widgetChanged = true;
		}
	}
	
	public void setMousePosition(int absX, int absY)
	{
		if (m_mouseX != absX || m_mouseY != absY)
		{
			m_mouseX = absX;
			m_mouseY = absY;
			m_mouseChanged = true;
		}
	}
	
	public void update()
	{
		if (m_mouseChanged)
		{
			setPosition(m_mouseX + 10, m_mouseY + 10);
			m_mouseChanged = false;
		}
		if (m_widgetChanged)
		{
			if (m_toolTipWidget != null)
			{
				setWidget(null);
				m_renderer.render(this, this);
				show();
			}
			else
			{
				hide();
			}
			m_widgetChanged = false;
		}
	}
	
	public boolean isShowing()
	{
		return isAttached();
	}
}
