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

import java.util.ArrayList;

import com.google.gwt.dom.client.*;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.*;

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
		if ((eventMask & Event.ONCONTEXTMENU) != 0)
		{
			result.append("contextmenu ");
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
		if (event == null)
			return String.valueOf(event);
		
        switch(DOM.eventGetType(event))
		{
			case Event.ONKEYDOWN: 
				return "event[type=onKeyDown," + createKeyString(event) + "]";
				
			case Event.ONKEYUP: 
				return "event[type=onKeyUp," + createKeyString(event) + "]";
				
			case Event.ONKEYPRESS: 
				return "event[type=onKeyPress," + createKeyString(event) + "]";
				
			case Event.ONCHANGE: 
				return "event[type=onChange,target=" + getTagName(DOM.eventGetTarget(event)) + "]";
				
			case Event.ONFOCUS: 
				return "event[type=onFocus,target=" + getTagName(DOM.eventGetTarget(event)) + "]";
				
			case Event.ONBLUR: 
				return "event[type=onBlur,target=" + getTagName(DOM.eventGetTarget(event)) + "]";
				
			case Event.ONCLICK: 
				return "event[type=onClick,target=" + getTagName(DOM.eventGetTarget(event)) + "]";
				
			case Event.ONDBLCLICK: 
				return "event[type=onDblClick,target=" + getTagName(DOM.eventGetTarget(event)) + "]";
				
			case Event.ONERROR: 
				return "event[type=onError,target=" + getTagName(DOM.eventGetTarget(event)) + "]";
				
			case Event.ONLOAD: 
				return "event[type=onLoad,target=" + getTagName(DOM.eventGetTarget(event)) + "]";
				
			case Event.ONLOSECAPTURE: 
				return "event[type=onLoseCapture,target=" + getTagName(DOM.eventGetTarget(event)) + "]";
				
			case Event.ONMOUSEDOWN: 
				return "event[type=onMouseDown," + createMouseString(event) + "]";
				
			case Event.ONMOUSEUP: 
				return "event[type=onMouseUp," + createMouseString(event) + "]";
				
			case Event.ONMOUSEOVER: 
				return "event[type=onMouseOver," + createMouseString(event) + "]";
				
			case Event.ONMOUSEOUT: 
				return "event[type=onMouseOut," + createMouseString(event) + "]";
				
			case Event.ONMOUSEMOVE: 
				return "event[type=onMouseMove," + createMouseString(event) + "]";
				
			case Event.ONMOUSEWHEEL: 
				return "event[type=onMouseWheel," + createMouseString(event) + "]";
				
			case Event.ONSCROLL: 
				return "event[type=onScroll,target=" + getTagName(DOM.eventGetTarget(event)) + "]";
				
			default: 
				return DOM.eventToString(event);
		}
	}
	
	private static String createKeyString(Event event)
	{
		return 
		"char=" + (char) DOM.eventGetKeyCode(event) + 
		",keyCode=" + DOM.eventGetKeyCode(event) + 
		",modifiers=" + createModifiersString(event) + 
		",target=" + getTagName(DOM.eventGetTarget(event));
	}
	
	private static String createMouseString(Event event)
	{
		return 
		"clientX=" + DOM.eventGetClientX(event) + 
		",clientY=" + DOM.eventGetClientY(event) + 
		",screenX=" + DOM.eventGetScreenX(event) + 
		",screenY=" + DOM.eventGetScreenY(event) + 
		",buttons=" + DOM.eventGetButton(event) + 
		",modifiers=" + createModifiersString(event) + 
		",target=" + getTagName(DOM.eventGetTarget(event));
	}
	
	private static String createModifiersString(Event event)
	{
		ArrayList<String> modifiers = new ArrayList<String>();
		if (event.getShiftKey())
		{
			modifiers.add("shift");
		}
		if (event.getCtrlKey())
		{
			modifiers.add("ctrl");
		}
		if (event.getAltKey())
		{
			modifiers.add("alt");
		}
		if (event.getMetaKey())
		{
			modifiers.add("meta");
		}
		
		if (modifiers.size() == 0)
			return "()";
		
		StringBuilder result = new StringBuilder("(");
		for (int i = 0, size = modifiers.size(); i < size; i++)
		{
			if (i > 0)
			{
				result.append('|');
			}
			result.append(modifiers.get(i));
		}
		result.append(')');
		return result.toString();
	}
	
	/**
	 * Get the HTML tag name.
	 * 
	 * @param element a DOM element
	 * @return the value of the tagName property, or null if
	 *         <code>element</code> is null
	 */
	public static String getTagName(Element element)
	{
		return (element != null) ? element.getTagName() : null;
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
		String tagName = element.getTagName();
		String id = element.getId();
		String classNames = element.getClassName();
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
			description = ButtonElement.as(element).getValue();
		}
		
		return (description == null) ? tagName : tagName + '[' + description + ']';
	}
}
