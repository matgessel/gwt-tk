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

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;

public class Properties implements AssociativeArray
{
	/**
	 * This has to be declared in "Java" to satisfy the hosted shell. 
	 */
	private JavaScriptObject m_array = createImpl();
	
	private native JavaScriptObject createImpl() /*-{
		return new Object();
	}-*/;
	
	/*
	 * (non-Javadoc)
	 * @see asquare.gwt.sb.client.util.AssociativeArray#get(java.lang.String)
	 */
	public Object get(String key)
	{
		if (key == null)
			throw new NullPointerException();
		
		try
		{
			return get0(key);
		}
		catch (IllegalArgumentException e)
		{
			throw new ClassCastException(e.getMessage());
		}
	}
	
	private native Object get0(String key) /*-{
		return this.@asquare.gwt.sb.client.util.Properties::m_array[key] || null;
	}-*/;
	
	/*
	 * (non-Javadoc)
	 * @see asquare.gwt.sb.client.util.AssociativeArray#getString(java.lang.String)
	 */
	public String getString(String key)
	{
		return (String) get(key);
	}
	
	/*
	 * (non-Javadoc)
	 * @see asquare.gwt.sb.client.util.AssociativeArray#getBoolean(java.lang.String)
	 */
	public boolean getBoolean(String key)
	{
		try
		{
			return getBoolean0(key);
		}
		catch (JavaScriptException e)
		{
			throw new ClassCastException(e.getDescription());
		}
	}
	
	private native boolean getBoolean0(String key) /*-{
		return this.@asquare.gwt.sb.client.util.Properties::m_array[key] == true;
	}-*/;
	
	/*
	 * (non-Javadoc)
	 * @see asquare.gwt.sb.client.util.AssociativeArray#set(java.lang.String, java.lang.Object)
	 */
	public native void set(String key, Object value) /*-{
		this.@asquare.gwt.sb.client.util.Properties::m_array[key] = value;
	}-*/;
	
	/*
	 * (non-Javadoc)
	 * @see asquare.gwt.sb.client.util.AssociativeArray#set(java.lang.String, boolean)
	 */
	public native void set(String key, boolean value) /*-{
		this.@asquare.gwt.sb.client.util.Properties::m_array[key] = value;
	}-*/;
	
	/*
	 * (non-Javadoc)
	 * @see asquare.gwt.sb.client.util.AssociativeArray#clear()
	 */
	public native void clear() /*-{
		for(var member in this.@asquare.gwt.sb.client.util.Properties::m_array)
		{
			delete this.@asquare.gwt.sb.client.util.Properties::m_array[member];
		}
	}-*/;
}
