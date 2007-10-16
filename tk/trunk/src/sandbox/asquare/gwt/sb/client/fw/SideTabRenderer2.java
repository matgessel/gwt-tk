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

import asquare.gwt.sb.client.util.Properties;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * A formatter for side tabs.
 * <p>
 * Pros
 * <ul>
 * <li>Relatively easy to style</li>
 * <li>Separate background for independent styling (translucent background but
 * type is still readable)</li>
 * </ul>
 * Cons
 * <ul>
 * <li>Does not support PNG transparency for background images in IE (DIVs do
 * not provide "layout" for Alpha and AlphaImageLoader filters)</li>
 * </ul>
 */
public class SideTabRenderer2 extends CellRendererDefault
{
	public SideTabRenderer2()
	{
		this(SideTabRenderer.STYLENAME);
	}
	
	public SideTabRenderer2(String listCellStyleName)
	{
		super(listCellStyleName);
	}
	
	public void renderCell(Element viewElement, Object modelElement, Properties properties)
	{
		// enables Content div to be absolutely positioned
		DOM.setStyleAttribute(viewElement, "position", "relative");
		DOM.setAttribute(viewElement, "className", buildStyleName(modelElement, properties));
		final String newHtml = 
			"<div class='bg'>" +
				"<div class='Content'>" +
					"<span style='visibility:hidden;'>" + String.valueOf(modelElement) + "</span>" +
				"</div>" +
			"</div>" + 
			"<div class='Content' style='position:absolute;'>" + String.valueOf(modelElement) + "</div>";
		DOM.setInnerHTML(viewElement, newHtml);
	}
}
