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
package asquare.gwt.tests.clientcoords.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventPreview;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;

public class Demo implements EntryPoint
{
	public void onModuleLoad()
	{
		RootPanel.get("outer").add(new Label("Event client position"));
		final TextArea output = new TextArea();
		RootPanel.get("outer").add(output);
		DOM.addEventPreview(new EventPreview()
		{
			public boolean onEventPreview(Event event)
			{
				if (DOM.eventGetType(event) == Event.ONMOUSEDOWN)
				{
					output.setText("clientX=" + DOM.eventGetClientX(event) + ", clientY=" + DOM.eventGetClientY(event));
				}
				return true;
			}
		});
	}
}
