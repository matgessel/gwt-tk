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

import junit.framework.AssertionFailedError;

import com.google.gwt.junit.client.GWTTestCase;

public class JUnitTC extends GWTTestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.tests.UnitTests";
	}
	
	public void testAssertEqualsFloat()
	{
		assertEquals(0.0f, 0.0f, 0.0f);
		assertEquals(1.1f, 1.1f, 0.0f);
		assertEquals(-1.1f, -1.1f, 0.0f);
		assertEquals(Float.MIN_VALUE, Float.MIN_VALUE, 0.0f);
		assertEquals(Float.MAX_VALUE, Float.MAX_VALUE, 0.0f);
		assertFailsHelper(0.0f, 0.00000000000000000000000000000000000000001f, 0.0f);
		assertFailsHelper(0.0f, 0.0000000000000000001f, 0.0f);
		assertFailsHelper(0.0f, 0.000000001f, 0.0f);
		assertFailsHelper(0.0f, 0.0001f, 0.0f);
		assertFailsHelper(0.0f, 0.1f, 0.0f);
		assertFailsHelper(1.0f, 2.0f, 0.1f);
		assertFailsHelper(2.0f, 1.0f, 0.1f);
		assertFailsHelper(-1.0f, -2.0f, 0.1f);
		assertFailsHelper(-2.0f, -1.0f, 0.1f);
	}
	
	private static void assertFailsHelper(float a, float b, float delta)
	{
		boolean assertionFailed = false;
		try
		{
			assertEquals(a, b, delta);
		}
		catch (AssertionFailedError e)
		{
			// EXPECTED
			assertionFailed = true;
		}
		if (! assertionFailed)
		{
			fail("Expected failure for assertEquals(" + a + ", " + b + ", " + delta + ")");
		}
	}
}
