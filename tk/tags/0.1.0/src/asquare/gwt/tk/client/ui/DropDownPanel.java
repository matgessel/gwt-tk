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

import java.util.Iterator;
import java.util.Vector;

import asquare.gwt.tk.client.ui.impl.DropDownPanelImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A panel that consists of a hideable content DIV and an optional header DIV.
 * The user can click the header to show/hide the content with the default
 * settings. The panel is closed initially. It can be opened &amp; closed with
 * {@link #setOpen(boolean)}.
 * <p>
 * <em>Note: <code>width:1px;</code> will cancel <code>white-space:nowrap;</code> in IE6.   
 * Workaround: use <code>&amp;nbsp;</code> to prevent line wrapping in the header. </em>
 * </p>
 * <p>
 * <em>In IE6, changing the header's background image via CSS will cause the cursor to revert to 
 * <code>default</code>. The cursor CSS will be applied again when the cursor moves. </em> 
 * </p>
 * <h3>CSS Style Rules</h3>
 * <ul class='css'>
 * <li>.tk-DropDownPanel { the outer DIV }</li>
 * <li>.tk-DropDownPanelHeader { the header DIV }</li>
 * <li>.tk-DropDownPanelContent { the content DIV }</li>
 * <li>.tk-DropDownPanel-closed { this is applied to the outer DIV when it's
 * content child is hidden }</li>
 * <li>.tk-DropDownPanel-open { this is applied to the outer DIV when it's
 * content child is shown }</li>
 * </ul>
 */
public class DropDownPanel extends ComplexPanel
{
	protected static DropDownPanelImpl impl = (DropDownPanelImpl) GWT.create(DropDownPanelImpl.class);
	
	private final Vector m_listeners = new Vector();
	
	private final Element m_contentDiv;
	
	private Element m_headerDiv = null;
	
	private boolean m_interactive = true;
	
	private boolean m_open = false;
	
	/**
	 * Constructs an empty panel. You can customize it later with
	 * {@link #add(Widget)} and {@link #setHeaderText(String, boolean)}.
	 */
	public DropDownPanel()
	{
		this(null, null, false);
	}
	
	/**
	 * Constructs a new panel, adding a widget to the content area and
	 * optionally setting the header.
	 * 
	 * @param w the widget to be added
	 * @param text the header text associated with this widget, or null
	 * @param asHTML <code>true</code> to treat the specified text as HTML.
	 *            Ignored if <code>text</code> is <code>null</code>.
	 */
	public DropDownPanel(Widget w, String text, boolean asHTML)
	{
		Element outer = DOM.createDiv();
		setElement(outer);
		setStyleName("tk-DropDownPanel");
		addStyleName("tk-DropDownPanel-closed");
		
		m_contentDiv = DOM.createDiv();
		setStyleName(m_contentDiv, "tk-DropDownPanelContent", true);
		setVisible(m_contentDiv, false);
		DOM.appendChild(outer, m_contentDiv);
		
		if (w != null)
		{
			add(w);
		}
		
		if (text != null)
		{
			setHeaderText(text, asHTML);
		}
	}
	
	/**
	 * Add a listener to be notified when the content DIV is shown/hidden. 
	 */
	public void addDropDownListener(DropDownListener listener)
	{
		m_listeners.add(listener);
	}
	
	public void removeDropDownListener(DropDownListener listener)
	{
		m_listeners.remove(listener);
	}
	
	/**
	 * Adds a widget to the panel. Only one widget may be added. 
	 * 
	 * @param w the widget to be added
	 * @return <code>false</code> if a widget has already been added
	 */
	public boolean add(Widget w)
	{
		if (getWidgetCount() > 0)
			return false;
		
		super.add(w);
		DOM.appendChild(m_contentDiv, w.getElement());
		return true;
	}
	
	/**
	 * Has the header been specified?
	 * 
	 * @return false if no header
	 */
	public boolean hasHeader()
	{
		return m_headerDiv != null;
	}
	
	/**
	 * Sets the text in the header DIV. Creates the header if necessary. 
	 * Removes the header if null;
	 * 
	 * @param text the text to be associated with it, or null to remove the header
	 * @param asHTML <code>true</code> to treat the specified text as HTML
	 */
	public void setHeaderText(String text, boolean asHTML)
	{
		if (text != null)
		{
			if (m_headerDiv == null)
			{
				createHeader();
			}
			
			if (asHTML)
			{
				DOM.setInnerHTML(m_headerDiv, text);
			}
			else
			{
				DOM.setInnerText(m_headerDiv, text);
			}
		}
		else if (m_headerDiv != null)
		{
			removeHeader();
		}
	}
	
	private void createHeader()
	{
		assert m_headerDiv == null;
		
		m_headerDiv = impl.createHeaderElement();
		DOM.insertChild(getElement(), m_headerDiv, 0);
		setStyleName(m_headerDiv, "tk-DropDownPanelHeader", true);
		DOM.sinkEvents(m_headerDiv, Event.ONCLICK | Event.ONMOUSEDOWN);
	}
	
	private void removeHeader()
	{
		assert m_headerDiv != null;
		
		DOM.removeChild(getElement(), m_headerDiv);
	}
	
	public boolean remove(Widget w)
	{
		if (w.getParent() != this)
			throw new IllegalArgumentException();
		
		DOM.removeChild(m_contentDiv, w.getElement());
		return super.remove(w);
	}

	/**
	 * Sets the visibility of the content DIV. 
	 * 
	 * @param open <code>true</code> to show the content DIV
	 */
	public void setOpen(boolean open)
	{
		if (m_open != open)
		{
			m_open = open;
			
			setStyleName(getElement(), "tk-DropDownPanel-open", m_open);
			setStyleName(getElement(), "tk-DropDownPanel-closed", ! m_open);
			setVisible(m_contentDiv, m_open);
			if (m_open)
			{
				fireDropDownOpened();
			}
			else
			{
				fireDropDownClosed();
			}
		}
	}
	
	/**
	 * Is the content DIV currently visible? 
	 */
	public boolean isOpen()
	{
		return m_open;
	}
	
	/**
	 * Toggles the visibility of the content DIV. 
	 */
	public void toggleOpen()
	{
		setOpen(! m_open);
	}

	/**
	 * Will clicking in the header will toggle the content DIV?
	 * 
	 * @return true if header click processing is enabled
	 */
	public boolean isInteractive()
	{
		return m_interactive;
	}

	/**
	 * Sets whether clicking in the header will toggle the content DIV. 
	 * 
	 * @param interactive true to enable header click processing
	 */
	public void setInteractive(boolean interactive)
	{
		m_interactive = interactive;
	}
	
	private void fireDropDownOpened()
	{
		if (m_listeners.size() == 0)
			return;
		
		for (Iterator iter = m_listeners.iterator(); iter.hasNext();)
		{
			((DropDownListener) iter.next()).dropDownOpened(this);
		}
	}
	
	private void fireDropDownClosed()
	{
		if (m_listeners.size() == 0)
			return;
		
		for (Iterator iter = m_listeners.iterator(); iter.hasNext();)
		{
			((DropDownListener) iter.next()).dropDownClosed(this);
		}
	}
	
	/**
	 * Handles user interaction and prevents text selection in the header.
	 */
	public void onBrowserEvent(Event event)
	{
		if (m_headerDiv == null)
			return;
		
		int eventType = DOM.eventGetType(event);
		if (eventType == Event.ONCLICK || eventType == Event.ONMOUSEDOWN)
		{
			// drill up through containment hierarchy to see if event originated in the header DIV
			Element headerDivCandidate = DOM.eventGetTarget(event);
			while (headerDivCandidate != null)
			{
				if (DOM.compare(m_headerDiv, headerDivCandidate))
				{
					break;
				}
				headerDivCandidate = DOM.getParent(headerDivCandidate);
			}
			if (headerDivCandidate != null)
			{
				if (eventType == Event.ONCLICK && m_interactive)
				{
					toggleOpen();
				}
				if (eventType == Event.ONMOUSEDOWN)
				{
					// prevent text selection (works in Firefox & Opera)
					DOM.eventPreventDefault(event);
				}
			}
		}
	}
}
