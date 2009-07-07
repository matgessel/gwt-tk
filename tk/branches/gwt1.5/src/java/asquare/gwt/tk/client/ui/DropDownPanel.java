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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import asquare.gwt.tk.client.ui.behavior.ControlSurfaceController;
import asquare.gwt.tk.client.ui.behavior.Controller;
import asquare.gwt.tk.client.ui.behavior.ControllerAdaptor;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * A panel that consists of a hideable content DIV and an optional header DIV.
 * By default, the user can click the header to show/hide the content. 
 * The panel is closed initially. It can be opened &amp; closed with
 * {@link #setOpen(boolean)}.
 * 
 * <p>Usage Notes</p>
 * <ul>
 * <li><code>width:1px</code> will cancel <code>white-space:nowrap</code> in IE6. <br/>
 * Workaround: use <code>&amp;nbsp;</code> to prevent line wrapping in the header.</li>
 * <li>In IE6, changing the header's background image via CSS will cause the cursor to revert to 
 * <code>default</code>. The cursor CSS will be applied as soon as the cursor is moved. </li>
 * </ul>
 * 
 * <h3>CSS Style Rules</h3>
 * <ul class='css'>
 * <li>.tk-DropDownPanel { the outer DIV }</li>
 * <li>.tk-DropDownPanelHeader { the header DIV }</li>
 * <li>.tk-DropDownPanelContent { the content DIV }</li>
 * <li>.tk-DropDownPanel-open { this is applied to the outer DIV when it's
 * content child is visible }</li>
 * </ul>
 */
public class DropDownPanel extends CComplexPanel
{
	public static final String PROPERTY_OPEN = "DropDownPanel-open";
	public static final String PROPERTY_INTERACTIVE = "DropDownPanel-interactive";
	
	private final ArrayList<DropDownListener> m_listeners = new ArrayList<DropDownListener>();
	private final Element m_contentDiv;
	private HTML m_header = null;
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
	 * @param w the widget to be added, or null
	 * @param text the header text associated with this widget, or null
	 * @param asHTML <code>true</code> to treat the specified text as HTML.
	 *            Ignored if <code>text</code> is <code>null</code>.
	 */
	public DropDownPanel(Widget w, String text, boolean asHTML)
	{
		super(DOM.createDiv());
		setStyleName("tk-DropDownPanel");
		
		m_contentDiv = DOM.createDiv();
		setStyleName(m_contentDiv, "tk-DropDownPanelContent", true);
		setVisible(m_contentDiv, false);
		DOM.appendChild(getElement(), m_contentDiv);
		
		if (w != null)
		{
			add(w);
		}
		
		if (text != null)
		{
			setHeaderText(text, asHTML);
		}
	}
	
	protected List<Controller> createHeaderControllers()
	{
		List<Controller> result = new ArrayList<Controller>();
		result.add(new OpenerController(this));
		result.add(ControlSurfaceController.getInstance());
		return result;
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
	 */
	public void add(Widget w)
	{
		super.add(w, m_contentDiv);
	}
	
	/**
	 * Has the header been specified?
	 * 
	 * @return false if no header
	 */
	public boolean hasHeader()
	{
		return m_header != null;
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
		if (text == null)
		{
			if (m_header != null)
			{
				super.remove(m_header);
			}
		}
		else
		{
			if (m_header == null)
			{
				m_header = new HTML();
				m_header.setStyleName("tk-DropDownPanelHeader");
				CWrapper headerWrapper = new CWrapper(m_header, createHeaderControllers());
				insert(headerWrapper, getElement(), 0, true);
			}
			if (asHTML)
			{
				m_header.setHTML(text);
			}
			else
			{
				m_header.setText(text);
			}
		}
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
		
		for (Iterator<DropDownListener> iter = m_listeners.iterator(); iter.hasNext();)
		{
			iter.next().dropDownOpened(this);
		}
	}
	
	private void fireDropDownClosed()
	{
		if (m_listeners.size() == 0)
			return;
		
		for (Iterator<DropDownListener> iter = m_listeners.iterator(); iter.hasNext();)
		{
			iter.next().dropDownClosed(this);
		}
	}
	
	public static class OpenerController extends ControllerAdaptor
	{
		public final DropDownPanel m_dd;
		
		public OpenerController(DropDownPanel dd)
		{
			super(OpenerController.class, Event.ONCLICK);
			m_dd = dd;
		}
		
		protected boolean doBrowserEvent(Widget widget, Event event)
		{
			if (m_dd.isInteractive())
			{
				m_dd.toggleOpen();
			}
			return true;
		}
	}
}
