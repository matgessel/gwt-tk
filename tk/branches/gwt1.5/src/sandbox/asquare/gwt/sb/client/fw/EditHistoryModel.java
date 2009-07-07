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
package asquare.gwt.sb.client.fw;

import java.util.*;

import asquare.gwt.sb.client.util.UndoableCommand;

public class EditHistoryModel
{
	public static final int MAXSIZE_DEFAULT = 50;
	
	private final EditHistoryListenerCollection m_listeners = new EditHistoryListenerCollection();
	private final Stack<UndoableCommand> m_undo = new Stack<UndoableCommand>();
	private final Stack<UndoableCommand> m_redo = new Stack<UndoableCommand>();
	
	private int m_maxSize;
	private boolean m_changed = false;
	
	public EditHistoryModel()
	{
		this(MAXSIZE_DEFAULT);
	}
	
	public EditHistoryModel(int maxSize)
	{
		m_maxSize = maxSize;
	}
	
	public void addListener(EditHistoryListener listener)
	{
		m_listeners.add(listener);
	}
	
	public void removeListener(EditHistoryListener listener)
	{
		m_listeners.remove(listener);
	}
	
	public int getMaxSize()
	{
		return m_maxSize;
	}
	
	public void setMaxSize(int maxSize)
	{
		if (maxSize < 0)
			throw new IllegalArgumentException();
		
		m_maxSize = maxSize;
		
		while (m_maxSize > 0 && m_undo.size() > m_maxSize)
		{
			m_changed = true;
			m_undo.remove(0);
		}
	}
	
	public boolean hasUndo()
	{
		return m_undo.size() > 0;
	}
	
	public boolean hasRedo()
	{
		return m_redo.size() > 0;
	}
	
	public UndoableCommand peekUndo()
	{
		return m_undo.peek();
	}
	
	public UndoableCommand peekRedo()
	{
		return m_redo.peek();
	}
	
	public void push(UndoableCommand command)
	{
		m_redo.clear();
		if (m_undo.size() == m_maxSize)
		{
			m_undo.remove(0);
		}
		m_undo.push(command);
		m_changed = true;
	}
	
	public UndoableCommand undo()
	{
		UndoableCommand result = m_undo.pop();
		m_redo.push(result);
		m_changed = true;
		return result;
	}
	
	public UndoableCommand redo()
	{
		UndoableCommand result = m_redo.pop();
		m_undo.push(result);
		m_changed = true;
		return result;
	}
//	public void undoTo(UndoableCommand command)
//	{
//		if (! m_undo.contains(command))
//			throw new IllegalArgumentException();
//			
//		while(peekUndo() != command)
//		{
//			undoImpl();
//		}
//		
//		m_listeners.fireHistoryChanged(this);
//	}
//	
//	public void redoTo(UndoableCommand command)
//	{
//		if (! m_redo.contains(command))
//			throw new IllegalArgumentException();
//			
//		while(redoImpl() != command)
//		{
//		}
//		
//		m_listeners.fireHistoryChanged(this);
//	}
	
	public List<UndoableCommand> getUndoItems()
	{
		List<UndoableCommand> result = new ArrayList<UndoableCommand>(m_undo);
		Collections.reverse(result);
		return result;
	}
	
	public List<UndoableCommand> getRedoItems()
	{
		List<UndoableCommand> result = new ArrayList<UndoableCommand>(m_redo);
		Collections.reverse(result);
		return result;
	}
	
	public void update()
	{
		if (m_changed)
		{
			m_listeners.fireUpdate(this);
			m_changed = false;
		}
	}
	
	UndoableCommand get(int index)
	{
		if (index == 0)
			throw new IllegalArgumentException();
		
		if (! isValid(index))
			return null;
		
		if (index < 0)
		{
			return m_undo.get(index + m_undo.size());
		}
		else
		{
			return m_redo.get(index - 1);
		}
	}
	
	boolean isValid(int index)
	{
		return (index != 0 && -index <= m_undo.size() && index <= m_redo.size());
	}
}
