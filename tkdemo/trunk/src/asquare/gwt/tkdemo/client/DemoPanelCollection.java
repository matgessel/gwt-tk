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
package asquare.gwt.tkdemo.client;

import asquare.gwt.tkdemo.client.demos.*;
import asquare.gwt.tkdemo.client.ui.AppPanelCollection;
import asquare.gwt.tkdemo.client.ui.WidgetProviderLazy;

import com.google.gwt.user.client.ui.Widget;

public class DemoPanelCollection extends AppPanelCollection
{
	public DemoPanelCollection()
	{
		add("dropdown", "DropDownPanel", new String[] {"DropDownPanel"}, new WidgetProviderLazy()
		{
			protected Widget createWidget()
			{
				return new DropDownPanelPanel();
			}
		});
		add("dialogs", "Dialogs", new String[] {"ModalDialog", "AlertDialog"}, new WidgetProviderLazy()
		{
			protected Widget createWidget()
			{
				return new DialogPanel();
			}
		});
		add("cellpanel", "Cell Panels", new String[] {"RowPanel", "ColumnPanel"}, new WidgetProviderLazy()
		{
			protected Widget createWidget()
			{
				return new ExposedCellPanelPanel();
			}
		});
		add("events", "Events", new String[] {"EventWrapper", "Controller"}, new WidgetProviderLazy()
		{
			protected Widget createWidget()
			{
				return new EventsPanel();
			}
		});
		add("debug", "Debug", new String[] {"Debug"}, new WidgetProviderLazy()
		{
			protected Widget createWidget()
			{
				return new DebugPanel();
			}
		});
		add("misc", "Misc", new String[] {"SimpleHyperLink", "ExternalHyperLink", FocusCycleDemo.NAME, GlassPanelDemo.NAME}, new WidgetProviderLazy()
		{
			protected Widget createWidget()
			{
				return new MiscPanel();
			}
		});
	}
}
