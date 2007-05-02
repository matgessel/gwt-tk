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
package asquare.gwt.tk.client.ui.behavior.impl;

import asquare.gwt.tk.client.ui.behavior.*;

import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HasFocus;
import com.google.gwt.user.client.ui.Widget;

public abstract class AbstractTabFocusController extends ControllerAdaptor implements TabFocusController, FocusListener, FocusModelListener
{
	private FocusModel m_focusModel = null;
	
	public AbstractTabFocusController()
	{
		this(0);
	}
	
	protected AbstractTabFocusController(int eventBits)
	{
		super(eventBits, TabFocusController.class);
	}
	
	public FocusModel getModel()
	{
		return m_focusModel;
	}
	
	public void setModel(FocusModel focusModel)
	{
		if (m_focusModel != null)
		{
			for (int i = 0; i < m_focusModel.getSize(); i++)
			{
				m_focusModel.getWidgetAt(i).removeFocusListener(this);
			}
			m_focusModel.removeListener(this);
		}
		if (focusModel != null)
		{
			m_focusModel = focusModel;
			m_focusModel.addListener(this);
			for (int i = 0; i < m_focusModel.getSize(); i++)
			{
				m_focusModel.getWidgetAt(i).addFocusListener(this);
			}
		}
	}
	
	// FocusListener methods
	public void onFocus(Widget sender)
	{
		assert sender instanceof HasFocus;
		m_focusModel.setFocusWidget((HasFocus) sender);
	}
	
	public void onLostFocus(Widget sender)
	{
	}
	
	// FocusModelListener
	public void widgetsAdded(FocusModel model, HasFocus[] added)
	{
		for (int i = 0; i < added.length; i++)
		{
			added[i].addFocusListener(this);
		}
	}
	
	public void widgetsRemoved(FocusModel model, HasFocus[] removed)
	{
		for (int i = 0; i < removed.length; i++)
		{
			removed[i].removeFocusListener(this);
		}
	}
}
