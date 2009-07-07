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

@SuppressWarnings("deprecation")
public abstract class EventDelegateAdaptor extends EventInterestAdaptor implements BrowserEventHandler, EventDelegate
{
	/**
	 * Default constructor for convienence. Subclasses must override
	 * {@link #getEventBits()} if they want to process events.
	 */
	public EventDelegateAdaptor()
	{
		this(0);
	}
	
	/**
	 * Creates an event delegate which is interested in the specified bits.
	 * 
	 * @param eventBits a bitmask of the events to process
	 * @see Event
	 */
	public EventDelegateAdaptor(int eventBits)
	{
		super(eventBits);
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
}
