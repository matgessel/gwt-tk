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

import asquare.gwt.tk.client.ui.behavior.Controller;
import asquare.gwt.tk.client.ui.behavior.EventController;
import asquare.gwt.tk.client.ui.behavior.MouseEvent;
import asquare.gwt.tk.client.util.GwtUtil;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

public abstract class HoverControllerBase extends EventController
{
	public HoverControllerBase(Class<? extends Controller> id)
	{
		this(id, 0);
	}
	
	protected HoverControllerBase(Class<? extends Controller> id, int eventBits)
	{
		super(id, eventBits | MouseEvent.MOUSE_OVER | MouseEvent.MOUSE_OUT);
	}
	
	protected abstract CellId getCellId(Widget widget, Element element);
	
	/**
	 * Template method to perform an action when the hovered cell changes
	 * 
	 * @param widget the view processing the event
	 * @param cellId a cell id, or <code>null</code> if hover is lost
	 */
	protected abstract void onHoverChanged(Widget widget, CellId cellId);

    @Override
	public void onMouseOver(MouseEvent e)
	{
		Widget source = e.getSourceWidget();
		CellId targetCell = getCellId(source, e.getTarget());
		
		Element fromElement = e.getFrom();
		CellId fromCell = null;
		if (fromElement != null && DOM.isOrHasChild(source.getElement(), fromElement))
		{
			fromCell = getCellId(source, fromElement);
		}
		
		// Ignore "over" events generated within the same view cell
		if (! GwtUtil.equals(targetCell, fromCell))
		{
			onHoverChanged(source, targetCell);
		}
	}
	
    @Override
	public void onMouseOut(MouseEvent e)
	{
		Widget source = e.getSourceWidget();
		CellId targetCell = getCellId(source, e.getTarget());
		
		Element toElement = e.getTo();
		CellId toCell = null;
		if (toElement != null && DOM.isOrHasChild(source.getElement(), toElement))
		{
			toCell = getCellId(source, toElement);
		}
		
		// Ignore "out" events generated within the same view cell
		if (! GwtUtil.equals(targetCell, toCell))
		{
			/*
			 * Performance: ignore "out" events if the cursor is moving to
			 * another cell in the same view. (The over event will set
			 * the active index anyway, resulting in a 2nd update).
			 */ 
			if (toCell == null)
			{
				onHoverChanged(source, null);
			}
		}
	}
}
