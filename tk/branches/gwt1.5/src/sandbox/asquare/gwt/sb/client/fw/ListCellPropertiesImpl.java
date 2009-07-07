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

public class ListCellPropertiesImpl extends CellPropertiesImpl implements ListCellProperties
{
	public ListCellPropertiesImpl configure(int index, ListModel<?> model)
	{
		int hoverIndex = (model.getHoverCell() != null) ? ((IndexedCellId) model.getHoverCell()).getIndex() : -1;
		return configure(index, hoverIndex, model.isIndexSelected(index, true), ! model.isIndexEnabled(index), model.getSize());
	}
	
	public ListCellPropertiesImpl configure(int index, int hoverIndex, boolean selected, boolean disabled, int listSize)
	{
		setHoverIndex(hoverIndex);
		setHover(hoverIndex == index);
		setSelected(selected && ! disabled);
		setDisabled(disabled);
		setFirst(index == 0);
		setLast(index == listSize - 1);
		setIndex(index);
		return this;
	}
	
	public boolean isFirst()
	{
		return getBoolean(FIRST);
	}
	
	public void setFirst(boolean first)
	{
		setBoolean(FIRST, first);
	}
	
	public boolean isLast()
	{
		return getBoolean(LAST);
	}
	
	public void setLast(boolean last)
	{
		setBoolean(LAST, last);
	}
	
	public int getIndex()
	{
		return getInt(INDEX);
	}
	
	public void setIndex(int index)
	{
		setInt(INDEX, index);
	}
	
	public boolean isOdd()
	{
		return getBoolean(ODD);
	}
	
	public void setOdd(boolean odd)
	{
		setBoolean(ODD, odd);
	}
	
	public boolean isEven()
	{
		return getBoolean(EVEN);
	}
	
	public void setEven(boolean even)
	{
		setBoolean(EVEN, even);
	}
	
	public int getHoverIndex()
	{
		return getInt(HOVER_INDEX);
	}
	
	public void setHoverIndex(int hoverIndex)
	{
		setInt(HOVER_INDEX, hoverIndex);
	}
}
