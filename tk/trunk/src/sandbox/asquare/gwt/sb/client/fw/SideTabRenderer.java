/*
 * Copyright 2007 Mat Gessel <mat.gessel@gmail.com>
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
package asquare.gwt.sb.client.fw;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * A formatter for side tabs. 
 * <p>
 * Pros
 * <ul>
 * <li>Simple, clean implementation that performs well</li>
 * </ul>
 * Cons
 * <ul>
 * <li>Does not support PNG transparency for background images in IE (DIVs do not provide
 * "layout" for Alpha and AlphaImageLoader filters)</li>
 * </ul>
 */
public class SideTabRenderer extends ListCellRendererDefault
{
	public static final String STYLENAME = "tk-SideTab";
	
	public SideTabRenderer()
	{
		this(STYLENAME);
	}
	
	public SideTabRenderer(String listCellStyleName)
	{
		super(listCellStyleName);
	}
	
	@Override
	public void renderCell(Element viewElement, Object modelElement, CellProperties properties)
	{
		DOM.setElementProperty(viewElement, "className", buildStyleName(new StringBuilder(), modelElement, properties).toString());
		final String newHtml = 
			"<div class='Content'>" + String.valueOf(modelElement) + "</div>"; 
		DOM.setInnerHTML(viewElement, newHtml);
	}
}
