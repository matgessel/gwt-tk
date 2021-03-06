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
package asquare.gwt.sb.client.fw;

import asquare.gwt.tk.client.ui.behavior.ControllerAdaptor;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

public class ListHoverController extends ControllerAdaptor
{
	private final ListModel m_model;
	
	public ListHoverController(ListModel model)
	{
		super(Event.ONMOUSEOVER | Event.ONMOUSEOUT, ListHoverController.class);
		m_model = model;
	}
	
	public void onBrowserEvent(Widget widget, Event event)
	{
		ListView list = (ListView) widget;
		int targetIndex = list.getIndexOf(DOM.eventGetTarget(event));
		
		switch (DOM.eventGetType(event))
		{
			case Event.ONMOUSEOVER: 
				int fromIndex = list.getIndexOf(DOM.eventGetFromElement(event));
				
				// Ignore over events generated within the same list item
				if (targetIndex != fromIndex)
				{
					m_model.setActiveIndex(targetIndex);
					m_model.update();
				}
				break;
			
			case Event.ONMOUSEOUT: 
				int toIndex = list.getIndexOf(DOM.eventGetToElement(event));
				
				// Ignore out events generated within the same list item
				if (targetIndex != toIndex)
				{
					/*
					 * Performance: ignore out events if the cursor is moving to
					 * another item in the same list. (The over event will set
					 * the active index anyway, resulting in a 2nd update).
					 */ 
					if (toIndex == -1)
					{
						m_model.setActiveIndex(-1);
						m_model.update();
					}
				}
				break;
		}
	}
}
