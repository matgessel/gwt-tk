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

import asquare.gwt.tk.client.util.impl.DomUtilImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.UIObject;

/**
 * Utility methods for working with DOM. 
 */
public class DomUtil
{
	private static DomUtilImpl s_impl = (DomUtilImpl) GWT.create(DomUtilImpl.class);
	
	/**
	 * Appends text to a #text node which is a child of the element. Drills down
	 * recursivly via '.firstChild' until a #text node is found. 
	 * 
	 * @param element an element containing a text node as a first child (e.g. a Label or HTML)
	 * @param text text to append
	 * @param create true to create a text node if one is not found
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if element has no child nodes
	 * @throws com.google.gwt.core.client.JavaScriptException if a #text node is
	 *             not found
	 */
	public static void appendText(Element element, String text, boolean create)
	{
		/*
		 * Catch as many error conditions as possible in Java since JSNI error handling sucks
		 */
		if (element == null)
			throw new NullPointerException("element cannot be null");
		
		if (! hasChildNodes(element) && ! create)
			throw new IllegalArgumentException("element has no child nodes");
		
		// assume element is now a #text node
		appendText0(element, text, create);
	}
	
	/**
	 * This exists because GWT DOM does not provide an accessor for 
	 * <em>Nodes</em> (as opposed to <em>Elements</em>).
	 */
	private static native boolean hasChildNodes(Element element) /*-{
		return element != null && element.hasChildNodes();
	}-*/;

	private static native void appendText0(Element element, String text, boolean create) /*-{
		var TEXT_NODE = 3;
		var node = element;
		var textNode = null;
		while(node.firstChild)
		{
			if (node.firstChild.nodeType == TEXT_NODE)
			{
				textNode = node.firstChild;
				break;
			}
			node = node.firstChild;
		}
		
		if (textNode == null)
		{
			if (create)
			{
				textNode = node.ownerDocument.createTextNode(text);
				node.appendChild(textNode);
				return;
			}
			else
			{
				throw new Error("Couldn't find node of type #text");
			}
		}
		
		textNode.appendData(text);
	}-*/;
	
	/**
	 * Gets the id attribute of the specified UIObject's element.
	 * 
	 * @param uio the UIObject
	 * @return the id or null the id attribute is not set
	 */
	public static String getId(UIObject uio)
	{
		return DOM.getAttribute(uio.getElement(), "id");
	}
	
	/**
	 * Sets the id attribute of the specified UIObject's element. Useful if you
	 * need to select a specific component in CSS.
	 * <p>
	 * Usage notes:
	 * <ul>
	 * <li>The id should not be the same as the history token. See <a
	 * href="http://code.google.com/p/google-web-toolkit/issues/detail?id=61">Issue
	 * 61</a></li>
	 * </ul>
	 * <h3>CSS Selection Example</h3>
	 * 
	 * <pre>
	 *      #fooPackage-barPanel {
	 *        border: groove black 1px;
	 *        text-align: left;
	 *      }
	 * </pre>
	 * 
	 * @param uio the UIObject
	 * @param id a unique id. It is a good practice to namespace ids to avoid
	 *            future id confilicts, i.e. organization.project.foo.
	 */
	public static void setId(UIObject uio, String id)
	{
		DOM.setAttribute(uio.getElement(), "id", id);
	}
	
	/**
	 * Sets an attribute on the specified UIObject's element. 
	 * 
	 * @param uio a UIObject
	 * @param name the attribute name, in "camelCase"
	 * @param value a value
	 */
	public static void setAttribute(UIObject uio, String name, String value)
	{
		DOM.setAttribute(uio.getElement(), name, value);
	}
	
	/**
	 * Sets a CSS style attribute on the specified UIObject's element. 
	 * 
	 * @param uio a UIObject
	 * @param name a CSS style property name, in "camelCase"
	 * @param value a valid CSS property value
	 */
	public static void setStyleAttribute(UIObject uio, String name, String value)
	{
		DOM.setStyleAttribute(uio.getElement(), name, value);
	}
	
	/**
	 * Creates a list of all elements contained by the specified element
	 * (inclusive) which have the specified CSS class (in GWT terms
	 * <em>stylename</em>).
	 * 
	 * @param element the element which is at the root of the hierarchy that you wish to search
	 * @param className the name of the CSS class to search for
	 * @param result a writable list which will be returned as the result (for recursion).
	 *            Typically, you pass <code>null</code> and the list will
	 *            be created on the fly.
	 * @return <b>result</b> a list containing 0 or more HTML elements
	 */
	public static List findElementsWithClass(Element element, String className, List result)
	{
		if (result == null)
		{
			result = new Vector();
		}

		String cls = DOM.getAttribute(element, "className");
		if (cls != null && cls.indexOf(className) >= 0)
		{
			result.add(element);
		}

		int childCount = DOM.getChildCount(element);
		for (int i = 0; i < childCount; i++)
		{
			findElementsWithClass(DOM.getChild(element, i), className, result);
		}

		return result;
	}
	
