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
package asquare.gwt.tkdemo.client.ui;

import java.util.*;

import com.google.gwt.user.client.ui.*;

/**
 * A collection which associates each added widget to a history token and a
 * UI String. 
 */
public class AppPanelCollection
{
	private final ArrayList m_entries = new ArrayList();
	
	public void add(String primaryToken, String uiString, WidgetProvider tabBody)
	{
		add(primaryToken, uiString, null, tabBody);
	}
	
	public void add(String primaryToken, String uiString, String[] additionalTokens, WidgetProvider tabBody)
	{
		if (primaryToken == null || uiString == null || tabBody == null)
			throw new IllegalArgumentException();
		
		m_entries.add(new Entry(primaryToken, uiString, tabBody, additionalTokens));
	}
	
	public void remove(int index)
	{
		m_entries.remove(index);
	}
	
	public Widget getWidget(int index)
	{
		return ((Entry) m_entries.get(index)).m_widgetProvider.getWidget();
	}
	
	public String getUIString(int index)
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
	
	public int getSize()
	{
		return m_entries.size();
	}
	
	private static class Entry
	{
		public final String m_token;
		public final String m_description;
		public final WidgetProvider m_widgetProvider;
		private final List m_additionalTokens;
		
		public Entry(String token, String description, WidgetProvider tabBody, String[] additionalTokens)
		{
			m_token = token;
			m_description = description;
			m_widgetProvider = tabBody;
			if (additionalTokens != null && additionalTokens.length > 0)
			{
				m_additionalTokens = Arrays.asList(additionalTokens);
			}
			else
			{
				m_additionalTokens = null;
			}
		}
		
		public boolean hasToken(String token)
		{
			return m_token.equals(token) || m_additionalTokens != null && m_additionalTokens.contains(token);
		}
	}
}
