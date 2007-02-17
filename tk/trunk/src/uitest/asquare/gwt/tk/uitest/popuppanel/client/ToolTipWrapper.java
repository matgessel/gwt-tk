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
package asquare.gwt.tk.uitest.popuppanel.client;

import asquare.gwt.tk.client.ui.CWrapper;

import com.google.gwt.user.client.ui.Widget;

public class ToolTipWrapper extends CWrapper implements HasToolTip
{
	private Object m_toolTipContent;
	
	public ToolTipWrapper(Widget widget, Object content)
	{
		super(widget);
		m_toolTipContent = content;
	}
	
	public Object getToolTipContent()
	{
		return m_toolTipContent;
	}
	
	public void setToolTipContent(Object toolTipContent)
	{
		m_toolTipContent = toolTipContent;
	}
}
