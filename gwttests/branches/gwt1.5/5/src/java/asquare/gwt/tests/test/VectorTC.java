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

import java.util.Vector;

import com.google.gwt.junit.client.GWTTestCase;

public class VectorTC extends GWTTestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.tests.UnitTests";
	}
	
	public void testRemove()
	{
		Vector<Character> v = new Vector<Character>();
		Character a1 = new Character('a');
		Character b1 = new Character('b');
		Character b2 = new Character('b');
		
		// test equality in removal
		v.add(a1);
		v.add(b1);
		v.add(b2);
		v.remove(b2);
		assertEquals(2, v.size());
		assertSame(a1, v.get(0));
		assertSame(b2, v.get(1));
		v.remove(b1);
		assertEquals(1, v.size());
		assertSame(a1, v.get(0));
	}
}
