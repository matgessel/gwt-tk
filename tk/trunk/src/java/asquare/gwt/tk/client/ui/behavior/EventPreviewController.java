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
import com.google.gwt.user.client.EventPreview;
import com.google.gwt.user.client.ui.Widget;

/**
 * A controller which converts native
 * {@link DOM#addEventPreview(EventPreview) EventPreview} events to framework
 * {@link MouseEvent}s. The controller also handles standard flow
 * events. 
 * 
 * @see EventBase#isPreviewPhase()
 * @see EventBase#killPreviewEvent()
 * @deprecated
 */
public class EventPreviewController extends EventController implements EventPreview
{
	public EventPreviewController(Class<? extends Controller> id)
	{
		this(id, 0, null);
	}
	
	public EventPreviewController(Class<? extends Controller> id, EventHandler handler)
	{
		this(id, 0, handler);
	}
	
	public EventPreviewController(Class<? extends Controller> id, int eventBits)
	{
		this(id, eventBits, null);
	}
	
	public EventPreviewController(Class<? extends Controller> id, int eventBits, EventHandler handler)
	{
		super(id, eventBits, handler);
	}
	
	public boolean shouldProcessNormalEvents()
	{
		return true;
	}

	public void onBrowserEvent(Widget widget, Event event)
	{
		if (shouldProcessNormalEvents())
		{
			super.onBrowserEvent(widget, event);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @throws IllegalStateException if the controller has EventPreview but its
	 *             widget is no longer attached
	 */
    public boolean onEventPreview(Event event)
	{
		if ((DOM.eventGetType(event) & getEventBits()) != 0)
		{
			EventBase event0 = convertEvent(getPluggedInWidget(), event, true);
			processEvent(event0);
			return ! event0.isKillPreviewEvent();
		}
		return true;
	}
}
