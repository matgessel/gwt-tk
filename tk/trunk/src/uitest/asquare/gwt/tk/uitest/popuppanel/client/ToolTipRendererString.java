package asquare.gwt.tk.uitest.popuppanel.client;

import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Label;

public class ToolTipRendererString implements ToolTipRenderer
{
	public void render(ComplexPanel toolTip, ToolTipModel model)
	{
		toolTip.add(new Label((String) ((HasToolTip) model.getToolTipWidget()).getToolTipContent()));
	}
}
