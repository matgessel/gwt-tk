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
import asquare.gwt.tk.testutil.TkTestUtil;

@SuppressWarnings("unused")
public class ObjectArrayRangeTC extends TestCase
{
	private Object[] m_0_4;
	private Object[] m_0_9;
	private Object[] m_5_9;
	private Object[] m_5_14;
	private Object[] m_10_14;
	private Object[] m_10_19;
	private ObjectArrayRange m_range0_9;
	private ObjectArrayRange m_range0_4;
	private ObjectArrayRange m_range5_9;
	private ObjectArrayRange m_range5_14;
	private ObjectArrayRange m_range10_19;
	
	public String getModuleName()
	{
		return "asquare.gwt.sb.SbTC";
	}
	
	protected void setupImpl()
	{
		m_0_9 = TkTestUtil.createIntegerArray(0, 10);
		m_0_4 = TkTestUtil.createIntegerArray(0, 5);
		m_5_9 = TkTestUtil.createIntegerArray(5, 5);
		m_5_14 = TkTestUtil.createIntegerArray(0, 10);
		m_10_14 = TkTestUtil.createIntegerArray(10, 5);
		m_10_19 = TkTestUtil.createIntegerArray(10, 10);
		m_range0_9 = new ObjectArrayRange(0, m_0_9);
		m_range0_4 = new ObjectArrayRange(0, m_0_4);
		m_range5_9 = new ObjectArrayRange(5, m_5_9);
		m_range5_14 = new ObjectArrayRange(5, m_5_14);
		m_range10_19 = new ObjectArrayRange(10, m_10_19);
	}
	
	public void testConstructor()
	{
		// negative start
		setupImpl();
		try
		{
			new ObjectArrayRange(-1, m_0_9);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}

		// null list
		setupImpl();
		try
		{
			new ObjectArrayRange(0, null);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}

		// empty list
		setupImpl();
		new ObjectArrayRange(0, new Object[] {});
	}
	
