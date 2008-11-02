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
package asquare.gwt.tests.test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;

public class StaticMethodAccess extends GWTTestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.tests.UnitTests";
	}
	
	@SuppressWarnings("static-access")
	public void testCallStaticMethodOnInstance()
	{
		final Helper helper = new Helper();
		Helper.init();
		helper.init();
		
		new Command()
		{
			public void execute()
			{
				try
				{
					Helper.init();
					helper.init();
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
		}.execute();
		
		delayTestFinish(500);
		DeferredCommand.addCommand(new Command()
		{
			public void execute()
			{
				try
				{
					Helper.init();
					helper.init();
					finishTest();
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	public static class Helper
	{
		private static int m_field1;
		private static Object m_field2;
		
		public static void init()
		{
			m_field1 = 10;
			m_field2 = new Object();
		}
		
		public static int getField1()
		{
			return m_field1;
		}
		
		public static Object getField2()
		{
			return m_field2;
		}
	}
}
