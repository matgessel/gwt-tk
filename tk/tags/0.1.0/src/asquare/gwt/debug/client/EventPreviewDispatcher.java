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
package asquare.gwt.debug.client;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventPreview;

/**
 * The {@link EventPreview} facility adds listeners to a stack, and only one the
 * listener on top of the stack recieves event preview. This class distributes
 * events to 1 or more listeners if it is on top of the stack. It will be pushed
 * down the stack if a popup or dialog is shown.
 */
public final class EventPreviewDispatcher implements EventPreview
{
	private static EventPreviewDispatcher s_instance = new EventPreviewDispatcher();
	
	private final ArrayList m_listeners = new ArrayList();
	
	private boolean m_installed = false;
	
	private EventPreviewDispatcher() {}
	
	/**
	 * Adds a listener. If this is the first listener the dispatcher will be
	 * added to the top of the event preview stack.
	 * 
	 * @param listener
	 */
	public static void addListener(DebugEventListener listener)
	{
		s_instance.m_listeners.add(listener);
		if (! s_instance.m_installed)
		{
			DOM.addEventPreview(s_instance);
			s_instance.m_installed = true;
		}
	}
	
	/**
	 * Removes a listener. If this is only listener the dispatcher will be
	 * removed from the event preview stack.
	 * 
	 * @param listener
	 */
	public static void removeListener(DebugEventListener listener)
	{
		s_instance.m_listeners.remove(listener);
		if (s_instance.m_listeners.size() == 0)
		{
			DOM.removeEventPreview(s_instance);
			s_instance.m_installed = false;
		}
	}
	
	// EventPreview listener method
	public boolean onEventPreview(Event event)
	{
		if (m_listeners.size() > 0)
		{
			for (Iterator iter = m_listeners.iterator(); iter.hasNext();)
			{
				DebugEventListener listener = (DebugEventListener) iter.next();
				listener.eventDispatched(event);
			}
		}
		return true;
	}
}
