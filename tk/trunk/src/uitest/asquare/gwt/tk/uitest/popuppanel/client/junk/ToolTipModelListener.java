package asquare.gwt.tk.uitest.popuppanel.client.junk;

import asquare.gwt.tk.uitest.popuppanel.client.ToolTipModel;

public interface ToolTipModelListener
{
	void hoverChanged(ToolTipModel model);

	void visibilityChanged(ToolTipModel model);

	void positionChanged(ToolTipModel model);
}
