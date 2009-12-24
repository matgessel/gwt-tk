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

import asquare.gwt.tk.client.ui.behavior.ControllerAdaptor;
import asquare.gwt.tk.client.ui.behavior.GlassPanelController;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

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
 * <li>.body-GlassPanelShowing { added to the BODY element when a GlassPanel is
 * shown }</li>
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
 * This example uses a transparent PNG background to workaround a bug with Flash
 * &amp; transparency in Firefox/Mac:
 * 
 * <pre>
 * <b>Java</b>
 * GlassPanel gp = new GlassPanel();
 * Label content = new Label();
 * content.setStyleName(&quot;Content&quot;);
 * content.setSize(&quot;100%&quot;, &quot;100%&quot;);
 * gp.add(content);
 * gp.show();
 * </pre>
 * 
 * <pre>
 * <b>CSS</b>
 * .tk-GlassPanel {
 *   filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='Gray20.png', sizingMethod='scale');
 * }
 * .tk-GlassPanel .Content
 *   background: url('Gray20.png');
 *   filter: alpha(opacity=0);
 * }
 * </pre>
 * 
 * The filter rule applies transparency in IE.
 * 
 * @see <a href="http://www.quirksmode.org/css/opacity.html">opacity on
 *      quirksmode</a>
 * @see <a href="http://msdn2.microsoft.com/en-us/library/ms532853.aspx">Filters
 *      for IE</a>
 */
public class GlassPanel extends CWindow
{
	public static final String DEFAULT_BODY_STYLENAME = "body-GlassPanelShowing";
	
	private String m_bodyStyleName;
	
	/**
	 * Creates a GlassPanel with the default options. 
	 */
	public GlassPanel()
	{
		this(DEFAULT_BODY_STYLENAME);
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
		super();
		m_bodyStyleName = bodyStyleName;
		setStyleName("tk-GlassPanel");
		addController((GlassPanelController) GWT.create(GlassPanelController.class));
		addController(new GlassPanelBodyStyleController());
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
	
	/**
	 * Sets the single widget in the panel. (GlassPanel no longer supports
	 * multiple widgets)
	 * 
	 * @deprecated use {@link #setWidget(Widget)}
	 */
	public void add(Widget w)
	{
		super.setWidget(w);
	}
	
	public static class GlassPanelBodyStyleController extends ControllerAdaptor
	{
		public GlassPanelBodyStyleController()
		{
			super(GlassPanelBodyStyleController.class);
		}
		
		@Override
		public void plugIn(Widget widget)
		{
			String bodyStyle = ((GlassPanel) widget).getBodyStyleName();
			if (bodyStyle != null)
			{
				RootPanel.get().addStyleName(bodyStyle);
			}
		}
		
		@Override
		public void unplug(Widget widget)
		{
			String bodyStyle = ((GlassPanel) widget).getBodyStyleName();
			if (bodyStyle != null)
			{
				RootPanel.get().removeStyleName(bodyStyle);
			}
		}
	}
}
