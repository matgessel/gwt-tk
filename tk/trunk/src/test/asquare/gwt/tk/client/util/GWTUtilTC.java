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
import java.util.Vector;

import asquare.gwt.tk.client.Tests;

import com.google.gwt.junit.client.GWTTestCase;

public class GWTUtilTC extends GWTTestCase
{
	private int[] m_sInt;
	private int[] m_dInt;
	private List m_list;
	private Object[] m_dObject;
	
	public String getModuleName()
	{
		return Tests.getModuleName();
	}
	
	protected void setUpImpl()
	{
		m_sInt = new int[] {1, 2, 3, 4};
		m_dInt = new int[4];
		m_list = new Vector();
		for (int i = 1; i <= 4; i++)
		{
			m_list.add(new Integer(i));
		}
		m_dObject = new Object[4];
	}
	
	public void testRangeCheck()
	{
		// size = 0, start = 0
		doTestRangeCheck(0, 0, -1, false, false);
		doTestRangeCheck(0, 0, 0, false, false);
		doTestRangeCheck(0, 0, 1, false, false);
		doTestRangeCheck(0, 0, -1, true, false);
		doTestRangeCheck(0, 0, 0, true, true);
		doTestRangeCheck(0, 0, 1, true, false);
		
		// size = 0, start = -1
		doTestRangeCheck(-1, 0, -2, false, false);
		doTestRangeCheck(-1, 0, -1, false, false);
		doTestRangeCheck(-1, 0, 0, false, false);
		doTestRangeCheck(-1, 0, -2, true, false);
		doTestRangeCheck(-1, 0, -1, true, true);
		doTestRangeCheck(-1, 0, 0, true, false);
		
		// size = 0, start = 1
		doTestRangeCheck(1, 0, 0, false, false);
		doTestRangeCheck(1, 0, 1, false, false);
		doTestRangeCheck(1, 0, 2, false, false);
		doTestRangeCheck(1, 0, 0, true, false);
		doTestRangeCheck(1, 0, 1, true, true);
		doTestRangeCheck(1, 0, 2, true, false);
		
		// size = 1, start = 0
		doTestRangeCheck(0, 1, -1, false, false);
		doTestRangeCheck(0, 1, 0, false, true);
		doTestRangeCheck(0, 1, 1, false, false);
		doTestRangeCheck(0, 1, -1, true, false);
		doTestRangeCheck(0, 1, 0, true, true);
		doTestRangeCheck(0, 1, 1, true, true);
		doTestRangeCheck(0, 1, 2, true, false);
		
		// size = 1, start = -1
		doTestRangeCheck(-1, 1, -2, false, false);
		doTestRangeCheck(-1, 1, -1, false, true);
		doTestRangeCheck(-1, 1, 0, false, false);
		doTestRangeCheck(-1, 1, -2, true, false);
		doTestRangeCheck(-1, 1, -1, true, true);
		doTestRangeCheck(-1, 1, 0, true, true);
		doTestRangeCheck(-1, 1, 1, true, false);
		
		// size = 1, start = 1
		doTestRangeCheck(1, 1, 0, false, false);
		doTestRangeCheck(1, 1, 1, false, true);
		doTestRangeCheck(1, 1, 2, false, false);
		doTestRangeCheck(1, 1, 0, true, false);
		doTestRangeCheck(1, 1, 1, true, true);
		doTestRangeCheck(1, 1, 2, true, true);
		doTestRangeCheck(1, 1, 3, true, false);
	}
	
	private void doTestRangeCheck(int start, int length, int index, boolean extend, boolean expectToPass)
	{
		if (expectToPass)
		{
			try
			{
				GwtUtil.rangeCheck(start, length, index, extend);
			}
			catch (IndexOutOfBoundsException e)
			{
				fail();
			}
		}
		else
		{
			try
			{
				GwtUtil.rangeCheck(start, length, index, extend);
				fail();
			}
			catch (IndexOutOfBoundsException e)
			{
				// EXPECTED
			}
		}
	}
	
