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

import java.util.EventListener;

import com.google.gwt.user.client.Command;

public interface CommandQueueListener extends EventListener
{
	boolean beforeCommandAdded(CommandQueueBase queue, Command command);
	
	void afterCommandAdded(CommandQueueBase queue, Command command);
	
	/**
	 * Called immediately before a command is be executed
	 * 
	 * @param queue the queue executing the command
	 * @param command the command to be executed
	 * @return <code>false</code> to cancel executing the command
	 */
	boolean beforeCommandExecuted(CommandQueueBase queue, Command command);
	
	/**
	 * Called immediately after a command is executed
	 * 
	 * @param queue the queue executing the command
	 * @param command the command which was executed
	 */
	void afterCommandExecuted(CommandQueueBase queue, Command command);
}
