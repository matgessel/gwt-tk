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
package asquare.gwt.sb.client.widget;

import java.util.EventListener;
import java.util.EventObject;

import asquare.gwt.sb.client.fw.*;
import asquare.gwt.sb.client.fw.ModelChangeEventComplex.PropertyChangeBoolean;
import asquare.gwt.tk.client.ui.behavior.ControlSurfaceController;
import asquare.gwt.tk.client.ui.behavior.MouseEvent;
import asquare.gwt.tk.client.ui.behavior.Pluggable;

import com.google.gwt.user.client.ui.Widget;

/**
 * @see ListSelectionController
 */
public class CList extends CComponent
{
	public CList()
	{
		this(null, null, null);
	}
	
	public CList(ListModel<?> model, ListView view)
	{
		this(model, view, null);
	}
	
	/**
	 * @param model a model, or <code>null</code>. {@link #createModel()} will
	 *            be invoked if <code>model</code> is <code>null</code>.
	 * @param view a view, or <code>null</code>. {@link #createView()} will be
	 *            invoked if <code>view</code> is <code>null</code>.
	 * @param updateController an Update Controller, or <code>null</code>. A
	 *            default impl will be created if <code>updateController</code>
	 *            is <code>null</code>.
	 */
	public CList(ListModel<?> model, ListView view, Pluggable updateController)
	{
		super(model, (Widget) view);
		
		if (updateController == null)
		{
			updateController = new ListUpdateController(getListModel(), getListView());
		}
		setUpdateController(updateController);
		addController(new CompositeCellViewHoverController(getListModel()));
		addController(ControlSurfaceController.getInstance());
		ListController listController = new ListController(this);
		setControllerDisablable(listController.getId(), true);
		addController(listController);
		getModel().addListener(new ListModelListener()
		{
			public void modelChanged(ListModelEvent event)
			{
				PropertyChangeBoolean enabledChange = event.getPropertyChangeBoolean(ListModel.PROPERTY_ENABLED);
				if (enabledChange != null)
				{
					CList.super.setEnabled(enabledChange.getNewValue());
				}
			}
		});
	}
	
	@Override
	protected ListModel<?> createModel()
	{
		return new ListModelDefault<Object>(new ListSelectionModelSingle());
	}
	
	@Override
	protected Widget createView()
	{
		return new ListViewBasic();
	}
	
	@Override
	public ListModel<?> getModel()
	{
		return (ListModel<?>) super.getModel();
	}
	
	@SuppressWarnings("unchecked")
	public <T> ListModel<T> getListModel()
	{
		return (ListModel<T>) getModel();
	}
	
	public ListView getListView()
	{
		return (ListView) getView();
	}
	
	public void addClickHandler(ListClickHandler handler)
	{
		ListController controller = (ListController) getController(ListController.class);
		if (controller != null)
		{
			controller.addClickHandler(handler);
		}
	}
	
	public void removeClickHandler(ListClickHandler handler)
	{
		ListController controller = (ListController) getController(ListController.class);
		if (controller != null)
		{
			controller.removeClickHandler(handler);
		}
	}
	
	public void addDoubleClickHandler(ListDoubleClickHandler handler)
	{
		ListController controller = (ListController) getController(ListController.class);
		if (controller != null)
		{
			controller.addDoubleClickHandler(handler);
		}
	}
	
	public void removeDoubleClickHandler(ListDoubleClickHandler handler)
	{
		ListController controller = (ListController) getController(ListController.class);
		if (controller != null)
		{
			controller.removeDoubleClickHandler(handler);
		}
	}
	
	public void setEnabled(boolean enabled)
	{
		// NO SUPER
		getListModel().setEnabled(enabled);
		getListModel().update();
	}
	
	public static interface ListClickHandler extends EventListener
	{
		void onListClick(ListClickEvent e);
	}
	
	public static interface ListDoubleClickHandler extends EventListener
	{
		void onListDoubleClick(ListClickEvent e);
	}
	
	public static interface ListEvent
	{
		public static final int LIST_CLICK = 1 << 0;
		public static final int LIST_DOUBLECLICK = 1 << 1;
		
		int getType();
		
		int getIndex();
		
		CList getList();
		
		ListModel<?> getListModel();
		
		ListView getListView();
		
		Object getModelElement();
	}
	
	public static class ListEventBase extends EventObject implements ListEvent
	{
		private static final long serialVersionUID = 1L;
		
		private final int m_type;
		private final int m_index;
		
		public ListEventBase(CList source, int type, int index)
		{
			super(source);
			m_type = type;
			m_index = index;
		}
		
		public int getType()
		{
			return m_type;
		}
		
		public CList getList()
		{
			return (CList) getSource();
		}
		
		public int getIndex()
		{
			return m_index;
		}
		
		public ListModel<?> getListModel()
		{
			return getList().getListModel();
		}
		
		public ListView getListView()
		{
			return getList().getListView();
		}
		
		public Object getModelElement()
		{
			return getListModel().get(m_index);
		}
	}
	
	public static interface ListClickEvent extends ListEvent
	{
		MouseEvent getMouseEvent();
	}
	
	protected static class ListClickEventImpl extends ListEventBase implements ListClickEvent
	{
		private static final long serialVersionUID = 1L;
		
		private final MouseEvent m_clickEvent;
		
		public ListClickEventImpl(CList source, int type, int index, MouseEvent event)
		{
			super(source, type, index);
			m_clickEvent = event;
		}
		
		public MouseEvent getMouseEvent()
		{
			return m_clickEvent;
		}
	}
}
