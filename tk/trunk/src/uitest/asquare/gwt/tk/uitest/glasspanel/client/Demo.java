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
package asquare.gwt.tk.uitest.glasspanel.client;

import asquare.gwt.tk.client.ui.GlassPanel;
import asquare.gwt.tk.client.ui.RowPanel;
import asquare.gwt.tk.client.ui.behavior.ControllerAdaptor;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.*;

public class Demo implements EntryPoint
{
	public void onModuleLoad()
	{
		RowPanel outer = new RowPanel();
		
		Button showGP = new Button("Show GlassPanel", new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				GlassPanel gp = new GlassPanel();
				gp.addController(new HideGlassPanelController());
				gp.show();
			}
		});
		outer.add(showGP);
		
//		Button showOpaqueGP = new Button("Show OpaqueGlassPanel", new ClickListener()
//		{
//			public void onClick(Widget sender)
//			{
//				GlassPanel gp = new GlassPanel();
//				gp.addController(new HideGlassPanelController());
//				gp.addController(new OpaqueController());
//				gp.show();
//			}
//		});
//		outer.add(showOpaqueGP);
		
		RootPanel.get().add(outer);
	}
	
	private static class HideGlassPanelController extends ControllerAdaptor
	{
		public HideGlassPanelController()
		{
			super(HideGlassPanelController.class, Event.ONCLICK);
		}
		
		public void onBrowserEvent(Widget widget, Event event)
		{
			((GlassPanel) widget).hide();
		}
	}
	
//	private static class OpaqueGlassPanel extends GlassPanel
//	{
//		private static final PopupImpl m_impl = (PopupImpl) GWT.create(PopupImpl.class);
//		
//		public OpaqueGlassPanel()
//		{
//			super(m_impl.createElement());
//		}
//		
//		public void show()
//		{
//			super.show();
//			m_impl.onShow(getElement());
//		}
//		
//		public void hide()
//		{
//			m_impl.onHide(getElement());
//			super.hide();
//		}
//	}
//	
//	private static class OpaqueController extends ControllerAdaptor
//	{
//		private static final PopupImpl m_impl = (PopupImpl) GWT.create(PopupImpl.class);
//		
//		public OpaqueController()
//		{
//			super(OpaqueController.class);
//		}
//		
//		public void plugIn(Widget widget)
//		{
//			m_impl.onShow(widget.getElement());
//		}
//		
//		public void unplug(Widget widget)
//		{
//			m_impl.onHide(widget.getElement());
//		}
//	}
}
