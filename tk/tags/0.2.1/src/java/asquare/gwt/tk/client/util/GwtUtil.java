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
	 * Checks that an index is valid for the specified range. A flag is passed to
	 * indicate whether the range should be extended by 1 to allow for adding a
	 * last element. Throws an exception if the index is out of bounds for the
	 * list.
	 * 
	 * @param firstIndex the first allowable index
	 * @param size the size of the allowable range
	 * @param index the index to range check
	 * @param adding <code>true</code> to allow for
	 *            {@link List#add(int, java.lang.Object) adding} at the index
	 *            after last element in list
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	public static void rangeCheck(int firstIndex, int size, int index, boolean adding) throws IndexOutOfBoundsException
	{
		if (index < firstIndex || index > size || (! adding) && index == size)
			throw new IndexOutOfBoundsException(Integer.toString(index));
	}
}
