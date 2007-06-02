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

import java.util.Vector;

import asquare.gwt.debug.client.Debug;
import asquare.gwt.debug.client.DebugConsole;
import asquare.gwt.debug.client.DebugEventListener;
import asquare.gwt.tk.client.ui.BasicPanel;
import asquare.gwt.tk.client.ui.ColumnPanel;
import asquare.gwt.tk.client.ui.RowPanel;
import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

public class DebugPanel extends Composite
{
	private final DebugEventListener m_eventListener = new DebugEventListener();
	
	public DebugPanel()
	{
		ColumnPanel outer = new ColumnPanel();
		
		initWidget(outer);
		
		Widget docPanel = createDocPanel();
		docPanel.addStyleName("division");
		outer.add(docPanel);
		
		RowPanel right = new RowPanel();
		outer.add(right);
		
		Widget controlPanel = createControlPanel(m_eventListener);
		controlPanel.addStyleName("division");
		controlPanel.addStyleName("example");
		right.add(controlPanel);
		
		Widget widgetPanel = createWidgetPanel();
		widgetPanel.addStyleName("division");
		widgetPanel.addStyleName("example");
		right.add(widgetPanel);
		
		m_eventListener.install();
	}
	
	private Widget createDocPanel()
	{
		String content = 
			"<H2>Debug Utilities</H2>" + 
			"<h4>Features</h4>" +
			"<dl>" +
			"  <dt>Tracing</dt>" +
			"  <dd>Debug output can be enabled at runtime via <code>Debug.enable()</code> or by pressing <code>Esc</code> twice (debug is enabled for this demo, but is otherwise disabled by default)." +
			"    You can <a href='#' onclick=\"Debug.prettyPrint(window);return false;\">pretty print</a> and <a href='#' onclick=\"Debug.dump(window);return false;\">dump</a> native JavaScript objects to the console." +
			"    Trace statments can be printed from JSNI and <a href='#' onclick=\"Debug.println('This is a pure HTML onclick handler');return false;\">external JavaScript</a>. " +
			"  </dd>" +
			"  <dt>Debug console</dt>" +
			"  <dd>Debugging messages are output to the in-browser Debug Console. In hosted mode, messages are mirrored to <code>System.out</code> and the GWT Shell. " +
			"    The console will be shown when it recieves output if it is enabled (it is enabled by default). " +
			"    Press <code>w</code> twice to enable/disable the debug console independently of other debug funtions. " +
			"  </dd>" +
			"  <dt>Event monitor</dt>" +
			"  <dd>Press <code>e</code> twice to enable event tracing. MouseMove, mouseIn, mouseOut &amp; scroll events are ignored by default. </dd>" +
			"  <dt>Stub implementation</dt>" +
			"  <dd>Facilitates compile-time removal of trace statements and debug class definitions. Place ahead of GWT Tk in your classpath when compiling. </dd>" +
			"</dl>" +
			"<p>Debug classes are packaged in a separate module for manual dependency elimination; just comment out the <code>inherit</code> element in your application's model descriptor. </p>" + 
			"<p>See the API documentation for usage notes. </p>" + 
			"";
		HTMLPanel doc = new HTMLPanel(content);
		DomUtil.setAttribute(doc, "id", "debug-doc");
		doc.addStyleName("description");
		return doc;
	}
	
	private Widget createControlPanel(final DebugEventListener eventListener)
	{
		BasicPanel outer = new BasicPanel();
		FlexTable table = new FlexTable();
		outer.add(table);
		
		// debug output
		table.setHTML(0, 0, "Debug&nbsp;output ");
		table.setWidget(0, 1, new Button("enable", new ClickListener()
				{
					public void onClick(Widget sender)
					{
						Debug.enable();
					}
				}));
		table.setWidget(0, 2, new Button("disable", new ClickListener()
				{
					public void onClick(Widget sender)
					{
						Debug.disable();
					}
				}));
		
		table.setHTML(1, 0, "Debug&nbsp;console ");
		table.setWidget(1, 1, new Button("enable", new ClickListener()
		{
			public void onClick(Widget sender)
			{
				DebugConsole.getInstance().enable();
			}
		}));
		table.setWidget(1, 2, new Button("disable", new ClickListener()
		{
			public void onClick(Widget sender)
			{
				DebugConsole.getInstance().disable();
			}
		}));
		
		table.setHTML(2, 0, "Event&nbsp;tracing ");
		table.setWidget(2, 1, new Button("enable", new ClickListener()
		{
			public void onClick(Widget sender)
			{
				eventListener.enable(true);
				Debug.println("You can trace any event. Some events are ignored by default");
			}
		}));
		table.setWidget(2, 2, new Button("disable", new ClickListener()
		{
			public void onClick(Widget sender)
			{
				eventListener.enable(false);
			}
		}));
		
		table.setWidget(3, 0, createCheckBoxes());
		((FlexCellFormatter) table.getCellFormatter()).setColSpan(3, 0, 3);
		
		return outer;
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
		
		ColumnPanel panel = new ColumnPanel();
		for (int i = 0; i < eventToMask.size(); i++)
		{
			if (i == 0 || i == 7)
			{
				panel.addCell();
			}
			EventToMask mapping = (EventToMask) eventToMask.get(i);
			addCheckBox(panel, mapping.m_event, mapping.m_mask, checkBoxController);
		}
		
		return panel;
	}
	
	private void addCheckBox(ColumnPanel parent, String label, int mask, ClickListener listener)
	{
		CheckBox cb = new CheckBox(label);
		DomUtil.setStyleAttribute(cb, "display", "block");
		DomUtil.setStyleAttribute(cb, "whiteSpace", "nowrap");
		cb.setChecked((m_eventListener.getEventMask() & mask) != 0);
		cb.addClickListener(listener);
		parent.addWidget(cb, false);
	}
	
	private Widget createWidgetPanel()
	{
		BasicPanel widgets = new BasicPanel();
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
