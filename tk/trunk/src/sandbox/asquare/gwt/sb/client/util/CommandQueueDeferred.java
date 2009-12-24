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
import com.google.gwt.user.client.DeferredCommand;

/**
 * A queue which pauses before starting command execution. 
 */
public class CommandQueueDeferred extends CommandQueueBase
{
	private boolean m_runScheduled = false;
	
	@Override
	public void add(Command command)
	{
		if (addImpl(command))
		{
			deferRun();
		}
	}
	
	@Override
	protected void scheduleRunIteration()
	{
		run();
	}
	
	protected void deferRun()
	{
//		Debug.println("CommandQueue.deferRun()");
		if (m_runScheduled)
			return;
		
		m_runScheduled = true;
		
		DeferredCommand.addCommand(new Command()
		{
			public void execute()
			{
				m_runScheduled = false;
				run();
			}
		});
	}
}
