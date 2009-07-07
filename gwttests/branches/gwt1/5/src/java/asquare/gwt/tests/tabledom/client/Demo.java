/*
 * Copyright 2008 Mat Gessel <mat.gessel@gmail.com>
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
package asquare.gwt.tests.tabledom.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.*;

public class Demo implements EntryPoint
{
	private final TextArea m_textArea = new TextArea();

	public void onModuleLoad()
	{
		m_textArea.setVisibleLines(20);
		m_textArea.setCharacterWidth(80);
		
		final Button button = new Button();
		DOM.setStyleAttribute(button.getElement(), "display", "block");
		button.setText("Execute");
		button.addClickListener(new ClickListener()
		{
			public void onClick(final Widget aSender)
			{
				Element tbody = DOM.createTBody();
				setInnerHtml(tbody, "", "setInnerHtml(tbody, \"\")");
				setInnerHtml(tbody, "", "setInnerHtml(tbody, \"\") again");
				Element table = DOM.createTable();
				DOM.appendChild(table, tbody);
				setInnerHtml(tbody, "", "setInnerHtml(table.tbody, \"\")");
				setInnerHtml(tbody, "&nbsp;", "setInnerHtml(table.tbody, \"&nbsp;\")");
				setInnerHtml(tbody, "<tr></tr>", "setInnerHtml(table.tbody, \"<tr></tr>\")");
				setInnerHtml(tbody, "<tr><td></td></tr>", "setInnerHtml(table.tbody, \"<tr><td></td></tr>\")");
				DOM.appendChild(RootPanel.getBodyElement(), table);
				setInnerHtml(tbody, "", "setInnerHtml(body.table.tbody, \"\")");
				DOM.removeChild(table, tbody);
				setInnerHtml(tbody, "", "setInnerHtml(tbody, \"\")");
				
				Element td = DOM.createTD();
				setInnerHtml(td, "", "setInnerHtml(td, \"\")");
				Element tr = DOM.createTR();
				DOM.appendChild(tr, td);
				setInnerHtml(td, "", "setInnerHtml(tr.td, \"\")");
				tbody = DOM.createTBody();
				DOM.appendChild(tbody, tr);
				setInnerHtml(td, "", "setInnerHtml(tbody.tr.td, \"\")");
				table = DOM.createTable();
				DOM.appendChild(table, tbody);
				setInnerHtml(td, "", "setInnerHtml(table.tbody.tr.td, \"\")");
				setInnerHtml(tr, "", "setInnerHtml(table.tbody.tr, \"\")");
				setInnerHtml(tbody, "", "setInnerHtml(table.tbody, \"\")");
				setInnerHtml(table, "", "setInnerHtml(table, \"\")");
				
				setInnerHtml(DOM.createTable(), "", "setInnerHtml(new Table(), \"\")");
				
				tr = DOM.createTR();
				setInnerHtml(tr, "<td></td>", "setInnerHtml(tr, \"<td></td>\")");
			}
		});
		RootPanel.get().add(button);
		RootPanel.get().add(m_textArea);
	}
	
	private void setInnerHtml(Element element, String html, String message)
	{
		boolean passed = false;
		try
		{
			DOM.setInnerHTML(element, html);
			passed = true;
		}
		catch (Exception e)
		{
		}
		m_textArea.setText(m_textArea.getText() + (passed ? "passed: " : "FAILED: ") + message + "\r\n");
	}
}
