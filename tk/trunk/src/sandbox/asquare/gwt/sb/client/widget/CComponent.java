package asquare.gwt.sb.client.widget;

import asquare.gwt.tk.client.ui.CWrapper;

import com.google.gwt.user.client.ui.Widget;

public class CComponent extends CWrapper
{
	private final Object m_model;
	private final Widget m_view;
	
	public CComponent(Object model, Widget view)
	{
		super(view, null, false);
		m_model = model;
		m_view = view;
	}
	
	public Object getModel()
	{
		return m_model;
	}
	
	public Widget getView()
	{
		return m_view;
	}
}
