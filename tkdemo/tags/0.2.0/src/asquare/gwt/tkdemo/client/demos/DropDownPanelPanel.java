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
import asquare.gwt.tk.client.ui.DropDownPanel;
import asquare.gwt.tk.client.ui.SimpleHyperLink;
import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.user.client.ui.*;

public class DropDownPanelPanel extends Composite
{
	public DropDownPanelPanel()
	{
		HorizontalPanel outer = new HorizontalPanel();
		outer.setSize("100%", "100%");

		initWidget(outer);

		BasicPanel left = new BasicPanel();
		left.addStyleName("division");
		DomUtil.setAttribute(left, "id", "dropDown-left");		
		
		DropDownPanel dropDown1 = new DropDownPanel();
		DomUtil.setAttribute(dropDown1, "id", "dropDown-1");		
		dropDown1.setHeaderText("Drop Down 1", false);
		Image image = new Image("icecube.jpg");
		image.setSize("100px", "100px");
		dropDown1.add(image);
		dropDown1.setOpen(true);
		left.add(dropDown1);
		
		DropDownPanel dropDown2 = new DropDownPanel();
		DomUtil.setAttribute(dropDown2, "id", "dropDown-2");		
		dropDown2.setHeaderText("Drop Down 2", true);
		image = new Image("icecube.jpg");
		image.setSize("100px", "250px");
		dropDown2.add(image);
		left.add(dropDown2);
		
		outer.add(left);
		outer.setCellWidth(left, "1px");
		
		String content = 
			"<H2>DropDownPanel</H2>" + 
			"<p>This widget consists of a hideable content DIV and an optional header DIV.</p>" + 
			"<p>The content can be <span id='open'></span>&nbsp;&amp; <span id='close'></span>&nbsp;programatically.</p>" + 
			"<p>The headers prevent selection of the text within them. In IE this is done in a special implementation using an <code>onselectstart</code> listener which returns <code>false</code>. In other browsers, <code>DOM.eventPreventDefault(Event)</code> for <code>Event.ONMOUSEDOWN</code> suffices. </p>" +
			"<p>You can add a listener to the DropDownPanel and make layout changes when the it opens/closes (e.g. maximize its parent element when it opens). </p>" + 
			"<p>The tip-down arrows on the left are background images applied through CSS rules. <em>Note: we cannot put a border around the header itself because in IE the background image would be obscured by the border.</em></p>";
		DropDownPanel dropDown3 = new DropDownPanel();
		dropDown3.addStyleName("division");
		DomUtil.setAttribute(dropDown3, "id", "dropDown-3");		
		dropDown3.setHeaderText("Drop&nbsp;Down&nbsp;3", true);
		HTMLPanel center = new HTMLPanel(content);
		center.addStyleName("description");
		SimpleHyperLink close = new SimpleHyperLink("hidden");
		SimpleHyperLink open = new SimpleHyperLink("shown");
		center.add(close, "close");
		center.add(open, "open");
		dropDown3.add(center);
		dropDown3.setOpen(true);
		outer.add(dropDown3);
		
		final DropDownPanel dropDown4 = new DropDownPanel();
		dropDown4.addStyleName("division");
		DomUtil.setAttribute(dropDown4, "id", "dropDown-4");
		image = new Image("icecube.jpg");
		image.setSize("200px", "250px");
		dropDown4.add(image);
		outer.add(dropDown4);
		outer.setCellWidth(dropDown4, "1px");
		
		open.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				dropDown4.setOpen(true);
			}
		});
		close.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				dropDown4.setOpen(false);
			}
		});
	}
}
