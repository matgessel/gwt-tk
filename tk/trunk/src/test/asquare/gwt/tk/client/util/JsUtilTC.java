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

import asquare.gwt.debug.client.Debug;
import asquare.gwt.tk.client.Tests;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.junit.client.GWTTestCase;

public class JsUtilTC extends GWTTestCase
{
    @Override
	public String getModuleName()
	{
		return Tests.getModuleName();
	}

	public void testJSArray()
	{
		Debug.enable();
		int[] intArray = new int[] {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE};
		JavaScriptObject intJSArray = JsUtil.arrayConvert(intArray);
		Debug.dump(intJSArray);
		for (int i = 0; i < JsUtil.arrayLength(intJSArray); i++)
		{
			assertEquals(intArray[i], JsUtil.arrayGetInt(intJSArray, i));
		}
		
		float[] floatArray = new float[] {Float.MIN_VALUE, -1.0f, 0.0f, 1.0f, Float.MAX_VALUE};
		JavaScriptObject floatJSArray = JsUtil.arrayConvert(floatArray);
		Debug.dump(floatJSArray);
		for (int i = 0; i < JsUtil.arrayLength(floatJSArray); i++)
		{
			assertEquals(floatArray[i], JsUtil.arrayGetFloat(floatJSArray, i), 0.0);
		}
		
		Object[] objArray = new Object[] {new Integer(1), new Float(2.0f), "String"};
		JavaScriptObject objJSArray = JsUtil.arrayConvert(objArray);
// bizarre crash here
//		Debug.dump(objJSArray);
		for (int i = 0; i < JsUtil.arrayLength(objJSArray); i++)
		{
			assertEquals(objArray[i], JsUtil.arrayGetObject(objJSArray, i));
		}
		assertEquals(objArray[0].toString(), _testJSArrayHelper(objJSArray));
	}
	
	public static native String _testJSArrayHelper(JavaScriptObject jsArray) /*-{
		return jsArray[0].@java.lang.Integer::toString()();
	}-*/;
	
	public void testArrayNewArray()
	{
		JsUtil.arrayNewArray(0);
		JsUtil.arrayNewArray(1);
		JsUtil.arrayNewArray(1000);
		
		try
		{
			JsUtil.arrayNewArray(-1);
			fail("expected JavaScript exception because array length is negative");
		}
		catch (JavaScriptException e)
		{
			// EXPECTED
		}
	}
	
	public void testArrayLength()
	{
		assertEquals(0, JsUtil.arrayLength(JsUtil.arrayNewArray(0)));
		assertEquals(1, JsUtil.arrayLength(JsUtil.arrayNewArray(1)));
		assertEquals(1000, JsUtil.arrayLength(JsUtil.arrayNewArray(1000)));
		
		try
		{
			JsUtil.arrayLength(null);
			fail("expected JavaScript exception because array is null");
		}
		catch (JavaScriptException e)
		{
			// EXPECTED
		}
	}
	
	public void testArraySetGet()
	{
		JavaScriptObject array = JsUtil.arrayNewArray(0);
		Object o = new Object();
		
		// text range expansion
		JsUtil.arraySet(array, 0, 1);
		assertEquals(1, JsUtil.arrayGetInt(array, 0));
		
		// min int
		JsUtil.arraySet(array, 0, Integer.MIN_VALUE);
		assertEquals(Integer.MIN_VALUE, JsUtil.arrayGetInt(array, 0));
		
		// max int
		JsUtil.arraySet(array, 0, Integer.MAX_VALUE);
		assertEquals(Integer.MAX_VALUE, JsUtil.arrayGetInt(array, 0));
		
		// min float
		JsUtil.arraySet(array, 0, Float.MIN_VALUE);
		assertTrue(Float.MIN_VALUE == JsUtil.arrayGetFloat(array, 0));
		
		// max float
		JsUtil.arraySet(array, 0, Float.MAX_VALUE);
		assertTrue(Float.MAX_VALUE == JsUtil.arrayGetFloat(array, 0));
		
		// object
		JsUtil.arraySet(array, 0, o);
		assertEquals(o, JsUtil.arrayGetObject(array, 0));
		
		// null array
		try
		{
			JsUtil.arraySet(null, 0, 0);
			fail("expected JavaScript exception because array is null");
		}
		catch (JavaScriptException e)
		{
			// EXPECTED
		}

		// negative index
		JsUtil.arraySet(array, -1, 0);
		
		// set int, get float
		JsUtil.arraySet(array, 0, 1);
		assertTrue(1.0f == JsUtil.arrayGetFloat(array, 0));
		
		// set float, get int
		JsUtil.arraySet(array, 0, 1.0f);
		assertTrue(1 == JsUtil.arrayGetInt(array, 0));
		
		// set object, get int
		JsUtil.arraySet(array, 0, o);
		try
		{
			JsUtil.arrayGetInt(array, 0);
			if (! GWT.isScript())
			{
				fail("expected JavaScript exception because value is an object, not an int");
			}
		}
		catch (JavaScriptException e)
		{
			// EXPECTED
		}
		
		// set object, get float
		JsUtil.arraySet(array, 0, o);
		try
		{
			JsUtil.arrayGetFloat(array, 0);
			if (! GWT.isScript())
			{
				fail("expected JavaScript exception because value is an object, not a float");
			}
		}
		catch (JavaScriptException e)
		{
			// EXPECTED
		}
		
		// set int, get object
		JsUtil.arraySet(array, 0, 0);
		try
		{
			JsUtil.arrayGetObject(array, 0);
			if (! GWT.isScript())
			{
				fail("expected JavaScript exception because value is an int, not an object");
			}
		}
		catch (JavaScriptException e)
		{
			// EXPECTED
		}
	}
}
