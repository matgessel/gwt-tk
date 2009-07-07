/*
 * Copyright 2008 Mat Gessel <mat.gessel@gmail.com>
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
package asquare.gwt.tests.test;

import com.google.gwt.junit.client.*;
import com.google.gwt.user.client.ui.*;

public class HorizontalPanelTC extends GWTTestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.tests.UnitTests";
	}
	
	public void testSetCellHeight()
	{
		HorizontalPanel panel = new HorizontalPanel();
        Widget w = new Label();
        panel.add(w);
        panel.setCellHeight(w, "1");
        panel.setCellHeight(w, "0");
        panel.setCellHeight(w, "1px");
        panel.setCellHeight(w, "0px");
	}
}
