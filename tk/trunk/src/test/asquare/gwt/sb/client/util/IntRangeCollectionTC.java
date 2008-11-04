/*
 * Copyright 2008 Mat Gessel <mat.gessel@gmail.com>
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
package asquare.gwt.sb.client.util;

import junit.framework.TestCase;

@SuppressWarnings("unused")
public class IntRangeCollectionTC extends TestCase
{
	private IntRange m_range0_9;
	private IntRange m_range0_4;
	private IntRange m_range5_9;
	private IntRange m_range5_14;
	private IntRange m_range10_19;
	private IntRangeCollection m_collection;
	
	public String getModuleName()
	{
		return "asquare.gwt.sb.SbTC";
	}
	
	protected void setupImpl()
	{
		m_range0_9 = new IntRange(0, 10);
		m_range0_4 = new IntRange(0, 5);
		m_range5_9 = new IntRange(5, 5);
		m_range5_14 = new IntRange(5, 5);
		m_range10_19 = new IntRange(10, 10);
		m_collection = new IntRangeCollection();
	}
	
	public void testToArray()
	{
		// discontiguous ranges
		setupImpl();
		m_collection.add(m_range0_4);
		m_collection.add(m_range10_19);
		int[] result = m_collection.toIntArray();
		assertEquals(15, result.length);
		assertEquals(0, result[0]);
		assertEquals(4, result[4]);
		assertEquals(10, result[5]);
		assertEquals(19, result[14]);
		
		// empty collection
		setupImpl();
		result = m_collection.toIntArray();
		assertNotNull(result);
		assertEquals(0, result.length);
	}
}
