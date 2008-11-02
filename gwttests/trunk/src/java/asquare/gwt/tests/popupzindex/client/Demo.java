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
package asquare.gwt.tests.popupzindex.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;

public class Demo implements EntryPoint
{
	public void onModuleLoad()
	{
        new MyDialog("Background").show();
        new MyDialog("Foreground").show();
	}
    
    private static class MyDialog extends DialogBox
    {
        public MyDialog(String captionText)
        {
            super();
            DOM.setStyleAttribute(getElement(), "zIndex", "1000");
            DOM.setStyleAttribute(getElement(), "border", "solid black 1px");
            DOM.setStyleAttribute(getElement(), "cursor", "move");
            DOM.setStyleAttribute(getElement(), "background", "white");
            setText(captionText);
            setWidget(new ListBox());
        }
    }
}
