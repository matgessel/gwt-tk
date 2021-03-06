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
package asquare.gwt.sb.client.util;

import asquare.gwt.tk.client.ui.behavior.event.KeyboardModifiers;

public interface ActionMap
{
	int MODIFIER_ALT = KeyboardModifiers.MODIFIER_ALT;
	int MODIFIER_CTRL = KeyboardModifiers.MODIFIER_CTRL;
	int MODIFIER_META = KeyboardModifiers.MODIFIER_META;
	int MODIFIER_SHIFT = KeyboardModifiers.MODIFIER_SHIFT;
	
	Action get(char keyCode);
	
	Action get(char keyCode, int modifiers);
	
	void put(char keyCode, Action command);
	
	void put(char keyCode, int modifiers, Action command);
}
