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
 * <p>
 * This controller prevents text selection within it's associated widget.
 * <p>
 * Since this is typically used as a singleton it is not allowed to have
 * children.
 */
public abstract class PreventSelectionController extends EventController
{
	/**
	 * A shared instance of the controller.
	 * 
	 * @deprecated static dependency on {@link GWT#create(Class)} means GWT must
	 *             be bootstrapped to test classes which reference this class
	 */
	public static final PreventSelectionController INSTANCE = (PreventSelectionController) GWT.create(PreventSelectionController.class);
	
	/**
	 * Gets a shared instance of the controller.
	 */
	public static final PreventSelectionController getInstance()
	{
		return INSTANCE;
	}
	
	protected PreventSelectionController(int eventBits)
	{
		super(PreventSelectionController.class, eventBits);
	}
	
	/**
	 * @deprecated not supported
	 * @throws UnsupportedOperationException
	 */
	public void addHandler(EventHandler handler)
	{
		throw new UnsupportedOperationException();
	}
}
