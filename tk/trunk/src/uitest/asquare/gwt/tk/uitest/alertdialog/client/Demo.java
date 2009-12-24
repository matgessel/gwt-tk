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
package asquare.gwt.tk.uitest.alertdialog.client;

import asquare.gwt.debug.client.*;
import asquare.gwt.tk.client.ui.AlertDialog;
import asquare.gwt.tk.client.ui.behavior.Controller;
import asquare.gwt.tk.client.ui.behavior.ControllerAdaptor;
import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;

public class Demo implements EntryPoint
{
	public void onModuleLoad()
	{
		Debug.enable();
		
		new DebugEventListener('x', Event.ONMOUSEDOWN, null)
		{
			@Override
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
				int docScrollX = Window.getScrollLeft();
				int docScrollY = Window.getScrollTop();
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
			@Override
			protected void doEvent(Event event)
			{
				Element target = DOM.eventGetTarget(event);
				printOffsetTopHierarchy(target);
			}
		}.install();
		
		new DebugHierarchyInspector().install();
		
		new DebugElementDumpInspector().install();
		
		new DebugEventListener(Event.ONMOUSEDOWN | Event.ONMOUSEUP).install();
		
		if (! GWT.isScript())
		{
			DebugConsole.getInstance().disable();
		}
		
		final Button button = new Button();
		button.setText("Default Info dialog");
		button.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				final AlertDialog alert =
				AlertDialog.createInfo(new Command()
				{
					public void execute()
					{
						Debug.println("OK clicked");
					}
				},
				"Info Dialog",
				"this is a default info dialog");
				alert.show();
			}
		});
		RootPanel.get().add(button);

		Command showDialog = new Command()
		{
			private AlertDialog m_dialog;
			
			public void execute()
			{
				if (m_dialog == null)
				{
					m_dialog = AlertDialog.createWarning(this, "Caption text", null);
					ScrollPanel message = new ScrollPanel();
					message.setAlwaysShowScrollBars(true);
					message.setWidth("100%");
					message.setHeight("100px");
					message.setWidget(new Label("These packages contain reference information about the main GWT user interface and utility classes. For higher-level explanations of how to take advantage of all this stuff, check out the Developer Guide. Among other things, there's a handy widget gallery and an explanation of how remote procedure calls work.These packages contain reference information about the main GWT user interface and utility classes. For higher-level explanations of how to take advantage of all this stuff, check out the Developer Guide. Among other things, there's a handy widget gallery and an explanation of how remote procedure calls work."));
					m_dialog.setMessage(message);
					m_dialog.setSize("400px", "300px");
					m_dialog.addController(new ControllerAdaptor(Controller.class, Event.ONMOUSEDOWN)
					{
					    @Override
						public void onBrowserEvent(Widget widget, Event event)
						{
							int x = DomUtil.eventGetAbsoluteX(event) - DOM.getAbsoluteLeft(widget.getElement());
							int y = DomUtil.eventGetAbsoluteY(event) - DOM.getAbsoluteTop(widget.getElement());
							Debug.println("onMouseDown(" + x + "," + y + ")");
						}
					});
				}
				m_dialog.show();
			}
		};
		showDialog.execute();
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
