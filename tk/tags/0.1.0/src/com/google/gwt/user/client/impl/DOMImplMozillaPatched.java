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
import com.google.gwt.user.client.Event;

/**
 * Modifications to GWT's Mozilla implementation go here. 
 */
class DOMImplMozillaPatched extends DOMImplMozilla
{
	/**
	 * Workaround for mozilla keyCode character bug
	 * 
	 * @see <a href="http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/b32204b11b0b43d0/">GWT Forums</A>
	 */
	public native char eventGetKeyCode(Event evt) /*-{
		return (evt.keyCode != 0) ? evt.keyCode : evt.charCode;
	}-*/;

	public void eventSetKeyCode(Event evt, char key)
	{
		throw new UnsupportedOperationException("Method disabled because it is unsupported on Mozilla");
	}
	
	/**
	 * According to the CSS specification, values without a unit are
	 * illegal and must be ignored. Firefox correctly ignores these values in
	 * strict rendering mode. The workaround is to automatically add
	 * &quot;px&quot; to any numeric value specified other than 0.
	 * 
	 * @see <a href="http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/cc4a617a9da39655">GWT Forums</a>
	 * @see <a href="http://www.w3.org/TR/CSS21/syndata.html#parsing-errors">CSS
	 *      Rules for handling parsing errors</a>
	 * @see DOMImplSafariPatched
	 */
	public native void setStyleAttribute(Element elem, String attr, String value) /*-{
		if (value != null && value != "" && INTEGER_REGEX.test(value))
		{
			value += "px";
		}
		elem.style[attr] = value;
	}-*/;
	
	private static native void defineIntegerRegex() /*-{
		INTEGER_REGEX = /^\d+$/;
	}-*/;

	static
	{
		defineIntegerRegex();
	}
}
