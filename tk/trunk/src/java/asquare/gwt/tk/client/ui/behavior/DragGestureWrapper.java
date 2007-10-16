/*
 * Copyright 2006 Mat Gessel <mat.gessel@gmail.com>
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

/**
 * A base class for wrapping a DragGesture. By default, the
 * {@link #start(int, int)}, {@link #step(int, int)} and
 * {@link #finish(int, int)} methods called the delegate implementation.
 */
public class DragGestureWrapper implements DragGesture
{
	private final DragGesture m_delegate;
	
	public DragGestureWrapper(DragGesture delegate)
	{
		m_delegate = delegate;
	}

	public DragGesture getDelegate()
	{
		return m_delegate;
	}
	
	public void start(MouseEvent e)
	{
		m_delegate.start(e);
	}
	
	public void step(DragEvent e)
	{
		m_delegate.step(e);
	}
	
	public void finish()
	{
		m_delegate.finish();
	}
}
