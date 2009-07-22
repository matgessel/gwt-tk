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
package asquare.gwt.tkdemo.client.ui;

import asquare.gwt.tk.client.ui.BasicPanel;
import asquare.gwt.tk.client.ui.behavior.FocusModel;
import asquare.gwt.tk.client.ui.behavior.TabFocusController;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.Widget;

/**
 * A panel which adds its children to a panel-wide focus cycle. The focus will
 * not be allowed to leave the panel via Tab key strokes. This is subject to
 * browser compatibility.
 */
public class FocusCyclePanel extends BasicPanel
{
	private final FocusModel m_focusModel = new FocusModel();
	
	public FocusCyclePanel(String element, String childrenDisplay)
	{
		super(element, childrenDisplay);
		sinkEvents(Event.KEYEVENTS);
		
		TabFocusController focusController = (TabFocusController) GWT.create(TabFocusController.class);
		focusController.setModel(m_focusModel);
		addController(focusController);
	}
    
    public FocusModel getFocusModel()
    {
        return m_focusModel;
    }
	
	public void add(Widget w)
	{
		super.add(w);
		if (w instanceof Focusable)
		{
			m_focusModel.add((Focusable) w);
		}
	}
	
	public boolean remove(Widget w)
	{
		if (! super.remove(w))
			return false;
		
		if (w instanceof Focusable)
		{
			m_focusModel.remove((Focusable) w);
		}
		return true;
	}
}
