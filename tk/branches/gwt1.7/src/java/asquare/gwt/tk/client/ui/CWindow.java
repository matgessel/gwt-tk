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
package asquare.gwt.tk.client.ui;

import asquare.gwt.tk.client.ui.behavior.AdjustObjectGesture.Positionable;
import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.impl.PopupImpl;

public class CWindow extends CComposite implements Positionable
{
	private final Window m_window;
	
	/*
	 * The container that this window will attach to. RootPanel is used if null.
	 */
	private HasWidgets m_container;
	
	/*
	 * The widget that will be attached/unattached from m_container
	 */
	private Widget m_localRoot;
	
	private int m_left, m_top;
    
	/**
	 * Creates a heavy weight window which attaches to the RootPanel. 
	 */
	public CWindow()
	{
		this(true, null);
	}

	/**
	 * @param heavyWeight <code>true</code> to back window by a heavy weight object.
	 * Used to block scrollbars on FF[Mac], and list boxes on IE. Can detract
	 * from performance, especially during drag n drop. 
	 */
	public CWindow(boolean heavyWeight)
	{
		this(heavyWeight, null);
	}
	
	public CWindow(HasWidgets container)
	{
		this(true, container);
	}

	public CWindow(boolean heavyWeight, HasWidgets container)
	{
		initWidget(new Window(heavyWeight));
		m_container = container;
		m_window = (Window) getWidget();
	}

	/**
	 * @see SimplePanel#getContainerElement()
	 */
	protected Element getContainerElement()
	{
		return m_window.getContainerElement();
	}
	
	/*
	 * Override to return PopupImpl inner DIV on Mac. 
	 */
	@Override
	protected Element getStyleElement()
	{
		return m_window.getStyleElement();
	}
	
	public HasWidgets getContainer()
	{
		return m_container;
	}
	
	/**
	 * @throws IllegalStateException if currently showing 
	 */
	public void setContainer(HasWidgets container)
	{
		if (isAttached())
			throw new IllegalStateException();
		
		m_container = container;
	}
	
	public int getLeft()
	{
        return m_left;
	}

	public void setLeft(int left)
	{
		m_left = left;
		if (isAttached())
		{
			DomUtil.setPixelStyleAttribute(this, "left", m_left);
		}
	}

	public int getTop()
	{
		return m_top;
	}

	public void setTop(int top)
	{
		m_top = top;
		if (isAttached())
		{
			DomUtil.setPixelStyleAttribute(this, "top", m_top);
		}
	}
	
	public void setPosition(int left, int top)
	{
		setLeft(left);
		setTop(top);
	}
	
	@Override
	public void setWidth(String width)
	{
		if (width.indexOf("%") > 0)
		{
			DOM.setStyleAttribute(getElement(), "width", width);
			DOM.setStyleAttribute(getContainerElement(), "width", width);
		}
		else
		{
			DOM.setStyleAttribute(getElement(), "width", "");
			DOM.setStyleAttribute(getContainerElement(), "width", width);
		}
	}
	
	@Override
	public void setHeight(String height)
	{
		if (height.indexOf("%") > 0)
		{
			DOM.setStyleAttribute(getElement(), "height", height);
			DOM.setStyleAttribute(getContainerElement(), "height", height);
		}
		else
		{
			DOM.setStyleAttribute(getElement(), "height", "");
			DOM.setStyleAttribute(getContainerElement(), "height", height);
		}
	}

	public int getZIndex()
	{
		return DomUtil.getIntStyleAttribute(this, "zIndex");
	}

	public void setZIndex(int zIndex)
	{
		DomUtil.setPixelStyleAttribute(this, "zIndex", zIndex);
	}
	
	@Override
	public String getTitle()
	{
		return DOM.getElementProperty(getContainerElement(), "title");
	}
	
	@Override
	public void setTitle(String title)
	{
		if (title == null || title.length() == 0)
		{
			DOM.removeElementAttribute(getContainerElement(), "title");
		}
		else
		{
			DOM.setElementAttribute(getContainerElement(), "title", title);
		}
	}
	
	/**
	 * @throws IllegalStateException if already showing
	 */
	public void show()
	{
		if (isAttached())
			throw new IllegalStateException();
		
        DomUtil.setStyleAttribute(this, "position", "absolute");
		DomUtil.setPixelStyleAttribute(this, "left", m_left);
		DomUtil.setPixelStyleAttribute(this, "top", m_top);
		
		/*
		 * Crawls up this widget's containment hierarchy to find it's ultimate
		 * ancestor. If this widget is contained by a Composite we need to attach the
		 * Composite to the DOM.
		 * 
		 * @return this widget's highest ancestor in the containment hierarchy
		 */
		m_localRoot = this;
		while (m_localRoot.getParent() != null)
		{
			m_localRoot = m_localRoot.getParent();
		}
		((m_container != null) ? m_container : RootPanel.get()).add(m_localRoot);
	}
	
	/**
	 * @throws IllegalStateException if not showing
	 */
	public void hide()
	{
		if (! isAttached())
			throw new IllegalStateException();
		
		((m_container != null) ? m_container : RootPanel.get()).remove(m_localRoot);
		m_localRoot = null;
	}
	
	@Override
	public void setWidget(Widget widget)
	{
		m_window.setWidget(widget);
	}
	
	@Override
	protected void onDetach()
	{
		if(! isAttached())
			return;
		
		try
		{
			super.onDetach();
		}
		finally
		{
			m_localRoot = null;
		}
	}
	
	private static class Window extends SimplePanel
	{
		private static PopupImpl s_implHeavy;
		private static PopupImpl s_implLight;
		
		private static PopupImpl getImplHeavy()
		{
			if (s_implHeavy == null)
			{
				s_implHeavy = (PopupImpl) GWT.create(PopupImpl.class);
			}
			return s_implHeavy;
		}

		private static PopupImpl getImplLight()
		{
			if (s_implLight == null)
			{
				s_implLight = new PopupImpl();
			}
			return s_implLight;
		}

		private final PopupImpl m_popupImpl;
		
		public Window(boolean heavyWeight)
		{
			this(heavyWeight ? getImplHeavy() : getImplLight());
		}
		
		private Window(PopupImpl popupImpl)
		{
			super(popupImpl.createElement());
			m_popupImpl = popupImpl;
		}
		
		@Override
		protected Element getContainerElement()
		{
			return m_popupImpl.getContainerElement(getElement());
		}
		
		@Override
		protected Element getStyleElement()
		{
			return m_popupImpl.getContainerElement(getElement());
		}

		@Override
		protected void onAttach()
		{
			if (isAttached())
				return;
			
			super.onAttach();
			m_popupImpl.onShow(getElement());
		}
		
		@Override
		protected void onDetach()
		{
			if(! isAttached())
				return;
			
			try
			{
				super.onDetach();
			}
			finally
			{
				m_popupImpl.onHide(getElement());
			}
		}
	}
}
