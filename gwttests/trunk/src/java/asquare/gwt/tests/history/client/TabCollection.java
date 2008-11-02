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
package asquare.gwt.tests.history.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Widget;

public class TabCollection
{
	private final ArrayList<String> m_tokens = new ArrayList<String>();
	private final ArrayList<String> m_descriptions = new ArrayList<String>();
	private final ArrayList<Widget> m_bodies = new ArrayList<Widget>();
	private int m_size = 0;
	
	public void add(String token, String description, Widget body)
	{
		if (token == null || description == null || body == null)
			throw new IllegalArgumentException();
		
		m_tokens.add(token);
		m_descriptions.add(description);
		m_bodies.add(body);
		m_size++;
	}
	
	public Widget getWidget(int i)
	{
		return (Widget) m_bodies.get(i);
	}

	public String getDescription(int i)
	{
		return (String) m_descriptions.get(i);
	}
	
	public String getToken(int i)
	{
		return (String) m_tokens.get(i);
	}
	
	public int getIndexForToken(String token)
	{
		return m_tokens.indexOf(token);
	}

	public int size()
	{
		return m_size;
	}
}
