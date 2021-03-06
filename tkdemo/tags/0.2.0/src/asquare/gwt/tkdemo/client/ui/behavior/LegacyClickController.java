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
package asquare.gwt.tkdemo.client.ui.behavior;

import asquare.gwt.tk.client.ui.behavior.ControllerAdaptor;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.*;

/**
 * A controller to drive legacy GWT mouse event listeners. 
 */
public class LegacyClickController extends ControllerAdaptor implements SourcesClickEvents
{
	private ClickListenerCollection m_listeners;
	
	public LegacyClickController()
	{
		this(null);
	}
	
	public LegacyClickController(ClickListenerCollection listeners)
	{
		super(Event.ONCLICK, LegacyClickController.class);
		m_listeners = listeners;
	}
	
	public boolean doBrowserEvent(Widget widget, Event event)
	{
		if (m_listeners != null)
			m_listeners.fireClick(widget);
		return true;
	}

	// SourcesClickEvents methods
	public void addClickListener(ClickListener listener)
	{
		if (m_listeners == null)
			m_listeners = new ClickListenerCollection();
		m_listeners.add(listener);
	}

	public void removeClickListener(ClickListener listener)
	{
		if (m_listeners != null)
			m_listeners.remove(listener);
	}
}
