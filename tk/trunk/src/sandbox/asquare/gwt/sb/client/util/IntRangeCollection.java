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
package asquare.gwt.sb.client.util;

public class IntRangeCollection extends RangeCollection
{
	public void add(int startIndex, int length)
	{
		add(new IntRange(startIndex, length));
	}
	
	// TODO: pull up? Don't really need IntRange for this
	public int[] toIntArray()
	{
		int[] result;
		int size = 0;
		int rangeCount = getSize();
		for (int rangeIx = 0; rangeIx < rangeCount; rangeIx++)
		{
			IntRange range = (IntRange) get(rangeIx);
			size += range.getLength();
		}
		result = new int[size];
		int offset = 0;
		for (int rangeIx = 0; rangeIx < rangeCount; rangeIx++)
		{
			IntRange range = (IntRange) get(rangeIx);
			range.toArray(result, offset);
			offset += range.getLength();
		}
		return result;
	}
}