	/**
	 * Reads <code>document.compatMode</code> and returns <code>true</code>
	 * if the property exists and equals "BackCompat". Supported on Opera, IE
	 * and Mozilla. Always returns <code>false</code> in Safari because the
	 * property is not defined.
	 * <p>
	 * <em>Note: Safari <strong>does</strong> have a quirks mode, but you cannot 
	 * determine this from JavaScript.</em>
	 * </p>
	 * 
	 * @return <code>true</code> if the browser is rendering in quirks mode
	 * @see <a href="http://www.quirksmode.org/css/quirksmode.html"></a>
	 */
	public static boolean isQuirksMode()
	{
		return s_impl.isQuirksMode();
	}
	
	/**
	 * Determines if the browser is running on the Mac OS. 
	 * 
	 * @return <code>true</code> if the browser is running on a Mac
	 */
	public static boolean isMac()
	{
		return s_impl.isMac();
	}
	
	/**
	 * Determines if the browser is running on a Windows OS. 
	 * 
	 * @return <code>true</code> if the browser is running on Windows
	 */
	public static boolean isWin()
	{
		return s_impl.isWin();
	}
	
	/**
	 * Get the width of the GWT application's primary viewport, aka "client
	 * area". (This measurement does not include scrollbars.)
	 * 
	 * @return the width in screen pixels
	 */
	public static int getViewportWidth()
	{
		return s_impl.getViewportWidth();
	}
	
	/**
	 * Get the height of the GWT application's primary viewport, aka "client
	 * area". (This measurement does not include scrollbars.)
	 * 
	 * @return the height in screen pixels
	 */
	public static int getViewportHeight()
	{
		return s_impl.getViewportHeight();
	}
	
	/**
	 * Get the horizontal scroll offset of the GWT application's primary window.
	 * Measured from the left-most position in the document (i.e.
	 * document.documentElement origin) to the left edge of the viewport.
	 * 
	 * @return 0 or a positive value in screen pixels 
	 */
	public static int getViewportScrollX()
	{
		return DOMExtenstion.getViewportScrollX();
	}
	
	/**
	 * Get the vertical scroll offset of the GWT application's primary window.
	 * Measured from the top-most position in the document (i.e.
	 * document.documentElement origin) to the top edge of the viewport.
	 * 
	 * @return 0 or a positive value in screen pixels 
	 */
	public static int getViewportScrollY()
	{
		return DOMExtenstion.getViewportScrollY();
	}
	
	/**
	 * Get the width of the document. 
	 * 
	 * @return the width in screen pixels
	 * @see <a href="http://developer.mozilla.org/en/docs/DOM:element.scrollWidth">Mozilla reference</a>
	 * @see <a href="http://msdn.microsoft.com/workshop/author/dhtml/reference/properties/scrollwidth.asp">Microsoft reference</a>
	 */
	public static int getDocumentScrollWidth()
	{
		return s_impl.getDocumentScrollWidth();
	}
	
	/**
	 * Get the height of the document. 
	 * 
	 * @return the height in screen pixels
	 * @see <a href="http://developer.mozilla.org/en/docs/DOM:element.scrollHeight">Mozilla reference</a>
	 * @see <a href="http://msdn.microsoft.com/workshop/author/dhtml/reference/properties/scrollheight.asp">Microsoft reference</a>
	 */
	public static int getDocumentScrollHeight()
	{
		return s_impl.getDocumentScrollHeight();
	}
	
	/**
	 * Gets a code representing a specific keyboard key which was pressed or
	 * released. 
	 * 
	 * @param event a keydown or keyup event
	 * @return a char representing the key pressed
	 * @throws IllegalArgumentException if event is other than onkeydown or
	 *             onkeyup
	 */
	public static char getKeyCode(Event event)
	{
		if ((DOM.eventGetType(event) & (Event.ONKEYDOWN | Event.ONKEYUP)) == 0)
			throw new IllegalArgumentException();
		
		return (char) DOM.eventGetKeyCode(event);
	}
	
	/**
	 * Gets the unicode character from a keypress event. 
	 * Returns 0 for keypresses which do not generate characters (i.e. <code>Delete</code>). 
	 * 
	 * @param event a keypress event
	 * @return a unicode character
	 * @throws IllegalArgumentException if event is other than onkeypress
	 */
	public static char getCharCode(Event event)
	{
		if (DOM.eventGetType(event) != Event.ONKEYPRESS)
			throw new IllegalArgumentException();
		
		return (char) DOM.eventGetKeyCode(event);
	}
	
	/**
	 * Removes all of the element's children. 
	 * 
	 * @param parent a DOM element
	 * @throws IllegalArgumentException if <code>parent</code> is null
	 */
	public static void clean(Element parent)
	{
		if (parent == null)
			throw new IllegalArgumentException();
		
		Element firstChild;
		while((firstChild = DOM.getFirstChild(parent)) != null)
		{
			DOM.removeChild(parent, firstChild);
		}
	}
}
