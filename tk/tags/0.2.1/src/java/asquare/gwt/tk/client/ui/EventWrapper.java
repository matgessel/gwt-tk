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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * A widget wrapper which can process the
 * wrapped widget's events. 
 */
public abstract class EventWrapper extends Composite
{
	private Widget m_widget;
	
	public EventWrapper()
	{
		
	}
	
	public EventWrapper(Widget w, int eventMask)
	{
		initWidget(w);
		sinkEvents(eventMask);
	}
	
	protected void initWidget(Widget widget)
	{
		super.initWidget(widget);
		m_widget = widget;
	}
	
	protected Widget getWidget()
	{
		return m_widget;
	}
	
	/*
	 *  (non-Javadoc)
	 * Hijacks events intended for the wrapped widget's element. 
	 * @see com.google.gwt.user.client.ui.Widget#onAttach()
	 */
	protected void onAttach()
	{
		super.onAttach();
		DOM.setEventListener(getElement(), this);
	}
	
	/**
	 * Override to process browser events. Don't forget to call super
	 * implementation if you want the wrapped widget to process events also.
	 * {@inheritDoc}
	 */
	public void onBrowserEvent(Event event)
	{
		m_widget.onBrowserEvent(event);
	}
}
