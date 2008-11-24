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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

public abstract class InputEventImpl extends EventBaseImpl implements InputEvent
{
	private static final long serialVersionUID = 1L;

	private boolean m_preventDefault = false;
	
	protected InputEventImpl(InputEventImpl e)
	{
		super(e);
	}
	
	public InputEventImpl(Widget source, Event domEvent, int type, boolean previewPhase)
	{
		super(source, domEvent, type, previewPhase);
	}
	
	public boolean isAltDown()
	{
		return getDomEvent().getAltKey();
	}
	
	public boolean isControlDown()
	{
		return getDomEvent().getCtrlKey();
	}
	
	public boolean isMetaDown()
	{
		return getDomEvent().getMetaKey();
	}
	
	public boolean isShiftDown()
	{
		return getDomEvent().getShiftKey();
	}
	
	public boolean isPreventDefault()
	{
		return m_preventDefault;
	}

	public void preventDefault()
	{
		if (isPreviewPhase())
			throw new IllegalStateException();
		
		m_preventDefault = true;
		DOM.eventPreventDefault(getDomEvent());
	}
}
