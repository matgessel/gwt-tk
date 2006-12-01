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

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

/**
 * This adaptor class provides stub implementations for Controller methods. Most
 * controllers will be interested in {@link #plugIn(Widget)}, {@link #unplug(Widget)}
 * or 
 * {@link asquare.gwt.tk.client.ui.behavior.EventDelegate#onBrowserEvent(Widget, Event) onBrowserEvent()}.
 */
public abstract class ControllerAdaptor extends EventDelegateAdaptor implements Controller
{
	private final Class m_id;
	
	/**
	 * Creates a ControllerAdaptor with the specified id. 
	 * 
	 * @param id the controller id
	 */
	public ControllerAdaptor(Class id)
	{
		this(0, id);
	}
	
	/**
	 * Creates a ControllerAdaptor with the specified event mask and id.
	 * 
	 * @param eventBits a bitmask representing the events this controller is
	 *            interested in
	 * @param id the controller id
	 */
	public ControllerAdaptor(int eventBits, Class id)
	{
		super(eventBits);
		m_id = id;
	}
	
	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.Controller#getId()
	 */
	public Class getId()
	{
		return m_id;
	}
	
	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.Controller#plugIn(com.google.gwt.user.client.ui.Widget)
	 */
	public void plugIn(Widget widget)
	{
	}
	
	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.Controller#unplug(com.google.gwt.user.client.ui.Widget)
	 */
	public void unplug(Widget widget)
	{
	}
}
