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

import java.util.List;

import asquare.gwt.tk.client.ui.behavior.Controller;
import asquare.gwt.tk.client.ui.behavior.ControllerSupport;
import asquare.gwt.tk.client.ui.behavior.ControllerSupportDelegate;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class CPopupPanel extends PopupPanel implements ControllerSupport
{
	private final ControllerSupportDelegate m_controllerSupport = new ControllerSupportDelegate(this);
	
	private boolean m_showing = false;

	public CPopupPanel(boolean autoHide)
	{
		super(autoHide);
	}
	
	public Widget addController(Controller controller)
	{
		return m_controllerSupport.addController(controller);
	}

	public Controller getController(Class id)
	{
		return m_controllerSupport.getController(id);
	}
	
	public void show()
	{
		m_showing = true;
		super.show();
	}
	
	public void hide()
	{
		super.hide();
		m_showing = false;
	}
	
	public void onAttach()
	{
		if (isAttached())
			return;
		
		m_controllerSupport.onAttach();
		super.onAttach();
	}
	
	public void onBrowserEvent(Event event)
	{
		m_controllerSupport.onBrowserEvent(event);
	}
	
	public void onDetach()
	{
		if(! isAttached())
			return;
		
		super.onDetach();
		m_controllerSupport.onDetach();
	}
	
	public Widget removeController(Controller controller)
	{
		return m_controllerSupport.removeController(controller);
	}

	public void setControllers(List controllers)
	{
		m_controllerSupport.setControllers(controllers);
	}

	public void sinkEvents(int eventBits)
	{
		m_controllerSupport.sinkEvents(eventBits);
	}

	public void unSinkEvents(int eventBits)
	{
		m_controllerSupport.unSinkEvents(eventBits);
	}

	public boolean isShowing()
	{
		return m_showing;
	}
}
