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
package asquare.gwt.tk.client;

import com.google.gwt.junit.client.GWTTestCase;

public class JUnitShellDocTC extends GWTTestCase
{
	static
	{
		/*
		 * Uncomment the following line to print the usage message. 
		 * (Note: this will break the test run) 
		 */
//		System.setProperty("gwt.args", "-help");
	}
	
	public String getModuleName()
	{
		return "asquare.gwt.tk.TkTc";
	}
	
	public void testPrintJUnitShellDoc()
	{
		// NOOP
	}
}
