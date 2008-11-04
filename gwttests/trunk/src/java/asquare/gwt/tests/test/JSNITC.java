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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.junit.client.GWTTestCase;

public class JSNITC extends GWTTestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.tests.UnitTests";
	}
	
	public void testInheritedMethodRef()
	{
		assertEquals("static", getStaticValue(new SubClass()));
		assertEquals("instance", getInstanceValue(new SubClass()));
	}
	
	private static native String getStaticValue(SubClass o) /*-{
		return o.@asquare.gwt.tests.test.JSNITC.SubClass::getStaticValue()();
	}-*/;
	
	private static native String getInstanceValue(SubClass o) /*-{
		return o.@asquare.gwt.tests.test.JSNITC.SubClass::getInstanceValue()();
	}-*/;

	private static class SuperClass
	{
		public static String getStaticValue()
		{
			return "staxic".replace('x', 't');
		}
		
		public String getInstanceValue()
		{
			return "insxance".replace('x', 't');
		}
	}
	
	private static class SubClass extends SuperClass
	{
	}
	
	public void testCoerceJSOField()
	{
		final String KEY1 = "key1";
		final Object VAL1 = "val1";
		AssociativeArray map = new AssociativeArray();
		
		map.set(KEY1, (String) VAL1);
		assertEquals(VAL1, map.get(KEY1));
		assertEquals(VAL1, map.getString(KEY1));
		
		map.set(KEY1, VAL1);
		assertEquals(VAL1, map.get(KEY1));
		
		// BOOM, test fails here in Hosted mode with OLE error
		assertEquals(VAL1, map.getString(KEY1));
	}
	
	public void testStringIdentity()
	{
		final String KEY1 = "key1";
		final String VAL1 = "val1";
		AssociativeArray map = new AssociativeArray();
		
		map.set(KEY1, VAL1);
		assertSame(VAL1, map.get(KEY1));
		assertSame(VAL1, map.getString(KEY1));
	}
	
	private static class AssociativeArray
	{
		JavaScriptObject m_impl;
		
		public AssociativeArray()
		{
			init();
		}
		
		private native void init() /*-{
			this.@asquare.gwt.tests.test.JSNITC.AssociativeArray::m_impl = new Object();
		}-*/;
		
		public native Object get(String key) /*-{
			return this.@asquare.gwt.tests.test.JSNITC.AssociativeArray::m_impl[key] || null;
		}-*/;
		
		public native String getString(String key) /*-{
			return this.@asquare.gwt.tests.test.JSNITC.AssociativeArray::m_impl[key] || null;
		}-*/;
	
		public native void set(String key, Object value) /*-{
			this.@asquare.gwt.tests.test.JSNITC.AssociativeArray::m_impl[key] = value;
		}-*/;
		
		/**
		 * This is the workaround
		 */
		public native void set(String key, String value) /*-{
			this.@asquare.gwt.tests.test.JSNITC.AssociativeArray::m_impl[key] = value;
		}-*/;
	}
}
