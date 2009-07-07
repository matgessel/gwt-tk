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
package asquare.gwt.tk.testutil;

import asquare.gwt.tk.client.util.GwtUtil;
import junit.framework.Assert;

public final class TkTestUtil
{
    public static Object[] popElement(Object[] src, int index)
    {
        Object[] result = new Object[src.length - 1];
        GwtUtil.arrayCopy(src, 0, result, 0, index);
        GwtUtil.arrayCopy(src, index + 1, result, index, src.length - (index + 1));
        return result;
    }
    
    public static void assertSameElements(Object[] expected, Object[] actual)
	{
		Assert.assertNotNull("expected=null", expected);
		Assert.assertNotNull("actual=null", actual);
		Assert.assertEquals("length", expected.length, actual.length);
		for (int i = 0; i < expected.length; i++)
		{
			Assert.assertSame(String.valueOf(i), expected[i], actual[i]);
		}
	}
    
    public static void assertSameElements(Object[][] expected, Object[][] actual)
    {
		Assert.assertNotNull("expected=null", expected);
		Assert.assertNotNull("actual=null", actual);
		Assert.assertEquals("length", expected.length, actual.length);
		for (int i = 0; i < expected.length; i++)
		{
			String iString = String.valueOf(i);
			if (expected[i] == actual[i])
			{
				continue;
			}
			Assert.assertNotNull("expected[" + iString + ']', expected[i]);
			Assert.assertNotNull("actual[" + iString + ']', actual[i]);
			Assert.assertEquals("length[" + iString + ']', expected[i].length, actual[i].length);
			for (int j = 0; j < expected[i].length; j++)
			{
				Assert.assertSame('[' + iString + ',' + String.valueOf(j) + ']', expected[i][j], actual[i][j]);
			}
		}
    }
	
	public static void assertEqualValues(Object[] expected, Object[] actual)
	{
		Assert.assertNotNull("expected=null", expected);
		Assert.assertNotNull("actual=null", actual);
		Assert.assertEquals("length", expected.length, actual.length);
		for (int i = 0; i < expected.length; i++)
		{
			Assert.assertEquals(String.valueOf(i), expected[i], actual[i]);
		}
	}
    
    public static void assertEqualValues(Object[][] expected, Object[][] actual)
    {
		Assert.assertNotNull("expected=null", expected);
		Assert.assertNotNull("actual=null", actual);
		Assert.assertEquals("length", expected.length, actual.length);
		for (int i = 0; i < expected.length; i++)
		{
			String iString = String.valueOf(i);
			if (expected[i] == actual[i])
			{
				continue;
			}
			Assert.assertNotNull("expected[" + iString + ']', expected[i]);
			Assert.assertNotNull("actual[" + iString + ']', actual[i]);
			Assert.assertEquals("length[" + iString + ']', expected[i].length, actual[i].length);
			for (int j = 0; j < expected[i].length; j++)
			{
				Assert.assertEquals('[' + iString + ',' + String.valueOf(j) + ']', expected[i][j], actual[i][j]);
			}
		}
    }
	
	public static void assertEqualValues(int[] expected, int[] actual)
	{
		Assert.assertNotNull("expected=null", expected);
		Assert.assertNotNull("actual=null", actual);
		Assert.assertEquals("length", expected.length, actual.length);
		for (int i = 0; i < expected.length; i++)
		{
			Assert.assertEquals(String.valueOf(i), expected[i], actual[i]);
		}
	}
    
    public static void assertEqualValues(int[][] expected, int[][] actual)
    {
		Assert.assertNotNull("expected=null", expected);
		Assert.assertNotNull("actual=null", actual);
		Assert.assertEquals("length", expected.length, actual.length);
		for (int i = 0; i < expected.length; i++)
		{
			String iString = String.valueOf(i);
			if (expected[i] == actual[i])
			{
				continue;
			}
			Assert.assertNotNull("expected[" + iString + ']', expected[i]);
			Assert.assertNotNull("actual[" + iString + ']', actual[i]);
			Assert.assertEquals("length[" + iString + ']', expected[i].length, actual[i].length);
			for (int j = 0; j < expected[i].length; j++)
			{
				Assert.assertEquals('[' + iString + ',' + String.valueOf(j) + ']', expected[i][j], actual[i][j]);
			}
		}
    }
	
    public static Integer[] createIntegerArray(int start, int count)
    {
    	Integer[] result = new Integer[count];
        for (int i = 0; i < count; i++)
        {
            result[i] = new Integer(start + i);
        }
        return result;
    }
    
    public static void arrayIdentityCompare(Object[] expected, int expectedPos, Object[] actual)
    {
        for (int i = 0; i < actual.length; i++)
        {
        	Assert.assertSame(String.valueOf(i), expected[expectedPos + i], actual[0 + i]);
        }
    }
    
    public static void arrayIdentityCompare(Object[] expected, int expectedPos, Object[] actual, int actualPos, int length)
    {
        for (int i = 0; i < length; i++)
        {
        	Assert.assertSame(String.valueOf(i), expected[expectedPos + i], actual[actualPos + i]);
        }
    }
    
    public static void arrayValueCompare(int[] expected, int expectedPos, int[] actual, int actualPos, int length)
    {
        for (int i = 0; i < length; i++)
        {
        	Assert.assertEquals(String.valueOf(i), expected[expectedPos + i], actual[actualPos + i]);
        }
    }

	public static int[] subRange(int[] array, int offset, int length)
	{
		int[] result = new int[length];
		GwtUtil.arrayCopy(array, offset, result, 0, length);
		return result;
	}

	public static Object[] subRange(Object[] array, int offset, int length)
	{
		Object[] result = new Object[length];
		GwtUtil.arrayCopy(array, offset, result, 0, length);
		return result;
	}
}
