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

public abstract class ListModelBase implements ListModel
{
	private final ChangeSupport m_changeSupport = new ChangeSupport();
	private final ListSelectionModel m_selectionModel;
	
	private int m_hoverIndex = -1;
	
	public ListModelBase(ListSelectionModel selectionModel)
	{
		m_selectionModel = selectionModel;
		if (m_selectionModel != null)
		{
			m_selectionModel.addListener(new ListSelectionModelListener()
			{
				public void listSelectionModelChanged(ListSelectionModel model, int index)
				{
					m_changeSupport.addChange(index);
				}
			});
		}
	}
	
	protected void addChange(int index)
	{
		m_changeSupport.addChange(index);
	}
	
	boolean isChanged()
	{
		return m_changeSupport.isChanged();
	}

	public void addListener(ModelListener listener)
	{
		m_changeSupport.addListener(listener);
	}

	public void removeListener(ModelListener listener)
	{
		m_changeSupport.removeListener(listener);
	}
	
	public ListSelectionModel getSelectionModel()
	{
		return m_selectionModel;
	}
	
	public boolean isIndexSelected(int index)
	{
		return m_selectionModel != null && m_selectionModel.isIndexSelected(index);
	}
	
	public boolean isIndexDisabled(int index)
	{
		return false;
	}
	
	public boolean isIndexHovering(int index)
	{
		return index == m_hoverIndex;
	}
	
	public void setHoverIndex(int index)
	{
		if (m_hoverIndex != index)
		{
			if (m_hoverIndex > -1)
			{
				m_changeSupport.addChange(m_hoverIndex);
			}
			m_hoverIndex = index;
			if (m_hoverIndex > -1)
			{
				m_changeSupport.addChange(m_hoverIndex);
			}
		}
	}
	
	public void update()
	{
		m_changeSupport.update();
	}
	
	private class ChangeSupport extends ModelChangeSupportHeavy
	{
		private int m_changeStart = -1;
		private int m_changeEnd = -1;
		
		public boolean isChanged()
		{
			return m_changeEnd > -1;
		}
		
		public void setChanged()
		{
			throw new UnsupportedOperationException();
		}
		
		protected void resetChanges()
		{
			m_changeStart = -1;
			m_changeEnd = -1;
		}
		
		protected void addChange(int index)
		{
			if (index < 0)
				throw new IndexOutOfBoundsException(String.valueOf(index));
			
			if (index < m_changeStart || m_changeStart == -1)
			{
				m_changeStart = index;
			}
			if (index > m_changeEnd)
			{
				m_changeEnd = index;
			}
		}
		
		protected ModelChangeEvent createChangeEvent()
		{
			return new IndexedModelEvent(ListModelBase.this, m_changeStart, m_changeEnd);
		}
	}
}
