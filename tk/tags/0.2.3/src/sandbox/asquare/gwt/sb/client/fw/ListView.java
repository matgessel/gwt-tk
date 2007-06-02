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
package asquare.gwt.sb.client.fw;

import asquare.gwt.sb.client.util.Properties;

import com.google.gwt.user.client.Element;

/**
 * A list widget which displays arbitrary items. 
 * 
 * <h3>CSS Style Rules</h3>
 * <ul class='css'>
 * <li>.tk-ListView { }</li>
 * <li>.tk-ListItem { an item in the list }</li>
 * </ul>
 */
public interface ListView
{
	public static final String STYLENAME_LIST = "tk-ListView";
	public static final String STYLENAME_LIST_ITEM = "tk-ListItem";
	
	ModelElementFormatter getFormatter();
	
	void setFormatter(ModelElementFormatter formatter);
	
	int getIndexOf(Element eventTarget);
	
	void add(Object item, Properties cellProperties);
	
	void insert(int index, Object item, Properties cellProperties);
	
	void remove(int index);
	
	void clear();
	
	int getSize();
	
	void formatCell(int index, Object item, Properties cellProperties);
}
