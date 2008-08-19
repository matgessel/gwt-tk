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
package asquare.gwt.tk.client.ui.behavior;

import com.google.gwt.core.client.GWT;

/**
 * Since this is typically used as a singleton it is not allowed to have
 * children.
 */
public abstract class PreventDragController extends EventController
{
	private static PreventDragController s_instance = null;
	
	public static PreventDragController getInstance()
	{
		if (s_instance == null)
		{
			s_instance = (PreventDragController) GWT.create(PreventDragController.class);
		}
		return s_instance;
	}
	
	protected PreventDragController(int eventBits)
	{
		super(PreventDragController.class, eventBits);
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
