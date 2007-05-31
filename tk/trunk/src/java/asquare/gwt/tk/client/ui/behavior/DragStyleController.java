package asquare.gwt.tk.client.ui.behavior;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

/**
 * A controller which applies the specified style name to the widget. 
 */
public class DragStyleController extends ControllerAdaptor
{
	private final Widget m_target;
	private final String m_styleName;
	
	public DragStyleController(Widget target, String styleName)
	{
		super(Event.ONMOUSEDOWN | Event.ONMOUSEUP, DragStyleController.class);
		m_target = target;
		m_styleName = styleName;
	}
	
	public void onBrowserEvent(Widget widget, Event event)
	{
		if (DOM.eventGetType(event) == Event.ONMOUSEDOWN)
		{
			m_target.addStyleName(m_styleName);
		}
		else
		{
			m_target.removeStyleName(m_styleName);
		}
	}
}
