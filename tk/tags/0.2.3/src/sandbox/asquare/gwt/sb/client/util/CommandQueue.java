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

import java.util.Vector;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;

public class CommandQueue
{
	private final Vector m_listeners = new Vector();
	private final Vector m_queue = new Vector();
	
	private boolean m_running = false;
	
	public void addListener(CommandQueueListener listener)
	{
		m_listeners.add(listener);
	}

	public void removeListener(CommandQueueListener listener)
	{
		m_listeners.remove(listener);
	}
	
	public boolean hasNext()
	{
		return m_queue.size() > 0;
	}
	
	public void add(Command command)
	{
//		Debug.println("CommandQueue.add(" + command + ')');
		m_queue.add(command);
		run();
	}
	
	protected void deferRun()
	{
//		Debug.println("CommandQueue.deferRun()");
		if (hasNext())
		{
			DeferredCommand.add(new Command()
			{
				public void execute()
				{
					CommandQueue.this.run();
				}
			});
		}
	}
	
	protected void run()
	{
//		Debug.println("CommandQueue.run()");
		if (m_running)
			return;
		
		m_running = true;
		while (hasNext())
		{
			Command command = (Command) m_queue.remove(0);
			executeCommand(command);
			fireCommandExecuted(command);
		}
		m_running = false;
	}
	
	protected void executeCommand(Command command)
	{
//		Debug.println("CommandQueue.executeCommand(" + command + ")");
		command.execute();
	}
	
	protected void fireCommandExecuted(Command command)
	{
		if (m_listeners.size() == 0)
			return;
		
		Object[] listeners = m_listeners.toArray();
		for (int i = 0; i < listeners.length; i++)
		{
			((CommandQueueListener) listeners[i]).commandExecuted(command);
		}
	}
}
