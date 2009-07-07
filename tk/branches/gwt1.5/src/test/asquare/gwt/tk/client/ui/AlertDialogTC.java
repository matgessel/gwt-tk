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
package asquare.gwt.tk.client.ui;

import asquare.gwt.tk.client.Tests;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;

public class AlertDialogTC extends GWTTestCase
{
	public String getModuleName()
	{
		return Tests.getModuleName();
	}
	
	protected void setUpImpl()
	{
	}
	
	public void testCreateError()
	{
		final AlertDialog dialog = AlertDialog.createError(null, "caption", "message");
		dialog.show();
		DeferredCommand.addCommand(new Command()
		{
			public void execute()
			{
				dialog.hide();
				finishTest();
			}
		});
		delayTestFinish(5000);
	}
	
	public void testCreateInfo()
	{
		final AlertDialog dialog = AlertDialog.createInfo(new Command()
		{
			public void execute()
			{
			}
		}, "caption", "message");
		dialog.show();
		DeferredCommand.addCommand(new Command()
		{
			public void execute()
			{
				dialog.hide();
				finishTest();
			}
		});
		delayTestFinish(5000);
	}
	
	public void testCreateWarning()
	{
		final AlertDialog dialog = AlertDialog.createWarning(null, "caption", "message");
		dialog.show();
		DeferredCommand.addCommand(new Command()
		{
			public void execute()
			{
				dialog.hide();
				finishTest();
			}
		});
		delayTestFinish(5000);
	}
}
