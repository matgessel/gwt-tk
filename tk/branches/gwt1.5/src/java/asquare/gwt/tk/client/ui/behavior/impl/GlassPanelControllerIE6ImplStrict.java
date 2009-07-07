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
package asquare.gwt.tk.client.ui.behavior.impl;

import asquare.gwt.tk.client.util.DomUtil;

public class GlassPanelControllerIE6ImplStrict extends GlassPanelControllerIE6ImplQuirks
{
	public String calculateHeight()
	{
		if (canScrollY())
		{
			return DomUtil.getDocumentScrollHeight() + "px";
		}
		else
		{
			/*
			 * 100% height does not work in Strict mode unless body's height
			 * is 100% and body's top/bottom margins are 0
			 */
			return DomUtil.getViewportHeight() + "px";
		}
	}
	
	protected void updateHeight()
	{
		getWidget().setHeight(calculateHeight());
	}
}
