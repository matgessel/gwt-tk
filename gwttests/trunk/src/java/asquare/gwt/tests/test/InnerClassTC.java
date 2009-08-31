/*
 * Copyright 2009 Mat Gessel <mat.gessel@gmail.com>
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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Button;

public class InnerClassTC extends GWTTestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.tests.UnitTests";
	}
	
	/**
	 * <a href="http://code.google.com/p/google-web-toolkit/issues/detail?id=4002">Bug 4002</a>
	 */
	public void testAnonymousInNestedConstructor()
	{
		class NewButton extends Button
		{
			private NewButton()
			{
				super("New", new ClickHandler()
				{
					public void onClick(ClickEvent event)
					{
						handleNewClick();
					}
				});
			}
		}
		new NewButton();
	}
	
	private void handleNewClick()
	{
	}
}
