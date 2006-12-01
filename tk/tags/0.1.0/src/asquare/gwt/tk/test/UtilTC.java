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
package asquare.gwt.tk.test;

import asquare.gwt.debug.client.Debug;
import asquare.gwt.tk.client.Util;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

/**
 * Many tests will fail in hosted mode because GWT wraps thrown
 * JavaScriptExceptions in a RuntimeException. Waiting to see if this will be
 * fixed.
 * 
 * @see <a href="http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/331562794647f775">Catching a JSNI exception in "Java"</a>
 */
public class UtilTC extends GWTTestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.tk.TkTC";
	}

	public void testAppendText()
	{
		Debug.enable();
		Label validTextContainer = new Label("A");
		assertEquals("A", validTextContainer.getText());
		Util.appendText(validTextContainer.getElement(), "B");
		assertEquals("AB", validTextContainer.getText());
		
		// HTML container with inline element
		HTML htmlContainer = new HTML("<PRE>A</PRE>");
		assertEquals("<PRE>A</PRE>", htmlContainer.getHTML());
		assertEquals("A", htmlContainer.getText());
		Util.appendText(htmlContainer.getElement(), "B");
		assertEquals("<PRE>AB</PRE>", htmlContainer.getHTML());
		assertEquals("AB", htmlContainer.getText());
		
		// null element
		try
		{
			Util.appendText(null, "B");
			fail("expected JavaScript exception because element is null");
		}
		catch (NullPointerException e)
		{
			// EXPECTED
		}

		// element with no children
		AbsolutePanel nullTextContainer = new AbsolutePanel();
		try
		{
			Util.appendText(nullTextContainer.getElement(), "B");
			fail("expected IllegalArgumentException exception because element has no children");
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}

		// element with first child other than #text 
		AbsolutePanel invalidTextContainer = new AbsolutePanel();
		invalidTextContainer.add(new AbsolutePanel());		
		try
		{
			Util.appendText(invalidTextContainer.getElement(), "B");
			fail("expected JavaScript exception because element does not have #text as first child node");
		}
		catch (JavaScriptException e)
		{
			// EXPECTED
		}
		catch (RuntimeException e)
		{
			// the test fails here in hosted mode. Works as expected in web mode. 
			// http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/331562794647f775
			assertTrue(e.getCause() instanceof JavaScriptException);
			addCheckpoint("Nested JS Exception test failed");
			fail("Caught RuntimeException, expected JavaScript exception\n" + e.getMessage());
		}
	}
	
	public void testJSArray()
	{
		Debug.enable();
		int[] intArray = new int[] {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE};
		JavaScriptObject intJSArray = Util.arrayConvert(intArray);
		Debug.prettyPrint(intJSArray);
		for (int i = 0; i < Util.arrayLength(intJSArray); i++)
		{
			assertEquals(intArray[i], Util.arrayGetInt(intJSArray, i));
		}
		
		float[] floatArray = new float[] {Float.MIN_VALUE, -1.0f, 0.0f, 1.0f, Float.MAX_VALUE};
		JavaScriptObject floatJSArray = Util.arrayConvert(floatArray);
		Debug.prettyPrint(floatJSArray);
		for (int i = 0; i < Util.arrayLength(floatJSArray); i++)
		{
			assertTrue(floatArray[i] == Util.arrayGetFloat(floatJSArray, i));
// GWTTestCase super-source bug
//			assertEquals(floatArray[i], Util.arrayGetFloat(floatJSArray, i), 0.0);
		}
		
		Object[] objArray = new Object[] {new Integer(1), new Float(2.0f), "String"};
		JavaScriptObject objJSArray = Util.arrayConvert(objArray);
		Debug.prettyPrint(objJSArray);
		for (int i = 0; i < Util.arrayLength(objJSArray); i++)
		{
			assertEquals(objArray[i], Util.arrayGetObject(objJSArray, i));
		}
		assertEquals(objArray[0].toString(), _testJSArrayHelper(objJSArray));
	}

	public static native String _testJSArrayHelper(JavaScriptObject jsArray) /*-{
		return jsArray[0].@java.lang.Integer::toString()();
	}-*/;
	
	public void testArrayNewArray()
	{
		Util.arrayNewArray(0);
		Util.arrayNewArray(1);
		Util.arrayNewArray(1000);
		
		try
		{
			Util.arrayNewArray(-1);
			fail("expected JavaScript exception because array length is negative");
		}
		catch (JavaScriptException e)
		{
			// EXPECTED
		}
	}
	
	public void testArrayLength()
	{
		assertEquals(0, Util.arrayLength(Util.arrayNewArray(0)));
		assertEquals(1, Util.arrayLength(Util.arrayNewArray(1)));
		assertEquals(1000, Util.arrayLength(Util.arrayNewArray(1000)));
		
		try
		{
			Util.arrayLength(null);
			fail("expected JavaScript exception because array is null");
		}
		catch (JavaScriptException e)
		{
			// EXPECTED
		}
	}
	
	public void testArraySetGet()
	{
		JavaScriptObject array = Util.arrayNewArray(0);
		Object o = new Object();
		
		// text range expansion
		Util.arraySet(array, 0, 1);
		assertEquals(1, Util.arrayGetInt(array, 0));
		
		// min int
		Util.arraySet(array, 0, Integer.MIN_VALUE);
		assertEquals(Integer.MIN_VALUE, Util.arrayGetInt(array, 0));
		
		// max int
		Util.arraySet(array, 0, Integer.MAX_VALUE);
		assertEquals(Integer.MAX_VALUE, Util.arrayGetInt(array, 0));
		
		
		// min float
		Util.arraySet(array, 0, Float.MIN_VALUE);
		assertTrue(Float.MIN_VALUE == Util.arrayGetFloat(array, 0));
		
		// max float
		Util.arraySet(array, 0, Float.MAX_VALUE);
		assertTrue(Float.MAX_VALUE == Util.arrayGetFloat(array, 0));
		
		// object
		Util.arraySet(array, 0, o);
		assertEquals(o, Util.arrayGetObject(array, 0));
		
		// null array
		try
		{
			Util.arraySet(null, 0, 0);
			fail("expected JavaScript exception because array is null");
		}
		catch (JavaScriptException e)
		{
			// EXPECTED
		}

		// negative index
		Util.arraySet(array, -1, 0);

// These methods throw RuntimeException in hosted mode and no error in web mode		
//		// set object, get int
//		Util.arraySet(array, 0, o);
//		try
//		{
//			addCheckpoint("Value: " + Util.arrayGetInt(array, 0));
//			fail("expected JavaScript exception because value is an object, not an int");
//		}
//		catch (JavaScriptException e)
//		{
//			// EXPECTED
//		}
//		
//		// set object, get float
//		Util.arraySet(array, 0, o);
//		try
//		{
//			Util.arrayGetFloat(array, 0);
//			fail("expected JavaScript exception because value is an object, not a float");
//		}
//		catch (JavaScriptException e)
//		{
//			// EXPECTED
//		}
//		
//		// set int, get object
//		Util.arraySet(array, 0, 0);
//		try
//		{
//			Util.arrayGetObject(array, 0);
//			fail("expected JavaScript exception because value is an int, not an object");
//		}
//		catch (JavaScriptException e)
//		{
//			// EXPECTED
//		}
		
		// set int, get float
		Util.arraySet(array, 0, 1);
		assertTrue(1.0f == Util.arrayGetFloat(array, 0));
		
		// set float, get int
		Util.arraySet(array, 0, 1.0f);
		assertTrue(1 == Util.arrayGetInt(array, 0));
	}
}
