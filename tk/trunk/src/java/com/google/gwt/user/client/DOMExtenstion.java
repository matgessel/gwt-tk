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
package com.google.gwt.user.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.impl.DOMExtensionImpl;

/**
 * Defines browser dependent functionality not present in the original GWT DOM
 * class.
 * <p>
 * Only includes method which are necessary to patch the DOM. Do not use this
 * class directly as it should be removed when DOM bugs are fixed.
 * 
 * @deprecated
 * @see asquare.gwt.tk.client.util.DomUtil
 */
public class DOMExtenstion
{
	private static final DOMExtensionImpl s_impl = (DOMExtensionImpl) GWT.create(DOMExtensionImpl.class);
	
	/*
	 * Needed here for mouse events
	 */
	public static int getViewportScrollX()
	{
		return s_impl.getViewportScrollX();
	}
	
	/*
	 * Needed here for mouse events
	 */
	public static int getViewportScrollY()
	{
		return s_impl.getViewportScrollY();
	}
	
	/*
	 * Needed here for mouse events
	 */
	public static int eventGetAbsoluteX(Event event)
	{
		return s_impl.eventGetAbsoluteX(event);
	}
	
	/*
	 * Needed here for mouse events
	 */
	public static int eventGetAbsoluteY(Event event)
	{
		return s_impl.eventGetAbsoluteY(event);
	}
}
