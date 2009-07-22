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
package asquare.gwt.tests.circularbinding.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Demo implements EntryPoint
{
	public void onModuleLoad()
	{
		RootPanel.get().add(createDemoPanel());
	}
	
	private Widget createDemoPanel()
	{
		VerticalPanel outer = new VerticalPanel();
		
		final TextArea output = new TextArea();
		outer.add(output);
		
		Label label = new Label("Click me");
		label.addMouseDownHandler(new MouseDownHandler()
		{
			public void onMouseDown(MouseDownEvent event)
			{
				// this works
				output.setText("x=" + DOM2.eventGetClientX());
				
				// this results in an infinite loop
				output.setText(output.getText() + "\r\ny=" + DOM2.eventGetClientY());				
			}
		});
		DOM.setStyleAttribute(label.getElement(), "border", "solid black 1px");
		outer.insert(label, 0);
		
		return outer;
	}
}
