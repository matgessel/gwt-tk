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

import java.util.ArrayList;

import asquare.gwt.sb.client.fw.ListModelEvent.ListChangeItemInsertion;
import asquare.gwt.sb.client.fw.ListModelEvent.ListChangeItemRemoval;
import asquare.gwt.sb.client.fw.ListModelEvent.ListItemPropertyChange;
import asquare.gwt.sb.client.fw.ModelChangeEventComplex.ChangeBase;
import asquare.gwt.sb.client.fw.ModelChangeEventComplex.PropertyChangeBase;
import asquare.gwt.sb.client.fw.ModelChangeEventComplex.PropertyChangeBoolean;
import asquare.gwt.sb.client.util.*;

/**
 * Note: does not set even/odd properties because row removal is done by
 * removing rows in the view. Row removal would invalidate the even/odd property
 * of each view item.
 */
public class ListUpdateController extends UpdateControllerBase implements ListModelListener
{
	private final ListModel<?> m_model;
	private final ListView m_view;
	private final ArrayList<String> m_styleProperties = new ArrayList<String>();
	private final ArrayList<String> m_contentProperties = new ArrayList<String>();
	
	public ListUpdateController(ListModel<?> model, ListView view)
	{
		m_model = model;
		m_view = view;
		m_contentProperties.add(ListModel.ITEM_PROPERTY_VALUE);
		m_styleProperties.add(ListModel.ITEM_PROPERTY_HOVER);
		m_styleProperties.add(ListModel.ITEM_PROPERTY_SELECTION);
		m_styleProperties.add(ListModel.ITEM_PROPERTY_ENABLED);
	}
	
	protected ListModel<?> getModel()
	{
		return m_model;
	}
	
	protected ListView getView()
	{
		return m_view;
	}
	
	protected Object getModelObject(int index)
	{
		return m_model.get(index);
	}
	
	/**
	 * Directs the controller to restyle an item if the specified model
	 * property changes.
	 */
	public void addStylePropertyChange(String propertyName)
	{
		if (propertyName == null)
			throw new IllegalArgumentException();
		
		m_styleProperties.add(propertyName);
	}
	
	public void removeStylePropertyChange(String propertyName)
	{
		if (propertyName == null)
			throw new IllegalArgumentException();
		
		m_styleProperties.remove(propertyName);
	}
	
	/**
	 * Directs the controller to redraw item content if the specified model
	 * property changes.
	 */
	public void addContentPropertyChange(String propertyName)
	{
		if (propertyName == null)
			throw new IllegalArgumentException();
		
		m_contentProperties.add(propertyName);
	}
	
	public void removeContentPropertyChange(String propertyName)
	{
		if (propertyName == null)
			throw new IllegalArgumentException();
		
		m_contentProperties.remove(propertyName);
	}
	
	protected void initImpl()
	{
		m_model.addListener(this);
		int size = m_model.getSize();
		if (size > 0)
		{
			Properties tempProperties = new Properties();
			IndexedCellId cellId = new IndexedCellIdImpl();
			for (int index = 0; index < size; index++)
			{
				cellId.setIndex(index);
				m_view.insert(cellId, getModelObject(index), configureCellProperties(index, m_model, tempProperties));
			}
		}
	}
	
	public void disposeImpl()
	{
		m_model.removeListener(this);
	}
	
