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

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Event;

public interface KeyEvent extends InputEvent
{
	int KEY_DOWN = Event.ONKEYDOWN;
	int KEY_PRESSED = Event.ONKEYPRESS;
	int KEY_UP = Event.ONKEYUP;
	int KEY_EVENTS = Event.ONKEYDOWN | Event.ONKEYPRESS | Event.ONKEYUP;
	
	int KEYCODE_ALT = KeyCodes.KEY_ALT;
	int KEYCODE_BACKSPACE = KeyCodes.KEY_BACKSPACE;
	int KEYCODE_CTRL = KeyCodes.KEY_CTRL;
	int KEYCODE_DELETE = KeyCodes.KEY_DELETE;
	int KEYCODE_DOWN = KeyCodes.KEY_DOWN;
	int KEYCODE_END = KeyCodes.KEY_END;
	int KEYCODE_ENTER = KeyCodes.KEY_ENTER;
	int KEYCODE_ESCAPE = KeyCodes.KEY_ESCAPE;
	int KEYCODE_HOME = KeyCodes.KEY_HOME;
	int KEYCODE_LEFT = KeyCodes.KEY_LEFT;
	int KEYCODE_PAGEDOWN = KeyCodes.KEY_PAGEDOWN;
	int KEYCODE_PAGEUP = KeyCodes.KEY_PAGEUP;
	int KEYCODE_RIGHT = KeyCodes.KEY_RIGHT;
	int KEYCODE_SHIFT = KeyCodes.KEY_SHIFT;
	int KEYCODE_TAB = KeyCodes.KEY_TAB;
	int KEYCODE_UP = KeyCodes.KEY_UP;
	
	/**
	 * Gets a code representing a specific keyboard key which was pressed or
	 * released. Not valid for keypress events. 
	 * 
	 * @return a char representing the key pressed
	 * @throws IllegalStateException if event is other than onkeydown or
	 *             onkeyup
	 */
	int getKeyCode();
	
	/**
	 * Gets the unicode character from a keypress event. 
	 * Returns 0 for keypresses which do not generate characters (i.e. <code>Delete</code>). 
	 * 
	 * @return a unicode character
	 * @throws IllegalStateException if event is other than onkeypress
	 */
	char getKeyPressChar();
}
