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

/**
 * A list widget which displays arbitrary items. 
 * 
 * <h3>CSS Style Rules</h3>
 * <ul class='css'>
 * <li>.tk-ListView { }</li>
 * <li>.tk-ListView-disabled { }</li>
 * <li>.tk-ListItem { an item in the list }</li>
 * </ul>
 */
public interface ListView extends CompositeCellView
{
	public static final String STYLENAME_LIST = "tk-ListView";
	public static final String STYLENAME_LIST_ITEM = "tk-ListItem";
	public static final String STYLESUFFIX_LIST_DISABLED = StyleNames.DISABLED;
	
	CellRenderer getRenderer();
	
	void setRenderer(CellRenderer renderer);
	
//	int getIndexOf(Element eventTarget);
	
	boolean isEnabled();
	
	void setEnabled(boolean enabled);
	
	void add(Object modelElement, CellProperties cellProperties);
	
	void insert(IndexedCellId cellId, Object modelElement, CellProperties cellProperties);
	
	void remove(IndexedCellId cellId);
	
	void clear();
	
	int getSize();
	
	// TODO trim(int size);
	
	void prepareElement(CellId cellId, Object modelElement, CellProperties cellProperties);
	
	void renderContent(CellId cellId, Object modelElement, CellProperties cellProperties);
}
