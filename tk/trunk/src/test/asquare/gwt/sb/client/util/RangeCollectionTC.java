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

import junit.framework.TestCase;
import asquare.gwt.tk.testutil.TkTestUtil;

public class RangeCollectionTC extends TestCase
{
    private RangeCollection m_collection;
	private Object[] m_0_1;
	private Object[] m_3_4;
	private Object[] m_6_7;
	private Object[] m_0_4;
	private Object[] m_0_9;
	private Object[] m_5_9;
	private Object[] m_5_14;
	private Object[] m_10_14;
	private Object[] m_10_19;
    private ObjectArrayRange m_range0_1;
    private ObjectArrayRange m_range3_4;
    private ObjectArrayRange m_range6_7;
    private ObjectArrayRange m_range0_9;
    private ObjectArrayRange m_range0_4;
    private ObjectArrayRange m_range5_9;
    private ObjectArrayRange m_range5_14;
    private ObjectArrayRange m_range10_14;
    private ObjectArrayRange m_range10_19;
    
    protected void setupImpl()
    {
        m_collection = new RangeCollection();
        m_0_1 = TkTestUtil.createIntegerArray(0, 2);
        m_3_4 = TkTestUtil.createIntegerArray(3, 2);
        m_6_7 = TkTestUtil.createIntegerArray(6, 2);
		m_0_9 = TkTestUtil.createIntegerArray(0, 10);
		m_0_4 = TkTestUtil.createIntegerArray(0, 5);
		m_5_9 = TkTestUtil.createIntegerArray(5, 5);
		m_5_14 = TkTestUtil.createIntegerArray(0, 10);
		m_10_14 = TkTestUtil.createIntegerArray(10, 5);
		m_10_19 = TkTestUtil.createIntegerArray(10, 10);
		
		m_range0_1 = new ObjectArrayRange(0, m_0_1);
		m_range3_4 = new ObjectArrayRange(3, m_3_4);
		m_range6_7 = new ObjectArrayRange(6, m_6_7);
		m_range0_9 = new ObjectArrayRange(0, m_0_9);
		m_range0_4 = new ObjectArrayRange(0, m_0_4);
		m_range5_9 = new ObjectArrayRange(5, m_5_9);
		m_range5_14 = new ObjectArrayRange(5, m_5_14);
		m_range10_14 = new ObjectArrayRange(10, m_10_14);
		m_range10_19 = new ObjectArrayRange(10, m_10_19);
    }
    
    public void testContains()
    {
        // index < 0
        setupImpl();
        try
        {
            m_collection.contains(-1, 10);
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
            m_collection.contains(0, -1);
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
            m_collection.contains(0, 0);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            // EXPECTED
        }
        
        setupImpl();
        assertFalse(m_collection.contains(0, 10));
        assertFalse(m_collection.contains(10, 10));
        
        setupImpl();
        m_collection.add(m_range0_9);
        assertTrue(m_collection.contains(0, 1));
        assertTrue(m_collection.contains(0, 10));
        assertTrue(m_collection.contains(9, 1));
    }
    
    public void testGet()
    {
        // negative index
        setupImpl();
        try
        {
            m_collection.get(-1, 10);
            fail();
        }
        catch (IndexOutOfBoundsException e)
        {
            // EXPECTED
        }
        
        // negative count
        setupImpl();
        try
        {
            m_collection.get(0, -1);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            // EXPECTED
        }
        
        // 0 count
        setupImpl();
        try
        {
            m_collection.get(0, 0);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            // EXPECTED
        }
        
        // basic test
        setupImpl();
        m_collection.add(m_range0_9);
        assertSameRange(0, 10, m_collection.get(0, 10));
        
        // at beginning of range
        setupImpl();
        m_collection.add(m_range5_14);
        assertSameRange(5, 10, m_collection.get(5, 5));
        
        // at end of range
        setupImpl();
        m_collection.add(m_range5_14);
        assertSameRange(5, 10, m_collection.get(10, 5));
        
        // get out of range
        setupImpl();
        m_collection.add(m_range5_14);
        assertNull(m_collection.get(0, 1));
        assertNull(m_collection.get(4, 1));
        assertNull(m_collection.get(15, 1));
        
        // get overlaps range partially
        assertNull(m_collection.get(4, 2));
        assertNull(m_collection.get(14, 2));
    }
    
