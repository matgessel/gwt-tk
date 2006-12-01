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
package asquare.gwt.tk.client.ui;

import asquare.gwt.tk.client.ui.EventWrapper;
import asquare.gwt.tk.client.ui.behavior.EventDelegate;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

/**
 * A widget wrapper which delegates events to an
 * {@link asquare.gwt.tk.client.ui.behavior.EventDelegate EventDelegate}. The
 * wrapped widget will allowed to process events before they are passed to the
 * delegate.
 */
public class DelegatingWrapper extends EventWrapper
{
	private final EventDelegate m_delegate;
		
	/**
	 * @throws IllegalArgumentException if <code>widget</code> or <code>delegate</code> are null
	 */
	public DelegatingWrapper(Widget widget, EventDelegate delegate)
	{
		if (widget == null || delegate == null)
			throw new IllegalArgumentException();
		
		m_delegate = delegate;
		initWidget(widget);
		DOM.sinkEvents(widget.getElement(), delegate.getEventBits());
	}
	
	/*
	 *  (non-Javadoc)
	 * @see com.google.gwt.user.client.EventListener#onBrowserEvent(com.google.gwt.user.client.Event)
	 */
	public void onBrowserEvent(Event event)
	{
		super.onBrowserEvent(event);
		if ((m_delegate.getEventBits() & DOM.eventGetType(event)) != 0)
		{
			m_delegate.onBrowserEvent(getWidget(), event);
		}
	}
}
