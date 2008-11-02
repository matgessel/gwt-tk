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
package asquare.gwt.tests.focus.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;

public class Demo implements EntryPoint
{
	public void onModuleLoad()
	{
		RootPanel.get().add(createFocusPanel());
	}

	private Widget createFocusPanel()
	{
		Table outer = new Table();
		
		outer.add(new FocusPanel(new Label("Label in a FocusPanel")));
		outer.add(new Button("Button"));
		outer.add(new CheckBox("CheckBox"));
		outer.add(new TextBox());
		outer.add(new PasswordTextBox());
		outer.add(new TextArea());
		outer.add(new RadioButton("group1", "RadioButton1"));
		outer.add(new RadioButton("group1", "RadioButton2"));
		ListBox listBox1 = new ListBox();
		listBox1.addItem("ListBox1");
		listBox1.addItem("item2");
		listBox1.addItem("item3");
		outer.add(listBox1);
		ListBox listBox2 = new ListBox();
		listBox2.setMultipleSelect(true);
		listBox2.setVisibleItemCount(3);
		listBox2.addItem("ListBox2");
		listBox2.addItem("item2");
		listBox2.addItem("item3");
		outer.add(listBox2);
		Tree tree = new Tree();
		tree.addItem("Tree");
		tree.addItem("item2");
		tree.addItem("item3");
		outer.add(tree);
		
		return outer;
	}
	
	private static class Table extends FlexTable
	{
		private int m_currentRow = 0;
		
		public void add(final Widget w)
		{
			setWidget(m_currentRow, 0, new Button("focus ->", new ClickListener()
			{
				public void onClick(Widget sender)
				{
					((HasFocus) w).setFocus(true);
				}
			}));
			setWidget(m_currentRow, 1, w);
			m_currentRow++;
		}
	}
}
