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
package asquare.gwt.tk.client.ui.behavior;

import com.google.gwt.core.client.GWT;

/**
 * A shared controller which disallows default browser behavior. This controller
 * prevents
 * <ul>
 * <li>text selection via drag
 * <li>text selection via double-click
 * <li>text drag &amp drop
 * <li>image drag &amp drop
 * <li>underlying text selection during drag &amp drop
 * </ul>
 * <p>
 * Since this is typically used as a singleton it is not allowed to have
 * children.
 * <p>
 * TODO: accept a focusable to forcibly focus in standard browsers on mouse
 * down?
 */
public class ControlSurfaceController extends EventController
{
	private static ControlSurfaceController s_instance = null;
	
	/**
	 * Gets a shared instance of the controller.
	 */
	public static final ControlSurfaceController getInstance()
	{
		if (s_instance == null)
		{
			s_instance = (ControlSurfaceController) GWT.create(ControlSurfaceController.class);
		}
		return s_instance;
	}
	
	protected ControlSurfaceController()
	{
		this(0);
	}
	
	protected ControlSurfaceController(int eventBits)
	{
		super(ControlSurfaceController.class, eventBits);
		super.addHandler(PreventDragController.getInstance());
		super.addHandler(PreventSelectionController.getInstance());
	}
	
	/**
	 * @deprecated not supported
	 * @throws UnsupportedOperationException
	 */
	@Override
	public void addHandler(EventHandler handler)
	{
		throw new UnsupportedOperationException();
	}
}
