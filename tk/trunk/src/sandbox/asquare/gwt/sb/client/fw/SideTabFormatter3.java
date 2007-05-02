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
 * <li>Background images are positioned by upper left corner, facilitating
 * replacement by AlphaImageLoader in IE. </li>
 * <li>Supports PNG transparency for background images in IE, providing a css
 * width is specified for the top DIV</li>
 * </ul>
 * Cons
 * <ul>
 * </ul>
 */
public class SideTabFormatter3 extends ModelElementFormatterDefault
{
	public SideTabFormatter3()
	{
		this(SideTabFormatter.STYLENAME);
	}
	
	public SideTabFormatter3(String listCellStyleName)
	{
		super(listCellStyleName);
	}
	
	public void formatCell(Element viewElement, Object modelElement, Properties properties)
	{
		DOM.setAttribute(viewElement, "className", buildStyleName(properties));
		final String newHtml = 
			"<div style='position:relative'>" + // enables Content div to be absolutely positioned
				"<div class='bg'>" +
					"<div class='top'>" +
						"<div class='Content'><span style='visibility:hidden;'>" + String.valueOf(modelElement) + "</span></div>" +
					"</div>" +
					"<div class='bottom' style='font-size:0;'></div>" +
				"</div>" +
				"<div class='Content' style='position:absolute;'>" + String.valueOf(modelElement) + "</div>" +
			"</div>";
		DOM.setInnerHTML(viewElement, newHtml);
	}
}
