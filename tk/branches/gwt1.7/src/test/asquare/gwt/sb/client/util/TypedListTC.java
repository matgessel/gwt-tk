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

public class TypedListTC extends TestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.sb.SbTC";
	}

	private TypedList m_list;
	private Object m_t1, m_t2, m_t3;
	private Object m_v1, m_v2, m_v3;
	
	protected void setupImpl()
	{
		m_list = new TypedList();
		m_t1 = "type foo";
		m_t2 = Object.class;
	}
	
	public void testRemove()
	{
		// value identity
		setupImpl();
		m_list.add(m_t1, m_v1);
		m_list.add(m_t1, m_v2);
		m_list.add(m_t1, m_v3);
		m_list.remove(m_t1, m_v2);
		assertList(new Object[] {m_t1, m_t1}, new Object[] {m_v1, m_v3});

		// type equality
		setupImpl();
		m_list.add(m_t1, m_v1);
		m_list.add(m_t2, m_v1);
		m_list.add(m_t3, m_v1);
		m_list.remove(m_t2, m_v1);
		assertList(new Object[] {m_t1, m_t3}, new Object[] {m_v1, m_v1});
	}
	
	private void assertList(Object[] types, Object[] values)
	{
		for (int i = 0, size = m_list.getSize(); i < size; i++)
		{
			assertEquals(String.valueOf(i), types[i], m_list.getType(i));
			assertSame(String.valueOf(i), values[i], m_list.getValue(i));
		}
	}
}
