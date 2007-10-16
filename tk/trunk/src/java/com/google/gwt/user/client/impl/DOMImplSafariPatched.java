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
package com.google.gwt.user.client.impl;

import com.google.gwt.user.client.DOMExtenstion;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;

/**
 * Modifications to GWT's Safari implementation go here. 
 */
class DOMImplSafariPatched extends DOMImplSafari
{
	private static final boolean VERSION2 = isSafari2();
	
	public native int getAbsoluteLeft(Element element) /*-{
		var absolutelyPositioned = element.style.position == 'absolute';
		var left = 0;
		var parent = element.offsetParent;
		while (parent && ! (absolutelyPositioned && parent == $doc.body))
		{
			left += element.offsetLeft - element.scrollLeft;
			element = parent;
			parent = element.offsetParent;
		}
		left += element.offsetLeft; // this may not be necessary; can the documentElement have a margin?
		return left;
	}-*/;
	
	public native int getAbsoluteTop(Element element) /*-{
		var absolutelyPositioned = element.style.position == 'absolute';
		var top = 0;
		var parent = element.offsetParent;
		while (parent && ! (absolutelyPositioned && parent == $doc.body))
		{
			top += element.offsetTop - element.scrollTop;
			element = parent;
			parent = element.offsetParent;
		}
		top += element.offsetTop; // this may not be necessary; can the documentElement have a margin?
		return top;
	}-*/;

	public int eventGetClientX(Event evt)
	{
		return eventGetClientX0(evt) - DOMExtenstion.getViewportScrollX();
	}

	private native int eventGetClientX0(Event evt) /*-{
		return evt.clientX;
	}-*/;

	public int eventGetClientY(Event evt)
	{
		return eventGetClientY0(evt) - DOMExtenstion.getViewportScrollY();
	}

	private native int eventGetClientY0(Event evt) /*-{
		return evt.clientY;
	}-*/;
	
	public native int eventGetButton(Event evt) /*-{
		var button = evt.button;
		if (@com.google.gwt.user.client.impl.DOMImplSafariPatched::VERSION2)
		{
			return button || -1;
		}
		else
		{
			if (button == 0)
			{
				return 1;
			}
			else if (button == 1)
			{
				return 4;
			}
			else if (button == 2)
			{
				return 2;
			}
		}
		return -1;
	}-*/;
	
	/**
	 * <em>Note: The GWT 1.4 Shell is treated as Safari 3.</em> 
	 * @see <a href="http://developer.apple.com/internet/safari/uamatrix.html">Safari and WebKit Version Information</a>
	 * @see <a href="http://developer.apple.com/internet/safari/faq.html#anchor2">What is the Safari user-agent string?</a>
	 * @see <a href="http://trac.webkit.org/projects/webkit/wiki/DetectingWebKit">Detecting WebKit with JavaScript</a>
	 * @return <code>true</code> if the browser is a WebKit version previous to Safari 3
	 */
	private static native boolean isSafari2() /*-{
		return RegExp("(WebKit/)(\\d+)").exec(navigator.userAgent)[2] < 420;
	}-*/;
}
