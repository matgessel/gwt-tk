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
package asquare.gwt.debug.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.KeyboardListenerCollection;
import com.google.gwt.user.client.ui.UIObject;

/**
 * Contains debugging utility methods. 
 */
public class DebugUtil
{
	/**
	 * Renders an event bitmask as a human readable String. 
	 * 
	 * @param eventMask
	 * @return a String describing the events, or "" if <code>eventMask</code> is <code>0</code>
	 */
	public static String prettyPrintEventMask(int eventMask)
	{
		StringBuffer result = new StringBuffer();
		if ((eventMask & Event.ONBLUR) != 0)
		{
			result.append("blur ");
		}
		if ((eventMask & Event.ONCHANGE) != 0)
		{
			result.append("change ");
		}
		if ((eventMask & Event.ONCLICK) != 0)
		{
			result.append("click ");
		}
		if ((eventMask & Event.ONDBLCLICK) != 0)
		{
			result.append("dblclick ");
		}
		if ((eventMask & Event.ONERROR) != 0)
		{
			result.append("error ");
		}
		if ((eventMask & Event.ONFOCUS) != 0)
		{
			result.append("focus ");
		}
		if ((eventMask & Event.ONKEYDOWN) != 0)
		{
			result.append("keydown ");
		}
		if ((eventMask & Event.ONKEYPRESS) != 0)
		{
			result.append("keypress ");
		}
		if ((eventMask & Event.ONKEYUP) != 0)
		{
			result.append("keyup ");
		}
		if ((eventMask & Event.ONLOAD) != 0)
		{
			result.append("load ");
		}
		if ((eventMask & Event.ONLOSECAPTURE) != 0)
		{
			result.append("losecapture ");
		}
		if ((eventMask & Event.ONMOUSEDOWN) != 0)
		{
			result.append("mousedown ");
		}
		if ((eventMask & Event.ONMOUSEMOVE) != 0)
		{
			result.append("mousemove ");
		}
		if ((eventMask & Event.ONMOUSEOUT) != 0)
		{
			result.append("mouseout ");
		}
		if ((eventMask & Event.ONMOUSEOVER) != 0)
		{
			result.append("mouseover ");
		}
		if ((eventMask & Event.ONMOUSEUP) != 0)
		{
			result.append("mouseup ");
		}
		if ((eventMask & Event.ONSCROLL) != 0)
		{
			result.append("scroll ");
		}
		return result.toString();
	}
	
