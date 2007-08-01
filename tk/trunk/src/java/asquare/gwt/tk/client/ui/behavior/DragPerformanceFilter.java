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

import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;

public class DragPerformanceFilter extends DragGestureWrapper
{
	private static final StepStrategy s_defaultImpl = (StepStrategy) GWT.create(StepStrategy.class);
	
	private StepStrategy m_stepStrategy = s_defaultImpl;
	
	public DragPerformanceFilter(DragGesture delegate)
	{
		super(delegate);
	}
	
	public StepStrategy getStepStrategy()
	{
		return m_stepStrategy;
	}

	public void setStepStrategy(StepStrategy impl)
	{
		m_stepStrategy = impl;
	}
	
	public void step(int x, int y)
	{
		getStepStrategy().step(getDelegate(), x, y);
	}
	
	public static abstract class StepStrategy
	{
		protected abstract void step(DragGesture gesture, int x, int y);
	}
	
	public static class StepStrategyImmediate extends StepStrategy
	{
		protected void step(DragGesture gesture, int x, int y)
		{
			gesture.step(x, y);
		}
	}
	
	public static class StepStrategyDeferred extends StepStrategy
	{
		/**
		 * Note: the command has been enqueued if this is not null
		 */
		private StepCommand m_command = null;
		
		protected void step(DragGesture gesture, int x, int y)
		{
			if (m_command != null)
			{
				m_command.update(x, y);
			}
			else
			{
				m_command = new StepCommand(gesture, x, y);
				DeferredCommand.add(m_command);
			}
		}
		
		private class StepCommand implements Command
		{
			private final DragGesture m_gesture;
			private int m_x, m_y;
			
			public StepCommand(DragGesture gesture, int x, int y)
			{
				m_gesture = gesture;
				update(x, y);
			}
			
			public void update(int x, int y)
			{
				m_x = x;
				m_y = y;
			}
			
			public void execute()
			{
				m_command = null;
				m_gesture.step(m_x, m_y);
			}
		}
	}
	
	public static class StepStrategyMozilla extends StepStrategy
	{
		private final StepStrategy m_strategy;
		
		public StepStrategyMozilla()
		{
			if(DomUtil.isMac() || DomUtil.isWin())
			{
				m_strategy = new StepStrategyImmediate();
			}
			else
			{
				m_strategy = new StepStrategyDeferred();
			}
		}
		
		protected void step(DragGesture gesture, int x, int y)
		{
			m_strategy.step(gesture, x, y);
		}
	}
}
