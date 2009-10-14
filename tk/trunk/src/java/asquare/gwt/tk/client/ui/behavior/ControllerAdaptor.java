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
package asquare.gwt.tk.client.ui.behavior;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

/**
 * This adaptor class provides stub implementations for Controller methods. Most
 * controllers will be interested in {@link #plugIn(Widget)}, {@link #unplug(Widget)}
 * or 
 * {@link asquare.gwt.tk.client.ui.behavior.EventDelegate#onBrowserEvent(Widget, Event) onBrowserEvent()}.
 */
public abstract class ControllerAdaptor extends PluggableEventHandlerAdaptor implements Controller
{
	private final Class<? extends Controller> m_id;
	
	/**
	 * Creates a ControllerAdaptor with the specified id. 
	 * 
	 * @param id the controller id
	 */
	public ControllerAdaptor(Class<? extends Controller> id)
	{
		this(id, 0);
	}
	
	/**
	 * @deprecated
	 * @see #ControllerAdaptor(Class, int)
	 */
	public ControllerAdaptor(int eventBits, Class<? extends Controller> id)
	{
		this(id, eventBits);
	}
	
	/**
	 * Creates a ControllerAdaptor with the specified event mask and id.
	 * 
	 * @param id the controller id
	 * @param eventBits a bitmask representing the events this controller is
	 *            interested in
	 */
	public ControllerAdaptor(Class<? extends Controller> id, int eventBits)
	{
		super(eventBits);
		m_id = id;
	}
	
	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.Controller#getId()
	 */
	public Class<? extends Controller> getId()
	{
		return m_id;
	}
	
	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.EventDelegate#onBrowserEvent(com.google.gwt.user.client.ui.Widget, com.google.gwt.user.client.Event)
	 */
	public void onBrowserEvent(Widget widget, Event event)
	{
		if (! doBrowserEvent(widget, event))
		{
			DOM.eventPreventDefault(event);
			DOM.eventCancelBubble(event, true);
		}
	}
	
	/**
	 * A convenience method for processing events. The event is cancelled if the
	 * return value is false.
	 * 
	 * @return false to cancel the event
	 */
	protected boolean doBrowserEvent(Widget widget, Event event)
	{
		return true;
	}
	
	public String toString()
	{
		return "[id=" + m_id + ",eventBits=" + getEventBits() + ']';
	}
}
