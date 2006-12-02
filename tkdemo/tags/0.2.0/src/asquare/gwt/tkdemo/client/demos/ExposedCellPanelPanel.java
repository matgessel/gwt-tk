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

import asquare.gwt.tk.client.ui.*;
import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;

public class ExposedCellPanelPanel extends RowPanel
{
	public ExposedCellPanelPanel()
	{
		DomUtil.setId(this, "rowPanelPanel");
		setSize("100%", "100%");
		
		String text = "<h2>RowPanel, ColumnPanel</h2>" +
				"<p>Flexible replacements for VerticalPanel &amp; HorizontalPanel</p>" + 
				"<ul><li>Allow multiple widgets in each table cell</li>" + 
				"<li>Exposes TD to CSS</li>" + 
				"<li>Simplifies layout by eliminating need for a nested container (e.g. the DIV in TabBar tabs)</li>" + 
				"<li>Random access to rows and widgets</li>" + 
				"</ul>";
		HTMLPanel description = new HTMLPanel(text);
		description.setStyleName("description division");
		add(description);
		
		ColumnPanel examples = new ColumnPanel();
		add(examples);
		
		BasicPanel ex1 = new BasicPanel();
		ex1.setStyleName("example division");
		examples.add(ex1);
		
		ex1.add(new Label("RowPanel"));
		
		RowPanel rp1 = new RowPanel();
		DomUtil.setId(rp1, "ex1");
		rp1.setSpacing(2);
		ex1.add(rp1);
		
		rp1.addCell();
		rp1.addCellStyleName("ex1-caption");
		Label label1 = new Label("Cell 1");
		label1.setWidth("100%");
		label1.setHeight("100%");
		rp1.addWidget(label1, false);
		
		rp1.addCell();
		rp1.addCellStyleName("ex1-content");
		Label label2 = new Label("Cell 2");
		rp1.addWidget(label2, false);
		
		rp1.addWidget(new Button("Button"), false);
		
		
		BasicPanel ex2 = new BasicPanel();
		ex2.setStyleName("example division");
		examples.add(ex2);
		
		ex2.add(new Label("ColumnPanel"));
		
		ColumnPanel cp1 = new ColumnPanel();
		DomUtil.setId(cp1, "ex1");
		cp1.setSpacing(2);
		ex2.add(cp1);
		
		cp1.addCell();
		cp1.addCellStyleName("ex1-caption");
		Label label3 = new Label("Cell 1");
		label3.setWidth("100%");
		label3.setHeight("100%");
		cp1.addWidget(label3, false);
		
		cp1.addCell();
		cp1.addCellStyleName("ex1-content");
		Label label4 = new Label("Cell 2");
		cp1.addWidget(label4, false);
		
		cp1.addWidget(new Button("Button"), false);
	}
}
