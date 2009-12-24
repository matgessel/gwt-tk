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
package asquare.gwt.debug.client;

import java.util.ArrayList;

import asquare.gwt.debug.client.Debug;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.DOM;

public class DebugTC extends GWTTestCase
{
    @Override
	public String getModuleName()
	{
		return "asquare.gwt.debug.Debug";
	}
	
	public void testDump()
	{
		assertTrue(Debug.ENABLED);
		Debug.enable();
		Debug.dump("testPrettyPrint");
		Debug.dump(new Integer(1));
		Debug.dump(new Integer[] {new Integer(0), new Integer(1), new Integer(2)});
		Debug.dump(new ArrayList<Object>());
		Debug.dump(null);
		Debug.dump(DOM.createLabel());
		Debug.dump((Object) DOM.createLabel());
	}
	
	public void testDumpNative()
	{
		assertTrue(Debug.ENABLED);
		Debug.enable();
		_testDumpNative("testPrettyPrint");
		_testDumpNative(new Integer(1));
		_testDumpNative(new Integer[] {new Integer(0), new Integer(1), new Integer(2)});
		_testDumpNative(new ArrayList<Object>());
		_testDumpNative(null);
		_testDumpNative(DOM.createLabel());
		_testDumpNative((Object) DOM.createLabel());
	}
	
	private native void _testDumpNative(Object o) /*-{
		Debug.dump(o);
	}-*/;
	
	private native void _testDumpNative(JavaScriptObject o) /*-{
		Debug.dump(o);
	}-*/;
	
	public void testPrettyPrintNative2()
	{
		assertTrue(Debug.ENABLED);
		Debug.enable();
		_testPrettyPrintNative2();
	}
	
	private native void _testPrettyPrintNative2() /*-{
		Debug.dump(1);
		Debug.dump($wnd);
		Debug.dump("native String");
		Debug.dump(null);
		Debug.dump(undefined);
		Debug.dump(0/10);
	}-*/;
}
