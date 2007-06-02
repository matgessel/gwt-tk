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

import com.google.gwt.user.client.ui.Widget;

public interface ToolTipModel
{
	Widget getToolTipWidget();
	
	void setToolTipWidget(Widget widget);

	void setMousePosition(int absX, int absY);
	
	boolean isShowing();
	
	void update();
}
