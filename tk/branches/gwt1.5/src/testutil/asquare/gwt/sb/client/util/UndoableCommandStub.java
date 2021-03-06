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

import asquare.gwt.sb.client.util.CloneableCommand;
import asquare.gwt.sb.client.util.UndoableCommand;

public class UndoableCommandStub extends CommandStub implements UndoableCommand
{
	public boolean m_undo = false;
	public boolean m_redo = false;
	
	public UndoableCommandStub(String id)
	{
		super(id);
	}
	
	public void reset()
	{
		super.reset();
		m_undo = false;
		m_redo = false;
	}
	
	public void undo()
	{
		m_undo = true;
	}

	public String getUIString()
	{
		return toString();
	}

	public void redo()
	{
		m_redo = true;
	}
	
	public CloneableCommand cloneCommand()
	{
		return new UndoableCommandStub(getId());
	}
}
