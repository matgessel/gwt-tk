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

import junit.framework.TestCase;

public class RangeTC extends TestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.sb.SbTC";
	}
	
	protected void setupImpl()
	{
	}
	
	public void testIntersects()
	{
		// index 1 < 0
		try
		{
			Range.intersects(-1, 1, 1, 1);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// index 2 < 0
		try
		{
			Range.intersects(1, 1, -1, 1);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// count 1 < 0
		try
		{
			Range.intersects(1, -1, 1, 1);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		
		// count 2 < 0
		try
		{
			Range.intersects(1, 1, 1, -1);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		
		// count 1 = 0
		assertFalse(Range.intersects(0, 0, 0, 9));
		
		// count 2 = 0
		assertFalse(Range.intersects(0, 9, 0, 0));
		
		// same range
		assertTrue(Range.intersects(0, 5, 0, 5));
		assertTrue(Range.intersects(10, 10, 10, 10));
		
		// ranges within
		assertTrue(Range.intersects(10, 10, 10, 1));
		assertTrue(Range.intersects(10, 1, 10, 10));
		assertTrue(Range.intersects(10, 10, 11, 8));
		assertTrue(Range.intersects(11, 8, 10, 10));
		assertTrue(Range.intersects(10, 10, 19, 1));
		assertTrue(Range.intersects(19, 1, 10, 10));
		
		// adjacent ranges
		assertFalse(Range.intersects(10, 10, 0, 10));
		assertFalse(Range.intersects(0, 10, 10, 10));
		assertFalse(Range.intersects(10, 10, 9, 1));
		assertFalse(Range.intersects(9, 1, 10, 10));
		
		// overlapping ranges
		assertTrue(Range.intersects(10, 10, 9, 2));
		assertTrue(Range.intersects(9, 2, 10, 10));
		assertTrue(Range.intersects(10, 10, 9, 10));
		assertTrue(Range.intersects(9, 10, 10, 10));
		
		// containing range
		assertTrue(Range.intersects(10, 10, 9, 11));
		assertTrue(Range.intersects(9, 11, 10, 10));
		assertTrue(Range.intersects(10, 10, 9, 12));
		assertTrue(Range.intersects(9, 12, 10, 10));
		assertTrue(Range.intersects(10, 10, 10, 11));
		assertTrue(Range.intersects(10, 11, 10, 10));
	}
}
