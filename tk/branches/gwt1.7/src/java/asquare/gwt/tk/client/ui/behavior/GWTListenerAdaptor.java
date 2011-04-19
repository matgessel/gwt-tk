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
package asquare.gwt.tk.client.ui.behavior;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.*;

@Deprecated
public class GWTListenerAdaptor extends ControllerSupportDelegate implements ChangeListener, ClickListener, FocusListener, KeyboardListener, LoadListener, MouseListener, MouseWheelListener, ScrollListener
{
	public GWTListenerAdaptor(Widget w)
	{
		super(w);
	}

	public void onChange(Widget sender)
	{
		onBrowserEvent(DOM.eventGetCurrentEvent());
	}

	public void onClick(Widget sender)
	{
		onBrowserEvent(DOM.eventGetCurrentEvent());
	}

	public void onError(Widget sender)
	{
		onBrowserEvent(DOM.eventGetCurrentEvent());
	}

	public void onFocus(Widget sender)
	{
		onBrowserEvent(DOM.eventGetCurrentEvent());
	}

	public void onKeyDown(Widget sender, char keyCode, int modifiers)
	{
		onBrowserEvent(DOM.eventGetCurrentEvent());
	}

	public void onKeyPress(Widget sender, char keyCode, int modifiers)
	{
		onBrowserEvent(DOM.eventGetCurrentEvent());
	}

	public void onKeyUp(Widget sender, char keyCode, int modifiers)
	{
		onBrowserEvent(DOM.eventGetCurrentEvent());
	}

	public void onLoad(Widget sender)
	{
		onBrowserEvent(DOM.eventGetCurrentEvent());
	}

	public void onLostFocus(Widget sender)
	{
		onBrowserEvent(DOM.eventGetCurrentEvent());
	}

	public void onMouseDown(Widget sender, int x, int y)
	{
		onBrowserEvent(DOM.eventGetCurrentEvent());
	}

	public void onMouseEnter(Widget sender)
	{
		onBrowserEvent(DOM.eventGetCurrentEvent());
	}

	public void onMouseLeave(Widget sender)
	{
		onBrowserEvent(DOM.eventGetCurrentEvent());
	}

	public void onMouseMove(Widget sender, int x, int y)
	{
		onBrowserEvent(DOM.eventGetCurrentEvent());
	}

	public void onMouseUp(Widget sender, int x, int y)
	{
		onBrowserEvent(DOM.eventGetCurrentEvent());
	}

	public void onMouseWheel(Widget sender, MouseWheelVelocity velocity)
	{
		onBrowserEvent(DOM.eventGetCurrentEvent());
	}

	public void onScroll(Widget widget, int scrollLeft, int scrollTop)
	{
		onBrowserEvent(DOM.eventGetCurrentEvent());
	}
}
