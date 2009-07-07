/*
 * Copyright 2007 Mat Gessel <mat.gessel@gmail.com>
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
package asquare.gwt.tk.uitest.popuppanel.client;

import asquare.gwt.tk.client.ui.ColumnPanel;
import asquare.gwt.tk.client.ui.RowPanel;
import asquare.gwt.tk.client.ui.behavior.Controller;
import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;

public class Demo implements EntryPoint
{
	public void onModuleLoad()
	{
		ColumnPanel outer = new ColumnPanel();
		outer.setSpacing(10);
		outer.setBorderWidth(0);
		outer.add(createDefaultTransparencyTest());
		outer.add(createTransparencyTest());
		RootPanel.get().add(outer);
	}
	
	private Widget createDefaultTransparencyTest()
	{
		RowPanel outer = new RowPanel();
		outer.add(new HTML("<h3>Default transparency test</h3>"));
		final Button button = new Button("Show popup");
		button.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				PopupPanel popup = new PopupPanel(true);
				popup.setPopupPosition(button.getAbsoluteLeft(), button.getAbsoluteTop() + button.getOffsetHeight());
				popup.setWidth("10em");
				DomUtil.setStyleAttribute(popup, "border", "solid black 1px");
				popup.setWidget(new HTML("<p>This popup should be transparent in all browsers.</p>"));
				popup.show();
			}
		});
		outer.add(button);
		return outer;
	}
	
	private Widget createTransparencyTest()
	{
		RowPanel outer = new RowPanel();
		BasicToolTip toolTip = new BasicToolTip();
		Controller ttController = new BasicToolTipController(toolTip);
		
		outer.add(new HTML("<h3>Popup transparency</h3><p>Hover over a widget</p>"));
		
		ListBox list = new ListBox();
		list.setVisibleItemCount(4);
		list.setMultipleSelect(true);
		list.addItem("ListBox");
		list.addItem("1");
		list.addItem("2");
		list.addItem("3");
		list.addItem("4");
		list.addItem("5");
		outer.add(new ToolTipWrapper(list, "Popup transparency over a ListBox").
				addController(ttController));
		
		outer.add(new ToolTipWrapper(new Frame("FrameContents.html"), "Popup transparency over a Frame").
				addController(ttController));
		
		outer.add(new ToolTipWrapper(new RadioButton("radioGroup", "RadioButton a"), "Choice a").
				addController(ttController));
		outer.add(new ToolTipWrapper(new RadioButton("radioGroup", "RadioButton b"), "Choice b").
				addController(ttController));
		outer.add(new ToolTipWrapper(new RadioButton("radioGroup", "RadioButton c"), "Choice c").
				addController(ttController));
		return outer;
	}
}
