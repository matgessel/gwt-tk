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

import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.*;

/**
 * Just a simple anchor. No DIV, no history tokens showing up in the browser URL. 
 * 
 * <h3>CSS Style Rules</h3>
 * <ul class='css'>
 * <li>.tk-SimpleHyperLink { }</li>
 * </ul>
 */
public class SimpleHyperLink extends Widget implements HasText, HasHTML, SourcesClickEvents
{
	private ClickListenerCollection m_listeners;
	
	public SimpleHyperLink()
	{
		this(null, null);
	}
	
	public SimpleHyperLink(String text)
	{
		this(text, null);
	}
	
	/**
	 * Constructs a new SimpleHyperLink
	 * 
	 * @param text a String or null
	 * @param clickListener a ClickListener or null
	 */
	public SimpleHyperLink(String text, ClickListener clickListener)
	{
		setElement(DOM.createAnchor());
		
		// prevents text selection by double-click
		DomUtil.setAttribute(this, "href", "#");
		
		sinkEvents(Event.ONCLICK);
		
		setStyleName("tk-SimpleHyperLink");
		
		if (text != null)
		{
			setText(text);
		}
		
		if (clickListener != null)
		{
			addClickListener(clickListener);
		}
	}
	
	public void onBrowserEvent(Event event)
	{
		if (DOM.eventGetType(event) == Event.ONCLICK)
		{
			if (m_listeners != null)
			{
				m_listeners.fireClick(this);
			}
			// keep '#' out of the location bar
			DOM.eventPreventDefault(event);
		}
	}
	
	// HasText methods
	public void setText(String text)
	{
		DOM.setInnerText(getElement(), text);
	}
	
	public String getText()
	{
		return DOM.getInnerText(getElement());
	}
	
	// HasHTML mehtods
	public String getHTML()
	{
		return DOM.getInnerHTML(getElement());
	}

	public void setHTML(String html)
	{
		DOM.setInnerHTML(getElement(), html);
	}
	
	// SourcesClickEvents methods
	public void addClickListener(ClickListener listener)
	{
		if (m_listeners == null)
		{
			m_listeners = new ClickListenerCollection();
		}
		m_listeners.add(listener);
	}

	public void removeClickListener(ClickListener listener)
	{
		if (m_listeners != null)
		{
			m_listeners.remove(listener);
		}
	}
}
