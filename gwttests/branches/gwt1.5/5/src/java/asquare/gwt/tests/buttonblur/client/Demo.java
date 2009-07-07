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
package asquare.gwt.tests.buttonblur.client;

import asquare.gwt.debug.client.Debug;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;

public class Demo implements EntryPoint
{
	public void onModuleLoad()
	{
		RootPanel.get().add(createDemoPanel());
		
		Debug.installEventTracer((char) 0, -1);
		Debug.enable();
	}
	
	private Widget createDemoPanel()
	{
		Button result = new Button("gwtButton");
		result.addFocusListener(new FocusListener()
		{
			public void onLostFocus(Widget sender)
			{
				Debug.println("gwtButton.onLostFocus()");
			}
			public void onFocus(Widget sender)
			{
				Debug.println("gwtButton.onFocus()");
			}
		});
		return result;
	}
}
