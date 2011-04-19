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

import java.util.HashMap;


public class DefaultActionMap implements ActionMap
{
	private final HashMap<String, Action> m_map = new HashMap<String, Action>();
	
	public Action get(char keyCode)
	{
		return m_map.get(encodeKey(keyCode, 0));
	}
	
	public Action get(char keyCode, int modifiers)
	{
		return m_map.get(encodeKey(keyCode, modifiers));
	}
	
	public void put(char keyCode, Action command)
	{
		m_map.put(encodeKey(keyCode, 0), command);
	}
	
	public void put(char keyCode, int modifiers, Action command)
	{
		m_map.put(encodeKey(keyCode, modifiers), command);
	}
	
	private String encodeKey(char keyCode, int modifiers)
	{
		/*
		 * Modifiers currently consume 3 bits. Leave an additional bit open
		 * in case a future version of GWT supports meta.
		 */
		return String.valueOf(keyCode << 4 + modifiers);
	}
}
