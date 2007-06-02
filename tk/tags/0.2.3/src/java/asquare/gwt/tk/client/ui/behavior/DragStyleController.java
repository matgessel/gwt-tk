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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

/**
 * A controller which applies the specified style name to the widget. 
 */
public class DragStyleController extends ControllerAdaptor
{
	private final Widget m_target;
	private final String m_styleName;
	
	public DragStyleController(Widget target, String styleName)
	{
		super(Event.ONMOUSEDOWN | Event.ONMOUSEUP, DragStyleController.class);
		m_target = target;
		m_styleName = styleName;
	}
	
	public void onBrowserEvent(Widget widget, Event event)
	{
		if (DOM.eventGetType(event) == Event.ONMOUSEDOWN)
		{
			m_target.addStyleName(m_styleName);
		}
		else
		{
			m_target.removeStyleName(m_styleName);
		}
	}
}
