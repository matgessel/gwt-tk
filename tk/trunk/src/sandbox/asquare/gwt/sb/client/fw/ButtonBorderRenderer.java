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
 * A renderer which divides the element into a 3x3 grid. The grid can be styled
 * to accomodate the size to the content. 
 * <p>
 * Pros
 * <ul>
 * <li>Transparent PNG background images work in IE (table cells provide
 * "layout" for Alpha and AlphaImageLoader filters)</li>
 * <li>Separate background. Background can be made translucent without
 * affecting foreground text readability.</li>
 * <li>Tolerates table layouts where a neigbor TD is 100% wide (prevents automatic cell collapse).</li>
 * </ul>
 * Cons
 * <ul>
 * <li>Changing element style is relatively slow...especially noticable with hovers styles. </li>
 * </ul>
 */
public class ButtonBorderRenderer extends CellRendererDefault
{
	public static final String STYLENAME = "tk-Button";
	
	public ButtonBorderRenderer()
	{
		this(STYLENAME);
	}
	
	public ButtonBorderRenderer(String listCellStyleName)
	{
		super(listCellStyleName);
	}
	
	public void renderCell(Element viewElement, Object modelElement, Properties properties)
	{
		DOM.setElementProperty(viewElement, "className", buildStyleName(modelElement, properties));
		
		// enables Content div to be absolutely positioned
		DOM.setStyleAttribute(viewElement, "position", "relative");
//		DOM.setStyleAttribute(viewElement, "width", "100%");
//		DOM.setStyleAttribute(viewElement, "height", "100%");
		String valueString = String.valueOf(modelElement);
		final String newHtml = 
			"<table class='bg' style='empty-cells: show;' cellSpacing='0' cellPadding='0'>" + 
				"<tr>" + 
					"<td><div class='nw' style='font-size:0;'></div></td>" + 
					"<td class='n'/>" + 
					"<td><div class='ne' style='font-size:0;'></div></td>" + 
				"</tr>" + 
				"<tr>" + 
					"<td class='w'/>" + 
					"<td class='center'><span style='visibility:hidden;'>" + valueString + "</span></td>" + 
					"<td class='e'/>" + 
				"</tr>" + 
				"<tr>" + 
					"<td><div class='sw' style='font-size:0;'></div></td>" + 
					"<td class='s'/>" + 
					"<td><div class='se' style='font-size:0;'></div></td>" + 
				"</tr>" + 
			"</table>" + 
			"<div class='Content' style='position:absolute;'>" + valueString + "</div>";
//		if (! DOM.getInnerHTML(itemElement).equals(newHtml))
		{
			DOM.setInnerHTML(viewElement, newHtml);
		}
	}
}
