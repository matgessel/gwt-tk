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

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;

public interface MouseEvent extends InputEvent
{
	int MOUSE_DOWN = Event.ONMOUSEDOWN;
	int MOUSE_UP = Event.ONMOUSEUP;
	int MOUSE_CLICK = Event.ONCLICK;
	int MOUSE_DOUBLECLICK = Event.ONDBLCLICK;
	int MOUSE_MOVE = Event.ONMOUSEMOVE;
	int MOUSE_OVER = Event.ONMOUSEOVER;
	int MOUSE_OUT = Event.ONMOUSEOUT;
	int MOUSE_EVENTS = MOUSE_DOWN | MOUSE_UP | MOUSE_CLICK | MOUSE_DOUBLECLICK | MOUSE_MOVE | MOUSE_OVER | MOUSE_OUT;
	
	int getClientX();

	int getClientY();
	
	int getScreenX();
	
	int getScreenY();

	int getWidgetX();

	int getWidgetY();

	Element getFrom();

	Element getTo();

	int getAbsoluteX();

	int getAbsoluteY();

	boolean isShiftDown();
	
	boolean isControlDown();
	
	boolean isMetaDown();
	
	boolean isAltDown();
}