    public void testAdd()
    {
        // null
        setupImpl();
        try
        {
            m_collection.add(null);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            // EXPECTED
        }
        
        // 0 length
        setupImpl();
        m_collection.add(new IntRange(5, 0));
        assertEquals(0, m_collection.getSize());
        
        // basic test
        setupImpl();
        m_collection.add(m_range0_9);
        assertSame(m_range0_9, m_collection.get(0));
        
        // index > 0
        setupImpl();
        m_collection.add(m_range10_19);
        assertSame(m_range10_19, m_collection.get(0));
        
        // add discontiguous ranges
        setupImpl();
        m_collection.add(new IntRange(0, 1));
        m_collection.add(new IntRange(2, 1));
        m_collection.add(new IntRange(4, 1));
        assertEquals(3, m_collection.getSize());
        assertSameRange(0, 1, m_collection.get(0));
        assertSameRange(2, 1, m_collection.get(1));
        assertSameRange(4, 1, m_collection.get(2));
        
        // add low range with existing high range
        setupImpl();
        m_collection.add(new IntRange(4, 1));
        m_collection.add(new IntRange(2, 1));
        m_collection.add(new IntRange(0, 1));
        assertEquals(3, m_collection.getSize());
        assertSameRange(0, 1, m_collection.get(0));
        assertSameRange(2, 1, m_collection.get(1));
        assertSameRange(4, 1, m_collection.get(2));
        
        // add middle range
        setupImpl();
        m_collection.add(new IntRange(0, 1));
        m_collection.add(new IntRange(4, 1));
        m_collection.add(new IntRange(2, 1));
        assertEquals(3, m_collection.getSize());
        assertSameRange(0, 1, m_collection.get(0));
        assertSameRange(2, 1, m_collection.get(1));
        assertSameRange(4, 1, m_collection.get(2));
        
        // new range connects two existing ranges
        setupImpl();
        m_collection.add(m_range0_4);
        m_collection.add(m_range10_14);
        m_collection.add(m_range5_9); // add last to connect
        assertEquals(1, m_collection.getSize());
        assertSameRange(0, 15, m_collection.get(0));
        
        // new range completely supercedes an existing range
        setupImpl();
        m_collection.add(m_range0_4);
        m_collection.add(m_range0_9);
        assertEquals(1, m_collection.getSize());
        assertSameRange(0, 10, m_collection.get(0));
        
        setupImpl();
        m_collection.add(m_range5_9);
        m_collection.add(m_range0_9);
        assertEquals(1, m_collection.getSize());
        assertSameRange(0, 10, m_collection.get(0));
        
        setupImpl();
        m_collection.add(new IntRange(11, 8));
        m_collection.add(m_range10_19);
        assertEquals(1, m_collection.getSize());
        assertSameRange(10, 10, m_collection.get(0));
    }
    
