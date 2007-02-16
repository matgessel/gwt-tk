package asquare.gwt.tk.uitest.popuppanel.client.junk;

import asquare.gwt.tk.client.ui.behavior.ControllerAdaptor;
import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class ToolTipPositionController extends ControllerAdaptor
{
	private final PopupPanel m_toolTip;
	
	public ToolTipPositionController(PopupPanel toolTip)
	{
		super(Event.ONMOUSEMOVE, ToolTipPositionController.class);
		m_toolTip = toolTip;
	}
	
	public void onBrowserEvent(Widget widget, Event event)
	{
		m_toolTip.setPopupPosition(DomUtil.eventGetAbsoluteX(event) + 5, DomUtil.eventGetAbsoluteY(event) + 5);
	}
}
