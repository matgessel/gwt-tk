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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;

public class DOMExtensionImpl
{
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

	public int eventGetAbsoluteX(Event event)
	{
		return getViewportScrollX() + DOM.eventGetClientX(event);
	}

	public int eventGetAbsoluteY(Event event)
	{
		return getViewportScrollY() + DOM.eventGetClientY(event);
	}
}
