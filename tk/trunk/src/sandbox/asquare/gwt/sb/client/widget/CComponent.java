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
package asquare.gwt.sb.client.widget;

import asquare.gwt.tk.client.ui.CComposite;

import com.google.gwt.user.client.ui.Widget;

public class CComponent extends CComposite
{
	private final Object m_model;
	private final Widget m_view;
	
	public CComponent(Object model)
	{
		this(model, null);
	}
	
	public CComponent(Widget view)
	{
		this(null, view);
	}
	
	/**
	 * @param model a model, or <code>null</code> to invoke {@link #createModel()}
	 * @param view a view, or <code>null</code> to invoke {@link #createView()}
	 */
	public CComponent(Object model, Widget view)
	{
		super(false);
		m_model = model != null ? model : createModel();
		m_view = view != null ? view : createView();
		initWidget(m_view);
	}
	
	protected Object createModel()
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	protected Widget createView()
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	public Object getModel()
	{
		return m_model;
	}
	
	public Widget getView()
	{
		return m_view;
	}
	
	/*
	 * Override for visibility. 
	 * (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.CComposite#setControllerDisablable(java.lang.Class, boolean)
	 */
	public void setControllerDisablable(Class id, boolean disablable)
	{
		super.setControllerDisablable(id, disablable);
	}
	
	/*
	 * Override for visibility. 
	 * (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.CComposite#isEnabled()
	 */
	public boolean isEnabled()
	{
		return super.isEnabled();
	}
	
	/*
	 * Override for visibility. 
	 * (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.CComposite#setEnabled(boolean)
	 */
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
	}
}
