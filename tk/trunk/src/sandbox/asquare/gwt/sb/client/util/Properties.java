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
import com.google.gwt.core.client.JavaScriptObject;

public class Properties implements AssociativeArray
{
	/**
	 * This has to be declared in "Java" to satisfy the hosted shell. 
	 */
	@SuppressWarnings("unused")
	private JavaScriptObject m_array = JavaScriptObject.createObject();
	
//	private native JavaScriptObject createImpl() /*-{
//		// create a 'native' object so that we can modify it in JSNI
//		return new Object();
//	}-*/;
	
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
		var result = this.@asquare.gwt.sb.client.util.Properties::m_array[key];
		return result != null ? result : null;
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
		if (! GWT.isScript())
		{
			try
			{
				return getBoolean0(key);
			}
			catch (RuntimeException e) // HostedModeException
			{
				throw new ClassCastException(e.getMessage());
			}
		}
		return getBoolean0(key);
	}
	
	private native boolean getBoolean0(String key) /*-{
		var result = this.@asquare.gwt.sb.client.util.Properties::m_array[key];
		return result === undefined ? false : result;
	}-*/;
	
	/*
	 * (non-Javadoc)
	 * @see asquare.gwt.sb.client.util.AssociativeArray#getInt(java.lang.String)
	 */
	public int getInt(String key)
	{
		if (! GWT.isScript())
		{
			try
			{
				return getInt0(key);
			}
			// getting SWTException in hosted mode when coercing Object to int
			catch (RuntimeException e)
			{
				throw new ClassCastException(e.getMessage());
			}
		}
		return getInt0(key);
	}
	
	private native int getInt0(String key) /*-{
		var result = this.@asquare.gwt.sb.client.util.Properties::m_array[key];
		return result != null ? result : -1;
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
	 * @see asquare.gwt.sb.client.util.AssociativeArray#set(java.lang.String, boolean)
	 */
	public native void set(String key, int value) /*-{
		this.@asquare.gwt.sb.client.util.Properties::m_array[key] = value;
	}-*/;
	
//	/*
//	 * (non-Javadoc)
//	 * @see asquare.gwt.sb.client.util.AssociativeArray#getKeySet()
//	 */
//	public String[] getKeySet()
//	{
//		JavaScriptObject result0 = getKeySet0();
//		String[] result = new String[JsUtil.arrayLength(result0)];
//		for (int i = 0; i < result.length; i++)
//		{
//			result[i] = (String) JsUtil.arrayGetObject(result0, i);
//		}
//		return result;
//	}
//	
//	/**
//	 * @return native array of keys
//	 */
//	private native JavaScriptObject getKeySet0() /*-{
//		var result = new Array();
//		
//		return result;
//	}-*/;
//	
//	public int getSize()
//	{
//		Debug.enableSilently();
//		Debug.dump(m_array);
//		return JsUtil.arrayLength(m_array); 
//	};
	
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
	
	public native String toString() /*-{
		var result = "{";
		var i = 0;
		for (var member in this.@asquare.gwt.sb.client.util.Properties::m_array)
		{
			if (i++ > 0)
			{
				result += ",";
			}
			result += member + "=";
			try
			{
				result += this.@asquare.gwt.sb.client.util.Properties::m_array[member];
			}
			catch(e)
			{
				result += "(Exception caught: " + e + ")";
			}
		}
		result += "}";
		return result;
	}-*/;
}
