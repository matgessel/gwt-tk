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
	 * Get the number of widgets in the focus cycle. 
	 */
	public int getSize()
	{
		return m_widgets.size();
	}
	
	/**
	 * Remove all widgets from the focus cycle. 
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
	 * Add widgets to the focus cycle in bulk. 
	 * 
	 * @param widgets an array of 0 or more widgets
	 */
	public void add(HasFocus[] widgets)
	{
		if (widgets.length == 0)
			return;
		
		HasFocus[] added = new HasFocus[widgets.length];
		for (int i = 0; i < widgets.length; i++)
		{
			insertImpl(widgets[i], m_widgets.size());
			added[i] = widgets[i];
		}
		fireAdded(added);
	}
	
	/**
	 * Add a widget to the focus cycle. 
	 * 
	 * @param widget a widget
	 * @throws IllegalArgumentException if <code>widget</code> is null
	 */
	public void add(HasFocus widget)
	{
		insert(widget, m_widgets.size());
	}
	
	/**
	 * Inserts a widget into to the focus cycle at the specified index.
	 * 
	 * @param widget a widget
	 * @param index an integer greater than 0 and less than or equal to number of
	 *            widgets in the model
	 * @throws IllegalArgumentException if <code>widget</code> is null
	 * @throws IndexOutOfBoundsException if <code>index</code> is out of range
	 */
	public void insert(HasFocus widget, int index)
	{
		insertImpl(widget, index);
		fireAdded(new HasFocus[] {widget});
	}
	
	private void insertImpl(HasFocus widget, int index)
	{
		if (widget == null)
			throw new IllegalArgumentException();
		
		GwtUtil.rangeCheck(0, m_widgets.size(), index, true);
		
		if(widget.getTabIndex() >= 0)
		{
			m_widgets.insertElementAt(widget, index);
			if (index <= m_focusIndex)
			{
				m_focusIndex++;
			}
		}
	}
	
	/**
	 * Get the index in the focus cycle of the specified widget. 
	 * 
	 * @param widget a widget
	 * @return a valid index, or -1
	 */
	public int getIndexOf(HasFocus widget)
	{
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
	 * Removes a widget from the focus cycle. 
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
		GwtUtil.rangeCheck(0, m_widgets.size(), index, false);
		
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
	 * Get the widget which is focused in this cycle.  
	 * 
	 * @return a widget, or <code>null</code>
	 */
	public HasFocus getFocusWidget()
	{
		return (m_focusIndex != -1) ? (HasFocus) m_widgets.get(m_focusIndex) : null;
	}
	
	/**
	 * Set widget which is focused in this cycle. 
	 * 
	 * @param widget a widget or <code>null</code>
	 */
	public void setFocusWidget(HasFocus widget)
	{
		m_focusIndex = m_widgets.indexOf(widget);
	}
	
	/**
	 * Get the widget after the currently focused widget.  
	 * 
	 * @return the next widget
	 * @throws IllegalStateException if the focus cycle is empty
	 */
	public HasFocus getNextWidget()
	{
		if (m_widgets.size() == 0)
			throw new IllegalStateException();
		
		int nextIndex = (m_focusIndex + 1) % m_widgets.size();
		return (HasFocus) getWidgetAt(nextIndex); 
	}
	
	/**
	 * Get the widget previous to the currently focused widget.  
	 * 
	 * @return the previous widget
	 * @throws IllegalStateException if the focus cycle is empty
	 */
	public HasFocus getPreviousWidget()
	{
		if (m_widgets.size() == 0)
			throw new IllegalStateException();
		
		int previousIndex = m_focusIndex > 0 ? m_focusIndex - 1 : m_widgets.size() - 1;
		return (HasFocus) getWidgetAt(previousIndex);
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
