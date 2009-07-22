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
package asquare.gwt.tests.memoryleak.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.*;

public class Demo implements EntryPoint
{
	private Button m_button = new Button("Add Popup");
	private PopupPanel m_popupPanel = null;
	
	public void onModuleLoad()
	{
		m_button.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				if (m_popupPanel == null)
				{
					m_popupPanel = new NonModalPopup();
					Grid grid = new Grid(10, 10);
					for (int row = 0; row < grid.getRowCount(); row++)
					{
						for (int col = 0; col < grid.getColumnCount(); col++)
						{
							grid.setHTML(row, col, "<img height='20' width='20' src='JohannesElison.jpg'/>");
						}
					}
					m_popupPanel.add(grid);
					m_popupPanel.show();
					m_button.setText("Remove Popup");
				}
				else
				{
					m_popupPanel.hide();
					m_popupPanel = null;
					m_button.setText("Add Popup");
				}
			}
		});
		RootPanel.get().add(m_button);
	}
	
	class NonModalPopup extends PopupPanel
	{
		public boolean onEventPreview(Event event)
		{
			return true;
		}
	}
}
