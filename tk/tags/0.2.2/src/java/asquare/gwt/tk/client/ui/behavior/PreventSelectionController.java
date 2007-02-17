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

import com.google.gwt.core.client.GWT;

/**
 * A marker interface for the prevent selection controller. Used to instantiate
 * the controller via deferred binding.
 * <p>This controller prevents text selection within it's associated widget. 
 */
public interface PreventSelectionController extends Controller
{
	/**
	 * A shared instance of the controller. 
	 */
	PreventSelectionController INSTANCE = (PreventSelectionController) GWT.create(PreventSelectionController.class);
}