	public void modelChanged(ListModelEvent event)
	{
		Properties tempProperties = new Properties();
		IntRangeCollection renderStyleItems = new IntRangeCollection();
		IntRangeCollection renderContentItems = new IntRangeCollection();
		
		for (int i = 0, size = event.getChangeCount(); i < size; i++)
		{
			processChange(event.getChangeAt(i), tempProperties, renderStyleItems, renderContentItems);
		}
		
		IntRangeCollection insertedItems = new IntRangeCollection();
		IntRangeCollection removedItems = new IntRangeCollection();
		for (int i = 0, size = event.getChangeCount(); i < size; i++)
		{
			ChangeBase change = event.getChangeAt(i);
			if (change instanceof ListChangeItemInsertion)
			{
				ListChangeItemInsertion insertion = (ListChangeItemInsertion) change;
				insertedItems.add(insertion.getIndex(), insertion.getCount());
			}
			if (change instanceof ListChangeItemRemoval)
			{
				ListChangeItemRemoval removal = (ListChangeItemRemoval) change;
				removedItems.add(removal.getIndex(), removal.getCount());
			}
		}
		
		if (insertedItems.getSize() > 0)
		{
			// update all following items (for even/odd/first/last properties)
			int min = insertedItems.getMinValue();
			if (min < m_model.getSize())
			{
				renderStyleItems.add(new IntRange(min, m_model.getSize() - min));
			}
		}
		
		if (removedItems.getSize() > 0)
		{
			// don't render the removed items
			renderStyleItems.removeAll(removedItems);
			renderContentItems.removeAll(removedItems);
			
			renderStyleItems.truncate(m_model.getSize());
			renderContentItems.truncate(m_model.getSize());
			
			// update all following items (for even/odd/first/last properties)
			int min = removedItems.getMinValue();
			if (min < m_model.getSize())
			{
				renderStyleItems.add(new IntRange(min, m_model.getSize() - min));
			}
		}
		
		IndexedCellId tempCellId = new IndexedCellIdImpl();
		for (int i = 0, size = renderStyleItems.getSize(); i < size; i++)
		{
			Range range = renderStyleItems.get(i);
			for (int index = range.getStartIndex(), terminus = index + range.getLength(); index < terminus; index++)
			{
				m_view.prepareElement(tempCellId.setIndex(index), getModelObject(index), configureCellProperties(index, m_model, tempProperties));
			}
		}
		for (int i = 0, size = renderContentItems.getSize(); i < size; i++)
		{
			Range range = renderContentItems.get(i);
			for (int index = range.getStartIndex(), terminus = index + range.getLength(); index < terminus; index++)
			{
				m_view.renderContent(tempCellId.setIndex(index), getModelObject(index), configureCellProperties(index, m_model, tempProperties));
			}
		}
	}
	
	protected void processChange(ChangeBase change, Properties tempProperties, IntRangeCollection renderStyleItems, IntRangeCollection renderContentItems)
	{
		if (change instanceof PropertyChangeBase)
		{
			processPropertyChange((PropertyChangeBase) change, tempProperties, renderStyleItems, renderContentItems);
		}
		else if (change instanceof IndexedChangeBase)
		{
			processListChange((IndexedChangeBase) change, tempProperties, renderStyleItems, renderContentItems);
		}
	}
	
	protected void processPropertyChange(PropertyChangeBase change, Properties tempProperties, IntRangeCollection renderStyleItems, IntRangeCollection renderContentItems)
	{
		if (ListModel.PROPERTY_ENABLED.equals(change.getName()))
		{
			m_view.setEnabled(((PropertyChangeBoolean) change).getNewValue());
		}
	}
	
	protected void processListChange(IndexedChangeBase listChange, Properties tempProperties, IntRangeCollection renderStyleItems, IntRangeCollection renderContentItems)
	{
		if (listChange instanceof ListChangeItemInsertion)
		{
			ListChangeItemInsertion itemInsertion = (ListChangeItemInsertion) listChange;
			IndexedCellId cellId = new IndexedCellIdImpl();
			for (int index = itemInsertion.getIndex(), terminus = index + itemInsertion.getCount(); index < terminus; index++)
			{
				cellId.setIndex(index);
				configureCellProperties(index, m_model, tempProperties);
				m_view.insert(cellId, getModelObject(index), tempProperties);
			}
		}
		else if (listChange instanceof ListChangeItemRemoval)
		{
			ListChangeItemRemoval itemRemoval = (ListChangeItemRemoval) listChange;
			IndexedCellId cellId = new IndexedCellIdImpl();
			for (int first = itemRemoval.getIndex(), index = first + itemRemoval.getCount() - 1; index >= first; index--)
			{
				cellId.setIndex(index);
				m_view.remove(cellId);
			}
		}
		else if (listChange instanceof ListItemPropertyChange)
		{
			processItemPropertyChange((ListItemPropertyChange) listChange, tempProperties, renderStyleItems, renderContentItems);
		}
	}
	
