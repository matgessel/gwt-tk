/*
 * Copyright 2009 Mat Gessel <mat.gessel@gmail.com>
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
package asquare.gwt.tests.mousebutton.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.HandlesAllMouseEvents;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Demo implements EntryPoint
{
	public void onModuleLoad()
	{
		RootPanel.get().add(createDemoPanel());
	}
	
	private Widget createDemoPanel()
	{
		VerticalPanel outer = new VerticalPanel();
		TextBox input = new TextBox();
		input.setText("Click Here");
		TextArea output = new TextArea();
		outer.add(input);
		outer.add(output);
		
		MouseHandler handler = new MouseHandler(output);
		HandlesAllMouseEvents.handle(input, handler);
		return outer;
	}
	
	private static class MouseHandler extends HandlesAllMouseEvents
	{
		private final TextArea m_output;
		
		public MouseHandler(TextArea output)
		{
			m_output = output;
		}
		
		public void onMouseDown(MouseDownEvent event) { onMouseEvent(event); }
		public void onMouseUp(MouseUpEvent event) { onMouseEvent(event); }
		public void onMouseMove(MouseMoveEvent event) { }
		public void onMouseOut(MouseOutEvent event) { }
		public void onMouseOver(MouseOverEvent event) { }
		public void onMouseWheel(MouseWheelEvent event) { }
		
		private void onMouseEvent(MouseEvent<?> event)
		{
			event.preventDefault();
			String button;
			switch(event.getNativeButton())
			{
				case NativeEvent.BUTTON_LEFT: 
					button = "left(" + NativeEvent.BUTTON_LEFT + ")";
					break;
					
				case NativeEvent.BUTTON_MIDDLE: 
					button = "middle(" + NativeEvent.BUTTON_MIDDLE + ")";
					break;
					
				case NativeEvent.BUTTON_RIGHT: 
					button = "right(" + NativeEvent.BUTTON_RIGHT + ")";
					break;
					
				default: 
					button = "unknown";
					break;
			}
			m_output.setText(button);
		}
	}
}
