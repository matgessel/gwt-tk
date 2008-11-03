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
package asquare.gwt.tk.client.ui.behavior;

import com.google.gwt.user.client.ui.Widget;

public class EventHandlerWrapper implements EventHandler
{
	private final EventHandler m_delegate;
	
	public EventHandlerWrapper(EventHandler delegate)
	{
		m_delegate = delegate;
	}
	public int getEventBits()
	{
		return m_delegate.getEventBits();
	}
	
	public EventHandler getDelegate()
	{
		return m_delegate;
	}
	
	public void plugIn(Widget widget)
	{
		m_delegate.plugIn(widget);
	}
	
	public void unplug(Widget widget)
	{
		m_delegate.unplug(widget);
	}
	
	public void processFocusGained(FocusEvent e)
	{
		m_delegate.processFocusGained(e);
	}
	
	public void processFocusLost(FocusEvent e)
	{
		m_delegate.processFocusLost(e);
	}
	
	public void processKeyDown(KeyEvent e)
	{
		m_delegate.processKeyDown(e);
	}
	
	public void processKeyPress(KeyEvent e)
	{
		m_delegate.processKeyPress(e);
	}
	
	public void processKeyUp(KeyEvent e)
	{
		m_delegate.processKeyUp(e);
	}
	
	public void processMouseClick(MouseEvent e)
	{
		m_delegate.processMouseClick(e);
	}
	
	public void processMouseDoubleClick(MouseEvent e)
	{
		m_delegate.processMouseDoubleClick(e);
	}
	
	public void processMouseDown(MouseEvent e)
	{
		m_delegate.processMouseDown(e);
	}
	
	public void processMouseMove(MouseEvent e)
	{
		m_delegate.processMouseMove(e);
	}
	
	public void processMouseOut(MouseEvent e)
	{
		m_delegate.processMouseOut(e);
	}
	
	public void processMouseOver(MouseEvent e)
	{
		m_delegate.processMouseOver(e);
	}
	
	public void processMouseUp(MouseEvent e)
	{
		m_delegate.processMouseUp(e);
	}
}
