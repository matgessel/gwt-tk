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
package asquare.gwt.tests.test;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.junit.client.GWTTestCase;

public class LanguageTC extends GWTTestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.tests.UnitTests";
	}
	
	/**
	 * GWT issue <a href="http://code.google.com/p/google-web-toolkit/issues/detail?id=351">351</a>. 
	 */
	@SuppressWarnings("null")
	public void testDereferenceNullPointer()
	{
		Object o = null;
		
		try
		{
			o.toString();
			fail();
		}
		catch (NullPointerException e)
		{
			// EXPECTED
		}
		catch (JavaScriptException e)
		{
			fail("Expected NullPointerException, got JavaScriptException");
		}
	}
}
