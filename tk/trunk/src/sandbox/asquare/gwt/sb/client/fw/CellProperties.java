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
package asquare.gwt.sb.client.fw;

/**
 * {@link CellProperties} is a parameter object which is used to configure the
 * {@link CellRenderer}.
 */
public interface CellProperties
{
	String SELECTED = "selected";
	String HOVER = "hover";
	String ACTIVE = "active";
	String DISABLED = "disabled";
	
	boolean getBoolean(String name);
	
	int getInt(String name);
	
	String getString(String name);
	
	/**
	 * @param <T> the expected type of the property
	 * @throws ClassCastException if the property is not type compatible with
	 *             <code>&lt;T&gt;</code>
	 */
	<T> T getObject(String name);
	
	boolean isSelected();
	
	boolean isHover();
	
	boolean isActive();
	
	boolean isDisabled();
}
