package asquare.gwt.tk.uitest.popuppanel.client.junk;

import asquare.gwt.tk.client.ui.behavior.ControllerAdaptor;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

public class HoverController extends ControllerAdaptor
{
	private final HoverModel m_model;
	
	public HoverController(HoverModel model)
	{
		this(model, Event.ONMOUSEOVER | Event.ONMOUSEOUT, HoverController.class);
	}
	
	public HoverController(HoverModel model, int eventBits, Class id)
	{
		super(eventBits | Event.ONMOUSEOVER | Event.ONMOUSEOUT, HoverController.class);
		m_model = model;
	}
	
	public void onBrowserEvent(Widget widget, Event event)
	{
		m_model.setHoverWidget(DOM.eventGetType(event) == Event.ONMOUSEOVER ? widget : null);
	}
}
