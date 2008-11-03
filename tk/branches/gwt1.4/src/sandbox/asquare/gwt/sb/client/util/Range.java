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

public abstract class Range
{
	private int m_startIndex;
	
	public Range(int startIndex)
	{
		setStartIndex(startIndex);
	}
	
	public static boolean equals(Range range1, Range range2)
	{
		return equals(range1.m_startIndex, range1.getLength(), range2.m_startIndex, range2.getLength());
	}
	
	public static boolean equals(int referenceIndex, int referenceLength, int targetIndex, int targetLength)
	{
		return referenceIndex == targetIndex && referenceLength == targetLength;
	}
	
	/**
	 * Determines if a range overlaps another range. This includes total containment. 
	 * 
	 * @return <code>true</code> if the ranges overlap
	 */
	public static boolean intersects(int index1, int length1, int index2, int length2)
	{
		if (index1 < 0 || index2 < 0)
			throw new IndexOutOfBoundsException(argsToString(index1, length1, index2, length2));
		
		if (length1 < 0 || length2 < 0)
			throw new IllegalArgumentException(argsToString(index1, length1, index2, length2));
		
		return index2 + length2 > index1 && index2 < index1 + length1;
	}
	
	/**
	 * Determines if a range overlaps, is contained by or neigbors another
	 * range.
	 * 
	 * @return <code>true</code> if the ranges overlap or neigbor
	 */
	public static boolean intersectesOrNeighbors(int index1, int length1, int index2, int length2)
	{
		if (index1 < 0 || index2 < 0)
			throw new IndexOutOfBoundsException(argsToString(index1, length1, index2, length2));
		
		if (length1 < 0 || length2 < 0)
			throw new IllegalArgumentException(argsToString(index1, length1, index2, length2));
		
		return index2 + length2 >= index1 && index2 <= index1 + length1;
	}
	
	/**
	 * Determines if a range contains an index.
	 * 
	 * @return <code>true</code> if the reference range contains the target
	 *         index
	 */
	public static boolean contains(int referenceIndex, int referenceLength, int targetIndex)
	{
		return contains(referenceIndex, referenceLength, targetIndex, 1);
	}
	
	/**
	 * Determines if a range contains another range.
	 * 
	 * @return <code>true</code> if the reference range contains the target
	 *         range
	 */
	public static boolean contains(int referenceIndex, int referenceLength, int targetIndex, int targetLength)
	{
		if (referenceIndex < 0 || targetIndex < 0)
			throw new IndexOutOfBoundsException(argsToString(referenceIndex, referenceLength, targetIndex, targetLength));
		
		if (referenceLength < 0 || targetLength < 0)
			throw new IllegalArgumentException(argsToString(referenceIndex, referenceLength, targetIndex, targetLength));
		
		return targetIndex >= referenceIndex && targetIndex + targetLength <= referenceIndex + referenceLength;
	}
	
	public static int getCombinedIndex(int referenceIndex, int targetIndex)
	{
		return Math.min(referenceIndex , targetIndex);
	}
	
	public static int getCombinedLength(int referenceIndex, int referenceLength, int targetIndex, int targetLength)
	{
		return Math.max(referenceIndex + referenceLength, targetIndex + targetLength) - Math.min(referenceIndex , targetIndex);
	}
	
	public int getStartIndex()
	{
		return m_startIndex;
	}
	
	public void setStartIndex(int index)
	{
		if (index < 0)
			throw new IndexOutOfBoundsException(String.valueOf(index));
		
		m_startIndex = index;
	}
	
	public int getEndIndex()
	{
		return m_startIndex + getLength() - 1;
	}
	
	public int getTerminus()
	{
		return m_startIndex + getLength();
	}
	
	public abstract int getLength();
	
	public boolean intersects(Range range)
	{
		return intersects(m_startIndex, getLength(), range.m_startIndex, range.getLength());
	}
	
	public boolean intersects(int startIndex, int length)
	{
		return intersects(m_startIndex, getLength(), startIndex, length);
	}
	
