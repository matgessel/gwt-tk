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
package asquare.gwt.tk.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.UIObject;

/**
 * Utility methods, mostly JSNI stuff. 
 * <p>
 * <em>Note: methods that are declared to throw a JavaScriptException
 * will throw a RuntimeException in hosted mode. 
 * Waiting to see if this is going to be fixed 
 * in an upcoming GWT release. </em>
 * </p>
 * @see <a href="http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/331562794647f775">Catching a JSNI exception in "Java"</a>
 */
public class Util
{
	/**
	 * Sets the id attribute the specified UIObject's element. Useful if you
	 * need to select a specific component in CSS.
	 * <h3>CSS Selection Example</h3>
	 * 
	 * <pre>
	 *     #fooPackage-barPanel {
	 *       border: groove black 1px;
	 *       text-align: left;
	 *     }
	 * </pre>
	 * 
	 * @param id a unique id. It is a good practice to namespace ids to avoid
	 *            future id confilicts.
	 */
	public static void setId(UIObject uio, String id)
	{
		DOM.setAttribute(uio.getElement(), "id", id);
	}
	
	/**
	 * Appends text to a #text node which is a child of the element. Drills down
	 * recursivly via '.firstChild' until a #text node is found. (This does not
	 * crawl the entire descendent hierarchy.)
	 * 
	 * @param element an element containing a text node as a first child (e.g. a Label or HTML)
	 * @param text text to append
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if element has no child nodes
	 * @throws com.google.gwt.core.client.JavaScriptException if a #text node is
	 *             not found
	 */
	public static void appendText(Element element, String text)
	{
		/*
		 * Catch as many error conditions as possible in Java since JSNI error handling sucks
		 */
		if (element == null)
			throw new NullPointerException("element cannot be null");
		
		if (! hasChildNodes(element))
			throw new IllegalArgumentException("element has no child nodes");
		
		// assume element is now a #text node
		appendText0(element, text);
	}
	
	/**
	 * This exists because GWT DOM does not provide an accessor for 
	 * <em>Nodes</em> (as opposed to <em>Elements</em>).
	 */
	private static native boolean hasChildNodes(Element element) /*-{
		return element != null && element.hasChildNodes();
	}-*/;

	private static native void appendText0(Element element, String text) /*-{
		var TEXT_NODE = 3;
		var node = element;
		while (node.nodeType != TEXT_NODE && node.firstChild)
		{
			node = node.firstChild;
		}
		
		if (! node.nodeType || node.nodeType != TEXT_NODE)
			throw new Error("Expected node type #text");
		
		node.appendData(text);
	}-*/;
	
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
	 * Constructs an empty native JavaScript array.
	 * 
	 * @param length the array length
	 * @return the array as an opaque JavaScriptObject
	 * @throws com.google.gwt.core.client.JavaScriptException if length is less
	 *             than 0
	 */
	public static native JavaScriptObject arrayNewArray(int length) /*-{
		if (length < 0)
			throw new Error();
		
		return new Array(length);
	}-*/;
	
	/**
	 * Gets the length of a native JavaScript array. 
	 * 
	 * @param array an opaque handle to a JavaScript array 
	 * @return the length
	 * @throws com.google.gwt.core.client.JavaScriptException if the array is null
	 */
	public static native int arrayLength(JavaScriptObject array) /*-{
		return array.length;
	}-*/;
	
	/**
	 * Gets an int value from a native JavaScript array. 
	 * 
	 * @param array an opaque handle to a JavaScript array
	 * @param index the index, starting at 0
	 * @return the value
	 * @throws com.google.gwt.core.client.JavaScriptException if the array is
	 *             null
	 * @throws com.google.gwt.core.client.JavaScriptException if the index is
	 *             out of bounds
	 * @throws com.google.gwt.core.client.JavaScriptException if value at the
	 *             index is not coercable to an int (hosted mode only)
	 */
	public static native int arrayGetInt(JavaScriptObject array, int index) /*-{
		return array[index];
	}-*/;
	
	/**
	 * Gets a float value from a native JavaScript array.
	 * 
	 * @param array an opaque handle to a JavaScript array
	 * @param index the index, starting at 0
	 * @return the value
	 * @throws com.google.gwt.core.client.JavaScriptException if the array is
	 *             null
	 * @throws com.google.gwt.core.client.JavaScriptException if the index is
	 *             out of bounds
	 * @throws com.google.gwt.core.client.JavaScriptException if value at the
	 *             index is not coercable to a float (hosted mode only)
	 */
	public static native float arrayGetFloat(JavaScriptObject array, int index) /*-{
		return array[index];
	}-*/;

	/**
	 * Gets an object from a native JavaScript array.
	 * 
	 * @param array an opaque handle to a JavaScript array
	 * @param index the index, starting at 0
	 * @return the value
	 * @throws com.google.gwt.core.client.JavaScriptException if the array is
	 *             null
	 * @throws com.google.gwt.core.client.JavaScriptException if the index is
	 *             out of bounds
	 * @throws com.google.gwt.core.client.JavaScriptException if value at the
	 *             index is not an object (hosted mode only)
	 */
	public static native Object arrayGetObject(JavaScriptObject array, int index) /*-{
		return array[index];
	}-*/;

	/**
	 * Sets an int value at the specified index in a native JavaScript array. The
	 * array will automatically allocate space to accommodate new values.
	 * 
	 * @param array an opaque handle to a JavaScript array
	 * @param index the index, starting at 0
	 * @param value an integer primative value
	 * @throws com.google.gwt.core.client.JavaScriptException if the array is
	 *             null
	 * @throws com.google.gwt.core.client.JavaScriptException if the index is
	 *             negative
	 */
	public static native void arraySet(JavaScriptObject array, int index, int value) /*-{
		array[index] = value;
	}-*/;
	
	/**
	 * Sets a float value at the specified index in a native JavaScript array.
	 * The array will automatically allocate space to accommodate new values.
	 * 
	 * @param array an opaque handle to a JavaScript array
	 * @param index the index, starting at 0
	 * @param value an float primative value
	 * @throws com.google.gwt.core.client.JavaScriptException if the array is
	 *             null
	 * @throws com.google.gwt.core.client.JavaScriptException if the index is
	 *             negative
	 */
	public static native void arraySet(JavaScriptObject array, int index, float value) /*-{
		array[index] = value;
	}-*/;
	
	/**
	 * Sets an object at the specified index in a native JavaScript array. The
	 * array will automatically allocate space to accommodate new values.
	 * 
	 * @param array an opaque handle to a JavaScript array
	 * @param index the index, starting at 0
	 * @param value an object
	 * @throws com.google.gwt.core.client.JavaScriptException if the array is
	 *             null
	 * @throws com.google.gwt.core.client.JavaScriptException if the index is
	 *             negative
	 */
	public static native void arraySet(JavaScriptObject array, int index, Object value) /*-{
		array[index] = value;
	}-*/;
}
