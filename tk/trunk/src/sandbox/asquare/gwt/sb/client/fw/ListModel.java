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

public interface ListModel<E> extends IndexedDataSource<E>, ModelHoverSupport, SourcesModelChangeEventComplex
{
	String ITEM_PROPERTY_VALUE = "listItemValue";
	String ITEM_PROPERTY_HOVER = "listItemHover";
	String ITEM_PROPERTY_SELECTION = "listItemSelection";
	
	void addListener(ListModelListener listener);

	void removeListener(ListModelListener listener);
	
	ListSelectionModel getSelectionModel();
	
	boolean isIndexSelected(int index);
	
	boolean isIndexEnabled(int index);
}
