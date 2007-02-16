package asquare.gwt.tk.uitest.popuppanel.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

public class BasicToolTip extends AbsolutePositionPanel implements ToolTipModel
{
	private final ToolTipRenderer m_renderer;
	
	private Widget m_toolTipWidget = null;
	private int m_mouseX;
	private int m_mouseY;
	private boolean m_widgetChanged = false;
	private boolean m_mouseChanged = false;
	
	public BasicToolTip()
	{
		this(new ToolTipRendererString());
	}
	
	public BasicToolTip(ToolTipRenderer renderer)
	{
		super(DOM.createDiv());
		m_renderer = renderer;
		setStyleName("popup");
	}
	
	public Widget getToolTipWidget()
	{
		return m_toolTipWidget;
	}
	
	public void setToolTipWidget(Widget widget)
	{
		if (m_toolTipWidget != widget)
		{
			m_toolTipWidget = widget;
			m_widgetChanged = true;
		}
	}
	
	public void setMousePosition(int absX, int absY)
	{
		if (m_mouseX != absX || m_mouseY != absY)
		{
			m_mouseX = absX;
			m_mouseY = absY;
			m_mouseChanged = true;
		}
	}
	
	public void update()
	{
		if (m_mouseChanged)
		{
			setPosition(m_mouseX + 10, m_mouseY + 10);
			m_mouseChanged = false;
		}
		if (m_widgetChanged)
		{
			if (m_toolTipWidget != null)
			{
				clear();
				m_renderer.render(this, this);
				show();
			}
			else
			{
				hide();
			}
			m_widgetChanged = false;
		}
	}
	
	public boolean isShowing()
	{
		return isAttached();
	}
}
