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
package asquare.gwt.tk.uitest.popuppanel.client.junk;

import asquare.gwt.tk.client.ui.behavior.ControllerAdaptor;
import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class ToolTipPositionController extends ControllerAdaptor
{
	private final PopupPanel m_toolTip;
	
	public ToolTipPositionController(PopupPanel toolTip)
	{
		super(Event.ONMOUSEMOVE, ToolTipPositionController.class);
		m_toolTip = toolTip;
	}
	
	public void onBrowserEvent(Widget widget, Event event)
	{
		m_toolTip.setPopupPosition(DomUtil.eventGetAbsoluteX(event) + 5, DomUtil.eventGetAbsoluteY(event) + 5);
	}
}
