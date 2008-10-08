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
package asquare.gwt.sb.client.fw;

import com.google.gwt.user.client.ui.Widget;

import asquare.gwt.tk.client.ui.behavior.Pluggable;

/**
 * Defines a basic lifecycle for update controllers.
 * <p>
 * Update controllers which listen to a shared model must unregister in
 * {@link #disposeImpl()} to prevent a memory leak.
 */
public abstract class UpdateControllerBase implements Pluggable
{
	private boolean m_initialized = false;
	
	public boolean isInitialized()
	{
		return m_initialized;
	}
	
	public void plugIn(Widget widget)
	{
		if (! m_initialized)
		{
			init();
		}
	}
	
	public void unplug(Widget widget)
	{
		if (m_initialized)
		{
			dispose();
		}
	}
	
	/**
	 * This can be called manually to initialize the view before it is attached.
	 */
	public final UpdateControllerBase init()
	{
		if (m_initialized)
			throw new IllegalStateException();
		
		m_initialized = true;
		initImpl();
		return this;
	}
	
	public final UpdateControllerBase dispose()
	{
		if (m_initialized)
		{
			disposeImpl();
			m_initialized = false;
		}
		return this;
	}
	
	/**
	 * Typically
	 * <ul>
	 * <li>register as a listener on the model</li>
	 * <li>intialize the view</li>
	 * </ul>
	 * Often called before the view is attached to the DOM. 
	 */
	protected abstract void initImpl();
	
	/**
	 * Typically
	 * <ul>
	 * <li>unregister as a listener on the model</ul>
	 * </ul>
	 */
	protected abstract void disposeImpl();
}
