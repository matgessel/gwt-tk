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
package asquare.gwt.tk.client.util;

import asquare.gwt.debug.client.Debug;
import asquare.gwt.tk.client.Tests;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

public class DomUtilTC extends GWTTestCase
{
	public String getModuleName()
	{
		return Tests.getModuleName();
	}

	public void testAppendText()
	{
		Debug.enable();
		Label validTextContainer = new Label("A");
		assertEquals("A", validTextContainer.getText());
		DomUtil.appendText(validTextContainer.getElement(), "B", false);
		assertEquals("AB", validTextContainer.getText());
		
		// HTML container with inline element
		HTML htmlContainer = new HTML("<PRE>A</PRE>");
		assertEquals("<PRE>A</PRE>", htmlContainer.getHTML());
		assertEquals("A", htmlContainer.getText());
		DomUtil.appendText(htmlContainer.getElement(), "B", false);
		assertEquals("<PRE>AB</PRE>", htmlContainer.getHTML());
		assertEquals("AB", htmlContainer.getText());
		
		// null element
		try
		{
			DomUtil.appendText(null, "B", false);
			fail("expected JavaScript exception because element is null");
		}
		catch (NullPointerException e)
		{
			// EXPECTED
		}
		
		// element with no children
		AbsolutePanel nullTextContainer = new AbsolutePanel();
		try
		{
			DomUtil.appendText(nullTextContainer.getElement(), "B", false);
			fail("expected IllegalArgumentException exception because element has no children");
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		
		// element with first child other than #text 
		AbsolutePanel invalidTextContainer = new AbsolutePanel();
		invalidTextContainer.add(new AbsolutePanel());		
		try
		{
			DomUtil.appendText(invalidTextContainer.getElement(), "B", false);
			fail("expected JavaScript exception because element does not have #text as first child node");
		}
		catch (JavaScriptException e)
		{
			// EXPECTED
		}
	}
}
