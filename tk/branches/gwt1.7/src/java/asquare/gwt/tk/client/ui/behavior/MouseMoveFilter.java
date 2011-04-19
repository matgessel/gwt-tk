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

public class MouseMoveFilter extends EventController
{
	private static final MoveStrategy s_defaultImpl = (MoveStrategy) GWT.create(MoveStrategy.class);
	
	private MoveStrategy m_moveStrategy = s_defaultImpl;
	
	public MouseMoveFilter(EventHandler handler)
	{
		this(0, handler);
	}
	
	public MouseMoveFilter(int eventBits)
	{
		this(eventBits, null);
	}
	
	/**
	 * @param eventBits additional events to sink, or to use the event bits
	 *            <code>0</code> from the handlers
	 * @param handler an PluggableEventHandler or <code>null</code>
	 */
	public MouseMoveFilter(int eventBits, EventHandler handler)
	{
		super(MouseMoveFilter.class, eventBits, handler);
	}
	
	public MoveStrategy getMoveStrategy()
	{
		return m_moveStrategy;
	}

	public void setMoveStrategy(MoveStrategy impl)
	{
		m_moveStrategy = impl;
	}
	
	@Override
	public void processMouseMove(MouseEvent e)
	{
		// NO SUPER
		getMoveStrategy().step(this, e);
	}
	
	private void superProcessMouseMove(MouseEvent e)
	{
		super.processMouseMove(e);
	}
	
	@Override
    public void processMouseUp(MouseEvent e)
    {
        getMoveStrategy().finish();
    	super.processMouseUp(e);
    }
    
	public static abstract class MoveStrategy
	{
		protected abstract void step(MouseMoveFilter handler, MouseEvent e);
		
		protected abstract void finish();
	}
	
	public static class MoveStrategyImmediate extends MoveStrategy
	{
		@Override
		protected void step(MouseMoveFilter handler, MouseEvent e)
		{
			handler.superProcessMouseMove(e);
		}
		
		@Override
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
		
		@Override
		protected void step(MouseMoveFilter handler, MouseEvent moveEvent)
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
		
		@Override
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
			private final MouseMoveFilter m_handler;
			
			private MouseEvent m_moveEvent;
			
			public StepTimer(MouseMoveFilter handler, MouseEvent moveEvent)
			{
				m_handler = handler;
				update(moveEvent);
			}
			
			public void update(MouseEvent e)
			{
				m_moveEvent = e;
			}
			
			@Override
			public void run()
			{
				m_timer = null;
				m_handler.superProcessMouseMove(m_moveEvent);
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
		
		@Override
		protected void step(MouseMoveFilter handler, MouseEvent moveEvent)
		{
			m_strategy.step(handler, moveEvent);
		}
		
		@Override
		protected void finish()
		{
			m_strategy.finish();
		}
	}
}
