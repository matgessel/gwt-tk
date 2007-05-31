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
package asquare.gwt.tk.test.emulation;

import java.io.Serializable;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.IsSerializable;

public class SerializableTC extends GWTTestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.tk.EmulationTC";
	}
	
	protected void setUpImpl()
	{
	}
	
	public void testSerializable()
	{
		class MyBO implements Serializable, IsSerializable
		{
		}
		Object o = new MyBO();
		assertTrue(o instanceof MyBO);
	}
}
