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
package asquare.gwt.tkdemo.client.demos;

import java.util.*;

import asquare.gwt.tk.client.ui.*;
import asquare.gwt.tk.client.ui.behavior.*;

import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;

public class EventsPanel extends Composite
{
	public static final String STYLENAME_EDITING = "events-editing";
	
	public EventsPanel()
	{
		ColumnPanel panel = new ColumnPanel();
		initWidget(panel);
		panel.add(createEx1());
		panel.add(createEx2());
	}
	
	private static Collection createSomeWidgets()
	{
        ArrayList result = new ArrayList();
		result.add(new HTML("<b>Double-click a widget to select it</b>"));
		result.add(new Button("Button"));
		result.add(new CheckBox("CheckBox"));
		result.add(new RadioButton("group", "RadioButton"));
		result.add(new SimpleHyperLink("SimpleHyperLink"));
		TextBox tb = new TextBox();
		tb.setText("TextBox");
		result.add(tb);
		PasswordTextBox ptb = new PasswordTextBox();
		ptb.setText("PasswordTextBox");
		result.add(ptb);
		return result;
	}
	
	/**
	 * This example shows how to use EventWrapper to process events propagated
	 * to the wrapped widget. It employs an ad hoc design with an intermingling
	 * of concerns.
	 */
	private static Widget createEx1()
	{
		BasicPanel outer = new BasicPanel();
		
		HTML description = new HTML("<h2>Example 1</h2>" + 
				"<p>Shows how to use an EventWrapper to process unsupported events on widgets without subclassing. " + 
				"In this example we add a style name to the widget when it is double-clicked. " + 
				"Logic to prevent text selection is implemented in an ad hoc manner. " + 
				"Logic for preventing selection, handling platform differences and handling the double-click event are intermingled. </p>");
		description.setStyleName("description division");
		outer.add(description);
		
		BasicPanel example = new BasicPanel("div", BasicPanel.DISPLAY_BLOCK);
		example.setStyleName("example division");
		outer.add(example);
		
		for (Iterator iter = createSomeWidgets().iterator(); iter.hasNext();)
		{
			example.add(new Ex1_EventWrapper((Widget) iter.next()));
		}
		
		return outer;
	}
	
	private static class Ex1_EventWrapper extends EventWrapper
	{
		private boolean m_editing = false;
		
		public Ex1_EventWrapper(Widget w)
		{
			super(w, Event.ONDBLCLICK | Event.ONMOUSEDOWN);
			installIEHook(w.getElement());
		}
		
		/*
		 * Prevents text selection in IE
		 */
		private native void installIEHook(Element element) /*-{
			element.onselectstart = function() { return false; };
		}-*/;
		
		public void onBrowserEvent(Event event)
		{
			switch (DOM.eventGetType(event))
			{
				case Event.ONDBLCLICK: 
					Element e = getElement();
					m_editing = ! m_editing;
					UIObject.setStyleName(e, STYLENAME_EDITING, m_editing);
					break;
				
				case Event.ONMOUSEDOWN: 
					 // prevents selection in non-IE browsers
					DOM.eventPreventDefault(event);
					break;
					
				default: 
					super.onBrowserEvent(event);
			}
		}
	}
	
	private static Widget createEx2()
	{
		BasicPanel outer = new BasicPanel();
		
		HTML description = new HTML("<h2>Example 2</h2>" +
				"<p>This is a rewrite of the previous example using separate controllers to handle events. " + 
				"  We can use <code>PreventSelectionController</code> to prevent text selection. " +
				"  This keeps the double-click controller pristine and ensures that selection logic is consistent across the entire system. " +
				"</p>" + 
				"<p><code>PreventSelectionController</code> encapsulates differences in browsers using implementation classes and the deferred binding mechanism.</p>" +
				"<p>Deferred binding results in: </p>" + 
				"<ul>" +
				"  <li>smaller script size</li>" +
				"  <li>easy replacement of existing logic via rules in your Module.gwt.xml file</li>" +
				"  <li>simple and clean support for additional browsers</li>" +
				"</ul>" + 
				"");
		description.setStyleName("description division");
		outer.add(description);
		
		BasicPanel example = new BasicPanel("div", BasicPanel.DISPLAY_BLOCK);
		example.setStyleName("example division");
		outer.add(example);
		
		/*
		 * Create a list of controllers to process events on each widget. 
		 * These controllers are stateless, so they may be shared between all widgets. 
		 */
        ArrayList controllers = new ArrayList();
		controllers.add(PreventSelectionController.getInstance());
		controllers.add(new Ex2_Controller());
		for (Iterator iter = createSomeWidgets().iterator(); iter.hasNext();)
		{
			example.add(new CWrapper((Widget) iter.next(), controllers));
		}
		
		return outer;
	}
	
	private static class Ex2_Controller extends ControllerAdaptor
	{
		/*
		 * We store state in an attribute in the widget so that a single
		 * controller instance may be shared.
		 */
		private static final String ATTR_EDIT_STATE = "__isEditing";
		
		public Ex2_Controller()
		{
			super(Ex2_Controller.class, Event.ONDBLCLICK);
		}
		
		public void onBrowserEvent(Widget widget, Event event)
		{
			/*
			 * We will only be notified of event types which were sunk. 
			 * We only sunk ONDBLCLICK, so case analysis of the event type is unnecessary. 
			 */
			Element e = widget.getElement();
			
			// get the current edit state and toggle it
			boolean editing = ! DOM.getElementPropertyBoolean(e, ATTR_EDIT_STATE);
			DOM.setElementPropertyBoolean(e, ATTR_EDIT_STATE, editing);
			
			// update the element style
			UIObject.setStyleName(e, STYLENAME_EDITING, editing);
		}
	}
}
