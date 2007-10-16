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
package asquare.gwt.sb.client.ui;

import asquare.gwt.tk.client.ui.*;
import asquare.gwt.tk.client.util.*;

import com.google.gwt.core.client.*;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.impl.*;

public class CWindow extends CWrapper
{
	private static final PopupImpl s_popupImpl = (PopupImpl) GWT.create(PopupImpl.class);
	
	private final SimplePanel m_panel;
	private final HasWidgets m_parent;
    
	public CWindow()
	{
		this(null);
	}

	public CWindow(HasWidgets parent)
	{
		super(new SimplePanel(s_popupImpl.createElement()) {});
		m_panel = (SimplePanel) getWidget();
		m_parent = parent;
        DOM.setStyleAttribute(getElement(), "position", "absolute");
	}

	public int getLeft()
	{
        return DomUtil.getPixelStyleAttribute(this, "offsetLeft");
	}

	public void setLeft(int left)
	{
		DomUtil.setPixelStyleAttribute(this, "left", left);
	}

	public int getTop()
	{
		return DomUtil.getPixelStyleAttribute(this, "offsetTop");
	}

	public void setTop(int top)
	{
		DomUtil.setPixelStyleAttribute(this, "top", top);
	}
	
	public void setPosition(int left, int top)
	{
		setLeft(left);
		setTop(top);
	}

	public int getZIndex()
	{
		return DomUtil.getIntStyleAttribute(this, "zIndex");
	}

	public void setZIndex(int zIndex)
	{
		DomUtil.setPixelStyleAttribute(this, "zIndex", zIndex);
	}
	
	public void show()
	{
		((m_parent != null) ? m_parent : RootPanel.get()).add(this);
	}
	
	public void hide()
	{
		((m_parent != null) ? m_parent : RootPanel.get()).remove(this);
	}
	
	public void setWidget(Widget widget)
	{
		m_panel.setWidget(widget);
	}
	
	protected void onAttach()
	{
		if (isAttached())
			return;
		
		super.onAttach();
		s_popupImpl.onShow(getElement());
	}
	
	protected void onDetach()
	{
		if (! isAttached())
			return;
		
		super.onDetach();
		s_popupImpl.onHide(getElement());
	}
}
