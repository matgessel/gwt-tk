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

import asquare.gwt.tk.client.ui.BasicPanel;
import asquare.gwt.tk.client.ui.CWrapper;
import asquare.gwt.tk.client.ui.behavior.FocusModel;
import asquare.gwt.tk.client.ui.behavior.TabFocusController;
import asquare.gwt.tk.client.util.DomUtil;
import asquare.gwt.tkdemo.client.FocusCyclePanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.*;

public class FocusCycleDemo extends BasicPanel
{
	public static final String NAME = "Focus";
	
	public FocusCycleDemo()
	{
		addStyleName("division");
		
		String content = "<H2>Focus Cycle</H2>" + 
			"<p>In some browsers you can programmatically override the default tab order. " +
			"Unsupported browsers use a stub implementation which allows the default behavior.</p>" +
			"<p>IE, Mozilla (Win) &amp; Safari are currently supported. <br/>" +
			"<code>asquare.gwt.tk.TabFocusController.gwt.xml</code><br/>" + 
			"<code>asquare.gwt.tk.client.ui.behavior.FocusModel</code><br/>" + 
			"<code>asquare.gwt.tk.client.ui.behavior.TabFocusController</code></p>";
		HTML header = new HTML(content);
		header.addStyleName("description");
		add(header);
		
		HorizontalPanel examples = new HorizontalPanel();
		examples.setWidth("100%");
		examples.addStyleName("example");
		
		examples.add(createFocusCycle1());
		examples.add(createFocusCycle2());

		add(examples);
	}
	
	private Widget createFocusCycle1()
	{
		FocusCyclePanel cycle1 = new FocusCyclePanel("div", "block");
		
		cycle1.add(new Label("Cycle 1"));
		cycle1.add(new Button("Button"));
		TextBox textBox = new TextBox();
		textBox.setText("TextBox");
		cycle1.add(textBox);
		PasswordTextBox pwBox = new PasswordTextBox();
		pwBox.setText("PasswordTextBox");
		cycle1.add(pwBox);
		TextArea textArea = new TextArea();
		textArea.setText("TextArea");
		cycle1.add(textArea);
		
		return cycle1;
	}
	
	private Widget createFocusCycle2()
	{
		BasicPanel cycle2 = new BasicPanel("div", "block");
		FocusModel focusModel = new FocusModel();
		TabFocusController focusController = (TabFocusController) GWT.create(TabFocusController.class);
		focusController.setModel(focusModel);
		
		cycle2.add(new Label("Cycle 2"));
		Label label = new Label("Focus cycle across containers");
		DomUtil.setStyleAttribute(label, "font-size", "smaller");
		cycle2.add(label);
		HorizontalPanel containers = new HorizontalPanel();
		
		Button[] buttons = new Button[6];
		for (int i = 0; i < buttons.length; i++)
		{
			buttons[i] = new Button("index&nbsp;" + i);
		}
		VerticalPanel container1 = new VerticalPanel();
		container1.addStyleName("focus-container");
		VerticalPanel container2 = new VerticalPanel();
		container2.addStyleName("focus-container");
		
		for (int i = 0; i < buttons.length; i+=2)
		{
			container1.add(buttons[i]);
			focusModel.add(buttons[i]);
			container2.add(buttons[i + 1]);
			focusModel.add(buttons[i + 1]);
		}
		
		containers.add(new CWrapper(container1).addController(focusController));
		containers.add(new CWrapper(container2).addController(focusController));
		cycle2.add(containers);
		
		return cycle2;
	}
}
