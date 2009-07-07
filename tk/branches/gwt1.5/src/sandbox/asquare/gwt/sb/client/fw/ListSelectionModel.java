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

public interface ListSelectionModel extends SourcesModelChangeEventComplex
{
	String PROPERTY_ANCHORINDEX = "anchorIndex";
	String PROPERTY_LEADINDEX = "leadIndex";
	
	/**
	 * Add a listener to be notified when indexes are added or removed from this
	 * model.
	 */
	void addListener(ListSelectionModelListener listener);
	
	void removeListener(ListSelectionModelListener listener);
	
	/**
	 * Determines if the model contains any indexes. 
	 *  
	 * @return <code>true</code> if the model is not empty
	 */
	boolean hasSelection();
	
	/**
	 * Get the number of selected indexes. 
	 */
	int getSelectionSize();
	
	/**
	 * Determines if the specified index is selected. 
	 * 
	 * @param index the index to test
	 * @return <code>true</code> if <code>index</code> is selected
	 * @throws IndexOutOfBoundsException if <code>index < 0</code>
	 */
	boolean isIndexSelected(int index);
	
	/**
	 * Get the smallest selected index. 
	 * 
	 * @return the index, or <code>-1</code> if the model is empty
	 */
	int getMinSelectedIndex();
	
	/**
	 * Get the greatest selected index. 
	 * 
	 * @return the index, or <code>-1</code> if the model is empty
	 */
	int getMaxSelectedIndex();
	
	/**
	 * In a multiple selection opertation, the anchor index is the index of the
	 * mouse down.
	 * 
	 * @return the <code>from</code> of the last selection change, or
	 *         <code>-1</code>
	 */
	int getAnchorIndex();
	
	/**
	 * In a multiple selection opertation, the lead index is the index of the
	 * current mouse position.
	 * 
	 * @return the <code>to</code> of the last selection change, or
	 *         <code>-1</code>
	 */
	int getLeadIndex();
	
	/**
	 * Get all of the selected indexes. 
	 * 
	 * @return an array of <code>0</code> or more indexes
	 */
	int[] getSelectedIndices();
	
	/**
	 * Add a range of indexes to the current selection. <code>to</code> may be
	 * less than or equal to <code>from</code>.
	 * <p>
	 * The meaning of <code>from</code> and <code>to</code> will vary by
	 * implementation. Generally, <code>to</code> is associated with the mouse
	 * up and is the value used for single selection mode.
	 */
	void addSelectionRange(int from, int to);
	
	/**
	 * Removes a range of indexes from the current selection. <code>to</code>
	 * may be less than or equal to <code>from</code>.
	 * <p>
	 * The meaning of <code>from</code> and <code>to</code> will vary by
	 * implementation. Generally, <code>to</code> is associated with the mouse
	 * up and is the value used for single selection mode.
	 */
	void removeSelectionRange(int from, int to);
	
	/**
	 * Set the contents of the model to the specified indexes, inclusive.
	 * <code>to</code> may be less than or equal to <code>from</code>.
	 * <p>
	 * The meaning of <code>from</code> and <code>to</code> will vary by
	 * implementation. Generally, <code>to</code> is associated with the mouse
	 * up and is the value used for single selection mode.
	 */
	void setSelectionRange(int from, int to);
	
	/**
	 * Removes all indexes from this model. Has no effect if the model is empty.
	 */
	void clearSelection();
	
	/**
	 * Updates the selection model to reflect the insertion of a contiguous
	 * range of items in the data model.
	 * 
	 * @param index the index items were inserted at
	 * @param count the number of items inserted
	 * @throws IndexOutOfBoundsException if <code>index < 0</code>
	 * @throws IllegalArgumentException if <code>count <= 0</code>
	 */
	void adjustForItemsInserted(int index, int count);
	
	/**
	 * Updates the selection model to reflect the removal of a contiguous range
	 * of items in the data model.
	 * 
	 * @param index the index items were inserted at
	 * @param count the number of items inserted
	 * @throws IndexOutOfBoundsException if <code>index < 0</code>
	 * @throws IllegalArgumentException if <code>count <= 0</code>
	 */
	void adjustForItemsRemoved(int index, int count);
}
