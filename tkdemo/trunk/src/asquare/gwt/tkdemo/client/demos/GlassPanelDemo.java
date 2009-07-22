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

import asquare.gwt.tk.client.ui.*;
import asquare.gwt.tk.client.ui.behavior.*;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.*;
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
		
		class CreateExample implements ClickHandler
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
			
			public void onClick(ClickEvent event)
			{
				final GlassPanel gp = new GlassPanel(m_bodyStyleName);
                gp.addStyleName(m_cssId);
				gp.addController(new HideGlassPanelController());
				gp.show();
			}
		}
		
		class ShowExample extends SimpleHyperLink
		{
			public ShowExample(String label, ClickHandler handler)
			{
				super(label);
				addClickHandler(handler);
			}
		}
		
		example.add(new ShowExample("Dark", new CreateExample("glasspanel-ex-dark")));
		example.add(new ShowExample("Light", new CreateExample("glasspanel-ex-light")));
		example.add(new ShowExample("Opaque", new CreateExample("glasspanel-ex-opaque")));
		example.add(new ShowExample("Transparent PNG background image", new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				final GlassPanel gp = new GlassPanel();
				gp.addStyleName("glasspanel-ex-transparentPNG");
				Label content = new Label();
				content.setStyleName("Content");
				content.setSize("100%", "100%");
				gp.setWidget(content);
				gp.addController(new HideGlassPanelController());
				gp.show();
			}
		}));
		example.add(new ShowExample("Tiled background image", new CreateExample("glasspanel-ex-tiledBG")));
		example.add(new ShowExample("Centered background image", new CreateExample("glasspanel-ex-centeredBG")));
		example.add(new ShowExample("Foreground text", new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				final GlassPanel gp = new GlassPanel();
				gp.addStyleName("glasspanel-ex-text");
				HTML contents = new HTML("<table cellspacing='0' cellpadding='0' style='width: 100%; height: 100%;'>" +
						"<td align='center' valign='middle'><h1>Wham!</h1></td>" +
						"</tr></table>");
				contents.setSize("100%", "100%");
				gp.setWidget(contents);
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
			super(HideGlassPanelController.class, Event.ONCLICK);
		}
		
		protected boolean doBrowserEvent(Widget widget, Event event)
		{
			final GlassPanel gp = (GlassPanel) widget;
			DeferredCommand.addCommand(new Command()
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
