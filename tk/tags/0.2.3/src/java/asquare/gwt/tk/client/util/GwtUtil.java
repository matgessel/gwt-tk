/*
 * Copyright 2006 Mat Gessel <mat.gessel@gmail.com>
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
package asquare.gwt.tk.client.util;

import java.util.List;

/**
 * General utility methods for working with in GWT. 
 */
public class GwtUtil
{
	/**
	 * Checks that the specified index is valid for the list (the GWT emulation
	 * of {@link java.util.Vector Vector} does not perform range checking). A
	 * flag is passed to indicate whether the range should be extended by 1 to
	 * allow for adding a last element. Throws an exception if the index is out
	 * of bounds for the list.
	 * 
	 * @param list the list to check against
	 * @param index the index to range check
	 * @param adding <code>true</code> to allow for
	 *            {@link List#add(int, java.lang.Object) adding} at the index
	 *            after last element in list
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	public static void rangeCheck(List list, int index, boolean adding) throws IndexOutOfBoundsException
	{
		rangeCheck(0, list.size(), index, adding);
	}
	
	/**
	 * Checks that a number is contained within the specified range. A flag is
	 * passed to indicate whether the range should be extended by 1 (to allow
	 * for adding a last element). Throws an exception if the index is out of
	 * bounds for the range.
	 * 
	 * @param first the first allowable number
	 * @param size the size of the allowed range
	 * @param n the index to range check
	 * @param extend <code>true</code> to extend the size of the range by
	 *            <code>1</code>
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	public static void rangeCheck(int first, int size, int n, boolean extend) throws IndexOutOfBoundsException
	{
		if (n < first || n > (first + size) || (! extend) && n == (first + size))
			throw new IndexOutOfBoundsException(Integer.toString(n));
	}
	
	/**
	 * Copies elements from the <code>src</code> array to the <code>dest</code> array. 
	 * 
	 * @param src the source array
	 * @param srcPos the starting position in the source array
	 * @param dest the destination array
	 * @param destPos the starting position in the destination array
	 * @param length the number of array elements to copy
	 * @throws NullPointerException if <code>src</code> or <code>dest</code> is <code>null</code>
	 * @throws IndexOutOfBoundsException if <code>srcPos < 0 || destPos < 0 || length < 0 || (srcPos + length > src.length) || (destPos + length > dest.length)</code>
	 * @throws ArrayStoreException if an element in <code>src</code> cannot not be copied to <code>dest</code> due to a type mismatch
	 * @see System#arraycopy(Object, int, Object, int, int)
	 */
	public static void arrayCopy(Object src, int srcPos, Object dest, int destPos, int length)
	{
		// need to check srcPos and destPos for script mode
		if (length < 0 || srcPos < 0 || destPos < 0)
			throw new IndexOutOfBoundsException();
		
		if (src instanceof Object[] && dest instanceof Object[])
		{
			arrayCopy((Object[]) src, srcPos, (Object[]) dest, destPos, length);
		}
		else if (src instanceof int[] && dest instanceof int[])
		{
			arrayCopy((int[]) src, srcPos, (int[]) dest, destPos, length);
		}
		else if (src instanceof boolean[] && dest instanceof boolean[])
		{
			arrayCopy((boolean[]) src, srcPos, (boolean[]) dest, destPos, length);
		}
		else if (src instanceof float[] && dest instanceof float[])
		{
			arrayCopy((float[]) src, srcPos, (float[]) dest, destPos, length);
		}
		else if (src instanceof char[] && dest instanceof char[])
		{
			arrayCopy((char[]) src, srcPos, (char[]) dest, destPos, length);
		}
		else if (src instanceof byte[] && dest instanceof byte[])
		{
			arrayCopy((byte[]) src, srcPos, (byte[]) dest, destPos, length);
		}
		else if (src instanceof short[] && dest instanceof short[])
		{
			arrayCopy((short[]) src, srcPos, (short[]) dest, destPos, length);
		}
		else if (src instanceof long[] && dest instanceof long[])
		{
			arrayCopy((long[]) src, srcPos, (long[]) dest, destPos, length);
		}
		else if (src instanceof double[] && dest instanceof double[])
		{
			arrayCopy((double[]) src, srcPos, (double[]) dest, destPos, length);
		}
		else if (src == null || dest == null)
		{
			throw new NullPointerException();
		}
		else
		{
			throw new ArrayStoreException();
		}
	}
	
