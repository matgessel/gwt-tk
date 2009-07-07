/*
 * Copyright 2009 Mat Gessel <mat.gessel@gmail.com>
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

import com.google.gwt.junit.client.*;
import com.google.gwt.user.client.*;

public class CellRendererDefaultTC extends GWTTestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.sb.SbTC";
	}
	
	protected void setupImpl()
	{
	}
	
	public void testEscapeMarkup()
	{
        Element viewElement = DOM.createSpan();
	    String html = "<span style=\"color:red;\">-19.38</span>";
//        String escapedHtml = "&lt;span style=\"color:red;\"&gt;-19.38&lt;/span&gt;";
        String escapedHtml = "&lt;span style=\"color:red;\">-19.38&lt;/span>";
        
	    // escaped
        CellRendererDefault defaultRenderer = new CellRendererDefault();
	    assertEquals(escapedHtml, defaultRenderer.escapeMarkup(html));
	    defaultRenderer.renderCell(viewElement, html, null);
        assertEquals(escapedHtml, viewElement.getInnerHTML());
        
        // non-escaped
        CellRendererDefault nonEscapingRenderer = new CellRendererDefault();
        nonEscapingRenderer.setEscapeMarkup(false);
        nonEscapingRenderer.renderCell(viewElement, html, null);
        assertEquals(html, viewElement.getInnerHTML());
	}
}
