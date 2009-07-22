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

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.Widget;

@Deprecated
public class CChangeListenerAdaptor extends ChangeListenerCollection implements Controller
{
	private static final long serialVersionUID = 1L;

	public static void addListener(ControllerSupport widget, ChangeListener listener)
	{
		CChangeListenerAdaptor controller = (CChangeListenerAdaptor) widget.getController(CChangeListenerAdaptor.class);
		if (controller == null)
		{
			controller = new CChangeListenerAdaptor();
			widget.addController(controller);
		}
		controller.add(listener);
	}
	
	public static void removeListener(ControllerSupport widget, ChangeListener listener)
	{
		CChangeListenerAdaptor controller = (CChangeListenerAdaptor) widget.getController(CChangeListenerAdaptor.class);
		if (controller != null)
		{
			controller.remove(listener);
			if (controller.size() == 0)
			{
				widget.removeController(controller);
			}
		}
	}
	
	public Class<? extends Controller> getId()
	{
		return CChangeListenerAdaptor.class;
	}
	
	public int getEventBits()
	{
		return Event.ONCHANGE;
	}
	
	public void plugIn(Widget widget)
	{
	}
	
	public void unplug(Widget widget)
	{
	}
	
	public void onBrowserEvent(Widget widget, Event event)
	{
		fireChange(widget);
	}
}
