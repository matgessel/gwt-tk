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
package asquare.gwt.sb.client.widget;

import asquare.gwt.sb.client.fw.*;
import asquare.gwt.tk.client.ui.behavior.*;

public abstract class ListSelectionControllerBase extends EventController
{
	private final ExplicitUpdateModel m_model;
	
	private ListSelectionModel m_selectionModel;
	
	public ListSelectionControllerBase(ListSelectionModel selectionModel, ExplicitUpdateModel model)
	{
		super(ListSelectionControllerBase.class);
		addHandler(new DragController(new ListDragHandler()));
		m_selectionModel = selectionModel;
		m_model = model;
	}
	
	protected abstract int getIndex(EventBase e);
	
	public ListSelectionModel getSelectionModel()
	{
		return m_selectionModel;
	}
	
	public void setSelectionModel(ListSelectionModel selectionModel)
	{
		m_selectionModel = selectionModel;
	}
	
	private class ListDragHandler extends EventControllerBase
	{
		private int m_anchorIndex = -1;
		private Strategy m_strategy = null;
		
		public ListDragHandler()
		{
			super(MouseEvent.MOUSE_DOWN | MouseEvent.MOUSE_OVER | MouseEvent.MOUSE_UP);
		}
		
		public void onMouseDown(MouseEvent e)
		{
	        m_anchorIndex = getIndex(e);
	        if (m_anchorIndex != -1)
	        {
	            if (e.isShiftDown())
	            {
	                m_strategy = new AddStrategy();
	            }
	            else if (e.isControlDown())
	            {
	                m_strategy = new ToggleStrategy();
	            }
	            else
	            {
	                m_strategy = new DefaultStrategy();
	            }
	            m_strategy.doMouseDown(m_selectionModel, m_anchorIndex, m_anchorIndex, e);
	            m_model.update();
	        }
		}
		
		public void onMouseOver(MouseEvent e)
		{
	        if (m_anchorIndex != -1)
	        {
	        	int leadIndex = getIndex(e);
	        	if (leadIndex != -1)
	        	{
	        		m_strategy.doMouseOver(m_selectionModel, m_anchorIndex, leadIndex, e);
	        		m_model.update();
	        	}
	        }
		}
		
		public void onMouseUp(MouseEvent e)
		{
			m_strategy = null;
			m_anchorIndex = -1;
		}
	}
	
	private static interface Strategy
	{
		void doMouseDown(ListSelectionModel selectionModel, int anchorIndex, int leadIndex, MouseEvent e);

		void doMouseOver(ListSelectionModel selectionModel, int anchorIndex, int leadIndex, MouseEvent e);
	}
	
	private static class DefaultStrategy implements Strategy
	{
		public void doMouseDown(ListSelectionModel selectionModel, int anchorIndex, int leadIndex, MouseEvent e)
		{
			selectionModel.setSelectionRange(anchorIndex, leadIndex);
        }
		
		public void doMouseOver(ListSelectionModel selectionModel, int anchorIndex, int leadIndex, MouseEvent e)
		{
            selectionModel.setSelectionRange(anchorIndex, leadIndex);
		}
	}
	
	private static class AddStrategy implements Strategy
	{
		public void doMouseDown(ListSelectionModel selectionModel, int anchorIndex, int leadIndex, MouseEvent e)
		{
			if (selectionModel.hasSelection())
        	{
        		anchorIndex = (leadIndex < selectionModel.getMinSelectedIndex()) ? selectionModel.getMaxSelectedIndex() : selectionModel.getMinSelectedIndex();
        	}
			selectionModel.addSelectionRange(anchorIndex, leadIndex);
		}
		
		public void doMouseOver(ListSelectionModel selectionModel, int anchorIndex, int leadIndex, MouseEvent e)
		{
			selectionModel.addSelectionRange(anchorIndex, leadIndex);
		}
	}
	
	private static class ToggleStrategy implements Strategy
	{
		private boolean m_adding;
		
		public void doMouseDown(ListSelectionModel selectionModel, int anchorIndex, int leadIndex, MouseEvent e)
		{
			m_adding = ! selectionModel.isIndexSelected(leadIndex);
			doToggle(selectionModel, anchorIndex, leadIndex, m_adding);
		}
		
		public void doMouseOver(ListSelectionModel selectionModel, int anchorIndex, int leadIndex, MouseEvent e)
		{
			doToggle(selectionModel, anchorIndex, leadIndex, m_adding);
		}
		
		public void doToggle(ListSelectionModel selectionModel, int anchorIndex, int leadIndex, boolean adding)
		{
			if (adding)
        	{
				selectionModel.addSelectionRange(anchorIndex, leadIndex);
        	}
        	else
        	{
        		selectionModel.removeSelectionRange(anchorIndex, leadIndex);
        	}
		}
	}
}
