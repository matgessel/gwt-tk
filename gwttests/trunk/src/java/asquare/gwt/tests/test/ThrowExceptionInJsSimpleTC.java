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
package asquare.gwt.tests.test;

import com.google.gwt.junit.client.GWTTestCase;

public class ThrowExceptionInJsSimpleTC extends GWTTestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.tests.UnitTests";
	}
	
	@SuppressWarnings("unused")
	private static NullPointerException newNPE()
	{
		return new NullPointerException();
	}
	
	private native void initNativeApi() /*-{
		NativeApi = {};
		NativeApi.newNPE = function()
		{
			return @asquare.gwt.tests.test.ThrowExceptionInJsSimpleTC::newNPE()();
		};
	}-*/;
	
	private native void createAndThrowJavaException() /*-{
		throw NativeApi.newNPE();
	}-*/;
	
	/**
	 * Logs "Malformed JSNI reference 'line'" in hosted mode. Test passes in both modes.
	 * @see <a href="http://code.google.com/p/google-web-toolkit/issues/detail?id=3850">GWT Issue 3850</a>
	 */
	public void testCreateJavaObject()
	{
		initNativeApi();
		try
		{
			createAndThrowJavaException();
			fail("expected NullPointerException");
		}
		catch (NullPointerException e)
		{
			// expected
		}
		catch (RuntimeException e)
		{
			fail("expected NullPointerException");
		}
	}
}
