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

/**
 * A map where keys Strings and values may be an Object or primitive types. 
 */
public interface AssociativeArray
{
	/**
	 * Get the Object associated with the specified key.
	 * 
	 * @param key a String
	 * @return the Object mapped to <code>key</code> or <code>null</code>
	 * @throws NullPointerException if <code>key</code> is <code>null</code>
	 * @throws ClassCastException if a primitive value is mapped to <code>key</code>
	 */
	Object get(String key);
	
	/**
	 * Get the String associated with the specified key.
	 * 
	 * @param key a String
	 * @return the String mapped to <code>key</code> or <code>null</code>
	 * @throws NullPointerException if <code>key</code> is <code>null</code>
	 * @throws ClassCastException if the value which is mapped to
	 *             <code>key</code> is not an instance of {@link String}
	 */
	String getString(String key);
	
	/**
	 * Get the primitive boolean value associated with the specified key. 
	 * 
	 * @param key a String
	 * @return <code>true</code> if <code>key</code> has been mapped to <code>true</code>, otherwise <code>false</code>
	 * @throws NullPointerException if <code>key</code> is <code>null</code>
	 * @throws ClassCastException if the value which is mapped to
	 *             <code>key</code> is not a primitive boolean value
	 */
	boolean getBoolean(String key);
	
	/**
	 * Get the primitive int value associated with the specified key. 
	 * 
	 * @param key a String
	 * @return the value <code>key</code> has been mapped to <code>true</code>, otherwise <code>-1</code>
	 * @throws NullPointerException if <code>key</code> is <code>null</code>
	 * @throws ClassCastException if the value which is mapped to
	 *             <code>key</code> is not a primitive int value
	 */
	int getInt(String key);
	
	/**
	 * Map an Object to the specified key, replacing any existing mapping for the key. 
	 * 
	 * @param key a String
	 * @param value an Object, or <code>null</code>
	 * @throws NullPointerException if <code>key</code> is <code>null</code>
	 */
	void set(String key, Object value);
	
	/**
	 * Map an boolean value to the specified key, replacing any existing mapping for the key. 
	 * 
	 * @param key a String
	 * @param value <code>true</code> or <code>false</code>
	 * @throws NullPointerException if <code>key</code> is <code>null</code>
	 */
	void set(String key, boolean value);
	
	/**
	 * Map an int value to the specified key, replacing any existing mapping for the key. 
	 * 
	 * @param key a String
	 * @param value <code>true</code> or <code>false</code>
	 * @throws NullPointerException if <code>key</code> is <code>null</code>
	 */
	void set(String key, int value);
	
	/**
	 * Removes all mappings (if any). 
	 */
	void clear();
}
