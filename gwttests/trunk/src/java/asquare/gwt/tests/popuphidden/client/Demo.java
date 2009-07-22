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
package asquare.gwt.tests.popuphidden.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.*;

public class Demo implements EntryPoint
{
	public void onModuleLoad()
	{
		RootPanel body = RootPanel.get();
		DOM.setStyleAttribute(body.getElement(), "background", "blue");
		final Button showPopupButton = new Button("Show popup");
		showPopupButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				final PopupPanel popup = new PopupPanel(true);
				VerticalPanel outer = new VerticalPanel();
				outer.add(new HTML("Click outside the popup to dismiss it"));
				outer.add(new Button("DOM.setStyleAttribute(popup.getElement(), \"visibility\", \"hidden\")", new ClickHandler()
				{
					public void onClick(ClickEvent event)
					{
						DOM.setStyleAttribute(popup.getElement(), "visibility", "hidden");
					}
				}));
				popup.setWidget(outer);
				DOM.setStyleAttribute(popup.getElement(), "border", "double black 4px");
				DOM.setStyleAttribute(popup.getElement(), "background", "red");
				popup.setSize("20em", "20em");
				int x = showPopupButton.getAbsoluteLeft();
				int y = showPopupButton.getAbsoluteTop() + showPopupButton.getOffsetHeight();
				popup.setPopupPosition(x, y);
				popup.show();
			}
		});
		body.add(showPopupButton);
	}
}
