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
package asquare.gwt.tkdemo.client;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.ui.Widget;

/**
 * A collection which associates each added widget to a history token and a
 * description. 
 */
public class HistoryWidgetCollection
{
	private final Vector m_entries = new Vector();
	
	public void add(String token, String description, Widget body)
	{
		add(token, description, body, null);
	}
	
	public void add(String token, String description, Widget body, String[] additinalTokens)
	{
		if (token == null || description == null || body == null)
			throw new IllegalArgumentException();
		
		m_entries.add(new Entry(token, description, body, additinalTokens));
	}
	
	public void remove(int index)
	{
		m_entries.remove(index);
	}
	
	public Widget getWidget(int index)
	{
		return ((Entry) m_entries.get(index)).m_body;
	}
	
	public String getDescription(int index)
	{
		return ((Entry) m_entries.get(index)).m_description;
	}
	
	public String getToken(int index)
	{
		return ((Entry) m_entries.get(index)).m_token;
	}
	
	public int getIndexForToken(String token)
	{
		for (int index = 0; index < m_entries.size(); index++)
		{
			if (((Entry) m_entries.get(index)).hasToken(token))
			{
				return index;
			}
		}
		return -1;
	}
	
	public int size()
	{
		return m_entries.size();
	}
	
	private static class Entry
	{
		public final String m_token;
		public final String m_description;
		public final Widget m_body;
		private final List m_additionalTokens;
		
		public Entry(String token, String description, Widget body, String[] additinalTokens)
		{
			m_token = token;
			m_description = description;
			m_body = body;
			if (additinalTokens != null && additinalTokens.length > 0)
			{
				m_additionalTokens = Arrays.asList(additinalTokens);
			}
			else
			{
				m_additionalTokens = null;
			}
		}
		
		public boolean hasToken(String token)
		{
			return m_token == token || m_additionalTokens != null && m_additionalTokens.contains(token);
		}
	}
}
