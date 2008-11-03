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
package asquare.gwt.tk.client.ui.behavior;

import com.google.gwt.user.client.ui.HasFocus;

public interface FocusModelListener
{
	/**
	 * Called when widgets are added to the focus cycle. 
	 * 
	 * @param model the focus model that originated the event
	 * @param added an array of 1 or more widgets
	 */
	void widgetsAdded(FocusModel model, HasFocus[] added);

	/**
	 * Called when widgets are removed from the focus cycle. 
	 * 
	 * @param model the focus model that originated the event
	 * @param removed an array of 1 or more widgets
	 */
	void widgetsRemoved(FocusModel model, HasFocus[] removed);
	
	/**
	 * Called when the focused widget property changes. 
	 * 
	 * @param model the focus model that originated the event
	 * @param previous the widget that was previously focused, or <code>null</code>
	 * @param current the widget that is currently focused, or <code>null</code>
	 */
	void focusChanged(FocusModel model, HasFocus previous, HasFocus current);
}
