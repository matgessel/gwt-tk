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
package asquare.gwt.tk.demo.client;

import java.util.*;

import asquare.gwt.debug.client.Debug;
import asquare.gwt.debug.client.DebugConsole;
import asquare.gwt.debug.client.DebugEventListener;
import asquare.gwt.tk.client.ui.BasicPanel;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.*;

public class DebugPanel extends Composite
{
	private final DebugEventListener m_eventListener = new DebugEventListener();
	
	public DebugPanel()
	{
		HorizontalPanel outer = new HorizontalPanel();
		
		setWidget(outer);
		
		Widget docPanel = createDocPanel();
		outer.add(docPanel);
		outer.setCellWidth(docPanel, "33%");
		
		Widget controlPanel = createControlPanel(m_eventListener);
		outer.add(controlPanel);
		outer.setCellWidth(controlPanel, "33%");
		
		Widget widgetPanel = createWidgetPanel();
		outer.add(widgetPanel);
		
		m_eventListener.install();
	}
	
	private Widget createDocPanel()
	{
		String content = 
			"<H2>Debug Utilities</H2>" + 
			"<p>Includes a debug console, <a href='#' onclick=\"Debug.prettyPrint(window);return false;\">pretty printing</a> of native JavaScript objects, a stub implementation for compile-time removal and an experimental event monitor. </p>" +
			"<p>Debugging statements are output to the in-browser Debug Console. In hosted mode the message also goes to <code>System.out</code> and the GWT Shell. The console will appear when it recieves output if it is enabled (it is enabled by default). Press <code>w</code> twice to enable/disable the debug console independently of other debug funtions.</p>" + 
			"<p>You can print debug statments from JSNI and <a href='#' onclick=\"Debug.println('This is an inline HTML onclick handler');return false;\">regular JavaScript</a>. </p>" + 
			"<p>Debug output can be enabled at runtime via <code>Debug.enable()</code> or by pressing <code>Caps Lock</code> twice (debug is enabled for this demo, but is otherwise disabled by default).</p>" + 
			"<p>Press <code>e</code> twice to enable event tracing. Mouse move/in/out &amp; scroll events are ignored by default. </p>" + 
			"<p>A stub implementation facilitates removal of trace statements and class definitions from the deliverable. </p>" + 
			"<p>Debug classes are packaged in a separate module for manual dependency elimination; just comment out the <code>inherit</code> element in your application's model descriptor. </p>" + 
			"<p>See the API documentation for usage notes. </p>" + 
			"";
		HTMLPanel doc = new HTMLPanel(content);
		DOM.setAttribute(doc.getElement(), "id", "debug-doc");
		return doc;
	}
	
	private Widget createControlPanel(final DebugEventListener eventListener)
	{
		BasicPanel controls = new BasicPanel();
		DOM.setAttribute(controls.getElement(), "id", "debug-controls");
		
		// debug output
		BasicPanel debug = new BasicPanel("p", BasicPanel.DISPLAY_INLINE);
		debug.add(new Label("Debug output ", false));
		debug.add(new Button("enable", new ClickListener()
				{
					public void onClick(Widget sender)
					{
						Debug.enable();
					}
				}));
		debug.add(new Button("disable", new ClickListener()
				{
					public void onClick(Widget sender)
					{
						Debug.disable();
					}
				}));
		controls.add(debug);
		
		// console
		BasicPanel console = new BasicPanel("p", BasicPanel.DISPLAY_INLINE);
		console.add(new Label("Debug console ", false));
		console.add(new Button("enable", new ClickListener()
		{
			public void onClick(Widget sender)
			{
				DebugConsole.getInstance().enable();
			}
		}));
		console.add(new Button("disable", new ClickListener()
		{
			public void onClick(Widget sender)
			{
				DebugConsole.getInstance().disable();
			}
		}));
		controls.add(console);
		
		// event tracing
		BasicPanel tracing = new BasicPanel("p", BasicPanel.DISPLAY_INLINE);
		tracing.add(new Label("Event tracing ", false));
		tracing.add(new Button("enable", new ClickListener()
		{
			public void onClick(Widget sender)
			{
				eventListener.enable(true);
				Debug.println("You can trace any event. Some events are ignored by default");
			}
		}));
		tracing.add(new Button("disable", new ClickListener()
		{
			public void onClick(Widget sender)
			{
				eventListener.enable(false);
			}
		}));
		controls.add(tracing);
		
		Widget checkBoxes = createCheckBoxes();
		controls.add(checkBoxes);
		DOM.setStyleAttribute(checkBoxes.getElement(), "display", "block");
		
		return controls;
	}
	
