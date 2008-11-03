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

import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.Widget;

public class FocusEventImpl extends EventBaseImpl implements FocusEvent
{
	private static final long serialVersionUID = 1L;
	
	protected FocusEventImpl(FocusEventImpl e)
	{
		super(e);
	}
	
	/**
	 * @param source the widget which is receiving the mouse event
	 * @param focusEvent a <code>mousedown</code>, <code>mousemove</code> or <code>mouseup</code> event
	 * @param type the DOM event type
	 * @param previewPhase <code>true</code> if the event was caught in event preview
	 */
	public FocusEventImpl(Widget source, Event focusEvent, int type, boolean previewPhase)
	{
		super(source, focusEvent, type, previewPhase);
	}
	
	public static boolean isFocusEvent(Event domEvent)
	{
		return isFocusEvent(DOM.eventGetType(domEvent));
	}
	
	public static boolean isFocusEvent(int domEventType)
	{
		return (domEventType & FOCUS_EVENTS) != 0;
	}
}
