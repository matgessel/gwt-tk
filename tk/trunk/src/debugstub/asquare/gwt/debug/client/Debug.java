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

import com.google.gwt.core.client.JavaScriptObject;

/**
 * A Stub implementation of the Debug class. Put this module ahead of the Tk
 * library in your classpath to prevent the Debug class definition from being
 * included in the generated JavaScript. (Verified that this works in GWT
 * 1.0.21)
 */
public class Debug
{
	public static final boolean ENABLED = false;
	
	public static final char DEFAULT_ENABLE_KEY = (char) 27;
	
	public static void init() {}
	
	public static void enableSilently() {}
	
	public static void enable() {}
	
	public static void disable() {}
	
	public static boolean isEnabled()
	{
		return false;
	}
	
	public static void print(String message) {}
	
	public static void println(String message) {}
	
	public static void printToBrowserConsole(String message) {}
	
	public static void prettyPrint(Object o) {}
	
	public static void prettyPrint(JavaScriptObject o) {}
	
	public static void dump(Object o) {}
	
	public static void dump(JavaScriptObject o) {}
	
	public static void installEventTracer(char enableKey, int eventMask) {}
	
	public static void uninstallEventTracer() {}
}
