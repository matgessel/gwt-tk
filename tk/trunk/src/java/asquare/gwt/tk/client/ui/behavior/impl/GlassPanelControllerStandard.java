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
package asquare.gwt.tk.client.ui.behavior.impl;

import asquare.gwt.tk.client.ui.behavior.ControllerAdaptor;
import asquare.gwt.tk.client.ui.behavior.GlassPanelController;
import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class GlassPanelControllerStandard extends ControllerAdaptor implements GlassPanelController, ResizeHandler
{
	private HandlerRegistration m_windowResizeRegistration;
	
	/*
	 * These track the last known scrollbar state. They are helpful in
	 * determining if the panel size needs to be updated when the window is
	 * resized.
	 */
	protected boolean m_xScroll;
	protected boolean m_yScroll;
	
	private Widget m_widget;
	
	public GlassPanelControllerStandard()
	{
		super(GlassPanelController.class);
	}
	
	@Override
	public void plugIn(Widget widget)
	{
		m_widget = widget;
		widget.setWidth(calculateWidth());
		widget.setHeight(calculateHeight());
		m_xScroll = canScrollX();
		m_yScroll = canScrollY();
		m_windowResizeRegistration = Window.addResizeHandler(this);
	}
	
	@Override
	public void unplug(Widget widget)
	{
		m_windowResizeRegistration.removeHandler();
		m_xScroll = m_yScroll = false;
		m_widget = null;
	}

	public Widget getWidget()
	{
		return m_widget;
	}
	
	public boolean canScrollX()
	{
		return DomUtil.getDocumentScrollWidth() > DomUtil.getViewportWidth();
	}
	
	public boolean canScrollY()
	{
		return DomUtil.getDocumentScrollHeight() > DomUtil.getViewportHeight();
	}
	
	public String calculateWidth()
	{
		if (canScrollX())
		{
			return DomUtil.getDocumentScrollWidth() + "px";
		}
		else
		{
			return "100%";
		}
	}
	
	public String calculateHeight()
	{
		if (canScrollY())
		{
			return DomUtil.getDocumentScrollHeight() + "px";
		}
		else
		{
			return "100%";
		}
	}
	
	protected void updateWidth()
	{
		boolean canScrollX = canScrollX();
		if (canScrollX != m_xScroll)
		{
			getWidget().setWidth(calculateWidth());
			m_xScroll = canScrollX;
		}
	}
	
	protected void updateHeight()
	{
		boolean canScrollY = canScrollY();
		// document height can change when width is resized, so always update if vertically scrollable
		if (canScrollY || canScrollY != m_yScroll)
		{
			getWidget().setHeight(calculateHeight());
			m_yScroll = canScrollY;
		}
	}
	
	public void onResize(ResizeEvent event)
	{
		updateWidth();
		updateHeight();
	}
}
