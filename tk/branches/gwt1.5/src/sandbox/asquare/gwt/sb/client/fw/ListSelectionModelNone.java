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
package asquare.gwt.sb.client.fw;

public final class ListSelectionModelNone implements ListSelectionModel
{
	private static final int[] s_selectedIndices = new int[0];
	
	private static ListSelectionModelNone s_instance = null;
	
	private ListSelectionModelNone()
	{
	}
	
	public static ListSelectionModelNone getInstance()
	{
		if (s_instance == null)
		{
			s_instance = new ListSelectionModelNone();
		}
		return s_instance;
	}
	
	public void addListener(ListSelectionModelListener listener)
	{
		// NOOP
	}
	
	public void removeListener(ListSelectionModelListener listener)
	{
		// NOOP
	}
	
	public int getAnchorIndex()
	{
		return -1;
	}
	
	public int getLeadIndex()
	{
		return -1;
	}
	
	public boolean isIndexSelected(int index)
	{
		return false;
	}
	
	public boolean hasSelection()
	{
		return false;
	}
	
	public int[] getSelectedIndices()
	{
		return s_selectedIndices;
	}
	
	public int getMinSelectedIndex()
	{
		return -1;
	}
	
	public int getMaxSelectedIndex()
	{
		return -1;
	}
	
	public void clearSelection()
	{
	}
	
	public int getSelectionSize()
	{
		return 0;
	}
	
	public void addSelectionRange(int from, int to)
	{
	}
	
	public void setSelectionRange(int from, int to)
	{
	}
	
	public void removeSelectionRange(int from, int to)
	{
	}
	
	public void adjustForItemsInserted(int index, int count)
	{
	}
	
	public void adjustForItemsRemoved(int index, int count)
	{
	}
}
