package asquare.gwt.tk.uitest.popuppanel.client;

import asquare.gwt.tk.client.ui.CWrapper;

import com.google.gwt.user.client.ui.Widget;

public class ToolTipWrapper extends CWrapper implements HasToolTip
{
	private Object m_toolTipContent;
	
	public ToolTipWrapper(Widget widget, Object content)
	{
		super(widget);
		m_toolTipContent = content;
	}
	
	public Object getToolTipContent()
	{
		return m_toolTipContent;
	}
	
	public void setToolTipContent(Object toolTipContent)
	{
		m_toolTipContent = toolTipContent;
	}
}