	private static void arrayCopy(Object[] src, int srcPos, Object[] dest, int destPos, int length)
	{
		if (srcPos + length > src.length || destPos + length > dest.length)
			throw new IndexOutOfBoundsException();
		
		for (int i = 0; i < length; i++)
		{
			dest[destPos + i] = src[srcPos + i];
		}
	}
	
	private static void arrayCopy(int[] src, int srcPos, int[] dest, int destPos, int length)
	{
		if (srcPos + length > src.length || destPos + length > dest.length)
			throw new IndexOutOfBoundsException();
		
		for (int i = 0; i < length; i++)
		{
			dest[destPos + i] = src[srcPos + i];
		}
	}
	
	private static void arrayCopy(boolean[] src, int srcPos, boolean[] dest, int destPos, int length)
	{
		if (srcPos + length > src.length || destPos + length > dest.length)
			throw new IndexOutOfBoundsException();
		
		for (int i = 0; i < length; i++)
		{
			dest[destPos + i] = src[srcPos + i];
		}
	}
	
	private static void arrayCopy(float[] src, int srcPos, float[] dest, int destPos, int length)
	{
		if (srcPos + length > src.length || destPos + length > dest.length)
			throw new IndexOutOfBoundsException();
		
		for (int i = 0; i < length; i++)
		{
			dest[destPos + i] = src[srcPos + i];
		}
	}
	
	private static void arrayCopy(char[] src, int srcPos, char[] dest, int destPos, int length)
	{
		if (srcPos + length > src.length || destPos + length > dest.length)
			throw new IndexOutOfBoundsException();
		
		for (int i = 0; i < length; i++)
		{
			dest[destPos + i] = src[srcPos + i];
		}
	}
	
	private static void arrayCopy(byte[] src, int srcPos, byte[] dest, int destPos, int length)
	{
		if (srcPos + length > src.length || destPos + length > dest.length)
			throw new IndexOutOfBoundsException();
		
		for (int i = 0; i < length; i++)
		{
			dest[destPos + i] = src[srcPos + i];
		}
	}
	
	private static void arrayCopy(short[] src, int srcPos, short[] dest, int destPos, int length)
	{
		if (srcPos + length > src.length || destPos + length > dest.length)
			throw new IndexOutOfBoundsException();
		
		for (int i = 0; i < length; i++)
		{
			dest[destPos + i] = src[srcPos + i];
		}
	}
	
	private static void arrayCopy(long[] src, int srcPos, long[] dest, int destPos, int length)
	{
		if (srcPos + length > src.length || destPos + length > dest.length)
			throw new IndexOutOfBoundsException();
		
		for (int i = 0; i < length; i++)
		{
			dest[destPos + i] = src[srcPos + i];
		}
	}
	
	private static void arrayCopy(double[] src, int srcPos, double[] dest, int destPos, int length)
	{
		if (srcPos + length > src.length || destPos + length > dest.length)
			throw new IndexOutOfBoundsException();
		
		for (int i = 0; i < length; i++)
		{
			dest[destPos + i] = src[srcPos + i];
		}
	}
	
	/**
	 * Copies all of the elements in <code>list</code> to <code>array</code>.
	 * Similar to {@link List#toArray(Object[])} except this does not
	 * automatically expand the array size.
	 * 
	 * @param src a list
	 * @param dest a non-primitive array
	 * @throws NullPointerException if <code>src</code> or <code>dest</code>
	 *             is <code>null</code>
	 * @throws IndexOutOfBoundsException if <code>dest</code> is not large
	 *             enough to contain all of the list elements.
	 * @throws ArrayStoreException if an element in <code>list</code> cannot
	 *             not be copied to <code>dest</code> due to a type mismatch
	 */
	public static Object[] toArray(List src, Object[] dest)
	{
		// necessary in web mode
		if (src == null || dest == null)
			throw new NullPointerException();
		
		int size = src.size();
		rangeCheck(0, dest.length, size - 1, false);
		
		for (int i = 0; i < size; i++)
		{
			dest[i] = src.get(i);
		}
		
		return dest;
	}
	
	/**
	 * Tests two Strings for equality. Same as {@link String#equals(Object)}.
	 * Either argument may be <code>null</code>.
	 * 
	 * @param a a String, or <code>null</code>
	 * @param b a String, or <code>null</code>
	 * @return <code>true</code> if <code>(a == b || a != null && a.equals(b))</code>
	 */
	public static native boolean equals(String a, String b) /*-{
		return a == b;
	}-*/;
}
