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
package asquare.gwt.debug.test;

import java.util.ArrayList;

import asquare.gwt.debug.client.Debug;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.DOM;

public class DebugTC extends GWTTestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.debug.DebugTC";
	}
	
	public void testPrettyPrint()
	{
		assertTrue(Debug.ENABLED);
		Debug.enable();
		Debug.prettyPrint("testPrettyPrint");
		Debug.prettyPrint(new Integer(1));
		Debug.prettyPrint(new Integer[] {new Integer(0), new Integer(1), new Integer(2)});
		Debug.prettyPrint(new ArrayList());
		Debug.prettyPrint(null);
		Debug.prettyPrint(DOM.createLabel());
		Debug.prettyPrint((Object) DOM.createLabel());
	}
	
	public void testPrettyPrintNative()
	{
		assertTrue(Debug.ENABLED);
		Debug.enable();
		_testPrettyPrintNative("testPrettyPrint");
		_testPrettyPrintNative(new Integer(1));
		_testPrettyPrintNative(new Integer[] {new Integer(0), new Integer(1), new Integer(2)});
		_testPrettyPrintNative(new ArrayList());
		_testPrettyPrintNative(null);
		_testPrettyPrintNative(DOM.createLabel());
		_testPrettyPrintNative((Object) DOM.createLabel());
	}
	
	private native void _testPrettyPrintNative(Object o) /*-{
		Debug.prettyPrint(o);
	}-*/;
	
	private native void _testPrettyPrintNative(JavaScriptObject o) /*-{
		Debug.prettyPrint(o);
	}-*/;
	
	public void testPrettyPrintNative2()
	{
		assertTrue(Debug.ENABLED);
		Debug.enable();
		_testPrettyPrintNative2();
	}
	
	private native void _testPrettyPrintNative2() /*-{
		Debug.prettyPrint(1);
		Debug.prettyPrint($wnd);
		Debug.prettyPrint("native String");
		Debug.prettyPrint(null);
		Debug.prettyPrint(undefined);
		Debug.prettyPrint(0/10);
	}-*/;
}
