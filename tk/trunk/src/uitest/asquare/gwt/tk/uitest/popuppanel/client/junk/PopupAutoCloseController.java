package asquare.gwt.tk.uitest.popuppanel.client.junk;

import asquare.gwt.tk.client.ui.behavior.ControllerAdaptor;
import asquare.gwt.tk.uitest.popuppanel.client.ToolTipModel;

import com.google.gwt.user.client.ui.PopupListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class PopupAutoCloseController extends ControllerAdaptor implements PopupListener
{
	private final ToolTipModel m_model;
	
	public PopupAutoCloseController(ToolTipModel model)
	{
		super(PopupAutoCloseController.class);
		m_model = model;
	}
	
	public void plugIn(Widget widget)
	{
		((PopupPanel) widget).addPopupListener(this);
	}
	
	public void unplug(Widget widget)
	{
		((PopupPanel) widget).removePopupListener(this);
	}

	// PopupListener methods
	public void onPopupClosed(PopupPanel sender, boolean autoClosed)
	{
		m_model.setToolTipWidget(null);
	}
}
