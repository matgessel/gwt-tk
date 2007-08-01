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

import junit.framework.Assert;

public final class TkTestUtil
{
	public static void assertSameElements(Object[] a1, Object[] a2)
	{
		Assert.assertNotNull(a1);
		Assert.assertNotNull(a2);
		Assert.assertEquals(a1.length, a2.length);
		for (int i = 0; i < a1.length; i++)
		{
			Assert.assertSame(String.valueOf(i), a1[i], a2[i]);
		}
	}
    
    public static Object[] createIntegerArray(int start, int count)
    {
        Object[] result = new Object[count];
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
        	Assert.assertSame(expected[expectedPos + i], actual[0 + i]);
        }
    }
    
    public static void arrayIdentityCompare(Object[] expected, int expectedPos, Object[] actual, int actualPos, int length)
    {
        for (int i = 0; i < length; i++)
        {
        	Assert.assertSame(expected[expectedPos + i], actual[actualPos + i]);
        }
    }
}
