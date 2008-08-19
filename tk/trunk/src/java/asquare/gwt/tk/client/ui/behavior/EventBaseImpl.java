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

import java.util.EventObject;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class EventBaseImpl extends EventObject implements EventBase
{
	private static final long serialVersionUID = 1L;

	private final Event m_domEvent;
	private final Element m_target;
	private final int m_type;
	private final boolean m_previewPhase; // true if this event is in the preview cycle
	
	private boolean m_killPreviewEvent = false;
	
	protected EventBaseImpl(EventBaseImpl e)
	{
		super(e.getSource());
		m_domEvent = e.m_domEvent;
		m_target = e.m_target;
		m_type = e.m_type;
		m_previewPhase = e.m_previewPhase;
	}

	public EventBaseImpl(Widget source, Event domEvent, int type, boolean previewPhase)
	{
		super(source);
		m_domEvent = domEvent;
		m_target = DOM.eventGetTarget(domEvent);
		m_type = type;
		m_previewPhase = previewPhase;
	}

	/**
	 * JDK15: change to override and narrow return type
	 */
	public Widget getSourceWidget()
	{
		return (Widget) getSource();
	}

	public Event getDomEvent()
	{
		return m_domEvent;
	}

	public Element getTarget()
	{
		return m_target;
	}
	
	public Element getCurrentTarget()
	{
		return DOM.eventGetCurrentTarget(m_domEvent);
	}
	
	public int getType()
	{
		return m_type;
	}
	
	public boolean isPreviewPhase()
	{
		return m_previewPhase;
	}
	
	public boolean didEventOccurIn(UIObject uio)
	{
		return didEventOccurIn(uio.getElement());
	}

	public boolean didEventOccurIn(Element e)
	{
		return DOM.isOrHasChild(e, m_target);
	}
	
	public void killPreviewEvent()
	{
		if (! m_previewPhase)
			throw new IllegalStateException();
		
		m_killPreviewEvent = true;
	}

	public void stopPropagation()
	{
		if (m_previewPhase)
			throw new IllegalStateException();
		
		DOM.eventCancelBubble(m_domEvent, true);
	}
	
	public boolean isKillPreviewEvent()
	{
		return m_killPreviewEvent;
	}
	
	public String toString()
	{
		return getPrettyName() + '[' + dumpProperties() + ']';
	}
	
	protected String getPrettyName()
	{
		return DOM.eventGetTypeString(m_domEvent);
	}
	
	protected String dumpProperties()
	{
		return "preview=" + m_previewPhase;
	}
}