	public void testGet()
	{
		// index < 0
		setupImpl();
		try
		{
			m_range0_9.get(-1);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// index > size
		setupImpl();
		try
		{
			m_range0_9.get(m_range0_9.getStartIndex() + m_range0_9.getLength());
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// basic test
		setupImpl();
		assertSame(m_0_9[0], m_range0_9.get(0));
		assertSame(m_0_9[9], m_range0_9.get(9));
	}
	
	public void testGet2()
	{
		// index < 0
		setupImpl();
		try
		{
			m_range0_9.get(-1, 10);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// index > size
		setupImpl();
		try
		{
			m_range0_9.get(m_range0_9.getStartIndex() + m_range0_9.getLength(), 10);
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
			m_range0_9.get(0, -1);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		
		// count = 0
		setupImpl();
		try
		{
			m_range0_9.get(0, 0);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		
		// range underflow
		setupImpl();
		try
		{
			m_range10_19.get(9, 10);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// range overflow
		setupImpl();
		try
		{
			m_range10_19.get(11, 10);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// basic test
		TkTestUtil.arrayIdentityCompare(m_0_9, 0, m_range0_9.get(0, 10), 0, 10);
		
		// sub-range
		TkTestUtil.arrayIdentityCompare(m_0_9, 0, m_range0_9.get(0, 2), 0, 2);
		TkTestUtil.arrayIdentityCompare(m_0_9, 2, m_range0_9.get(2, 6), 0, 6);
		TkTestUtil.arrayIdentityCompare(m_0_9, 8, m_range0_9.get(8, 2), 0, 2);
	}
	
	public void testIntersectesOrNeighbors()
	{
		setupImpl();
		
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
		setupImpl();
		
		// index < 0
		setupImpl();
		try
		{
			m_range10_19.add(-1, m_0_9);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		
		// null list
		setupImpl();
		try
		{
			m_range10_19.add(0, null);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		
		// discontiguous range
		setupImpl();
		m_range10_19.add(m_range0_4);
		assertBounds(m_range10_19, 0, 20);
		assertIdentityOverRange(m_0_4, 0, 0, m_range10_19, 0, 5);
		assertIdentityOverRange(m_10_19, 0, 10, m_range10_19, 0, 10);
		
		// 0 length
		setupImpl();
		m_range10_19.add(new ObjectArrayRange(10, new Object[0]));
		m_range10_19.add(new ObjectArrayRange(20, new Object[0]));
		assertBounds(m_range10_19, 10, 10);
		assertIdentityOverRange(m_10_19, 0, 10, m_range10_19, 0, 10);
		
		// insert before
		setupImpl();
		m_range10_19.add(5, m_5_9);
		assertIdentityOverRange(m_5_9, 0, 5, m_range10_19, 0, 5);
		assertIdentityOverRange(m_10_19, 0, 10, m_range10_19, 0, 10);
		
		// overwrite of previous values
		setupImpl();
		m_range0_9.add(0, m_5_9);
		assertIdentityOverRange(m_5_9, 0, 0, m_range0_9, 0, 5);
		assertIdentityOverRange(m_0_9, 5, 0, m_range0_9, 5, 5);
	}
	
	public void testRemove()
	{
		// overlap lower boundary
		setupImpl();
		m_range10_19.subtract(8, 4);
		assertIdentityOverRange(m_10_19, 2, 12, m_range10_19, 0, 8);
		
		// overlap upper boundary
		setupImpl();
		m_range10_19.subtract(18, 4);
		assertIdentityOverRange(m_10_19, 0, 10, m_range10_19, 0, 8);
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
		assertIdentityOverRange(m_10_19, 0, 10, m_range10_19, 0, m_10_19.length);
		
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
		assertRange(TkTestUtil.subRange(m_10_19, 0, 2), (ObjectArrayRange) m_range10_19.intersect(10, 2));
		
		// same upper boundary
		assertRange(TkTestUtil.subRange(m_10_19, 8, 2), (ObjectArrayRange) m_range10_19.intersect(18, 2));
		
		// overlap lower boundary
		assertRange(TkTestUtil.subRange(m_10_19, 0, 2), (ObjectArrayRange) m_range10_19.intersect(8, 4));
		
		// overlap upper boundary
		assertRange(TkTestUtil.subRange(m_10_19, 8, 2), (ObjectArrayRange) m_range10_19.intersect(18, 4));
		
		// same range
		assertRange(m_10_19, (ObjectArrayRange) m_range10_19.intersect(10, 10));
		
		// intersect encompassing area
		assertRange(m_5_9, (ObjectArrayRange) m_range5_9.intersect(4, 7));
		
		// intersect encompassing area w/ same lower boundary
		assertRange(m_5_9, (ObjectArrayRange) m_range5_9.intersect(5, 6));
		
		// intersect encompassing area w/ same upper boundary
		assertRange(m_5_9, (ObjectArrayRange) m_range5_9.intersect(4, 6));
	}
	
	public void testToArray()
	{
		setupImpl();
		
		Object[] result = new Object[20];
		
		m_range0_4.toArray(result, 0);
		m_range5_14.toArray(result, 5);
		m_range10_19.toArray(result, 10);
		
		assertSame(m_0_4[0], result[0]);
		assertSame(m_0_4[4], result[4]);
		assertSame(m_5_14[0], result[5]);
		assertSame(m_10_19[0], result[10]);
		assertSame(m_10_19[9], result[19]);
	}
	
	private static void assertBounds(Range range, int startIndex, int length)
	{
		assertEquals(startIndex, range.getStartIndex());
		assertEquals(length, range.getLength());
	}
	
	private static void assertRange(Object[] expected, ObjectArrayRange actual)
	{
		assertEquals(expected.length, actual.getLength());
		assertIdentityOverRange(expected, 0, actual.getStartIndex(), actual, 0, expected.length);
	}
	
	private static void assertIdentityOverRange(Object[] expected, int expectedPos, int expectedStartingIndex, 
			ObjectArrayRange actual, int actualPos, int length)
	{
		for (int i = 0; i < length; i++)
		{
			assertSame(String.valueOf(i), expected[expectedPos + i], actual.get(expectedStartingIndex + actualPos + i));
		}
	}
}