	protected void processItemPropertyChange(ListItemPropertyChange itemPropertyChange, Properties tempProperties, IntRangeCollection renderStyleItems, IntRangeCollection renderContentItems)
	{
		int index = itemPropertyChange.getIndex();
		int count = itemPropertyChange.getCount();
		if (m_styleProperties.contains(itemPropertyChange.getName()))
		{
			renderStyleItems.add(index, count);
		}
		if (m_contentProperties.contains(itemPropertyChange.getName()))
		{
			renderContentItems.add(index, count);
		}
	}
	
	protected Properties configureCellProperties(int index, ListModel<?> model, Properties properties)
	{
		int hoverIndex = model.getHoverCell() != null ? ((IndexedCellId) model.getHoverCell()).getIndex() : -1;
		properties.set(ListCellRenderer.PROPERTY_HOVER_INDEX, hoverIndex);
		properties.set(ListCellRenderer.PROPERTY_HOVER, hoverIndex == index);
		properties.set(ListCellRenderer.PROPERTY_SELECTED, model.isIndexSelected(index));
		properties.set(ListCellRenderer.PROPERTY_DISABLED, ! model.isIndexEnabled(index));
		properties.set(ListCellRenderer.PROPERTY_FIRST, index == 0);
		properties.set(ListCellRenderer.PROPERTY_LAST, index == model.getSize() - 1);
		properties.set(ListCellRenderer.PROPERTY_INDEX, index);
		return properties;
	}
	
//	public void modelChanged(ListModelEvent event)
//	{
//		int first = -1;
//		int last = -1;
//		for (int i = 0, size = event.getChangeCount(); i < size; i++)
//		{
//			ChangeBase change = event.getChangeAt(i);
//			
//			// instanceof is very fast in GWT
//			if (change instanceof ListChangeBase)
//			{
//				ListChangeBase listChange = (ListChangeBase) change;
//				if (first == -1)
//				{
//					first = listChange.getIndex();
//					last = listChange.getLastIndex();
//				}
//				else
//				{
//					first = Math.min(first, listChange.getIndex());
//					last = Math.max(last, listChange.getLastIndex());
//				}
//			}
//		}
//		
//		if (first != -1)
//		{
//			modelChanged(event.getListModel(), m_view, first, last);
//		}
//	}
//	
//	private void modelChanged(ListModel model, ListView view, int changeStart, int changeEnd)
//	{
//		Properties tempProperties = new Properties();
//		int newSize = model.getSize();
//		int oldSize = m_view.getSize();
//		
//		// ensure view has enough items
//		for (int i = oldSize; i < newSize; i++)
//		{
//			m_view.insert(i, model.get(i), configureCellProperties(i, model, tempProperties));
//		}
//		
//		// remove extra view items
//		for (int i = oldSize - 1; i >= newSize; i--)
//		{
//			m_view.remove(i);
//		}
//		
//		/*
//		 * Re-render previously existing view items. 
//		 * Don't render items that were just inserted. 
//		 * Don't render items that were removed. 
//		 */
//		IndexedCellId cellId = new IndexedCellId();
//		for (int i = changeStart; i < oldSize && i < newSize && i < changeEnd + 1; i++)
//		{
//			cellId.setIndex(i);
//			m_view.renderCell(cellId, model.get(i), configureCellProperties(i, model, tempProperties));
//		}
//	}
}
