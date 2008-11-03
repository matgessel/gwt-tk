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

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

import asquare.gwt.tk.client.ui.behavior.GlassPanelController;
import asquare.gwt.tk.client.util.DomUtil;

public class GlassPanelControllerIE6 implements GlassPanelController
{
	/*
	 * Originally, the implementation was selected via a deferred binding rule.
	 * However, adding an additional rule for strict/quirks mode increased
	 * compile time by 40%.
	 */
	private final GlassPanelController s_impl;
	
	public GlassPanelControllerIE6()
	{
		s_impl = createImpl();
	}
	
	protected GlassPanelController createImpl()
	{
		if (DomUtil.isQuirksMode())
		{
			return new GlassPanelControllerIE6ImplQuirks();
		}
		else
		{
			return new GlassPanelControllerIE6ImplStrict();
		}
	}

	public int getEventBits()
	{
		return s_impl.getEventBits();
	}

	public Class getId()
	{
		return s_impl.getId();
	}

	public void onBrowserEvent(Widget widget, Event event)
	{
		s_impl.onBrowserEvent(widget, event);
	}

	public void plugIn(Widget widget)
	{
		s_impl.plugIn(widget);
	}

	public void unplug(Widget widget)
	{
		s_impl.unplug(widget);
	}
}
