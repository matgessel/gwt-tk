package asquare.gwt.tk.uitest.popuppanel.client;

import asquare.gwt.tk.client.ui.behavior.ControllerAdaptor;
import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

public class BasicToolTipController extends ControllerAdaptor
{
	private final ToolTipModel m_toolTip;
	
	public BasicToolTipController(ToolTipModel toolTip)
	{
		super(Event.ONMOUSEOVER | Event.ONMOUSEOUT | Event.ONMOUSEMOVE, BasicToolTipController.class);
		m_toolTip = toolTip;
	}
	
	public void onBrowserEvent(Widget widget, Event event)
	{
		switch (DOM.eventGetType(event))
		{
			case Event.ONMOUSEOUT: 
				m_toolTip.setToolTipWidget(null);
				break;

			case Event.ONMOUSEOVER: 
				m_toolTip.setToolTipWidget(widget);
				// NOBREAK

			case Event.ONMOUSEMOVE: 
				m_toolTip.setMousePosition(DomUtil.eventGetAbsoluteX(event), DomUtil.eventGetAbsoluteY(event));
				break;
		}
		m_toolTip.update();
	}
}
