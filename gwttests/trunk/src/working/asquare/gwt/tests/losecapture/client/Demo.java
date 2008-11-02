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
package asquare.gwt.tests.losecapture.client;

import asquare.gwt.debug.client.Debug;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class Demo implements EntryPoint
{
	public void onModuleLoad()
	{
		Debug.enable();
		Debug.installEventTracer((char) 0, ~0 & ~Event.ONMOUSEMOVE);
		RootPanel.get().add(new DragBox().setTop(300));
        RootPanel.get().add(new DragBoxNoSelect().setTop(350));
        RootPanel.get().add(new DragBoxNoSelectCSS().setTop(400));
		RootPanel.get().add(new DragBoxNoSelectFixed().setTop(450));
	}
	
	private static class DragBox extends Widget
	{
		private boolean m_dragging = false;
		private int m_lastX;
		
		public DragBox()
		{
			Element e = DOM.createDiv();
			setElement(e);
			DOM.setInnerText(e, "<-- Drag me off screen and back -->");
			DOM.setStyleAttribute(getElement(), "position", "absolute");
			DOM.setStyleAttribute(e, "border", "solid black 1px");
			DOM.setStyleAttribute(e, "cursor", "move");
			sinkEvents(Event.ONMOUSEDOWN | Event.ONMOUSEMOVE | Event.ONMOUSEUP | Event.ONLOSECAPTURE);
		}
		
		public Widget setTop(int top)
		{
			DOM.setStyleAttribute(getElement(), "top", top + "px");
			return this;
		}
		
		public Widget setLeft(int left)
		{
			DOM.setStyleAttribute(getElement(), "left", left + "px");
			return this;
		}
		
		public void onBrowserEvent(Event event)
		{
			switch(DOM.eventGetType(event))
			{
				case Event.ONMOUSEDOWN: 
					Debug.println("ONMOUSEDOWN");
					m_dragging = true;
					DOM.setCapture(getElement());
					m_lastX = DOM.eventGetScreenX(event);
					break;
					
				case Event.ONMOUSEMOVE: 
//					Debug.println("ONMOUSEMOVE");
					if (m_dragging)
					{
						int currentX = DOM.eventGetScreenX(event);
						int delta = currentX - m_lastX;
						int oldLeft = DOM.getElementPropertyInt(getElement(), "offsetLeft");
						int newLeft = oldLeft + delta;
						setLeft(newLeft);
						m_lastX = currentX;
					}
					break;
					
				case Event.ONMOUSEUP: 
					Debug.println("ONMOUSEUP");
					DOM.releaseCapture(getElement());
					m_dragging = false;
					break;
			}
		}
	}

    private static class DragBoxNoSelect extends DragBox
    {
        public DragBoxNoSelect()
        {
            DOM.setElementProperty(getElement(), "onselectstart", "return false;");
        }
        
        public void onBrowserEvent(Event event)
        {
            super.onBrowserEvent(event);
            if (DOM.eventGetType(event) == Event.ONMOUSEDOWN)
            {
                DOM.eventPreventDefault(event);
            }
        }
    }

    private static class DragBoxNoSelectCSS extends DragBox
    {
        public DragBoxNoSelectCSS()
        {
            DOM.setElementProperty(getElement(), "onselectstart", "return false;");
            DOM.setStyleAttribute(getElement(), "MozUserSelect", "none");
        }
    }

	private static class DragBoxNoSelectFixed extends DragBoxNoSelect
	{
		public DragBoxNoSelectFixed()
		{
			sinkEvents(Event.ONLOSECAPTURE);
		}
		
		public void onBrowserEvent(Event event)
		{
			if (DOM.eventGetType(event) == Event.ONLOSECAPTURE)
			{
				Debug.println("ONLOSECAPTURE");
				Element e = DOM.getCaptureElement();
				if (e != null)
				{
					DOM.releaseCapture(e);
				}
				DOM.setCapture(getElement());
			}
			super.onBrowserEvent(event);
		}
	}
}