	private static class EventToMask
	{
		public final String m_event;
		public final int m_mask;
		
		public EventToMask(String event, int mask)
		{
			m_event = event;
			m_mask = mask;
		}

		public boolean equals(Object obj)
		{
			return m_event.equals(obj);
		}
	}
	
	private Widget createCheckBoxes()
	{
		// can't use HashMap because it does not preserve order
		final Vector eventToMask = new Vector();
		eventToMask.add(new EventToMask("onfocus", Event.ONFOCUS));
		eventToMask.add(new EventToMask("onblur", Event.ONBLUR));
		eventToMask.add(new EventToMask("onchange", Event.ONCHANGE));
		eventToMask.add(new EventToMask("onkeydown", Event.ONKEYDOWN));
		eventToMask.add(new EventToMask("onkeypress", Event.ONKEYPRESS));
		eventToMask.add(new EventToMask("onkeyup", Event.ONKEYUP));
		eventToMask.add(new EventToMask("onscroll", Event.ONSCROLL));
		eventToMask.add(new EventToMask("onmousemove", Event.ONMOUSEMOVE));
		eventToMask.add(new EventToMask("onmouseover", Event.ONMOUSEOVER));
		eventToMask.add(new EventToMask("onmouseout", Event.ONMOUSEOUT));
		eventToMask.add(new EventToMask("onmousedown", Event.ONMOUSEDOWN));
		eventToMask.add(new EventToMask("onmouseup", Event.ONMOUSEUP));
		eventToMask.add(new EventToMask("onclick", Event.ONCLICK));
		eventToMask.add(new EventToMask("ondblclick", Event.ONDBLCLICK));
		eventToMask.add(new EventToMask("onerror", Event.ONERROR));
		
		ClickListener checkBoxController = new ClickListener()
		{
			public void onClick(Widget sender)
			{
				CheckBox cb = (CheckBox) sender;
				EventToMask mapping = (EventToMask) eventToMask.get(eventToMask.indexOf(cb.getText()));
				int traceEventMask = m_eventListener.getEventMask();
				if (cb.isChecked())
				{
					traceEventMask |= mapping.m_mask;
				}
				else
				{
					traceEventMask &= ~mapping.m_mask;
				}
				m_eventListener.setEventMask(traceEventMask);
			}
		};
		
		FlowPanel panel = new FlowPanel();
		for (int i = 0; i < eventToMask.size(); i++)
		{
			EventToMask mapping = (EventToMask) eventToMask.get(i);
			addCheckBox(panel, mapping.m_event, mapping.m_mask, checkBoxController);
		}
		return panel;
	}
	
	private void addCheckBox(ComplexPanel parent, String label, int mask, ClickListener listener)
	{
		CheckBox cb = new CheckBox(label);
		cb.setChecked((m_eventListener.getEventMask() & mask) != 0);
		cb.addClickListener(listener);
		parent.add(cb);
		DOM.appendChild(parent.getElement(), DOM.createElement("br"));
	}

	private Widget createWidgetPanel()
	{
		BasicPanel widgets = new BasicPanel();
		DOM.setAttribute(widgets.getElement(), "id", "debug-widgets");
		TextArea textArea = new TextArea();
		textArea.setText("Enable event tracing then type in here");
		widgets.add(textArea);
		
		ListBox listBox = new ListBox();
		listBox.addItem("List Box");
		listBox.addItem("foo");
		listBox.addItem("bar");
		listBox.addItem("baz");
		widgets.add(listBox);
		
		TreeItem treeRoot = new TreeItem("Tree");
		TreeItem treeItem = new TreeItem("foo");
		treeRoot.addItem(treeItem);
		treeRoot.addItem("bar");
		treeRoot.addItem("baz");
		Tree tree = new Tree();
		tree.addItem(treeRoot);
		tree.setSelectedItem(treeItem);
		tree.ensureSelectedItemVisible();
		widgets.add(tree);
		
		Image image = new Image("icecube.jpg");
		ScrollPanel scrollPanel = new ScrollPanel(image);
		scrollPanel.setSize("200px", "200px");
		widgets.add(scrollPanel);
		
		return widgets;
	}
}
