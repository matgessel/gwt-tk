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
package asquare.gwt.sb.client.util;

import java.util.ArrayList;
import java.util.List;

import asquare.gwt.tk.client.util.GwtUtil;

public class TypedList
{
	/**
	 * Interleaved list of type/value pairs. Even indices (0,2,4...) specify
	 * the value type for the following value.
	 */
	private final ArrayList m_list = new ArrayList();
	
	public boolean isType(int index, Object type)
	{
		return m_list.get(index * 2).equals(type);
	}
	
	public Object getValue(int index)
	{
		return m_list.get(index * 2 + 1);
	}
	
	public Object getType(int index)
	{
		return m_list.get(index * 2);
	}
	
	public boolean containsType(Object type)
	{
		for (int i = 0, size = m_list.size(); i < size; i+=2)
		{
			if (GwtUtil.equals(type, m_list.get(i)))
			{
				return true;
			}
		}
		return false;
	}
	
	public int getSize()
	{
		return m_list.size() / 2;
	}
	
	public void add(Object type, Object value)
	{
		m_list.add(type);
		m_list.add(value);
	}
	
	public void remove(Object type, Object value)
	{
		for (int i = 0, size = getSize(); i < size; i++)
		{
			if (getValue(i) == value && getType(i).equals(type))
			{
				remove(i);
				break;
			}
		}
	}
	
	public void remove(Object value)
	{
		int index = m_list.indexOf(value);
		if (index != -1)
		{
			remove((index - 1) / 2);
		}
	}
	
	public void removeAll(Object value)
	{
		int index = m_list.indexOf(value);
		while (index != -1)
		{
			remove((index - 1) / 2);
			index = m_list.indexOf(value);
		}
	}
	
	public void remove(int index)
	{
		int actualIndex = index * 2;
		m_list.remove(actualIndex); // type
		m_list.remove(actualIndex); // value
	}
	
	public void clear()
	{
		m_list.clear();
	}
	
	public int getIndexOfType(Object type)
	{
		return getIndexOfType(type, 0);
	}
	
	public int getIndexOfType(Object type, int fromIndex)
	{
		for (int i = fromIndex, size = getSize(); i < size; i++)
		{
			if (getType(i).equals(type))
			{
				return i;
			}
		}
		return -1;
	}
	
	public Object getValue(Object type)
	{
		return getValue(type, 0);
	}
	
	public Object getValue(Object type, int fromIndex)
	{
		int index = getIndexOfType(type, fromIndex);
		return index != -1 ? getValue(index) : null;
	}
	
	public int getValueCountFor(Object type)
	{
		int result = 0;
		for (int i = 0, size = m_list.size(); i < size; i+=2)
		{
			if (m_list.get(i).equals(type))
			{
				result++;
			}
		}
		return result;
	}
	
	public List getValuesFor(Object type)
	{
		List result = new ArrayList();
		for (int i = 0, size = m_list.size(); i < size; i+=2)
		{
			if (m_list.get(i).equals(type))
			{
				result.add(m_list.get(i + 1));
			}
		}
		return result;
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append('[');
		for (int i = 0, size = getSize(); i < size; i++)
		{
			if (i > 0)
			{
				sb.append(", ");
			}
			sb.append(getValue(i));
		}
		sb.append(']');
		return sb.toString();
	}
}
