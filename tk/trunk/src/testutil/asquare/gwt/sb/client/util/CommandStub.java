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
package asquare.gwt.sb.client.util;

import com.google.gwt.user.client.Command;

public class CommandStub implements Command
{
	private final String m_id;
	
	public boolean m_executed = false;
	
	public CommandStub(String id)
	{
		m_id = id;
	}
	
	public void reset()
	{
		m_executed = true;
	}
	
	public void execute()
	{
		m_executed = true;
	}

    @Override
	public String toString()
	{
		return m_id;
	}
	
	protected String getId()
	{
		return m_id;
	}
}
