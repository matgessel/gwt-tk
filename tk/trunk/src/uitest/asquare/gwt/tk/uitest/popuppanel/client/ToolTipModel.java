package asquare.gwt.tk.uitest.popuppanel.client;

import com.google.gwt.user.client.ui.Widget;

public interface ToolTipModel
{
	Widget getToolTipWidget();
	
	void setToolTipWidget(Widget widget);

	void setMousePosition(int absX, int absY);
	
	boolean isShowing();
	
	void update();
}
