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

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Command;

public class CommandQueueTC extends GWTTestCase
{
	private CommandQueueListenerStub m_listener;
	private CommandQueue m_queue;
	private UndoableCommandStub m_undo1;
	
	public String getModuleName()
	{
		return "asquare.gwt.sb.SbTC";
	}
	
	protected void setupImpl()
	{
		m_listener = new CommandQueueListenerStub();
		m_queue = new CommandQueue();
		m_queue.addListener(m_listener);
		m_undo1 = new UndoableCommandStub("undo1");
	}
	
	public void testAdd()
	{
		setupImpl();
		
		m_queue.add(m_undo1);
		assertTrue(m_undo1.m_executed);
		assertTrue(m_listener.m_notified);
	}
	
	private static class CommandQueueListenerStub implements CommandQueueListener
	{
		public boolean m_notified = false;

		public void commandExecuted(Command command)
		{
			m_notified = true;
		}
	}
}
