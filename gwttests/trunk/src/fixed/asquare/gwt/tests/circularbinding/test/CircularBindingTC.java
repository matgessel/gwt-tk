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
package asquare.gwt.tests.circularbinding.test;

import asquare.gwt.tests.circularbinding.client.DOM2;

import com.google.gwt.junit.client.GWTTestCase;

public class CircularBindingTC extends GWTTestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.tests.circularbinding.Demo";
	}
	
	public void testCallNativeSuper()
	{
		// this works
		assertEquals(5, DOM2.eventGetClientX());
		
		// this results in an infinite loop
		assertEquals(5, DOM2.eventGetClientY());
	}
}
