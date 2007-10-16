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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

import asquare.gwt.tk.client.ui.behavior.ControllerAdaptor;

public class PreventBubbleController extends ControllerAdaptor
{
	private int m_eventBits;
	
	public PreventBubbleController()
	{
		this(0);
	}
	
	public PreventBubbleController(int eventBits)
	{
		super(PreventBubbleController.class);
		m_eventBits = eventBits;
	}
	
	public int getEventBits()
	{
		return m_eventBits;
	}
	
	/**
	 * Supports creation via default constructor &amp; {@link GWT#create(Class)}.
	 * <strong>Note: set event bits before attaching to a widget so that the
	 * events will be sunk</strong>
	 * 
	 * @param eventBits
	 */
	public void setEventBits(int eventBits)
	{
		m_eventBits = eventBits;
	}
	
	public void onBrowserEvent(Widget widget, Event event)
	{
		DOM.eventCancelBubble(event, true);
	}
}
