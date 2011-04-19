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

public class IntRangeTC extends TestCase
{
	private IntRange m_range0_9;
	private IntRange m_range0_4;
	private IntRange m_range5_9;
	private IntRange m_range5_14;
	private IntRange m_range10_19;
	
	public String getModuleName()
	{
		return "asquare.gwt.sb.SbTC";
	}
	
	protected void setupImpl()
	{
		m_range0_9 = new IntRange(0, 10);
		m_range0_4 = new IntRange(0, 5);
		m_range5_9 = new IntRange(5, 5);
		m_range5_14 = new IntRange(5, 5);
		m_range10_19 = new IntRange(10, 10);
	}
	
	public void testConstructor()
	{
		// negative start
		setupImpl();
		try
		{
			new IntRange(-1, 9);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}

		// negative length
		setupImpl();
		try
		{
			new IntRange(0, -1);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}

		// 0 length
		setupImpl();
		new IntRange(0, 0);
	}
	
	public void testIntersectsOrNeighbors()
	{
		// start index < 0
		setupImpl();
		try
		{
			m_range0_9.intersectsOrNeighbors(-1, 10);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// count < 0
		setupImpl();
		try
		{
			m_range0_9.intersectsOrNeighbors(0, -1);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		
		// count = 0
		setupImpl();
		assertTrue(m_range0_9.intersectsOrNeighbors(0, 0));
		
		// same range
		assertTrue(m_range0_4.intersectsOrNeighbors(0, 5));
		assertTrue(m_range10_19.intersectsOrNeighbors(10, 10));
		
		// ranges within
		assertTrue(m_range10_19.intersectsOrNeighbors(10, 1));
		assertTrue(m_range10_19.intersectsOrNeighbors(11, 8));
		assertTrue(m_range10_19.intersectsOrNeighbors(19, 1));
		
		// adjacent ranges before
		assertTrue(m_range10_19.intersectsOrNeighbors(0, 10));
		assertTrue(m_range10_19.intersectsOrNeighbors(9, 1));
		
		// adjacent ranges after
		assertTrue(m_range10_19.intersectsOrNeighbors(20, 1));
		assertTrue(m_range10_19.intersectsOrNeighbors(20, 10));
		
		// overlapping ranges before
		assertTrue(m_range10_19.intersectsOrNeighbors(9, 2));
		assertTrue(m_range10_19.intersectsOrNeighbors(9, 10));
		
		// overlapping ranges after
		assertTrue(m_range10_19.intersectsOrNeighbors(11, 10));
		assertTrue(m_range10_19.intersectsOrNeighbors(19, 2));
		
		// containing range
		assertTrue(m_range10_19.intersectsOrNeighbors(9, 11));
		assertTrue(m_range10_19.intersectsOrNeighbors(9, 12));
		assertTrue(m_range10_19.intersectsOrNeighbors(10, 11));
	}
	
	public void testAdd()
	{
		// index < 0
		setupImpl();
		try
		{
			m_range10_19.add(-1, 9);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// negative length
		setupImpl();
		try
		{
			m_range10_19.add(0, -1);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		
		// discontiguous range
		setupImpl();
		try
		{
			m_range10_19.add(m_range0_4);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		
		// 0 length
		setupImpl();
		m_range10_19.add(10, 0);
		assertSameRange(10, 10, m_range10_19);
		m_range10_19.add(14, 0);
		assertSameRange(10, 10, m_range10_19);
		m_range10_19.add(19, 0);
		assertSameRange(10, 10, m_range10_19);
		
		// basic test
		setupImpl();
		m_range10_19.add(5, 5);
		assertSameRange(5, 15, m_range10_19);
		m_range10_19.add(20, 5);
		assertSameRange(5, 20, m_range10_19);
		m_range10_19.add(4, 2);
		assertSameRange(4, 21, m_range10_19);
		m_range10_19.add(24, 2);
		assertSameRange(4, 22, m_range10_19);
		
		// no effect
		setupImpl();
		m_range10_19.add(10, 5);
		m_range10_19.add(12, 5);
		m_range10_19.add(15, 5);
		assertSameRange(10, 10, m_range10_19);
	}
	
	public void testSubtract()
	{
		// index < 0
		setupImpl();
		try
		{
			m_range10_19.subtract(-1, 9);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// negative length
		setupImpl();
		try
		{
			m_range10_19.subtract(0, -1);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		
		// completely contained
		setupImpl();
		try
		{
			m_range10_19.subtract(12, 6);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		
		// completely contained (0 length)
		setupImpl();
		try
		{
			m_range10_19.subtract(12, 0);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		
		// not overlapping
		setupImpl();
		m_range10_19.subtract(m_range0_4);
		assertSameRange(10, 10, m_range10_19);
		
		// 0 length
		setupImpl();
		m_range10_19.subtract(10, 0);
		assertSameRange(10, 10, m_range10_19);
		m_range10_19.subtract(20, 0);
		assertSameRange(10, 10, m_range10_19);
		
		// same lower boundary
		setupImpl();
		m_range10_19.subtract(10, 2);
		assertSameRange(12, 8, m_range10_19);
		
		// same upper boundary
		setupImpl();
		m_range10_19.subtract(18, 2);
		assertSameRange(10, 8, m_range10_19);
		
		// overlap lower boundary
		setupImpl();
		m_range10_19.subtract(8, 4);
		assertSameRange(12, 8, m_range10_19);
		
		// overlap upper boundary
		setupImpl();
		m_range10_19.subtract(18, 4);
		assertSameRange(10, 8, m_range10_19);
		
		// same range
		setupImpl();
		m_range10_19.subtract(10, 10);
		assertSameRange(10, 0, m_range10_19);
		
		// subtract encompassing area
		setupImpl();
		m_range5_9.subtract(4, 7);
		assertEquals(5, m_range5_9.getStartIndex());
		assertEquals(0, m_range5_9.getLength());
		
		// subtract encompassing area w/ same lower boundary
		setupImpl();
		m_range5_9.subtract(5, 6);
		assertEquals(5, m_range5_9.getStartIndex());
		assertEquals(0, m_range5_9.getLength());
		
		// subtract encompassing area w/ same upper boundary
		setupImpl();
		m_range5_9.subtract(4, 6);
		assertEquals(5, m_range5_9.getStartIndex());
		assertEquals(0, m_range5_9.getLength());
	}
	
	public void testIntersect()
	{
		setupImpl();
		
		// index < 0
		try
		{
			m_range10_19.intersect(-1, 9);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// negative length
		try
		{
			m_range10_19.intersect(0, -1);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		
		// no side effect
		m_range10_19.intersect(12, 2);
		assertSameRange(10, 10, m_range10_19);
		
		// lower miss
		assertNull(m_range10_19.intersect(m_range0_4));
		
		// upper miss
		assertNull(m_range0_4.intersect(m_range10_19));
		
		// lower miss, same boundary
		assertNull(m_range5_9.intersect(m_range0_4));
		
		// upper miss, same boundary
		assertNull(m_range0_4.intersect(m_range5_9));
		
		// 0 length
		assertNull(m_range10_19.intersect(10, 0));
		assertNull(m_range10_19.intersect(15, 0));
		assertNull(m_range10_19.intersect(20, 0));
		
		// same lower boundary
		assertSameRange(10, 2, m_range10_19.intersect(10, 2));
		
		// same upper boundary
		assertSameRange(18, 2, m_range10_19.intersect(18, 2));
		
		// overlap lower boundary
		assertSameRange(10, 2, m_range10_19.intersect(8, 4));
		
		// overlap upper boundary
		assertSameRange(18, 2, m_range10_19.intersect(18, 4));
		
		// same range
		assertSameRange(10, 10, m_range10_19.intersect(10, 10));
		
		// intersect encompassing area
		assertSameRange(5, 5, m_range5_9.intersect(4, 7));
		
		// intersect encompassing area w/ same lower boundary
		assertSameRange(5, 5, m_range5_9.intersect(5, 6));
		
		// intersect encompassing area w/ same upper boundary
		assertSameRange(5, 5, m_range5_9.intersect(4, 6));
	}
	
	public void testToArray()
	{
		setupImpl();
		
		int[] result = new int[20];
		
		m_range0_4.toArray(result, 0);
		m_range5_14.toArray(result, 5);
		m_range10_19.toArray(result, 10);
		
		for (int i = 0; i < result.length; i++)
		{
			assertEquals(String.valueOf(i), i, result[i]);
		}
	}
	
	private static void assertSameRange(int expectedPos, int expectedLength, Range actual)
	{
		assertEquals("position", expectedPos, actual.getStartIndex());
		assertEquals("length", expectedLength, actual.getLength());
	}
}
