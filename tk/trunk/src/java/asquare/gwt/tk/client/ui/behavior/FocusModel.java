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

import java.util.Vector;

import asquare.gwt.tk.client.util.GwtUtil;

import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasFocus;

/**
 * A primitive focus model. Tracks the widgets in a focus cycle and which widget
 * <em>should</em> be focused. 
 */
public class FocusModel
{
	private final Vector m_listeners = new Vector();
	private final Vector m_widgets = new Vector();
	
	private int m_focusIndex = -1;
	
	public void addListener(FocusModelListener listener)
	{
		m_listeners.add(listener);
	}
	
	public void removeListener(FocusModelListener listener)
	{
		m_listeners.remove(listener);
	}
	
	/**
	 * Get the number of widgets in the model. 
	 */
	public int getSize()
	{
		return m_widgets.size();
	}
	
	/**
	 * Remove all widgets from the model. 
	 */
	public void clear()
	{
		HasFocus[] removed = new HasFocus[m_widgets.size()];
		for (int i = removed.length - 1; i >= 0; i--)
		{
			removed[i] = (HasFocus) m_widgets.get(i);
		}
		m_widgets.clear();
		m_focusIndex = -1;
		fireRemoved(removed);
	}
	
	/**
	 * Add widgets to the model in bulk.
	 * 
	 * @param widgets an array of 0 or more widgets
	 * @throws IllegalArgumentException if <code>widgets</code> is <code>null</code>
	 * @throws IllegalArgumentException if an element is <code>null</code>
	 * @throws IllegalArgumentException if an element is already present in the model
	 * @see #shouldAdd(HasFocus)
	 */
	public void add(HasFocus[] widgets)
	{
		if (widgets == null)
			throw new IllegalArgumentException();
		
		if (widgets.length == 0)
			return;
		
		Vector added = new Vector();
		for (int i = 0; i < widgets.length; i++)
		{
			if (insertImpl(widgets[i], m_widgets.size()))
			{
				added.add(widgets[i]);
			}
		}
		if (added.size() > 0)
		{
			HasFocus[] result = new HasFocus[added.size()];
			GwtUtil.toArray(added, result);
			fireAdded(result);
		}
	}
	
	/**
	 * Add a widget to the model. 
	 * 
	 * @param widget a widget
	 * @throws IllegalArgumentException if <code>widget</code> is <code>null</code>
	 * @throws IllegalArgumentException if <code>widget</code> is already present in the model
	 * @see #shouldAdd(HasFocus)
	 */
	public void add(HasFocus widget)
	{
		insert(widget, m_widgets.size());
	}
	
	/**
	 * Inserts a widget into to the model at the specified index.
	 * 
	 * @param widget a widget
	 * @param index an integer greater than 0 and less than or equal to number of
	 *            widgets in the model
	 * @throws IllegalArgumentException if <code>widget</code> is <code>null</code>
	 * @throws IllegalArgumentException if <code>widget</code> is already present in the model
	 * @throws IndexOutOfBoundsException if <code>index < 0 || index > {@link #getSize()}</code>
	 * @see #shouldAdd(HasFocus)
	 */
	public void insert(HasFocus widget, int index)
	{
		if (insertImpl(widget, index))
		{
			fireAdded(new HasFocus[] {widget});
		}
	}
	
	/**
	 * @return true if <code>widget</code> is added to the model
	 */
	private boolean insertImpl(HasFocus widget, int index)
	{
		if (widget == null)
			throw new IllegalArgumentException("widget cannot be null");
		
		GwtUtil.rangeCheck(0, m_widgets.size(), index, true);
		
		if (m_widgets.indexOf(widget) != -1)
			throw new IllegalArgumentException("cannot add widget twice");
		
		boolean result = false;
		
		if(shouldAdd(widget))
		{
			m_widgets.insertElementAt(widget, index);
			if (index <= m_focusIndex)
			{
				m_focusIndex++;
			}
			result = true;
		}
		return result;
	}
	
	/**
	 * Determines whether the specified Widget can be added to the model. 
	 * 
	 * @param w a widget which is candidate to be added to the model
	 * @return <code>true</code> if <code>widget.getTabIndex() >= 0</code>
	 */
	protected boolean shouldAdd(HasFocus widget)
	{
		return widget.getTabIndex() >= 0;
	}
	
	/**
	 * Get the index corresponding to the currently focused widget.
	 * 
	 * @return a value between <code>0</code> and
	 *         <code>{@link #getSize()}</code>, or <code>-1</code> if no
	 *         widget is focused
	 */
	public int getCurrentIndex()
	{
		return m_focusIndex;
	}
	
	/**
	 * Get the index of the specified widget in the model.
	 * 
	 * @param widget a widget
	 * @return a valid index, or <code>-1</code> if <code>widget</code> is
	 *         not present in the model
	 * @throws IllegalArgumentException if <code>widget</code> is
	 *             <code>null</code>
	 */
	public int getIndexOf(HasFocus widget)
	{
		if (widget == null)
			throw new IllegalArgumentException();
		
		return m_widgets.indexOf(widget);
	}
	
