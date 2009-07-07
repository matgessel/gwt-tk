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
import asquare.gwt.tk.client.ui.ExternalHyperLink;
import asquare.gwt.tk.client.ui.SimpleHyperLink;
import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class MiscPanel extends Composite
{
	public MiscPanel()
	{
		HorizontalPanel outer = new HorizontalPanel();
		DomUtil.setAttribute(outer, "id", "miscPanel");
		initWidget(outer);
		
		BasicPanel left = new BasicPanel();
		BasicPanel right = new BasicPanel();
		
		left.add(new FocusCycleDemo());
		left.add(createSimpleHyperLinkDemo());
		right.add(new GlassPanelDemo());
		right.add(createExternalHyperLinkDemo());
		
		outer.add(left);
		outer.setCellWidth(left, "50%");
		outer.add(right);
		outer.setCellWidth(right, "50%");
	}
	
	private Widget createSimpleHyperLinkDemo()
	{
		BasicPanel panel = new BasicPanel();
		panel.addStyleName("division");
		
		String content = "<H2>SimpleHyperLink</H2>" + 
			"A hyperlink based on an anchor. No pesky DIV messing up your text flow. No history tokens in the location bar.";
		HTML header = new HTML(content);
		header.addStyleName("description");
		panel.add(header);
		
		BasicPanel example = new BasicPanel();
		example.addStyleName("example");
		
		example.add(new SimpleHyperLink("SpongeBob says...", new ClickListener()
		{
			public void onClick(Widget sender)
			{
				Window.alert("I'm a goofy goober!");
			}
		}));
		
		panel.add(example);
		return panel;
	}
	
	private Widget createExternalHyperLinkDemo()
	{
		BasicPanel panel = new BasicPanel();
		panel.addStyleName("division");
		
		String content = "<H2>ExternalHyperLink</H2>" + 
			"An anchor linking to a page (or resource) external to the application.";
		HTML header = new HTML(content);
		header.addStyleName("description");
		panel.add(header);
		
		BasicPanel example = new BasicPanel("div", "block");
		example.addStyleName("example");
		
		example.add(new ExternalHyperLink("Text content, target '_blank'", false, "http://www.google.com", ExternalHyperLink.TARGET_BLANK));
		example.add(new ExternalHyperLink("Text content, target 'bob'", false, "http://www.google.com", "bob"));
		example.add(new ExternalHyperLink("<b>HTML</b> content, target '_self'", true, "http://www.google.com", ExternalHyperLink.TARGET_SELF));
		example.add(new ExternalHyperLink("sales@example.com", "mailto:sales@example.com"));
		ExternalHyperLink styled = new ExternalHyperLink("Styled link", false, "http://www.google.com", ExternalHyperLink.TARGET_BLANK);
		styled.addStyleName("externalhyperlink-styled");
		example.add(styled);
		
		panel.add(example);
		return panel;
	}
	
	// work in progress...
	@SuppressWarnings("unused")
	private Widget createBasicPanelPanel()
	{
		BasicPanel panel = new BasicPanel();
		panel.addStyleName("division");
		
		String content = "<H2>BasicPanel</H2>" + 
		"A barebones ComplexPanel which allows customization of the root element and <code>display</code> style of added children. ";
		HTML header = new HTML(content);
		header.addStyleName("header");
		header.addStyleName("description");
		panel.add(header);
		
		BasicPanel example = new BasicPanel();
		example.addStyleName("example");
		
		BasicPanel panel1 = new BasicPanel("div", BasicPanel.DISPLAY_INLINE);
		for (int i = 0; i < 5; i++)
		{
			panel1.add(new Button("Button"));
		}
		for (int i = 0; i < 5; i++)
		{
			panel1.add(new Button("Button"));
		}
		example.add(panel1);
		
		BasicPanel panel2 = new BasicPanel("span", BasicPanel.DISPLAY_BLOCK);
		for (int i = 0; i < 10; i++)
		{
			panel2.add(new Button("Button"));
		}
		example.add(panel2);
		
		panel.add(example);
		return panel;
	}
}
