package asquare.gwt.tk.client.ui.behavior.event;

import asquare.gwt.tk.client.ui.behavior.FocusModel;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.Focusable;

public class FocusModelEvent
{
	public static class WidgetsAdded extends GwtEvent<FocusModelHandler>
	{
		private static final Type<FocusModelHandler> TYPE = new Type<FocusModelHandler>();
		
		private final FocusModel m_model;
		private final Focusable[] m_widgets;
		
		public WidgetsAdded(FocusModel model, Focusable[] widgets)
		{
			m_model = model;
			m_widgets = widgets;
		}
		
		public static Type<FocusModelHandler> getType()
		{
			return TYPE;
		}

		@Override
		public Type<FocusModelHandler> getAssociatedType()
		{
			return TYPE;
		}

		@Override
		protected void dispatch(FocusModelHandler handler)
		{
			handler.widgetsAdded(m_model, m_widgets);
		}
	}
	
	public static class WidgetsRemoved extends GwtEvent<FocusModelHandler>
	{
		private static final Type<FocusModelHandler> TYPE = new Type<FocusModelHandler>();
		
		private final FocusModel m_model;
		private final Focusable[] m_widgets;
		
		public WidgetsRemoved(FocusModel model, Focusable[] widgets)
		{
			m_model = model;
			m_widgets = widgets;
		}
		
		public static Type<FocusModelHandler> getType()
		{
			return TYPE;
		}

		@Override
		public Type<FocusModelHandler> getAssociatedType()
		{
			return TYPE;
		}

		@Override
		protected void dispatch(FocusModelHandler handler)
		{
			handler.widgetsRemoved(m_model, m_widgets);
		}
	}
	
	public static class FocusChanged extends GwtEvent<FocusModelHandler>
	{
		private static final Type<FocusModelHandler> TYPE = new Type<FocusModelHandler>();
		
		private final FocusModel m_model;
		private final Focusable m_previous;
		private final Focusable m_current;
		
		public FocusChanged(FocusModel model, Focusable previous, Focusable current)
		{
			m_model = model;
			m_previous = previous;
			m_current = current;
		}
		
		public static Type<FocusModelHandler> getType()
		{
			return TYPE;
		}

		@Override
		public Type<FocusModelHandler> getAssociatedType()
		{
			return TYPE;
		}

		@Override
		protected void dispatch(FocusModelHandler handler)
		{
			handler.focusChanged(m_model, m_previous, m_current);
		}
	}
}
