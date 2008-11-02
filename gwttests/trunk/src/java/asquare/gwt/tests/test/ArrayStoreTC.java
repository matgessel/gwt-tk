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

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.junit.client.GWTTestCase;

public class ArrayStoreTC extends GWTTestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.tests.UnitTests";
	}
	
	/**
	 * Fixed in 1.0.21
	 */
	public void testStoreHashSetInSet()
	{
		Set<?>[][] candidates = new Set[9][9];
		
		for (int row = 0; row < candidates.length; row++)
		{
			for (int col = 0; col < candidates[row].length; col++)
			{
				candidates[row][col] = new HashSet<Object>(9);
			}
		}
	}
}