	/**
	 * Get the widget at the specified index. 
	 * 
	 * @param index an integer greater than 0 and less than or equal to number of
	 *            widgets in the model
	 * @return the widget
	 * @throws IndexOutOfBoundsException if <code>index</code> is out of range
	 */
	public HasFocus getWidgetAt(int index)
	{
		GwtUtil.rangeCheck(0, m_widgets.size(), index, false);
		
		return (HasFocus) m_widgets.get(index);
	}
	
	/**
	 * Removes a widget from the model. 
	 * 
	 * @param widget
	 */
	public void remove(HasFocus widget)
	{
		int index = m_widgets.indexOf(widget);
		if (index >= 0)
		{
			remove(index);
		}
	}
	
	private void remove(int index)
	{
		HasFocus widget = (HasFocus) m_widgets.remove(index);
		if (m_focusIndex > index)
		{
			m_focusIndex--;
		}
		else if (m_focusIndex == index)
		{
			m_focusIndex = -1;
		}
		fireRemoved(new HasFocus[] {widget});
	}
	
	/**
	 * Get the widget which is focused.  
	 * 
	 * @return a widget, or <code>null</code> if no widget is focused
	 */
	public HasFocus getFocusWidget()
	{
		return (m_focusIndex != -1) ? (HasFocus) m_widgets.get(m_focusIndex) : null;
	}
	
	/**
	 * Set the widget which is focused.
	 * 
	 * @param widget a widget in the model, or <code>null</code>
	 * @throws IllegalArgumentException if <code>widget</code> is not
	 *             present in the model and not <code>null</code>
	 */
	public void setFocusWidget(HasFocus widget)
	{
		int index;
		if (widget == null)
		{
			index = -1;
		}
		else
		{
			index = m_widgets.indexOf(widget);
			if (index == -1)
				throw new IllegalArgumentException();
		}
		m_focusIndex = index;
//		m_focusIndex = (widget == null) ? -1 : m_widgets.indexOf(widget);
	}
	
	/**
	 * Get the next widget in the cycle.  
	 * The widget is skipped if <code>(widget.getTabIndex() < 0 || ! widget.isEnabled())</code>.
	 * 
	 * @param forward <code>true</code> to cycle forward, <code>false</code> to cycle backward
	 * @return the next focusable widget, or <br/>
	 * the currently focused widget if no other focusable widget is available, or<br/>
	 * <code>null</code> no if focusable widget is available
	 * @throws IllegalStateException if the model is empty
	 */
	public HasFocus getNextWidget(boolean forward)
	{
		int nextIndex = getNextIndex(m_focusIndex, forward);
		if (nextIndex != -1)
		{
			return (HasFocus) getWidgetAt(nextIndex); 
		}
		return null;
	}
	
	/**
	 * Get the widget after the currently focused widget.  
	 * 
	 * @return the next widget
	 * @throws IllegalStateException if the model is empty
	 */
	public HasFocus getNextWidget()
	{
		return getNextWidget(true);
	}
	
	/**
	 * Get the widget previous to the currently focused widget.  
	 * 
	 * @return the previous widget
	 * @throws IllegalStateException if the model is empty
	 */
	public HasFocus getPreviousWidget()
	{
		return getNextWidget(false);
	}
	
	private int getNextIndex(int index, boolean forward)
	{
		int size = m_widgets.size();
		GwtUtil.rangeCheck(-1, size + 1, index, false);
		if (size == 0)
			throw new IllegalStateException();
		
		return getNextIndex(index, index, m_widgets.size(), forward);
	}
	
	private int getNextIndex(int initialIndex, int current, int size, boolean forward)
	{
		// avoid infinite loop if initialIndex = -1
		if (initialIndex == -1)
		{
			if (forward && current == size - 1 || ! forward && current == 0) 
				return initialIndex;
		}
		
		if (forward)
		{
			current = (current + 1) % size;
		}
		else
		{
			current = (current > 0) ? current - 1 : size - 1;
		}
		
		// give up after looping once
		if (current == initialIndex)
			return initialIndex;
		
		// return if widget is not a disabled FocusWidget
		if (shouldFocus((HasFocus) m_widgets.get(current)))
			return current;
		
		return getNextIndex(initialIndex, current, size, forward);
	}
	
	/**
	 * Determines whether the specified Widget can receive focus.
	 * 
	 * @param w a widget in this model which is candidate for focus
	 * @return <code>true</code> unless <code>w</code> is a disabled
	 *         {@link FocusWidget}
	 */
	protected boolean shouldFocus(HasFocus w)
	{
		if (w instanceof FocusWidget)
		{
			return ((FocusWidget) w).isEnabled();
		}
		return true;
	}
	
	private void fireAdded(HasFocus[] added)
	{
		Object[] listeners = m_listeners.toArray();
		for (int i = 0; i < listeners.length; i++)
		{
			((FocusModelListener) listeners[i]).widgetsAdded(this, added);
		}
	}
	
	private void fireRemoved(HasFocus[] removed)
	{
		Object[] listeners = m_listeners.toArray();
		for (int i = 0; i < listeners.length; i++)
		{
			((FocusModelListener) listeners[i]).widgetsRemoved(this, removed);
		}
	}
}
