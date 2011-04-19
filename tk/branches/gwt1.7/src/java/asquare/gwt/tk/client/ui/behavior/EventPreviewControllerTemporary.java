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

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.Widget;

/**
 * A controller which converts native
 * {@link DOM#addEventPreview(EventPreview) EventPreview} events to framework
 * {@link MouseEvent}s. It is often cleaner to use an independent controller
 * for preview events since events may be treated differently.
 */
public class EventPreviewControllerTemporary extends EventControllerBase implements NativePreviewHandler
{
	private HandlerRegistration m_registration;
	
	public EventPreviewControllerTemporary()
	{
		this(0, null);
	}
	
	public EventPreviewControllerTemporary(int eventBits)
	{
		this(eventBits, null);
	}
	
	public EventPreviewControllerTemporary(int eventBits, EventHandler handler)
	{
		super(eventBits, handler);
	}
	
	public void start(Widget widget)
	{
		if (widget == null)
			throw new NullPointerException();
		
		plugIn(widget);
		m_registration = Event.addNativePreviewHandler(this);
	}
	
	public void stop()
	{
		unplug(getPluggedInWidget());
	}
	
	@Override
	public void unplug(Widget widget)
	{
        m_registration.removeHandler();
        m_registration = null;
		super.unplug(widget);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @throws IllegalStateException if the controller has EventPreview but its
	 *             widget is no longer attached
	 */
    public void onPreviewNativeEvent(Event.NativePreviewEvent event)
	{
    	if ((event.getTypeInt() & getEventBits()) != 0)
		{
			EventBase event0 = convertEvent(getPluggedInWidget(), Event.as(event.getNativeEvent()), true);
			processEvent(event0);
			if (event0.isKillPreviewEvent())
			{
				event.cancel();
			}
		}
	}
}