	public void testArrayCopy()
	{
		Object[] src;
		Object[] dest;
		
		// null src
		addCheckpoint("null src");
		setUpImpl();
		try
		{
			GwtUtil.arrayCopy(null, 0, m_dInt, 0, 4);
			fail();
		}
		catch (NullPointerException e)
		{
			// EXPECTED
			assertDestNotModified();
		}
		
		// null dest
		addCheckpoint("null dest");
		setUpImpl();
		try
		{
			GwtUtil.arrayCopy(m_sInt, 0, null, 0, 4);
			fail();
		}
		catch (NullPointerException e)
		{
			// EXPECTED
			assertDestNotModified();
		}
		
		// srcPos < 0
		addCheckpoint("srcPos < 0");
		setUpImpl();
		try
		{
			GwtUtil.arrayCopy(m_sInt, -1, m_dInt, 0, 4);
			for (int i = 0; i < m_dInt.length; i++)
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
			assertDestNotModified();
		}
		
		// srcPos + length > src.length
		addCheckpoint("srcPos + length > src.length");
		setUpImpl();
		try
		{
			GwtUtil.arrayCopy(m_sInt, 1, m_dInt, 0, 4);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
			assertDestNotModified();
		}
		
		// destPos < 0
		addCheckpoint("destPos < 0");
		setUpImpl();
		try
		{
			GwtUtil.arrayCopy(m_sInt, 0, m_dInt, -1, 4);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
			assertDestNotModified();
		}
		
		// destPos + length > dest.length
		addCheckpoint("destPos + length > dest.length");
		setUpImpl();
		try
		{
			GwtUtil.arrayCopy(m_sInt, 0, m_dInt, 1, 4);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
			assertDestNotModified();
		}
		
		// length < 0
		addCheckpoint("length < 0");
		setUpImpl();
		try
		{
			GwtUtil.arrayCopy(m_sInt, 0, m_dInt, 0, -4);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
			assertDestNotModified();
		}
		
		// src not array
		addCheckpoint("src not array");
		setUpImpl();
		try
		{
			GwtUtil.arrayCopy(new Object(), 0, m_dInt, 0, 4);
			fail();
		}
		catch (ArrayStoreException e)
		{
			// EXPECTED
			assertDestNotModified();
		}
			
		// dest not array
		addCheckpoint("dest not array");
		setUpImpl();
		try
		{
			GwtUtil.arrayCopy(m_sInt, 0, new Object(), 0, 4);
			fail();
		}
		catch (ArrayStoreException e)
		{
			// EXPECTED
			assertDestNotModified();
		}
		
		// primative type mismatch
		addCheckpoint("primative type mismatch");
		setUpImpl();
		boolean[] booleanArray = {true, true, false, false};
		try
		{
			GwtUtil.arrayCopy(booleanArray, 0, m_dInt, 0, 4);
			fail();
		}
		catch (ArrayStoreException e)
		{
			// EXPECTED
			assertDestNotModified();
		}
		
		// src = primative[], dest = Object[]
		addCheckpoint("src = primative[], dest = Object[]");
		setUpImpl();
		dest = new Object[4];
		try
		{
			GwtUtil.arrayCopy(m_sInt, 0, dest, 0, 4);
			fail();
		}
		catch (ArrayStoreException e)
		{
			// EXPECTED
		}
		
		// src = Object[], dest = primative[]
		addCheckpoint("src = Object[], dest = primative[]");
		setUpImpl();
		src = new Object[] {new Apple()};
		try
		{
			GwtUtil.arrayCopy(src, 0, m_dInt, 0, 1);
			fail();
		}
		catch (ArrayStoreException e)
		{
			// EXPECTED
		}
		
		// array type mismatch
		addCheckpoint("array type mismatch");
		setUpImpl();
		src = new Food[] {new Food()};
		dest = new Apple[1];
		try
		{
			GwtUtil.arrayCopy(src, 0, dest, 0, 1);
			fail();
		}
		catch (ArrayStoreException e)
		{
			// EXPECTED
		}
		
		// element type mismatch
		addCheckpoint("element type mismatch");
		setUpImpl();
		src = new Object[] {new Food()};
		dest = new Apple[1];
		try
		{
			GwtUtil.arrayCopy(src, 0, dest, 0, 1);
			fail();
		}
		catch (ArrayStoreException e)
		{
			// EXPECTED
		}
		
		// basic test
		addCheckpoint("basic test");
		setUpImpl();
		GwtUtil.arrayCopy(m_sInt, 0, m_dInt, 0, m_sInt.length);
		assertEquals(m_sInt[0], m_dInt[0]);
		assertEquals(m_sInt[3], m_dInt[3]);
		
		// src and dest same
		setUpImpl();
		GwtUtil.arrayCopy(m_sInt, 0, m_sInt, 2, 2);
		assertEquals(1, m_sInt[2]);
		assertEquals(2, m_sInt[3]);

		// src and dest same (overlaping)
		setUpImpl();
		GwtUtil.arrayCopy(m_sInt, 0, m_sInt, 1, 3);
		assertEquals(1, m_sInt[1]);
		assertEquals(1, m_sInt[2]);
		assertEquals(1, m_sInt[3]);
	}
	
	private void assertDestNotModified()
	{
		for (int i = 0; i < m_dInt.length; i++)
		{
			assertEquals(String.valueOf(i), 0, m_dInt[i]);
		}
	}
	
	public void testToArray()
	{
		setUpImpl();
		GwtUtil.toArray(m_list, m_dObject);
		for (int i = 0, size = m_list.size(); i < size; i++)
		{
			assertSame(String.valueOf(i), m_list.get(i), m_dObject[i]);
		}
		
		// src null
		addCheckpoint("src null");
		setUpImpl();
		try
		{
			GwtUtil.toArray(null, m_dObject);
			fail();
		}
		catch (NullPointerException e)
		{
			// EXPECTED
		}
		
		// dest null
		addCheckpoint("dest null");
		setUpImpl();
		try
		{
			GwtUtil.toArray(m_list, null);
			fail();
		}
		catch (NullPointerException e)
		{
			// EXPECTED
		}
		
		// src.size() > dest.length
		addCheckpoint("src.size() > dest.length");
		setUpImpl();
		try
		{
			GwtUtil.toArray(m_list, new Object[3]);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// dest wrong type
		addCheckpoint("dest wrong type");
		setUpImpl();
		try
		{
			GwtUtil.toArray(m_list, new String[4]);
			fail();
		}
		catch (ArrayStoreException e)
		{
			// EXPECTED
		}
		
		// basic test
		Object[] dest = new Object[m_list.size()];
		Object[] result = GwtUtil.toArray(m_list, dest);
		assertSame(dest, result);
		for (int i = 0; i < result.length; i++)
		{
			assertSame(m_list.get(i), result[i]);
		}
		
		// 0 length list
		Vector emptyList = new Vector();
		result = GwtUtil.toArray(emptyList, new Object[emptyList.size()]);
		
		// array.length > list.size()
		dest = new Object[m_list.size() + 10];
		for (char i = 0; i < dest.length; i++)
		{
			dest[i] = new Character((char) ('a' + i));
		}
		result = GwtUtil.toArray(m_list, dest);
		assertNull(dest[m_list.size()]);
	}
	
	private static class Food
	{
	}
	
	private static class Apple extends Food
	{
	}
}
