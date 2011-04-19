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
import com.google.gwt.user.client.ui.Widget;

/**
 * A controller which:
 * <ul>
 * <li>converts native DOM mouse events to library {@link EventBase Event}s</li>
 * <li>implements event handler interfaces (i.e. handler adaptor) and provides
 * empty implementations of event methods</li>
 * <li>can delegate events to child handlers</li>
 * </ul>
 * <p>
 * <em>Note: child handlers should be added/removed <strong>before</strong> the controller 
 * is added to the widget so that events are properly sunk on the widget.<em>
 * <p>
 * TODO: add support for all event types
 */
public class EventController extends EventControllerBase implements Controller
{
	private final Class<? extends Controller> m_id;

	public EventController(Class<? extends Controller> id)
	{
		this(id, 0, null);
	}
	
	public EventController(Class<? extends Controller> id, EventHandler handler)
	{
		this(id, 0, handler);
	}
	
	public EventController(Class<? extends Controller> id, int eventBits)
	{
		this(id, eventBits, null);
	}
	
	/**
	 * @param eventBits additional events to sink, or to use the event bits
	 *            <code>0</code> from the handlers
	 * @param handler an PluggableEventHandler or <code>null</code>
	 */
	public EventController(Class<? extends Controller> id, int eventBits, EventHandler handler)
	{
		super(eventBits, handler);
		m_id = id;
	}
	
	public Class<? extends Controller> getId()
	{
		return m_id;
	}
	
	public void onBrowserEvent(Widget widget, Event event)
	{
		processEvent(convertEvent(widget, event, false));
	}
	
	@Override
	public String toString()
	{
		return "[id=" + String.valueOf(getId()) + ']';
	}
}
