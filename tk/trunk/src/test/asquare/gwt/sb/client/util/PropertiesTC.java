/*
 * Copyright 2007 Mat Gessel <mat.gessel@gmail.com>
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

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;

public class PropertiesTC extends GWTTestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.sb.SbTC";
	}

	private static final String KEY_O = "object";
	private static final String KEY_S = "string";
	private static final String KEY_B = "boolean";
	
	private Properties m_properties;
	private Object m_o;
	private String m_s;
	
	protected void setupImpl()
	{
		m_properties = new Properties();
		m_o = new Object()
		{
			public String toString()
			{
				return "Object value";
			}
		};
		m_s = "String value";
	}
	
	public void testGetSet_Object()
	{
		setupImpl();
		
		assertNull(m_properties.get(KEY_O));
		
		m_properties.set(KEY_O, m_o);
		assertSame(m_o, m_properties.get(KEY_O));
		
		// key non-identity
		m_properties.set("a key", m_o);
		assertSame(m_o, m_properties.get("a" + ' ' + "key"));
		
		// runtime type of String
		m_properties.set(KEY_O, (Object) m_s);
		assertSame(m_s, m_properties.get(KEY_O));
		
		// null key
		try
		{
			m_properties.get(null);
			if (! GWT.isScript())
			{
				fail("null key");
			}
		}
		catch (NullPointerException e)
		{
			// EXPECTED
		}
		
		// failed coercion
		m_properties.set(KEY_B, true);
		try
		{
			m_properties.get(KEY_B);
			if (! GWT.isScript())
			{
				fail("failed coercion: boolean -> Object");
			}
		}
		catch (ClassCastException e)
		{
			// EXPECTED
		}
	}
	
	public void testGetSet_String()
	{
		setupImpl();
		
		assertNull(m_properties.getString(KEY_S));
		
		m_properties.set(KEY_S, m_s);
		assertSame(m_s, m_properties.getString(KEY_S));
		
		// failed coercion
		m_properties.set("key3", new Object());
		try
		{
			m_properties.getString("key3");
			if (! GWT.isScript())
			{
				fail("failed coercion: Object -> String");
			}
		}
		catch (ClassCastException e)
		{
			// EXPECTED
		}
		
		// null key
		try
		{
			m_properties.get(null);
			if (! GWT.isScript())
			{
				fail("null key");
			}
		}
		catch (NullPointerException e)
		{
			// EXPECTED
		}
		
		// failed coercion
		m_properties.set("key3", true);
		try
		{
			m_properties.getString("key3");
			if (! GWT.isScript())
			{
				fail("failed coercion: boolean -> String");
			}
		}
		catch (ClassCastException e)
		{
			// EXPECTED
		}
	}
	
	public void testGetSet_boolean()
	{
		setupImpl();
		
		assertFalse(m_properties.getBoolean("key1"));
		
		m_properties.set("key1", true);
		assertTrue(m_properties.getBoolean("key1"));
		
		m_properties.set("key2", true);
		assertTrue(m_properties.getBoolean("key2"));

		m_properties.set("key2", false);
		assertFalse(m_properties.getBoolean("key2"));
		
		// null key
		try
		{
			m_properties.get(null);
			if (! GWT.isScript())
			{
				fail("null key");
			}
		}
		catch (NullPointerException e)
		{
			// EXPECTED
		}
		
		// failed coercion
		m_properties.set("key3", new Object());
		try
		{
			m_properties.getBoolean("key3");
			if (! GWT.isScript())
			{
				fail("failed coercion: Object -> boolean");
			}
		}
		catch (ClassCastException e)
		{
			// EXPECTED
		}
	}
	
	public void testClear()
	{
		setupImpl();
		
		assertNull(m_properties.get(KEY_O));
		assertNull(m_properties.getString(KEY_S));
		assertFalse(m_properties.getBoolean(KEY_B));
		m_properties.set(KEY_O, m_o);
		m_properties.set(KEY_S, m_s);
		m_properties.set(KEY_B, true);
		m_properties.clear();
		assertNull(m_properties.get(KEY_O));
		assertNull(m_properties.getString(KEY_S));
		assertFalse(m_properties.getBoolean(KEY_B));
	}
}
