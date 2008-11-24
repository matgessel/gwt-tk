/*
 * Copyright 2008 Mat Gessel <mat.gessel@gmail.com>
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

import com.google.gwt.user.client.ui.UIObject;

public abstract class AdjustObjectGesture implements DragGesture
{
	private final Positionable m_positionable;
	private final Resizable m_resizable;
	private final ResizeListener m_resizeListener;
	
	private int m_startLeft, m_startTop;
	private int m_startWidth, m_startHeight;
	
	protected AdjustObjectGesture(Positionable positionable, Resizable resizable, ResizeListener resizeListener)
	{
		m_positionable = positionable;
		m_resizable = resizable;
		m_resizeListener = resizeListener;
	}

	public void start(MouseEvent e)
	{
		if (m_positionable != null)
		{
			m_startLeft = m_positionable.getLeft();
			m_startTop = m_positionable.getTop();
		}
		if (m_resizable != null && m_resizeListener != null)
		{
			m_startWidth = m_resizable.getWidth();
			m_startHeight = m_resizable.getHeight();
		}
	}
	
	public void step(DragEvent e)
	{
		stepImpl(e);
		if (m_resizable != null && m_resizeListener != null)
		{
			m_resizeListener.onResizing();
		}
	}
	
	protected abstract void stepImpl(DragEvent e);
	
	public void finish()
	{
		if (m_resizeListener != null)
		{
			m_resizeListener.onResized();
		}
		m_startLeft = m_startTop = 0;
		m_startWidth = m_startHeight = 0;
	}
	
	protected void adjustLeft(int delta)
	{
		if (delta != 0)
		{
			m_positionable.setLeft(m_startLeft + delta);
		}
	}
	
	protected void adjustTop(int delta)
	{
		if (delta != 0)
		{
			m_positionable.setTop(m_startTop + delta);
		}
	}
	
	protected void adjustWidth(int delta)
	{
		if (delta != 0)
		{
			m_resizable.setWidth(m_startWidth + delta);
		}
	}
	
	protected void adjustHeight(int delta)
	{
		if (delta != 0)
		{
			m_resizable.setHeight(m_startHeight + delta);
		}
	}
	
	public static class Move extends AdjustObjectGesture
	{
		public Move(Positionable positionable)
		{
			super(positionable, null, null);
		}
		
		protected void stepImpl(DragEvent e)
		{
			adjustLeft(e.getCumulativeX());
			adjustTop(e.getCumulativeY());
		}
	}
	
	public static class MoveH extends AdjustObjectGesture
	{
		public MoveH(Positionable positionable)
		{
			super(positionable, null, null);
		}
		
		protected void stepImpl(DragEvent e)
		{
			adjustLeft(e.getCumulativeX());
		}
	}
	
	public static class MoveV extends AdjustObjectGesture
	{
		public MoveV(Positionable positionable)
		{
			super(positionable, null, null);
		}
		
		protected void stepImpl(DragEvent e)
		{
			adjustTop(e.getCumulativeY());
		}
	}
	
	public static class ResizeNW extends AdjustObjectGesture
	{
		public ResizeNW(Positionable positionable, Resizable resizable)
		{
			this(positionable, resizable, null);
		}
		
		public ResizeNW(Positionable positionable, Resizable resizable, ResizeListener resizeListener)
		{
			super(positionable, resizable, resizeListener);
		}
		
		protected void stepImpl(DragEvent e)
		{
			adjustWidth(-e.getCumulativeX());
			adjustLeft(e.getCumulativeX());
			adjustHeight(-e.getCumulativeY());
			adjustTop(e.getCumulativeY());
			
		}
	}
	
	public static class ResizeN extends AdjustObjectGesture
	{
		public ResizeN(Positionable positionable, Resizable resizable)
		{
			this(positionable, resizable, null);
		}
		
		public ResizeN(Positionable positionable, Resizable resizable, ResizeListener resizeListener)
		{
			super(positionable, resizable, resizeListener);
		}
		
		protected void stepImpl(DragEvent e)
		{
			adjustHeight(-e.getCumulativeY());
			adjustTop(e.getCumulativeY());
		}
	}
	
	public static class ResizeNE extends AdjustObjectGesture
	{
		public ResizeNE(Positionable positionable, Resizable resizable)
		{
			this(positionable, resizable, null);
		}
		
		public ResizeNE(Positionable positionable, Resizable resizable, ResizeListener resizeListener)
		{
			super(positionable, resizable, resizeListener);
		}
		
		protected void stepImpl(DragEvent e)
		{
			adjustHeight(-e.getCumulativeY());
			adjustTop(e.getCumulativeY());
			adjustWidth(e.getCumulativeX());
		}
	}
	
	public static class ResizeW extends AdjustObjectGesture
	{
		public ResizeW(Positionable positionable, Resizable resizable)
		{
			this(positionable, resizable, null);
		}
		
		public ResizeW(Positionable positionable, Resizable resizable, ResizeListener resizeListener)
		{
			super(positionable, resizable, resizeListener);
		}
		
		protected void stepImpl(DragEvent e)
		{
			adjustWidth(-e.getCumulativeX());
			adjustLeft(e.getCumulativeX());
		}
	}
	
	public static class ResizeE extends AdjustObjectGesture
	{
		public ResizeE(Resizable resizable)
		{
			this(resizable, null);
		}
		
		public ResizeE(Resizable resizable, ResizeListener resizeListener)
		{
			super(null, resizable, resizeListener);
		}
		
		protected void stepImpl(DragEvent e)
		{
			adjustWidth(e.getCumulativeX());
		}
	}
	
	public static class ResizeSW extends AdjustObjectGesture
	{
		public ResizeSW(Positionable positionable, Resizable resizable)
		{
			this(positionable, resizable, null);
		}
		
		public ResizeSW(Positionable positionable, Resizable resizable, ResizeListener resizeListener)
		{
			super(positionable, resizable, resizeListener);
		}
		
		protected void stepImpl(DragEvent e)
		{
			adjustLeft(e.getCumulativeX());
			adjustWidth(-e.getCumulativeX());
			adjustHeight(e.getCumulativeY());
		}
	}
	
	public static class ResizeS extends AdjustObjectGesture
	{
		public ResizeS(Resizable resizable)
		{
			this(resizable, null);
		}
		
		public ResizeS(Resizable resizable, ResizeListener resizeListener)
		{
			super(null, resizable, resizeListener);
		}
		
		protected void stepImpl(DragEvent e)
		{
			adjustHeight(e.getCumulativeY());
		}
	}
	
	public static class ResizeSE extends AdjustObjectGesture
	{
		public ResizeSE(Resizable resizable)
		{
			this(resizable, null);
		}
		
		public ResizeSE(Resizable resizable, ResizeListener resizeListener)
		{
			super(null, resizable, resizeListener);
		}
		
		protected void stepImpl(DragEvent e)
		{
			adjustWidth(e.getCumulativeX());
			adjustHeight(e.getCumulativeY());
		}
	}

	public static interface Positionable
	{
		int getLeft();
		
		void setLeft(int left);
		
		int getTop();
		
		void setTop(int top);
	}
	
	public static interface Resizable
	{
		int getWidth();
		
		void setWidth(int width);
		
		int getHeight();
		
		void setHeight(int height);
		
	    public static class UIObjectAdaptor implements Resizable
	    {
	        private final UIObject m_uio;
	        
	        public UIObjectAdaptor(UIObject uio)
			{
	        	m_uio = uio;
			}
	    	
	    	public void setWidth(int width)
	        {
	    		m_uio.setWidth(width + "px");
	        }
	    
	        public void setHeight(int height)
	        {
	        	m_uio.setHeight(height + "px");
	        }
	    
	        public int getWidth()
	        {
	            return m_uio.getOffsetWidth();
	        }
	    
	        public int getHeight()
	        {
	            return m_uio.getOffsetHeight();
	        }
	    }
	}
	
	public static interface PositionListener
	{
		void onPositionChanged();
	}
	
	public static interface ResizeListener
	{
		void onResizing();
		
		void onResized();
	}
}
