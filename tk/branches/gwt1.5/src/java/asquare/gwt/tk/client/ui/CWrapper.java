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
package asquare.gwt.tk.client.ui;

import java.util.List;

import asquare.gwt.tk.client.ui.behavior.Controller;
import asquare.gwt.tk.client.ui.behavior.ControllerSupport;

import com.google.gwt.user.client.ui.Widget;

/**
 * Wraps an widget to provide event processing via
 * {@link asquare.gwt.tk.client.ui.behavior.Controller controllers}. The
 * wrapped widget will continue to process events and notify listeners as if it
 * was not wrapped.
 */
public class CWrapper extends CComposite implements ControllerSupport
{
	/**
	 * Creates a CWrapper around the specified widget. 
	 */
	public CWrapper(Widget widget)
	{
		this(widget, null);
	}
	
	/**
	 * Creates a CWrapper around the specified widget. Pass a
	 * value for <code>controllers</code> if you wish to avoid controller
	 * creation via {@link #createControllers()}. Otherwise pass null. 
	 * The event target widget will be the wrapper.  
	 * 
	 * @param widget the widget to wrap
	 * @param controllers a list of 0 or more controllers, or <code>null</code>
	 * @throws IllegalArgumentException if <code>widget</code> is null
	 */
	public CWrapper(Widget widget, List<Controller> controllers)
	{
		this(widget, controllers, true);
	}
	
	/**
	 * Creates a CWrapper around the specified widget. Pass a value for
	 * <code>controllers</code> if you wish to avoid controller creation via
	 * {@link #createControllers()}. Otherwise pass null.
	 * 
	 * @param widget the widget to wrap
	 * @param controllers a list of 0 or more controllers, or <code>null</code>
	 * @param eventsTargetWrapper <code>true</code> to pass this wrapper
	 *            object to {@link Controller} methods, <code>false</code> to
	 *            pass the wrapped widget
	 * @throws IllegalArgumentException if <code>widget</code> is null
	 */
	public CWrapper(Widget widget, List<Controller> controllers, boolean eventsTargetWrapper)
	{
		super(eventsTargetWrapper);
		super.initWidget(widget);
		if (controllers != null)
		{
			setControllers(controllers);
		}
	}
	
	/**
	 * Not supported. The widget is set in the constructor and cannot be changed. 
	 * 
	 * @throws UnsupportedOperationException
	 */
	protected final void initWidget(Widget widget) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}
}
