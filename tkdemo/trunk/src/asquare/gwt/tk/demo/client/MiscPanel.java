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
package asquare.gwt.tk.demo.client;

import asquare.gwt.tk.client.ui.BasicPanel;
import asquare.gwt.tk.client.ui.SimpleHyperLink;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class MiscPanel extends Composite
{
	public MiscPanel()
	{
		HorizontalPanel outer = new HorizontalPanel();
		DOM.setAttribute(outer.getElement(), "id", "misc");
		setWidget(outer);
		
		BasicPanel left = new BasicPanel();
		BasicPanel right = new BasicPanel();
		
		left.add(createSimpleHyperLinkPanel());
//		right.add(createBasicPanelPanel());
		
		outer.add(left);
		outer.add(right);
	}
	
	private Widget createSimpleHyperLinkPanel()
	{
		BasicPanel panel = new BasicPanel("div");
		panel.setStyleName("division");
		
		String content = "<H2>SimpleHyperLink</H2>" + 
		"A hyperlink based on an anchor. No pesky DIV messing up your text flow. No history tokens in the location bar.";
		HTML header = new HTML(content);
		header.addStyleName("header");
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
	
	// work in progress...
	private Widget createBasicPanelPanel()
	{
		BasicPanel panel = new BasicPanel("div");
		panel.setStyleName("division");
		
		String content = "<H2>BasicPanel</H2>" + 
		"A barebones ComplexPanel which allows customization of the root element and <code>display</code> style of added children. ";
		HTML header = new HTML(content);
		header.addStyleName("header");
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
