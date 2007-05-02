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

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * JSNI methods for working with native JavaScript objects. 
 */
public class JsUtil
{
	/**
	 * Converts an array of int to a native JavaScript array. 
	 * 
	 * @param array a regular Java array 
	 * @return a new array as an opaque JavaScriptObject
	 */
	public static JavaScriptObject arrayConvert(int[] array)
	{
		JavaScriptObject result = arrayNewArray(array.length);
		for (int i = 0; i < array.length; i++)
		{
			arraySet(result, i, array[i]);
		}
		return result;
	}
	
	/**
	 * Converts an array of float to a native JavaScript array. 
	 * 
	 * @param array a regular Java array
	 * @return a new array as an opaque JavaScriptObject
	 */
	public static JavaScriptObject arrayConvert(float[] array)
	{
		JavaScriptObject result = arrayNewArray(array.length);
		for (int i = 0; i < array.length; i++)
		{
			arraySet(result, i, array[i]);
		}
		return result;
	}
	
	/**
	 * Converts an array of objects to a native JavaScript array. 
	 * 
	 * @param array a regular Java array 
	 * @return a new array as an opaque JavaScriptObject
	 */
	public static JavaScriptObject arrayConvert(Object[] array)
	{
		JavaScriptObject result = arrayNewArray(array.length);
		for (int i = 0; i < array.length; i++)
		{
			arraySet(result, i, array[i]);
		}
		return result;
	}
	
	/**
	 * Constructs an empty native JavaScript array. JavaScript arrays are not
	 * typed.
	 * 
	 * @param length the array length
	 * @return the array as an opaque JavaScriptObject
	 * @throws JavaScriptException if length is less than 0
	 */
	public static native JavaScriptObject arrayNewArray(int length) /*-{
		if (length < 0)
			throw new Error();
		
		return new Array(length);
	}-*/;
	
	/**
	 * Get the length of a native JavaScript array.
	 * 
	 * @param array an opaque handle to a JavaScript array
	 * @return the length
	 * @throws JavaScriptException if the array is null
	 */
	public static native int arrayLength(JavaScriptObject array) /*-{
		return array.length;
	}-*/;
	
	/**
	 * Get an int value from a native JavaScript array.
	 * 
	 * @param array an opaque handle to a JavaScript array
	 * @param index the index, starting at 0
	 * @return the value
	 * @throws JavaScriptException if the array is null
	 * @throws JavaScriptException if the index is out of bounds
	 * @throws JavaScriptException if value at the index is not coercable to an
	 *             int (hosted mode only)
	 */
	public static int arrayGetInt(JavaScriptObject array, int index)
	{
		try
		{
			return arrayGetInt0(array, index);
		}
		catch (RuntimeException e)
		{
			throw new JavaScriptException(null, e.getMessage());
		}
	}
	
	private static native int arrayGetInt0(JavaScriptObject array, int index) /*-{
		return array[index];
	}-*/;
	
	/**
	 * Get a float value from a native JavaScript array.
	 * 
	 * @param array an opaque handle to a JavaScript array
	 * @param index the index, starting at 0
	 * @return the value
	 * @throws JavaScriptException if the array is null
	 * @throws JavaScriptException if the index is out of bounds
	 * @throws JavaScriptException if value at the index is not coercable to a
	 *             float (hosted mode only)
	 */
	public static float arrayGetFloat(JavaScriptObject array, int index)
	{
		try
		{
			return arrayGetFloat0(array, index);
		}
		catch (RuntimeException e)
		{
			throw new JavaScriptException(null, e.getMessage());
		}
	}
	
	private static native float arrayGetFloat0(JavaScriptObject array, int index) /*-{
		return array[index];
	}-*/;

	/**
	 * Get an object from a native JavaScript array.
	 * 
	 * @param array an opaque handle to a JavaScript array
	 * @param index the index, starting at 0
	 * @return the value
	 * @throws JavaScriptException if the array is null
	 * @throws JavaScriptException if the index is out of bounds
	 * @throws JavaScriptException if value at the index is not an object
	 *             (hosted mode only)
	 */
	public static Object arrayGetObject(JavaScriptObject array, int index)
	{
		try
		{
			return arrayGetObject0(array, index);
		}
		catch (IllegalArgumentException e)
		{
			throw new JavaScriptException(null, e.getMessage());
		}
	}
	
	private static native Object arrayGetObject0(JavaScriptObject array, int index) /*-{
		return array[index];
	}-*/;

	/**
	 * Set an int value at the specified index in a native JavaScript array. The
	 * array will automatically allocate space to accommodate new values.
	 * 
	 * @param array an opaque handle to a JavaScript array
	 * @param index the index, starting at 0
	 * @param value an integer primative value
	 * @throws JavaScriptException if the array is null
	 * @throws JavaScriptException if the index is negative
	 */
	public static native void arraySet(JavaScriptObject array, int index, int value) /*-{
		array[index] = value;
	}-*/;
	
	/**
	 * Set a float value at the specified index in a native JavaScript array.
	 * The array will automatically allocate space to accommodate new values.
	 * 
	 * @param array an opaque handle to a JavaScript array
	 * @param index the index, starting at 0
	 * @param value an float primative value
	 * @throws JavaScriptException if the array is null
	 * @throws JavaScriptException if the index is negative
	 */
	public static native void arraySet(JavaScriptObject array, int index, float value) /*-{
		array[index] = value;
	}-*/;
	
	/**
	 * Set an object at the specified index in a native JavaScript array. The
	 * array will automatically allocate space to accommodate new values.
	 * 
	 * @param array an opaque handle to a JavaScript array
	 * @param index the index, starting at 0
	 * @param value an object
	 * @throws JavaScriptException if the array is null
	 * @throws JavaScriptException if the index is negative
	 */
	public static native void arraySet(JavaScriptObject array, int index, Object value) /*-{
		array[index] = value;
	}-*/;
}
