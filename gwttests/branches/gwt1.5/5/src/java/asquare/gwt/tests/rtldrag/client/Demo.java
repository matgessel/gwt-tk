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
package asquare.gwt.tests.rtldrag.client;

import asquare.gwt.debug.client.Debug;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.*;

public class Demo implements EntryPoint
{
	public void onModuleLoad()
	{
		final DialogBox dialog = new DialogBox();
		dialog.setSize("200px", "100px");
		DOM.setStyleAttribute(dialog.getElement(), "border", "solid 1px black");
		dialog.setText("Drag me off the right side of the page");
		dialog.show();
		
		Debug.installEventTracer('e', Event.ONMOUSEMOVE);
	}
}
