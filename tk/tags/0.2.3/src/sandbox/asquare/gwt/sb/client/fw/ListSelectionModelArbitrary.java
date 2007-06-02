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

import java.util.Vector;

public class ListSelectionModelArbitrary extends ListSelectionModelBase
{
	private static final Object SELECTED = Boolean.TRUE;
	private static final Object NOTSELECTED = null;
	
	/*
	 * Acts as an int to object map. Int = index, Boolean.TRUE = selected. 
	 */
	private Vector m_selection = new Vector();
	
	public boolean isIndexSelected(int index)
	{
		if (index < 0)
			throw new IndexOutOfBoundsException();
		
		return m_selection.size() > index && m_selection.get(index) == SELECTED;
	}
	
	public void setIndexSelected(int index, boolean selected)
	{
		if (selected)
		{
			if (m_selection.size() <= index)
			{
				m_selection.setSize(index + 1);
			}
			if (m_selection.get(index) != SELECTED)
			{
				m_selection.set(index, SELECTED);
				fireSelectionChange(index);
			}
		}
		else 
		{
			if (isIndexSelected(index))
			{
				// must call set(). remove() will shift elements down. 
				m_selection.set(index, NOTSELECTED);
				fireSelectionChange(index);
			}
		}
	}
	
	public void clearSelection()
	{
		for (int i = m_selection.size() - 1; i >=0; i--)
		{
			m_selection.remove(i);
			fireSelectionChange(i);
		}
	}
}
