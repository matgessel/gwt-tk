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

import com.google.gwt.user.client.Element;

public class DOMExtensionImpl
{
	public native int getAbsoluteTop(Element element) /*-{
		var top = 0;
		var parent = element.offsetParent;
		while (parent)
		{
			top += element.offsetTop - element.scrollTop;
			element = parent;
			parent = element.offsetParent;
		}
		top += element.offsetTop; // this may not be necessary; can the documentElement have a margin?
		return top;
	}-*/;
	
	public native int getAbsoluteLeft(Element element) /*-{
		var left = 0;
		var parent = element.offsetParent;
		while (parent)
		{
			left += element.offsetLeft - element.scrollLeft;
			element = parent;
			parent = element.offsetParent;
		}
		left += element.offsetLeft; // this may not be necessary; can the documentElement have a margin?
		return left;
	}-*/;
	
	public native int getViewportScrollX() /*-{
		if ($doc.compatMode == "BackCompat")
		{
			return $doc.body.scrollLeft;
		}
		return $doc.documentElement.scrollLeft;
	}-*/;

	public native int getViewportScrollY() /*-{
		if ($doc.compatMode == "BackCompat")
		{
			return $doc.body.scrollTop;
		}
		return $doc.documentElement.scrollTop;
	}-*/;
}
