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

public class MouseHandlerWrapper implements MouseHandler
{
	private final MouseHandler m_delegate;
	
	public MouseHandlerWrapper(MouseHandler delegate)
	{
		m_delegate = delegate;
	}
	public int getEventBits()
	{
		return m_delegate.getEventBits();
	}
	
	public MouseHandler getDelegate()
	{
		return m_delegate;
	}
	
	public void onMouseDown(MouseEvent e)
	{
		m_delegate.onMouseDown(e);
	}
	
	public void onMouseMove(MouseEvent e)
	{
		m_delegate.onMouseMove(e);
	}
	
	public void onMouseUp(MouseEvent e)
	{
		m_delegate.onMouseUp(e);
	}
	
	public void onMouseOver(MouseEvent e)
	{
		m_delegate.onMouseOver(e);
	}
	
	public void onMouseOut(MouseEvent e)
	{
		m_delegate.onMouseOut(e);
	}
}
