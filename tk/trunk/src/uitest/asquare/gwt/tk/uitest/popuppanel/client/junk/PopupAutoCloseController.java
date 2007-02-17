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
package asquare.gwt.tk.uitest.popuppanel.client.junk;

import asquare.gwt.tk.client.ui.behavior.ControllerAdaptor;
import asquare.gwt.tk.uitest.popuppanel.client.ToolTipModel;

import com.google.gwt.user.client.ui.PopupListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class PopupAutoCloseController extends ControllerAdaptor implements PopupListener
{
	private final ToolTipModel m_model;
	
	public PopupAutoCloseController(ToolTipModel model)
	{
		super(PopupAutoCloseController.class);
		m_model = model;
	}
	
	public void plugIn(Widget widget)
	{
		((PopupPanel) widget).addPopupListener(this);
	}
	
	public void unplug(Widget widget)
	{
		((PopupPanel) widget).removePopupListener(this);
	}

	// PopupListener methods
	public void onPopupClosed(PopupPanel sender, boolean autoClosed)
	{
		m_model.setToolTipWidget(null);
	}
}
