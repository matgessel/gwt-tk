/*
 * Copyright 2008 Mat Gessel <mat.gessel@gmail.com>
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
package asquare.gwt.tk.client.util.impl;

import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

public class DomUtilImplIE6 extends DomUtilImpl
{
	public void clean(Element element)
	{
		String elementName = DomUtil.getElementName(element);
		if ("TR".equals(elementName) || "TABLE".equalsIgnoreCase(elementName) || "TBODY".equalsIgnoreCase(elementName))
		{
			Element firstChild;
			while((firstChild = DOM.getFirstChild(element)) != null)
			{
				DOM.removeChild(element, firstChild);
			}
		}
		else
		{
			super.clean(element);
		}
	}
}
