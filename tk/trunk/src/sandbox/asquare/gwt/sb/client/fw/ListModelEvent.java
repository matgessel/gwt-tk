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

public class ListModelEvent extends ModelChangeEventComplex
{
	public ListModelEvent(SourcesModelChangeEventComplex source)
	{
		super(source);
	}
	
	public void addItemPropertyChange(String propertyName, int index, int count)
	{
		addChange(new ListItemPropertyChange(propertyName, index, count));
	}
	
	public ListItemPropertyChange getItemPropertyChange(String itemPropertyName)
	{
        int searchIndex = getIndexOfChange(ListItemPropertyChange.class);
		while (searchIndex != -1)
		{
            ListItemPropertyChange candidate = (ListItemPropertyChange) getChangeAt(searchIndex);
			if (candidate.getName().equals(itemPropertyName))
            {
            	return candidate;
            }
            searchIndex = getIndexOfChange(ListItemPropertyChange.class, searchIndex + 1);
		}
        return null;
	}
	
	public boolean hasItemPropertyChange(String itemPropertyName)
	{
        return getItemPropertyChange(itemPropertyName) != null;
	}
	
	public static class ListChangeItemInsertion extends IndexedChangeBase
	{
		public ListChangeItemInsertion(int index, int count)
		{
			super(ListChangeItemInsertion.class, index, count);
		}
		
		public boolean addChange(ChangeBase change)
		{
			return change instanceof ListChangeItemInsertion && addListChange((IndexedChangeBase) change);
		}
	}
	
	public static class ListChangeItemRemoval extends IndexedChangeBase
	{
		public ListChangeItemRemoval(int index, int count)
		{
			super(ListChangeItemRemoval.class, index, count);
		}
		
		public boolean addChange(ChangeBase change)
		{
			return change instanceof ListChangeItemRemoval && addListChange((IndexedChangeBase) change);
		}
	}
	
	public static class ListItemPropertyChange extends IndexedChangeBase
	{
		private final String m_name;
		
		public ListItemPropertyChange(String name, int index, int count)
		{
			super(ListItemPropertyChange.class, index, count);
			
			if (name == null)
				throw new IllegalArgumentException();
			
			m_name = name;
		}
		
		public String getName()
		{
			return m_name;
		}
		
		public boolean addChange(ChangeBase change)
		{
			if (change instanceof ListItemPropertyChange)
			{
				ListItemPropertyChange change0 = (ListItemPropertyChange) change;
				if (m_name.equals(change0.m_name))
				{
					return addListChange((IndexedChangeBase) change);
				}
			}
			return false;
		}
		
		public String toString()
		{
			return m_name + '[' + getIndex() + '-' + getEndIndex() + ']';
		}
	}
}