	public boolean intersectsOrNeighbors(Range range)
	{
		return intersectesOrNeighbors(m_startIndex, getLength(), range.m_startIndex, range.getLength());
	}
	
	public boolean intersectsOrNeighbors(int startIndex, int length)
	{
		return intersectesOrNeighbors(m_startIndex, getLength(), startIndex, length);
	}
	
	public boolean contains(int targetIndex)
	{
		return contains(targetIndex, 1);
	}
	
	public boolean contains(Range range)
	{
		return contains(m_startIndex, getLength(), range.m_startIndex, range.getLength());
	}
	
	public boolean contains(int startIndex, int length)
	{
		return contains(m_startIndex, getLength(), startIndex, length);
	}
	
	public Range subRange(int startIndex, int length)
	{
		if (startIndex < 0 || startIndex < m_startIndex)
			throw new IndexOutOfBoundsException(argsToString(m_startIndex, getLength(), startIndex, length));
		
		if (length < 0)
			throw new IllegalArgumentException(String.valueOf(length));
		
		if (startIndex + length > m_startIndex + getLength())
			throw new IllegalArgumentException(argsToString(m_startIndex, getLength(), startIndex, length));
		
		return subRangeImpl(startIndex, length);
	}
	
	protected abstract Range subRangeImpl(int startIndex, int length);
	
	/**
	 * @throws ClassCastException if <code>range</code> is not a compatible
	 *             type
	 * @throws IllegalArgumentException if <code>range</code> is not
	 *             contiguous with this range
	 */
	public abstract void add(Range range);
	
	/**
	 * @throws ClassCastException if <code>range</code> is not a compatible
	 *             type
	 * @throws IllegalArgumentException if <code>range</code> does not overlap
	 *             this range
	 * @throws IllegalArgumentException if <code>range</code> does not reach
	 *             or exceed one of the boundaries of this range (this range
	 *             cannot be subdivided by this operation)
	 */
	public void subtract(Range range)
	{
		subtract(range.m_startIndex, range.getLength());
	}
	
	public void subtract(int startIndex, int length)
	{
		if (startIndex < 0)
			throw new IndexOutOfBoundsException(String.valueOf(startIndex));
		
		if (length < 0)
			throw new IllegalArgumentException(String.valueOf(length));
		
		// subtraction would result in subdivision
		if (startIndex > m_startIndex && startIndex + length < m_startIndex + getLength())
			throw new IllegalArgumentException(argsToString(m_startIndex, getLength(), startIndex, length));
		
		if (! intersects(startIndex, length))
			return;
		
		subtractImpl(startIndex, length);
	}
	
	protected abstract void subtractImpl(int startIndex, int length);
	
	public Range intersect(Range range)
	{
		return intersect(range.m_startIndex, range.getLength());
	}
	
	public Range intersect(int startIndex, int length)
	{
		if (startIndex < 0)
			throw new IndexOutOfBoundsException(String.valueOf(startIndex));
		
		if (length < 0)
			throw new IllegalArgumentException(String.valueOf(length));
		
		if (length == 0 || getLength() == 0 || ! intersects(startIndex, length))
			return null;
		
		int resultStartIndex = Math.max(m_startIndex, startIndex);
		int resultTerminus = Math.min(m_startIndex + getLength(), startIndex + length);
		return subRange(resultStartIndex, resultTerminus - resultStartIndex);
	}
	
	public boolean equals(Object obj)
	{
		return obj == this || obj instanceof Range && equals((Range) obj);
	}
	
	public boolean equals(Range range)
	{
		return range == this || equals(this, range);
	}
	
	public boolean equals(int startIndex, int length)
	{
		return equals(m_startIndex, getLength(), startIndex, length);
	}
	
	public int hashCode()
	{
		return m_startIndex * 31 + getLength();
	}
	
	public abstract Range duplicate();
	
	public String toString()
	{
		return "[" + m_startIndex + '-' + getEndIndex() + ']';
	}
	
	protected static String argsToString(int referenceIndex, int referenceLength, int targetIndex, int targetLength)
	{
		return "[" + referenceIndex + ',' + referenceLength + "][" + targetIndex + ',' + targetLength + "]";
	}
}
