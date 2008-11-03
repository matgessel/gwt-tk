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

public class IndexedCellIdImpl extends CellIdImpl implements IndexedCellId
{
	private int m_index;
	
	public IndexedCellIdImpl()
	{
	}
	
	public IndexedCellIdImpl(int index)
	{
		setIndex(index);
	}
	
	public IndexedCellIdImpl(IndexedCellId id)
	{
		m_index = id.getIndex();
	}
	
	public int getIndex()
	{
		return m_index;
	}

	public IndexedCellId setIndex(int index)
	{
		if (index < 0)
			throw new IndexOutOfBoundsException(String.valueOf(index));
		
		m_index = index;
		return this;
	}
	
	public IndexedCellId set(IndexedCellId id)
	{
		m_index = id.getIndex();
		return this;
	}
	
	public int hashCode()
	{
		return m_index;
	}
	
	public boolean equals(Object obj)
	{
		return obj instanceof IndexedCellId && ((IndexedCellId) obj).getIndex() == m_index;
	}
	
	public String toString()
	{
		return "[index=" + m_index + "]";
	}
}
