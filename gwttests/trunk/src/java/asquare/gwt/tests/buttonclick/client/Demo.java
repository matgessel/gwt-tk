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
package asquare.gwt.tests.buttonclick.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class Demo implements EntryPoint
{
	public void onModuleLoad()
	{
		Button m_button1 = new Button("Click me");
		final Button m_button2 = new Button("Target button");
		m_button1.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				m_button2.click();
			}
		});
		m_button2.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				Window.alert("target button click() called");
			}
		});
		RootPanel.get().add(m_button1);
		RootPanel.get().add(m_button2);
	}
}
