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
package asquare.gwt.tk.client.util;

import java.util.HashMap;

import com.google.gwt.user.client.Command;

/**
 * A map that facilitates looking up which command is associated to a hot key.
 * Supports (char, Command) pairs. Similar to the Swing
 * {@link javax.swing.ActionMap ActionMap}.
 */
public class KeyMap extends HashMap<Character, Command>
{
	private static final long serialVersionUID = -4705847631304175265L;

	/**
	 * Create a mapping between a hot key and a command. To ensure case
	 * consistency across keypress and keydown/keyup events you can convert hot
	 * key characters to upper-case before creating the mapping.
	 * 
	 * @param keyCode a keyboard shortcut
	 * @param command a Command to associate with the hot key
	 */
	public void put(char keyCode, Command command)
	{
		put(new Character(keyCode), command);
	}

	/**
	 * Determines if the map contains a mapping for the specified hot key.
	 * 
	 * @param keyCode a keyboard shortcut
	 * @return <code>true</code> if a mapping exists for <code>keyCode</code>
	 */
	public boolean containsKey(char keyCode)
	{
		return containsKey(new Character(keyCode));
	}

	/**
	 * Get the command to which the specified hot key is mapped.
	 * 
	 * @param keyCode a keyboard shortcut
	 * @return a Command or <code>null</code>
	 */
	public Command get(char keyCode)
	{
		return (Command) get(new Character(keyCode));
	}
	
	/**
	 * Remove a mapping for the specified hot key. Returns the previously mapped
	 * command, if applicable.
	 * 
	 * @param keyCode a keyboard shortcut
	 * @return a Command or <code>null</code>
	 */
	public Command remove(char keyCode)
	{
		return (Command) remove(new Character(keyCode));
	}
}
