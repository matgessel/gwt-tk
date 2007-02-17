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

import java.util.List;

import asquare.gwt.tk.client.ui.CComplexPanel;
import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.impl.PopupImpl;

/**
 * A panel which positions itself absolutely in the parent's coordinate space.
 */
public class AbsolutePositionPanel extends CComplexPanel
{
	private static PopupImpl s_impl = (PopupImpl) GWT.create(PopupImpl.class);
	
	public AbsolutePositionPanel(Element element)
	{
		this(element, null);
	}
	
	public AbsolutePositionPanel(Element element, List controllers)
	{
		super(element, controllers);
		DomUtil.setStyleAttribute(this, "position", "absolute");
	}
	
	public int getLeft()
	{
		return DomUtil.getIntAttribute(this, "offsetLeft");
	}
	
	public void setLeft(int left)
	{
		DomUtil.setPixelStyleAttribute(this, "left", left);
	}
	
	public int getTop()
	{
		return DomUtil.getIntAttribute(this, "offsetTop");
	}
	
	public void setTop(int top)
	{
		DomUtil.setPixelStyleAttribute(this, "top", top);
	}
	
	public void setPosition(int left, int top)
	{
		Element element = getElement();
		DOM.setStyleAttribute(element, "left", left + "px");
		DOM.setStyleAttribute(element, "top", top + "px");
	}
	
	public void add(Widget w)
	{
		add(w, getElement());
	}
	
	public void show()
	{
		RootPanel.get().add(this);
		s_impl.onShow(getElement());
	}
	
	public void hide()
	{
		s_impl.onHide(getElement());
		RootPanel.get().remove(this);
	}
}
