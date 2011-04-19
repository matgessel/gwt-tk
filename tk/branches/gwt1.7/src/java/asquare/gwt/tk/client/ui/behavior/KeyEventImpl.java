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
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

public class KeyEventImpl extends InputEventImpl implements KeyEvent
{
	private static final long serialVersionUID = 1L;
	
	protected KeyEventImpl(KeyEventImpl e)
	{
		super(e);
	}
	
	/**
	 * @param source the widget which is receiving the mouse event
	 * @param keyEvent a <code>keydown</code>, <code>keypress</code> or <code>keyup</code> event
	 * @param type the DOM event type
	 * @param previewPhase <code>true</code> if the event was caught in event preview
	 */
	public KeyEventImpl(Widget source, Event keyEvent, int type, boolean previewPhase)
	{
		super(source, keyEvent, type, previewPhase);
	}
	
	public static boolean isKeyEvent(Event domEvent)
	{
		return isKeyEvent(DOM.eventGetType(domEvent));
	}
	
	public static boolean isKeyEvent(int domEventType)
	{
		return (domEventType & KEY_EVENTS) != 0;
	}
	
	/**
	 * Gets a code representing a specific keyboard key which was pressed or
	 * released. Not valid for keypress events. 
	 * 
	 * @param event a keydown or keyup event
	 * @return a char representing the key pressed
	 * @throws IllegalArgumentException if event is other than onkeydown or
	 *             onkeyup
	 */
	public static int getKeyCode(Event event)
	{
		if ((DOM.eventGetType(event) & (Event.ONKEYDOWN | Event.ONKEYUP)) == 0)
			throw new IllegalArgumentException();
		
		return DOM.eventGetKeyCode(event);
	}
	
	public int getKeyCode()
	{
		if ((getType() & (Event.ONKEYDOWN | Event.ONKEYUP)) == 0)
			throw new IllegalStateException();
		
		return DOM.eventGetKeyCode(getDomEvent());
	}
	
	/**
	 * Gets the unicode character from a keypress event. 
	 * Returns 0 for keypresses which do not generate characters (i.e. <code>Delete</code>). 
	 * 
	 * @param event a keypress event
	 * @return a unicode character
	 * @throws IllegalArgumentException if event is other than onkeypress
	 */
	public static char getKeyPressChar(Event event)
	{
		if (DOM.eventGetType(event) != Event.ONKEYPRESS)
			throw new IllegalArgumentException();
		
		return (char) DOM.eventGetKeyCode(event);
	}
	
	public char getKeyPressChar()
	{
		if (getType() != Event.ONKEYPRESS)
			throw new IllegalStateException();
		
		return (char) DOM.eventGetKeyCode(getDomEvent());
	}
}
