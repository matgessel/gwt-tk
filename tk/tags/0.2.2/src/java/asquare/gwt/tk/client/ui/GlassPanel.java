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

import java.util.List;
import java.util.Vector;

import asquare.gwt.tk.client.ui.behavior.GlassPanelController;
import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.impl.PopupImpl;

/**
 * A panel which covers the entire viewport or document, whichever is larger.
 * The GlassPanel prevents interaction with the document. Useful for modal
 * dialogs and the "lightbox" effect. Window resizing logic is handled in a
 * pluggable controller whose implementation varies by platform. When the
 * GlassPanel is shown, a style name is applied to the body element; the style
 * name is removed when the GlassPanel is hidden.
 * <h3>CSS Style Rules</h3>
 * <ul class='css'>
 * <li>.tk-GlassPanel { }</li>
 * <li>.body-GlassPanelShowing { added to the BODY element when a GlassPanel is shown }</li>
 * </ul>
 * CSS Example
 * 
 * <pre>
 * .tk-GlassPanel {
 *   background: black;
 *   opacity: 0.2;
 *   filter: alpha(opacity=20);
 * }
 * </pre>
 * 
 * The filter rule applies transparency in IE.
 * 
 * @see <a href="http://www.quirksmode.org/css/opacity.html">opacity on
 *      quirksmode</a>
 * @see <a
 *      href="http://msdn.microsoft.com/library/default.asp?url=/workshop/author/filter/reference/reference.asp">Filters
 *      for IE</a>
 */
public class GlassPanel extends CComplexPanel
{
	public static final String DEFAULT_BODY_STYLENAME = "body-GlassPanelShowing";
	
	private static final PopupImpl m_impl = (PopupImpl) GWT.create(PopupImpl.class);
	
	private String m_bodyStyleName;
	
	/**
	 * Creates a GlassPanel based on a div. 
	 */
	public GlassPanel()
	{
		this(DEFAULT_BODY_STYLENAME);
	}
	
	/**
	 * Creates a GlassPanel based the specified element. 
	 */
	public GlassPanel(Element element)
	{
		this(element, DEFAULT_BODY_STYLENAME);
	}
	
	/**
	 * Creates a GlassPanel based on a div. If not null,
	 * <code>bodyStyleName</code> will applied to the body element for the
	 * duration that the GlassPanel is visible.
	 *  
	 * @param bodyStyleName a CSS class name, or <code>null</code>
	 */
	public GlassPanel(String bodyStyleName)
	{
		this(DOM.createDiv(), bodyStyleName);
	}
	
	/**
	 * Creates a GlassPanel based the specified element. If not null,
	 * <code>bodyStyleName</code> will applied to the body element for the
	 * duration that the GlassPanel is visible.
	 * 
	 * @param element an element
	 * @param bodyStyleName a CSS class name, or <code>null</code>
	 */
	public GlassPanel(Element element, String bodyStyleName)
	{
		super(element);
		m_bodyStyleName = bodyStyleName;
		setStyleName("tk-GlassPanel");
	}
	
	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.CComplexPanel#createControllers()
	 */
	protected List createControllers()
	{
		List result = new Vector();
		result.add(GWT.create(GlassPanelController.class));
		return result;
	}
	
	/**
	 * Gets the style name which will be applied to the body element while the
	 * GlassPanel is shown.
	 * 
	 * @return a String or <code>null</code>
	 */
	public String getBodyStyleName()
	{
		return m_bodyStyleName;
	}
	
	/**
	 * Sets the style name which will be applied to the body element while the
	 * GlassPanel is shown.
	 * 
	 * @param bodyStyleName a CSS class name, or <code>null</code>
	 */
	public void setBodyStyleName(String bodyStyleName)
	{
		m_bodyStyleName = bodyStyleName;
	}
	
	/*
	 *  (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#add(com.google.gwt.user.client.ui.Widget)
	 */
	public void add(Widget w)
	{
		add(w, getElement());
	}
	
	/**
	 * Positions the GlassPanel, then makes it visible by appending it to the
	 * RootPanel. 
	 */
	public void show()
	{
		if (m_bodyStyleName != null)
		{
			RootPanel.get().addStyleName(m_bodyStyleName);
		}
		/*
		 * Need to set the position to (0,0) first, so that the panel itself does 
		 * not alter document size measurements made later. 
		 */ 
		DomUtil.setStyleAttribute(this, "position", "absolute");
		DomUtil.setStyleAttribute(this, "left", "0px");
		DomUtil.setStyleAttribute(this, "top", "0px");
		RootPanel.get().add(this);
		m_impl.onShow(getElement());
	}
	
	/**
	 * Hides the GlassPanel by detaching it from the RootPanel.
	 */
	public void hide()
	{
		m_impl.onHide(getElement());
		RootPanel.get().remove(this);
		if (m_bodyStyleName != null)
		{
			RootPanel.get().removeStyleName(m_bodyStyleName);
		}
	}
}
