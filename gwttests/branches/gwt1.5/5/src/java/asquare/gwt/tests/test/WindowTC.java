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

import java.util.ArrayList;

import com.google.gwt.junit.client.GWTTestCase;

public class WindowTC extends GWTTestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.tests.UnitTests";
	}
	
	public void testRemove()
	{
		ArrayList<Character> list = new ArrayList<Character>();
		Character a1 = new Character('a');
		Character b1 = new Character('b');
		Character b2 = new Character('b');
		
		// test equality in removal
		list.add(a1);
		list.add(b1);
		list.add(b2);
		list.remove(b2);
		assertEquals(2, list.size());
		assertSame(a1, list.get(0));
		assertSame(b2, list.get(1));
		list.remove(b1);
		assertEquals(1, list.size());
		assertSame(a1, list.get(0));
	}
}
