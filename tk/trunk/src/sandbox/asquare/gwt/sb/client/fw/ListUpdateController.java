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

import asquare.gwt.sb.client.util.Properties;

public class ListUpdateController implements ModelListener
{
	private final ListView m_view;
	
	public ListUpdateController(ListModel model, ListView view)
	{
		m_view = view;
		model.addListener(this);
		
		// populate view if model is not empty
		int changeEnd = model.getSize() - 1;
		if (changeEnd > -1)
		{
			modelChanged(model, 0, changeEnd);
		}
	}
	
	protected ListView getView()
	{
		return m_view;
	}
	
	public void modelChanged(ModelChangeEvent e)
	{
		IndexedModelEvent lme = (IndexedModelEvent) e;
		modelChanged((ListModel) lme.getSource(), lme.getBeginInterval(), lme.getEndInterval());
	}
	
	private void modelChanged(ListModel model, int changeStart, int changeEnd)
	{
		Properties tempProperties = new Properties();
		int newSize = model.getSize();
		int oldSize = m_view.getSize();
		
		// ensure view has enough items
		for (int i = oldSize; i < newSize; i++)
		{
			m_view.insert(i, model.get(i), configureCellProperties(i, model, tempProperties));
		}
		
		// remove extra view items
		for (int i = oldSize - 1; i >= newSize; i--)
		{
			m_view.remove(i);
		}
		
		/*
		 * Re-render previously existing view items. 
		 * Don't render items that were just inserted. 
		 * Don't render items that were removed. 
		 */
		for (int i = changeStart; i < oldSize && i < newSize && i < changeEnd + 1; i++)
		{
			m_view.renderCell(i, model.get(i), configureCellProperties(i, model, tempProperties));
		}
	}
	
	protected Properties configureCellProperties(int index, ListModel model, Properties properties)
	{
		properties.set(CellRenderer.PROPERTY_SELECTED, model.isIndexSelected(index));
		properties.set(CellRenderer.PROPERTY_HOVER, model.isIndexHovering(index));
		properties.set(CellRenderer.PROPERTY_DISABLED, model.isIndexDisabled(index));
		properties.set(CellRenderer.PROPERTY_FIRST, index == 0);
		properties.set(CellRenderer.PROPERTY_LAST, index == model.getSize() - 1);
		properties.set(CellRenderer.PROPERTY_INDEX, index);
		return properties;
	}
}
