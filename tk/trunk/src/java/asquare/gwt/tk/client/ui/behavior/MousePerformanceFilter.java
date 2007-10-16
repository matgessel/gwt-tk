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
import com.google.gwt.user.client.Timer;

public class MousePerformanceFilter extends MouseHandlerWrapper
{
	private static final MoveStrategy s_defaultImpl = (MoveStrategy) GWT.create(MoveStrategy.class);
	
	private MoveStrategy m_MoveStrategy = s_defaultImpl;
	
	public MousePerformanceFilter(MouseHandler delegate)
	{
		super(delegate);
	}
	
	public MoveStrategy getMoveStrategy()
	{
		return m_MoveStrategy;
	}

	public void setMoveStrategy(MoveStrategy impl)
	{
		m_MoveStrategy = impl;
	}
	
	public void onMouseMove(MouseEvent e)
	{
		getMoveStrategy().step(getDelegate(), e);
	}
	
    public void onMouseUp(MouseEvent e)
    {
        getMoveStrategy().finish();
    	super.onMouseUp(e);
    }
	
	public static abstract class MoveStrategy
	{
		protected abstract void step(MouseHandler handler, MouseEvent e);
		
		protected abstract void finish();
	}
	
	public static class MoveStrategyImmediate extends MoveStrategy
	{
		protected void step(MouseHandler handler, MouseEvent e)
		{
			handler.onMouseMove(e);
		}
		
		protected void finish()
		{
		}
	}
	
	public static class MoveStrategyDeferred extends MoveStrategy
	{
		/**
		 * Note: the timer is active if this field is not null
		 */
		private StepTimer m_timer = null;
		
		protected void step(MouseHandler handler, MouseEvent moveEvent)
		{
			if (m_timer != null)
			{
				m_timer.update(moveEvent);
			}
			else
			{
				m_timer = new StepTimer(handler, moveEvent);
				m_timer.schedule(1);
			}
		}
		
		protected void finish()
		{
			if (m_timer != null)
			{
				m_timer.cancel();
				m_timer.run();
			}
        }
		
		private class StepTimer extends Timer
		{
			private final MouseHandler m_handler;
			
			private MouseEvent m_moveEvent;
			
			public StepTimer(MouseHandler gesture, MouseEvent moveEvent)
			{
				m_handler = gesture;
				update(moveEvent);
			}
			
			public void update(MouseEvent e)
			{
				m_moveEvent = e;
			}
			
			public void run()
			{
				m_timer = null;
				m_handler.onMouseMove(m_moveEvent);
			}
		}
	}
	
	public static class MoveStrategyMozilla extends MoveStrategy
	{
		private final MoveStrategy m_strategy;
		
		public MoveStrategyMozilla()
		{
			if (DomUtil.isMac() || DomUtil.isWin())
			{
				m_strategy = new MoveStrategyImmediate();
			}
			else
			{
				m_strategy = new MoveStrategyDeferred();
			}
		}
		
		protected void step(MouseHandler handler, MouseEvent moveEvent)
		{
			m_strategy.step(handler, moveEvent);
		}
		
		protected void finish()
		{
			m_strategy.finish();
		}
	}
}
