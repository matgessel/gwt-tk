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
import asquare.gwt.tk.client.ui.behavior.Pluggable;

import com.google.gwt.user.client.ui.Widget;

public class CComponent extends CComposite
{
	private final Object m_model;
	private final Widget m_view;
	
	/*
	 * We keep a reference to the update controller so we can clear listeners.
	 * This is handy the component has a shorter life cycle than the model it
	 * depends on.
	 */
	private Pluggable m_updateController;
	
	protected CComponent()
	{
		this(null, null, null);
	}
	
	protected CComponent(Object model)
	{
		this(model, null, null);
	}
	
	protected CComponent(Widget view)
	{
		this(null, view, null);
	}
	
	public CComponent(Object model, Widget view)
	{
		this(model, view, null);
	}
	
	/**
	 * @param model a model, or <code>null</code> to invoke
	 *            {@link #createModel()}
	 * @param view a view, or <code>null</code> to invoke {@link #createView()}
	 * @param updateController a lifecycle managed controller which listens to
	 *            the model and updates the view
	 */
	public CComponent(Object model, Widget view, Pluggable updateController)
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
	
	public Pluggable getUpdateController()
	{
		return m_updateController;
	}
	
	public void setUpdateController(Pluggable updateController)
	{
		if (m_updateController != null)
		{
			m_updateController.unplug(this);
		}
		m_updateController = updateController;
		if (m_updateController != null)
		{
			m_updateController.plugIn(this);
		}
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
	
	protected void onAttach()
	{
		if (isAttached())
			return;
		
		if (m_updateController != null)
		{
			m_updateController.plugIn(this);
		}
		super.onAttach();
	}
	
	/*
	 * The idea here is to ensure the update controller gets automatically
	 * disposed when the view is disposed.
	 */
	protected void onDetach()
	{
		if(! isAttached())
			return;
		
		try
		{
			super.onDetach();
		}
		finally
		{
			if (m_updateController != null)
			{
				m_updateController.unplug(this);
			}
		}
	}
}
