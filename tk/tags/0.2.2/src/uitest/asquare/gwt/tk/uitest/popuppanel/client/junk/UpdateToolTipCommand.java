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
package asquare.gwt.tk.uitest.popuppanel.client.junk;

import asquare.gwt.tk.uitest.popuppanel.client.ToolTipModel;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;

public class UpdateToolTipCommand implements Command
{
	private final ToolTipModel m_toolTipModel;
	private final HoverModel m_hoverModel;
	private final Widget m_currentWidget;
	
	public UpdateToolTipCommand(ToolTipModel toolTipModel, HoverModel hoverModel)
	{
		m_toolTipModel = toolTipModel;
		m_hoverModel = hoverModel;
		m_currentWidget = hoverModel.getHoverWidget();
	}
	
	public void execute()
	{
		if (m_hoverModel.getHoverWidget() == m_currentWidget)
		{
			// set owner widget (position)
			m_toolTipModel.setToolTipWidget(m_currentWidget);
						
			// set content
			
			// set visible
		}
	}
}
