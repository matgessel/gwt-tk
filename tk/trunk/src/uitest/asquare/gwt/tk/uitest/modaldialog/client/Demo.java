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
package asquare.gwt.tk.uitest.modaldialog.client;

import asquare.gwt.debug.client.*;
import asquare.gwt.tk.client.ui.AlertDialog;
import asquare.gwt.tk.client.ui.ModalDialog;
import asquare.gwt.tk.client.ui.behavior.Controller;
import asquare.gwt.tk.client.ui.behavior.ControllerAdaptor;
import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

public class Demo implements EntryPoint
{
	public void onModuleLoad()
	{
		AlertDialog dialog = new AlertDialog();
		dialog.setCaptionText("Caption Text", false);
		dialog.addButton("Save", 's', null, AlertDialog.BUTTON_DEFAULT);
		dialog.addButton("Don't Save", 'd', null, AlertDialog.BUTTON_PLAIN);
		dialog.addButton("Cancel", 'c', null, AlertDialog.BUTTON_CANCEL);
		dialog.removeController(dialog.getController(ModalDialog.PositionDialogController.class));
		dialog.setSize("400px", "200px");
		dialog.setPopupPosition(200, 200);
		dialog.addController(new ControllerAdaptor(Event.ONMOUSEDOWN, Controller.class)
		{
			public void onBrowserEvent(Widget widget, Event event)
			{
				int x = DomUtil.eventGetAbsoluteX(event) - DOM.getAbsoluteLeft(widget.getElement());
				int y = DomUtil.eventGetAbsoluteY(event) - DOM.getAbsoluteTop(widget.getElement());
				Debug.println("onMouseDown(" + x + "," + y + ")");
			}
		});
		dialog.show();
		
		new DebugEventListener('x', Event.ONMOUSEDOWN, null)
		{
			protected void doEvent(Event event)
			{
				Element target = DOM.eventGetTarget(event);
				Debug.println("target=" + DebugUtil.prettyPrintElement(target));
				int screenX = DOM.eventGetScreenX(event);
				int screenY = DOM.eventGetScreenY(event);
				int clientX = DOM.eventGetClientX(event);
				int clientY = DOM.eventGetClientY(event);
				int absLeft = DOM.getAbsoluteLeft(target);
				int absTop = DOM.getAbsoluteTop(target);
				int offsetLeft = getOffsetLeft(target);
				int offsetTop = getOffsetTop(target);
				int docScrollX = DomUtil.getViewportScrollX();
				int docScrollY = DomUtil.getViewportScrollY();
				Debug.println(
						"screenX=" + screenX + 
			    		",screenY=" + screenY + 
						",clientX=" + clientX + 
			    		",clientY=" + clientY + 
			    		",absLeft=" + absLeft + 
			    		",absTop=" + absTop + 
			    		",offsetLeft=" + offsetLeft + 
			    		",offsetTop=" + offsetTop + 
			    		",docScrollX=" + docScrollX +
			    		",docScrollY=" + docScrollY
			    		);
			}
		}.install();
		new DebugEventListener('z', Event.ONMOUSEDOWN, "Offset hierarchy inspector")
		{
			protected void doEvent(Event event)
			{
				Element target = DOM.eventGetTarget(event);
				printOffsetTopHierarchy(target);
			}
		}.install();
		
		new DebugHierarchyInspector().install();
		
		new DebugElementDumpInspector().install();
		
		new DebugEventListener(Event.ONMOUSEDOWN | Event.ONMOUSEUP).install();
		
		Debug.enable();
		if (! GWT.isScript())
		{
			DebugConsole.getInstance().disable();
		}
	}
	
	private static native void printOffsetTopHierarchy(Element element) /*-{
		var prefix = "+";
		while(element)
		{
			Debug.println(prefix + element.tagName + " {offsetTop=" + element.offsetTop + ",scrollTop=" + element.scrollTop + "}");
			element = element.offsetParent;
			prefix = " " + prefix;
		}
	}-*/;
	
	public native int getOffsetLeft(Element elem) /*-{
		return elem.offsetLeft;
	}-*/;
	
	public native int getOffsetTop(Element elem) /*-{
		return elem.offsetTop;
	}-*/;
}
