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
package asquare.gwt.tests.focusevent.client;

import java.util.HashMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.user.client.ui.*;

public class Demo implements EntryPoint
{
	public void onModuleLoad()
	{
		RootPanel.get("gwt").add(createDemoPanel());
	}
	
	private Widget createDemoPanel()
	{
		TestPanel outer = new TestPanel();
		
		outer.add(new Label("GWT onfocus Event Handler"));
		outer.add(new Button("Button"), "Button");
		outer.add(new CheckBox("CheckBox"), "CheckBox");
		outer.add(new RadioButton("group1", "RadioButton1"), "RadioButton1");
		TextBox textBox = new TextBox();
		textBox.setText("TextBox");
		outer.add(textBox, "TextBox");
		PasswordTextBox passwordTextBox = new PasswordTextBox();
		passwordTextBox.setText("PasswordTextBox");
		outer.add(passwordTextBox, "PasswordTextBox");
		TextArea textArea = new TextArea();
		textArea.setText("TextArea");
		outer.add(textArea, "TextArea");
		ListBox listBox = new ListBox();
		listBox.addItem("ListBox");
		listBox.addItem("item2");
		listBox.addItem("item3");
		outer.add(listBox, "ListBox");
		outer.add(new FocusPanel(new Label("Label in a FocusPanel")), "FocusPanel");
		Tree tree = new Tree();
		tree.addItem("item1");
		tree.addItem("item2");
		tree.addItem("item3");
		outer.add(tree, "Tree");
		
		return outer;
	}
	
	private static class TestPanel extends VerticalPanel implements FocusHandler
	{
		private final HashMap<Widget, UIObject> m_borders = new HashMap<Widget, UIObject>();
		
		public void add(Widget w, String name)
		{
			FlowPanel border = new FlowPanel();
			border.addStyleName("fail");
			border.add(new Label(name));
			border.add(w);
			add(border);
			m_borders.put(w, border);
			((HasFocusHandlers) w).addFocusHandler(this);
		}
		
		public void onFocus(FocusEvent event)
		{
			UIObject border = m_borders.get(event.getSource());
			border.removeStyleName("fail");
			border.addStyleName("pass");
		}
	}
}