	/**
	 * Creates a human readable String describing the specified event.
	 * 
	 * @param event an {@link Event}
	 * @return a String describing the <code>event</code>, or
	 *         <code>"null"</code> if <code>event</code> is null
	 */
	public static String prettyPrintEvent(Event event)
	{
		String result = String.valueOf((Object) null);
		
		if (event != null)
		{
	        int eventType = DOM.eventGetType(event);
			switch(eventType)
			{
				case Event.ONKEYDOWN: 
					result = "event[type=onKeyDown," + createKeyString(event) + "]";
					break;
					
				case Event.ONKEYUP: 
					result = "event[type=onKeyUp," + createKeyString(event) + "]";
					break;
					
				case Event.ONKEYPRESS: 
					result = "event[type=onKeyPress," + createKeyString(event) + "]";
					break;
					
				case Event.ONCHANGE: 
					result = "event[type=onChange,element=" + getTagName(DOM.eventGetTarget(event)) + "]";
					break;
					
				case Event.ONFOCUS: 
					result = "event[type=onFocus,element=" + getTagName(DOM.eventGetTarget(event)) + "]";
					break;
					
				case Event.ONBLUR: 
					result = "event[type=onBlur,element=" + getTagName(DOM.eventGetTarget(event)) + "]";
					break;
					
				case Event.ONCLICK: 
					result = "event[type=onClick,element=" + getTagName(DOM.eventGetTarget(event)) + "]";
					break;
					
				case Event.ONDBLCLICK: 
					result = "event[type=onDblClick,element=" + getTagName(DOM.eventGetTarget(event)) + "]";
					break;
					
				case Event.ONERROR: 
					result = "event[type=onError,element=" + getTagName(DOM.eventGetTarget(event)) + "]";
					break;
					
				case Event.ONLOAD: 
					result = "event[type=onLoad,element=" + getTagName(DOM.eventGetTarget(event)) + "]";
					break;
					
				case Event.ONLOSECAPTURE: 
					result = "event[type=onLoseCapture,element=" + getTagName(DOM.eventGetTarget(event)) + "]";
					break;
					
				case Event.ONMOUSEDOWN: 
					result = "event[type=onMouseDown," + createMouseString(event) + "]";
					break;
					
				case Event.ONMOUSEUP: 
					result = "event[type=onMouseUp," + createMouseString(event) + "]";
					break;
					
				case Event.ONMOUSEOVER: 
					result = "event[type=onMouseOver," + createMouseString(event) + "]";
					break;
					
				case Event.ONMOUSEOUT: 
					result = "event[type=onMouseOut," + createMouseString(event) + "]";
					break;
					
				case Event.ONMOUSEMOVE: 
					result = "event[type=onMouseMove," + createMouseString(event) + "]";
					break;
					
				case Event.ONSCROLL: 
					result = "event[type=onScroll,element=" + getTagName(DOM.eventGetTarget(event)) + "]";
					break;
					
				default: 
					result = DOM.eventToString(event);
			}
		}
		
		return result;
	}
	
	private static String createKeyString(Event event)
	{
		return 
		"element=" + getTagName(DOM.eventGetTarget(event)) + 
		",char=" + (char) DOM.eventGetKeyCode(event) + 
		",keyCode=" + (int) DOM.eventGetKeyCode(event) + 
		",modifiers=" + KeyboardListenerCollection.getKeyboardModifiers(event);
	}
	
	private static String createMouseString(Event event)
	{
		return 
		"clientX=" + DOM.eventGetClientX(event) + 
		",clientY=" + DOM.eventGetClientY(event) + 
		",screenX=" + DOM.eventGetScreenX(event) + 
		",screenY=" + DOM.eventGetScreenY(event) + 
		",buttons=" + DOM.eventGetButton(event) + 
		",target=" + getTagName(DOM.eventGetTarget(event));
	}
	
	/**
	 * Get the HTML tag name.
	 * 
	 * @param element a DOM element
	 * @return the value of the tagName property, or null if
	 *         <code>element</code> is null
	 */
	public static native String getTagName(Element element) /*-{
		return element ? element.tagName : null;
	}-*/;
	
	/**
	 * Get the HTML tag name of the specified UIObject's element.
	 * 
	 * @param uio a UIObject
	 * @return the value of the tagName property, or <code>null</code> if
	 *         <code>uio</code> is null.
	 */
	public static String getTagName(UIObject uio)
	{
		return getTagName((uio != null) ? uio.getElement() : null);
	}
	
	/**
	 * Get a short description of the element.
	 * 
	 * @param element a DOM element or <code>null</code>
	 * @return a String or <code>null</code> if <code>element</code> is null
	 */
	public static String prettyPrintElement(Element element)
	{
		if (element == null)
		{
			return String.valueOf(element);
		}
		String tagName = getTagName(element);
		String id = DOM.getAttribute(element, "id");
		String classNames = DOM.getAttribute(element, "className");
		String description = null;
		
		if ("div".equalsIgnoreCase(tagName) || "span".equalsIgnoreCase(tagName))
		{
			if (id != null && ! "".equals(id))
			{
				description = id;
			}
			else if (classNames != null && ! "".equals(classNames))
			{
				description = classNames;
			}
		}
		else if (tagName.equalsIgnoreCase("button"))
		{
			description = DOM.getAttribute(element, "value");
		}
		
		return (description == null) ? tagName : tagName + "[" + description + "]";
	}
}
