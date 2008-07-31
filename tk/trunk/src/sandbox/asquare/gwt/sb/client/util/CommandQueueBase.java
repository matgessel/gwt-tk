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

import java.util.ArrayList;

import com.google.gwt.user.client.Command;

/**
 * The subclass is responsible for starting the queue once a command has been
 * added and determining how each run cycle is scheduled.
 */
public abstract class CommandQueueBase implements SourcesCommandQueueEvents
{
	private final ArrayList m_listeners = new ArrayList();
	private final ArrayList m_queue = new ArrayList();
	
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
	
	/**
	 * Get the command at the specified index. <code>0</code> is the next
	 * command to be executed.
	 * 
	 * @param index
	 * @return the command
	 * @throws IndexOutOfBoundsException if
	 *             <code>index < 0 || index > {@link #getSize()}</code>
	 */
	public Command get(int index)
	{
		return (Command) m_queue.get(index);
	}
	
	public Command remove(int index)
	{
		return (Command) m_queue.remove(index);
	}
	
	public void clear()
	{
		m_queue.clear();
	}
	
	public int getSize()
	{
		return m_queue.size();
	}
	
	public abstract void add(Command command);
	
	protected boolean addImpl(Command command)
	{
//		Debug.println("CommandQueue.addImpl(" + command + ')');
		if (! fireBeforeCommandAdded(command))
			return false;
		
		m_queue.add(command);
		fireAfterCommandAdded(command);
		return true;
	}
	
	protected abstract void scheduleRunIteration();
	
	protected void run()
	{
//		Debug.println("CommandQueue.run()");
		executeCommand((Command) m_queue.remove(0));
		if (hasNext())
		{
			scheduleRunIteration();
		}
	}
	
	/**
	 * Notifies listeners and executes the specified command. 
	 * <ul>
	 *   <li>pre: the command has been removed from the queue</li>
	 *   <li>post: the queue may have be modified</li>
	 * </ul>
	 * @param command
	 */
	protected void executeCommand(Command command)
	{
//		Debug.println("CommandQueue.executeCommand(" + command + ")");
		if (! fireBeforeCommandExecuted(command))
			return;
		
		executeCommandImpl(command);
		fireAfterCommandExecuted(command);
	}
	
	protected void executeCommandImpl(Command command)
	{
		command.execute();
	}
	
	protected boolean fireBeforeCommandAdded(Command command)
	{
		if (m_listeners.size() == 0)
			return true;
		
		boolean result = true;
		
		Object[] listeners = m_listeners.toArray();
		for (int i = 0; i < listeners.length; i++)
		{
			result &= ((CommandQueueListener) listeners[i]).beforeCommandAdded(this, command);
		}
		return result;
	}
	
	protected void fireAfterCommandAdded(Command command)
	{
		if (m_listeners.size() == 0)
			return;
		
		Object[] listeners = m_listeners.toArray();
		for (int i = 0; i < listeners.length; i++)
		{
			((CommandQueueListener) listeners[i]).afterCommandAdded(this, command);
		}
	}
	
	protected boolean fireBeforeCommandExecuted(Command command)
	{
		if (m_listeners.size() == 0)
			return true;
		
		boolean result = true;
		
		Object[] listeners = m_listeners.toArray();
		for (int i = 0; i < listeners.length; i++)
		{
			result |= ((CommandQueueListener) listeners[i]).beforeCommandExecuted(this, command);
		}
		return result;
	}
	
	protected void fireAfterCommandExecuted(Command command)
	{
		if (m_listeners.size() == 0)
			return;
		
		Object[] listeners = m_listeners.toArray();
		for (int i = 0; i < listeners.length; i++)
		{
			((CommandQueueListener) listeners[i]).afterCommandExecuted(this, command);
		}
	}
	
	public String toString()
	{
		return "CommandQueueBase" + m_queue.toString();
	}
}
