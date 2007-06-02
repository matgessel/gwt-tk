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
 * <strong>NOTICE</strong>:
 * portions of this file are copied from Google's DOMImplMozillaPatched: 
 */
class DOMImplMozillaPatched extends DOMImplMozilla
{
	public void eventSetKeyCode(Event evt, char key)
	{
		throw new UnsupportedOperationException("Method disabled because it is unsupported on Mozilla");
	}
	
	/**
	 * @see <a href="https://bugzilla.mozilla.org/show_bug.cgi?id=330961">Mozilla
 	 *      bug 330961</a>
 	 * @see <a href="https://bugzilla.mozilla.org/show_bug.cgi?id=208427">Mozilla
 	 *      bug 208427</a>
	 */
	public native boolean isOrHasChild(Element parent, Element child) /*-{
		while (child)
		{
			if (parent.isSameNode(child))
			{
				return true;
			}
			// Workaround for Mozilla bug # 
			try
			{
				child = child.parentNode;
				if (child.nodeType != 1)
				{
					child = null;
				}
			}
			catch (e)
			{
				child = null;
			}
		}
		return false;
	}-*/;
}
