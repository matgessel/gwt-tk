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

import com.google.gwt.user.client.Event;

public abstract class EventDelegateAdaptorBase
{
	private final int m_eventBits;
	
	/**
	 * Default constructor for convienence. Subclasses must override
	 * {@link #getEventBits()} if they want to process events.
	 */
	public EventDelegateAdaptorBase()
	{
		m_eventBits = 0;
	}
	
	/**
	 * Creates an event delegate which is interested in the specified bits.
	 * 
	 * @param eventBits a bitmask of the events to process
	 * @see Event
	 */
	public EventDelegateAdaptorBase(int eventBits)
	{
		m_eventBits = eventBits;
	}
	
	public int getEventBits()
	{
		return m_eventBits;
	}
}
