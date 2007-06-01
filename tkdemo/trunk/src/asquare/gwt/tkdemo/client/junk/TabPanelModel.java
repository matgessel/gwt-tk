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
package asquare.gwt.tkdemo.client.junk;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.ui.Widget;

import asquare.gwt.sb.client.fw.ListModelBase;
import asquare.gwt.sb.client.fw.ListSelectionModelSingle;
import asquare.gwt.tkdemo.client.ui.WidgetProvider;

public class TabPanelModel extends ListModelBase
{
	private final Vector m_list = new Vector();
	private final ListSelectionModelSingle m_selectionModel;
	
	public TabPanelModel()
	{
		super(new ListSelectionModelSingle());
		m_selectionModel = (ListSelectionModelSingle) getSelectionModel();
	}
	
	public void add(String primaryToken, String uiString, String[] additionalTokens, WidgetProvider tabBody)
	{
		m_list.add(new TabModelEntry(primaryToken, uiString, additionalTokens, tabBody));
	}
	
	public Object get(int index)
	{
		return m_list.get(index);
	}
	
	public int getSelectedIndex()
	{
		return m_selectionModel.getSelectedIndex();
	}
	
	public void setSelectedIndex(int index)
	{
		m_selectionModel.setSelectedIndex(index);
	}
	
	public Widget getTabBody(int index)
	{
		return ((TabModelEntry) m_list.get(index)).m_tabBody.getWidget();
	}
	
	public int getSize()
	{
		return m_list.size();
	}
	
	private static class TabModelEntry
	{
		protected final String m_token;
		protected final String m_uiString;
		protected final WidgetProvider m_tabBody;
		private final List m_additionalTokens;
		
		public TabModelEntry(String primaryToken, String uiString, String[] additionalTokens, WidgetProvider tabBody)
		{
			m_token = primaryToken;
			m_uiString = uiString;
			m_tabBody = tabBody;
			if (additionalTokens != null && additionalTokens.length > 0)
			{
				m_additionalTokens = Arrays.asList(additionalTokens);
			}
			else
			{
				m_additionalTokens = null;
			}
		}
		
		public String getUiString()
		{
			return m_uiString;
		}
		
		public String toString()
		{
			return getUiString();
		}
		
		public boolean hasToken(String token)
		{
			return m_token == token || m_additionalTokens != null && m_additionalTokens.contains(token);
		}
	}
}