    public void testAddAll()
    {
    	// null collection
    	setupImpl();
    	try
		{
			m_collection.addAll(null);
    		fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
    	
    	// basic test
		setupImpl();
    	RangeCollection ranges = new RangeCollection();
    	ranges.add(m_range0_1);
    	ranges.add(m_range3_4);
    	m_collection.addAll(ranges);
    	assertRangeValues(new ObjectArrayRange[] {m_range0_1, m_range3_4});
    	
    	// empty collection
    	m_collection.addAll(new RangeCollection());
    	assertRangeValues(new ObjectArrayRange[] {m_range0_1, m_range3_4});
    	
    	// duplicate range
    	m_collection.addAll(ranges);
    	m_collection.addAll(new RangeCollection());
    	assertRangeValues(new ObjectArrayRange[] {m_range0_1, m_range3_4});
    }
    
    public void testClear()
    {
    	// empty
    	setupImpl();
    	m_collection.clear();
    	assertEquals(0, m_collection.getSize());
    	
    	// basic test
    	setupImpl();
    	m_collection.add(m_range0_4);
    	m_collection.clear();
    	assertEquals(0, m_collection.getSize());
    	
    	// discontiguous ranges
    	setupImpl();
    	m_collection.add(m_range0_4);
    	m_collection.add(m_range10_14);
    	m_collection.clear();
    	assertEquals(0, m_collection.getSize());
    }
    
    public void testRemove()
    {
        // null
        setupImpl();
        try
        {
            m_collection.remove(null);
            fail();
        }
        catch (IllegalArgumentException e)
        {
            // EXPECTED
        }
    }
    
    public void testRemove1()
    {
		// index < 0
		setupImpl();
    	try
		{
			m_collection.remove(-1, 9);
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
			m_collection.remove(0, -1);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
        
        // 0 length
        setupImpl();
        m_collection.add(m_range5_9);
        m_collection.remove(0, 0);
        m_collection.remove(4, 0);
        m_collection.remove(5, 0);
        m_collection.remove(7, 0);
        m_collection.remove(9, 0);
        m_collection.remove(10, 0);
        assertRangeValues(new ObjectArrayRange[] {m_range5_9});
        
        // exact
        setupImpl();
        m_collection.add(m_range0_4);
        m_collection.remove(m_range0_4);
        assertEquals(0, m_collection.getSize());
        
        // miss before
        setupImpl();
        m_collection.add(m_range5_9);
        m_collection.remove(m_range0_4);
        assertRangeValues(new ObjectArrayRange[] {m_range5_9});
        
        // miss after
        setupImpl();
        m_collection.add(m_range5_9);
        m_collection.remove(m_range10_14);
        assertRangeValues(new ObjectArrayRange[] {m_range5_9});
        
        // miss between
        setupImpl();
        m_collection.add(m_range0_4);
        m_collection.add(m_range10_14);
        m_collection.remove(m_range5_9);
        assertRangeValues(new ObjectArrayRange[] {m_range0_4, m_range10_14});
        
        // partial overlap before
        setupImpl();
        m_collection.add(m_range5_9);
        m_collection.remove(new IntRange(4, 2));
        assertRangeValues(new ObjectArrayRange[] {new ObjectArrayRange(6, TkTestUtil.subRange(m_5_9, 1, 4))});
        
        // partial overlap after
        setupImpl();
        m_collection.add(m_range5_9);
        m_collection.remove(new IntRange(9, 2));
        assertRangeValues(new ObjectArrayRange[] {new ObjectArrayRange(5, TkTestUtil.subRange(m_5_9, 0, 4))});
        
        // overlap between multiple
        setupImpl();
        m_collection.add(m_range0_4);
        m_collection.add(m_range10_14);
        m_collection.remove(new IntRange(4, 7));
        assertRangeValues(new ObjectArrayRange[] {new ObjectArrayRange(0, TkTestUtil.subRange(m_0_4, 0, 4)), new ObjectArrayRange(11, TkTestUtil.subRange(m_10_14, 1, 4))});
        
        // completely overlap multiple
        setupImpl();
        m_collection.add(new IntRange(2, 2));
        m_collection.add(new IntRange(6, 2));
        m_collection.remove(new IntRange(1, 8));
        assertEquals(0, m_collection.getSize());
    }
    
    public void testRemoveAll()
    {
    	// null collection
    	setupImpl();
    	try
		{
			m_collection.removeAll(null);
    		fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
    	
    	// basic test
		setupImpl();
    	RangeCollection ranges = new RangeCollection();
    	ranges.add(m_range0_1);
    	ranges.add(m_range3_4);
    	m_collection.add(m_range6_7);
    	m_collection.addAll(ranges);
    	m_collection.removeAll(ranges);
    	assertRangeValues(new ObjectArrayRange[] {m_range6_7});
    	
    	// empty collection
    	m_collection.removeAll(new RangeCollection());
    	assertRangeValues(new ObjectArrayRange[] {m_range6_7});
    }
    
    public void testShift_asserts()
    {
    	// index < 0
    	setupImpl();
    	try
		{
			m_collection.shift(-1, 10);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
    	
    	// index < 0
    	setupImpl();
    	try
		{
			m_collection.shift(-1, -10);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// index + count < 0
    	setupImpl();
    	try
		{
			m_collection.shift(5, -6);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
    	
		// count == 0
		setupImpl();
		m_collection.add(m_range0_4);
		m_collection.shift(0, 0);
		m_collection.shift(1, 0);
		m_collection.shift(3, 0);
		assertRangeValues(new ObjectArrayRange[] {m_range0_4});
    }
    
    public void testShift_up()
    {
		// shift at 0
		setupImpl();
		m_collection.add(m_range0_4);
		m_collection.shift(0, 1);
		assertEquals(1, m_collection.getSize());
		assertSameRange(1, 5, m_collection.get(0));
		
		// shift at range start index
		setupImpl();
		m_collection.add(m_range5_9);
		m_collection.shift(5, 1);
		assertEquals(1, m_collection.getSize());
		assertSameRange(6, 5, m_collection.get(0));
		
		// shift at range middle index
		setupImpl();
		m_collection.add(m_range5_9);
		m_collection.shift(7, 1);
		assertEquals(2, m_collection.getSize());
		assertSameRange(5, 2, m_collection.get(0));
		assertSameRange(8, 3, m_collection.get(1));
		
		// shift at range end index
		setupImpl();
		m_collection.add(m_range5_9);
		m_collection.shift(9, 1);
		assertEquals(2, m_collection.getSize());
		assertSameRange(5, 4, m_collection.get(0));
		assertSameRange(10, 1, m_collection.get(1));
		
		// shift at range start index - 1
		setupImpl();
		m_collection.add(m_range5_9);
		m_collection.shift(4, 1);
		assertEquals(1, m_collection.getSize());
		assertSameRange(6, 5, m_collection.get(0));
		
		// shift at range terminus
		setupImpl();
		m_collection.add(m_range5_9);
		m_collection.shift(10, 1);
		assertEquals(1, m_collection.getSize());
		assertSameRange(5, 5, m_collection.get(0));

        // multiple ranges
		setupImpl();
        m_collection.add(new IntRange(0, 2));
        m_collection.add(new IntRange(3, 2));
        m_collection.add(new IntRange(6, 2));
        m_collection.shift(4, 1);
        assertEquals(4, m_collection.getSize());
        assertSameRange(0, 2, m_collection.get(0));
        assertSameRange(3, 1, m_collection.get(1));
        assertSameRange(5, 1, m_collection.get(2));
        assertSameRange(7, 2, m_collection.get(3));
    }
    
    public void testShift_down()
    {
		// shift at range start index
		setupImpl();
		m_collection.add(m_range5_9);
		m_collection.shift(5, -1);
		assertRangeValues(new ObjectArrayRange[] {new ObjectArrayRange(4, m_5_9)});
		
		// shift to 0
		setupImpl();
		m_collection.add(m_range5_9);
		m_collection.shift(5, -5);
		assertRangeValues(new ObjectArrayRange[] {new ObjectArrayRange(0, m_5_9)});
		
		// shift at range middle index
		setupImpl();
		m_collection.add(m_range5_9);
		m_collection.shift(7, -1);
		assertRangeValues(new ObjectArrayRange[] {new ObjectArrayRange(5, new Object[] {new Integer(5), new Integer(7), new Integer(8), new Integer(9)})});
		
		// shift at range end index
		setupImpl();
		m_collection.add(m_range5_9);
		m_collection.shift(9, -1);
		assertRangeValues(new ObjectArrayRange[] {new ObjectArrayRange(5, new Object[] {new Integer(5), new Integer(6), new Integer(7), new Integer(9)})});
		
		// shift at range start index - 1
		setupImpl();
		m_collection.add(m_range5_9);
		m_collection.shift(4, -1);
		assertRangeValues(new ObjectArrayRange[] {new ObjectArrayRange(4, m_5_9)});
		
		// shift at range start index + 1
		setupImpl();
		m_collection.add(m_range5_9);
		m_collection.shift(6, -1);
		assertRangeValues(new ObjectArrayRange[] {new ObjectArrayRange(5, new Object[] {new Integer(6), new Integer(7), new Integer(8), new Integer(9)})});
		
		// shift at range terminus
		setupImpl();
		m_collection.add(m_range5_9);
		m_collection.shift(10, -1);
		assertRangeValues(new ObjectArrayRange[] {new ObjectArrayRange(5, TkTestUtil.createIntegerArray(5, 4))});

		// shift to range beginning, erasing range
		setupImpl();
		m_collection.add(m_range5_9);
		m_collection.shift(10, -7);
		assertEquals(0, m_collection.getSize());
		
		// shift past range beginning, erasing range
		setupImpl();
		m_collection.add(m_range5_9);
		m_collection.shift(10, -5);
		assertEquals(0, m_collection.getSize());
		
		// shift past range beginning, erasing range & combining
		setupImpl();
        m_collection.add(m_range0_1);
        m_collection.add(m_range3_4);
        m_collection.add(m_range6_7);
        m_collection.shift(7, -3);
        assertRangeValues(new ObjectArrayRange[] {m_range0_1, new ObjectArrayRange(3, new Integer[] {new Integer(3), new Integer(7)})});
    }
    
    public void testIntersect_Range()
    {
    	// null range
    	setupImpl();
    	try
		{
			m_collection.intersect((Range) null);
    		fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
    }
    
    public void testIntersect_int_int()
    {
    	// negative index
    	setupImpl();
    	try
		{
			m_collection.intersect(-1, 1);
    		fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
    	
    	// 0 length
    	setupImpl();
    	m_collection.add(m_range0_4);
		assertEquals(0, m_collection.intersect(0, 0).getSize());
		assertEquals(0, m_collection.intersect(1, 0).getSize());
		assertEquals(0, m_collection.intersect(4, 0).getSize());
		assertEquals(0, m_collection.intersect(5, 0).getSize());
    }
    
    public void testIntersect_RangeCollection()
    {
    	// null collection
    	setupImpl();
    	try
		{
			m_collection.intersect((Range) null);
    		fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		
		// empty collection
		setupImpl();
		RangeCollection ranges = new RangeCollection();
		ranges.add(m_range0_1);
		assertEquals(0, m_collection.intersect(ranges).getSize());
		
		// empty parameter
		setupImpl();
		m_collection.add(m_range0_1);
		assertEquals(0, m_collection.intersect(new RangeCollection()).getSize());
		
		// basic test & correct return type/values
		setupImpl();
		m_collection.add(m_range0_1);
		m_collection.add(m_range3_4);
		ranges = new RangeCollection();
		ranges.add(new IntRange(2, 5));
		assertRangeValues(new ObjectArrayRange[] {m_range3_4}, m_collection.intersect(ranges));
		RangeCollection intRangeResult = ranges.intersect(m_collection);
		assertEquals(1, intRangeResult.getSize());
		assertEquals(new IntRange(3, 2), intRangeResult.get(0));
		assertTrue(intRangeResult.get(0) instanceof IntRange);
    }
    
    public void testGetTotalCount()
    {
    	// empty
    	setupImpl();
    	assertEquals(0, m_collection.getTotalCount());
    	
    	// basic test
    	setupImpl();
    	m_collection.add(m_range0_4);
    	assertEquals(5, m_collection.getTotalCount());
    	
    	// discontiguous ranges
    	setupImpl();
    	m_collection.add(m_range0_4);
    	m_collection.add(m_range10_14);
    	assertEquals(10, m_collection.getTotalCount());
    }
    
    public void testIsEmpty()
    {
    	setupImpl();
    	assertTrue(m_collection.isEmpty());
    	
    	m_collection.add(m_range0_1);
    	assertFalse(m_collection.isEmpty());
    	
    	m_collection.remove(m_range0_1);
    	assertTrue(m_collection.isEmpty());
    }
    
    private void assertSameRange(int expectedStartIndex, int expectedLength, Range range)
    {
    	assertEquals("startIndex", expectedStartIndex, range.getStartIndex());
    	assertEquals("length", expectedLength, range.getLength());
    }
    
    private void assertRangeValues(ObjectArrayRange[] expected)
    {
    	assertRangeValues(expected, m_collection);
    }
    
    private void assertRangeValues(ObjectArrayRange[] expected, RangeCollection collection)
    {
    	assertEquals("range count", expected.length, collection.getSize());
    	for (int range = 0; range < expected.length; range++)
		{
    		ObjectArrayRange actualRange = (ObjectArrayRange) collection.get(range);
    		assertEquals("startIndex", expected[range].getStartIndex(), actualRange.getStartIndex());
    		assertEquals("length", expected[range].getLength(), actualRange.getLength());
			Object[] expectedValues = expected[range].toArray();
			Object[] actualValues = actualRange.toArray();
			for (int value = 0; value < expectedValues.length; value++)
			{
				assertEquals("range=" + range + ",value=" + value, expectedValues[value], actualValues[value]);
			}
		}
    }
}
