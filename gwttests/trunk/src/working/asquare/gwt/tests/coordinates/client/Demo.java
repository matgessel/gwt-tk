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
package asquare.gwt.tests.coordinates.client;

import asquare.gwt.debug.client.*;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class Demo implements EntryPoint
{
	public void onModuleLoad()
	{
		DialogBox dialog = new DialogBox()
		{
			public void onMouseUp(Widget sender, int x, int y)
			{
				super.onMouseUp(sender, x, y);
			}
		
			public void onMouseMove(Widget sender, int x, int y)
			{
				super.onMouseMove(sender, x, y);
			}
		
			public void onMouseDown(Widget sender, int x, int y)
			{
				Debug.println("DialogBox.onMouseDown(" + x + "," + y + ")");
				super.onMouseDown(sender, x, y);
			}
		
		}; 
		dialog.setText("Dialog Caption");
		Image image = new Image("one.gif");
		DOM.setStyleAttribute(image.getElement(), "height", "100px");
		DOM.setStyleAttribute(image.getElement(), "width", "200px");
		
		dialog.setWidget(image);
		dialog.show();
		dialog.setPopupPosition(200, 200);
		
		Debug.enable();
		if (! GWT.isScript())
			DebugConsole.getInstance().disable();
		
		new DebugHierarchyInspector().install();
		new DebugElementDumpInspector().install();
		new DebugEventListener('a', Event.ONMOUSEDOWN, "Absolute position inspector")
		{
			protected void doEvent(Event event)
			{
				Element target = DOM.eventGetTarget(event);
				Debug.println(getTagName(target) + "[absLeft=" + DOM.getAbsoluteLeft(target) + ",absTop=" + DOM.getAbsoluteTop(target) + "]");
			}
		}.install();
		
		new DebugEventListener('o', Event.ONMOUSEDOWN, "Offset hierarchy inspector")
		{
			protected void doEvent(Event event)
			{
				Element target = DOM.eventGetTarget(event);
				printOffsetHierarchy(target);
			}
		}.install();
		
		new DebugEventListener().install();
	}
	
	private native void printOffsetHierarchy(Element element) /*-{
		var prefix = "+";
		while(element)
		{
			Debug.println(prefix + element.tagName + " {offsetTop=" + element.offsetTop + ",scrollTop=" + element.scrollTop + "}");
			element = element.offsetParent;
			prefix = " " + prefix;
		}
	}-*/;
	
	private native String getTagName(Element element) /*-{
		return element.tagName;
	}-*/;
}
