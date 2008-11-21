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

import java.util.EventListener;

import asquare.gwt.sb.client.fw.ModelChangeEventComplex.ChangeBase;
import asquare.gwt.sb.client.util.RangeCollection;

public abstract class ListModelBase<E> implements ListModel<E>, SourcesModelChangeEventComplex
{
	private final ListModelChangeSupport m_changeSupport;
	private final ListSelectionModel m_selectionModel;
	
	private boolean m_enabled = true;
	private IndexedCellId m_hoverCell = null;
	
	public ListModelBase(ListSelectionModel selectionModel)
	{
		this(selectionModel, null);
	}
	
	protected ListModelBase(ListSelectionModel selectionModel, ListModelChangeSupport changeSupport)
	{
		m_changeSupport = changeSupport != null ? changeSupport : new ListModelChangeSupport(this);
		m_selectionModel = selectionModel;
		if (m_selectionModel != null)
		{
			m_selectionModel.addListener(new ListSelectionModelListener()
			{
				public void listSelectionModelChanged(ListSelectionModelEvent e)
				{
					RangeCollection selectedRanges = e.getSelectedRanges();
					for (int i = 0, size = selectedRanges.getSize(); i < size; i++)
					{
						addItemPropertyChange(ListModel.ITEM_PROPERTY_SELECTION, selectedRanges.get(i).getStartIndex(), selectedRanges.get(i).getLength());
					}
					RangeCollection deselectedRanges = e.getDeselectedRanges();
					for (int i = 0, size = deselectedRanges.getSize(); i < size; i++)
					{
						addItemPropertyChange(ListModel.ITEM_PROPERTY_SELECTION, deselectedRanges.get(i).getStartIndex(), deselectedRanges.get(i).getLength());
					}
				}
			});
		}
	}
	
	protected ListModelChangeSupport getChangeSupport()
	{
		return m_changeSupport;
	}
	
	protected void addChange(ChangeBase change)
	{
		m_changeSupport.addChange(change);
	}
	
	protected void addItemPropertyChange(String name, int index, int count)
	{
		m_changeSupport.addItemPropertyChange(name, index, count);
	}
	
	public void resetChanges()
	{
		m_changeSupport.resetChanges();
	}
	
	public void addListener(ListModelListener listener)
	{
		m_changeSupport.addListener(listener);
	}

	public void removeListener(ListModelListener listener)
	{
		m_changeSupport.removeListener(listener);
	}
	
	boolean isChanged()
	{
		return m_changeSupport.isChanged();
	}

	public ListSelectionModel getSelectionModel()
	{
		return m_selectionModel;
	}
	
	public boolean isIndexSelected(int index)
	{
		return m_selectionModel != null && m_selectionModel.isIndexSelected(index);
	}
	
	public boolean isIndexEnabled(int index)
	{
		return m_enabled;
	}
	
	public boolean isEnabled()
	{
		return m_enabled;
	}
	
	public void setEnabled(boolean enabled)
	{
		if (m_changeSupport.propertyChanged(PROPERTY_ENABLED, m_enabled, enabled))
		{
			m_enabled = enabled;
			int size = getSize();
			if (size > 0)
			{
				m_changeSupport.addItemPropertyChange(ITEM_PROPERTY_ENABLED, 0, size);
			}
		}
	}
	
	public CellId getHoverCell()
	{
		return m_hoverCell;
	}
	
	public void setHoverCell(CellId cellId)
	{
		setHoverCell((IndexedCellId) cellId);
	}
	
	public void setHoverCell(IndexedCellId cellId)
	{
		if (m_hoverCell != cellId)
		{
			if (m_hoverCell != null)
			{
				addItemPropertyChange(ListModel.ITEM_PROPERTY_HOVER, m_hoverCell.getIndex(), 1);
			}
			m_hoverCell = cellId;
			if (m_hoverCell != null)
			{
				addItemPropertyChange(ListModel.ITEM_PROPERTY_HOVER, m_hoverCell.getIndex(), 1);
			}
		}
	}
	
	public void update()
	{
		m_changeSupport.update();
	}
	
	public static class ListModelChangeSupport extends ModelChangeSupportComplex
	{
		public ListModelChangeSupport(SourcesModelChangeEventComplex source)
		{
			super(source);
		}
		
		public void addListener(ListModelListener listener)
		{
			addListenerImpl(listener);
		}
		
		public void removeListener(ListModelListener listener)
		{
			removeListenerImpl(listener);
		}
		
		public void addItemPropertyChange(String name, int index, int count)
		{
			((ListModelEvent) getEvent()).addItemPropertyChange(name, index, count);
		}
		
		protected ModelChangeEventComplex createChangeEvent(SourcesModelChangeEventComplex source)
		{
			return new ListModelEvent(source);
		}
		
		protected void notifyListener(EventListener listener, Object source, ModelChangeEventComplex event)
		{
			((ListModelListener) listener).modelChanged((ListModelEvent) event);
		}
	}
}
