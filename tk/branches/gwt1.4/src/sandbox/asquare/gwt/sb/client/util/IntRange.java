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

public class IntRange extends Range
{
	private int m_length;
	
	public IntRange(int startIndex, int length)
	{
		super(startIndex);
		setLength(length);
	}
	
	public int getLength()
	{
		return m_length;
	}
	
	protected void setLength(int length)
	{
		if (length < 0)
			throw new IllegalArgumentException(String.valueOf(length));
		
		m_length = length;
	}
	
	protected Range subRangeImpl(int startIndex, int length)
	{
		return new IntRange(startIndex, length);
	}

	public void add(Range range)
	{
		add(range.getStartIndex(), range.getLength());
	}
	
	public void add(int startIndex, int length)
	{
		if (startIndex < 0)
			throw new IndexOutOfBoundsException(String.valueOf(startIndex));
		
		if (length < 0)
			throw new IllegalArgumentException(String.valueOf(length));
		
		if (! intersectesOrNeighbors(getStartIndex(), m_length, startIndex, length))
			throw new IllegalArgumentException(argsToString(getStartIndex(), m_length, startIndex, length));
		
		if (length == 0)
			return;
		
		int oldStartIndex = getStartIndex();
		int newStartIndex = Math.min(oldStartIndex, startIndex);
		setStartIndex(newStartIndex);
		m_length = Math.max(oldStartIndex + m_length, startIndex + length) - newStartIndex;
	}
	
	protected void subtractImpl(int startIndex, int length)
	{
		int oldStartIndex = getStartIndex();
		if (startIndex - oldStartIndex > 0)
		{
			m_length = startIndex - getStartIndex();
		}
		else
		{
			int maybeLength = oldStartIndex + m_length - (startIndex + length);
			if (maybeLength > 0)
			{
				setStartIndex(startIndex + length);
				m_length = maybeLength;
			}
			else
			{
				m_length = 0;
			}
		}
	}
	
	public Range duplicate()
	{
		return new IntRange(getStartIndex(), m_length);
	}
	
	public int[] toArray()
	{
		return toArray(new int[m_length], 0);
	}
	
	public int[] toArray(int[] result, int offset)
	{
		if (result == null)
			throw new IllegalArgumentException();
		
		if (offset < 0 || offset + m_length > result.length)
			throw new IndexOutOfBoundsException(String.valueOf(offset));
		
		int startIndex = getStartIndex();
		for (int i = 0, size = m_length; i < size; i++)
		{
			result[offset + i] = startIndex + i;
		}
		return result;
	}
}
