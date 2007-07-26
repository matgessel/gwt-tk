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

public class IndexedViewHoverController extends ControllerAdaptor
{
	private final ModelHoverSupport m_model;
	
	public IndexedViewHoverController(ModelHoverSupport model)
	{
		super(Event.ONMOUSEOVER | Event.ONMOUSEOUT, IndexedViewHoverController.class);
		m_model = model;
	}
	
	public void plugIn(Widget widget)
	{
		if (! (widget instanceof IndexedView))
			throw new ClassCastException();
	}
	
	public void onBrowserEvent(Widget widget, Event event)
	{
		IndexedView indexedView = (IndexedView) widget;
		int targetIndex = indexedView.getIndexOf(DOM.eventGetTarget(event));
		
		switch (DOM.eventGetType(event))
		{
			case Event.ONMOUSEOVER: 
				int fromIndex = indexedView.getIndexOf(DOM.eventGetFromElement(event));
				
				// Ignore over events generated within the same view cell
				if (targetIndex != fromIndex)
				{
					m_model.setHoverIndex(targetIndex);
					m_model.update();
				}
				break;
			
			case Event.ONMOUSEOUT: 
				int toIndex = indexedView.getIndexOf(DOM.eventGetToElement(event));
				
				// Ignore out events generated within the same view cell
				if (targetIndex != toIndex)
				{
					/*
					 * Performance: ignore out events if the cursor is moving to
					 * another cell in the same view. (The over event will set
					 * the active index anyway, resulting in a 2nd update).
					 */ 
					if (toIndex == -1)
					{
						m_model.setHoverIndex(-1);
						m_model.update();
					}
				}
				break;
		}
	}
}
