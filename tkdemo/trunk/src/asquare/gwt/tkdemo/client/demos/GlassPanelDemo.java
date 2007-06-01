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
package asquare.gwt.tkdemo.client.demos;

import asquare.gwt.tk.client.ui.BasicPanel;
import asquare.gwt.tk.client.ui.GlassPanel;
import asquare.gwt.tk.client.ui.SimpleHyperLink;
import asquare.gwt.tk.client.ui.behavior.ControllerAdaptor;
import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.*;

public class GlassPanelDemo extends BasicPanel
{
	public static final String NAME = "GlassPanel";
	
	public GlassPanelDemo()
	{
		BasicPanel outer = new BasicPanel();
		outer.addStyleName("division");
		add(outer);
		
		String content = "<H2>GlassPanel</H2>"
				+ "<p>A GlassPanel covers the entire surface of the document. " +
						"It prevents user interaction with the document. </p>";
		HTML header = new HTML(content);
		header.addStyleName("description");
		outer.add(header);
		
		BasicPanel example = new BasicPanel("div", BasicPanel.DISPLAY_BLOCK);
		example.addStyleName("example");
		outer.add(example);
		
		class CreateExample implements ClickListener
		{
			private final String m_cssId;
			private final String m_bodyStyleName;
			
			public CreateExample(String cssId)
			{
				this(cssId, GlassPanel.DEFAULT_BODY_STYLENAME);
			}
			
			public CreateExample(String cssId, String bodyStyleName)
			{
				m_cssId = cssId;
				m_bodyStyleName = bodyStyleName;
			}
			
			public void onClick(Widget sender)
			{
				final GlassPanel gp = new GlassPanel(m_bodyStyleName);
				DomUtil.setId(gp, m_cssId);
				gp.addController(new HideGlassPanelController());
				gp.show();
			}
		}
		
		class ShowExample extends SimpleHyperLink
		{
			public ShowExample(String label, ClickListener listener)
			{
				super(label);
				addClickListener(listener);
			}
		};
		
		example.add(new ShowExample("Dark", new CreateExample("glasspanel-ex-dark")));
		example.add(new ShowExample("Light", new CreateExample("glasspanel-ex-light")));
		example.add(new ShowExample("Opaque", new CreateExample("glasspanel-ex-opaque")));
		example.add(new ShowExample("Transparent PNG background image", new ClickListener()
		{
			public void onClick(Widget sender)
			{
				final GlassPanel gp = new GlassPanel();
				DomUtil.setId(gp, "glasspanel-ex-transparentPNG");
				Label content = new Label();
				content.setStyleName("Content");
				content.setSize("100%", "100%");
				gp.add(content);
				gp.addController(new HideGlassPanelController());
				gp.show();
			}
		}));
		example.add(new ShowExample("Tiled background image", new CreateExample("glasspanel-ex-tiledBG")));
		example.add(new ShowExample("Centered background image", new CreateExample("glasspanel-ex-centeredBG")));
		example.add(new ShowExample("Foreground text", new ClickListener()
		{
			public void onClick(Widget sender)
			{
				final GlassPanel gp = new GlassPanel();
				DomUtil.setId(gp, "glasspanel-ex-text");
				HTML contents = new HTML("<table cellspacing='0' cellpadding='0' style='width: 100%; height: 100%;'>" +
						"<td align='center' valign='middle'><h1>Wham!</h1></td>" +
						"</tr></table>");
				contents.setSize("100%", "100%");
				gp.add(contents);
				gp.addController(new HideGlassPanelController());
				gp.show();
			}
		}));
		example.add(new ShowExample("Blur (IE only)", new CreateExample("glasspanel-ex-ieBodyBlur", "glasspanel-ex-ieBodyBlur-BODY")));
	}
	
	static class HideGlassPanelController extends ControllerAdaptor
	{
		public HideGlassPanelController()
		{
			super(Event.ONCLICK, HideGlassPanelController.class);
		}
		
		protected boolean doBrowserEvent(Widget widget, Event event)
		{
			final GlassPanel gp = (GlassPanel) widget;
			DeferredCommand.add(new Command()
			{
				public void execute()
				{
					gp.hide();
				}
			});
			return true;
		}
	}
}
