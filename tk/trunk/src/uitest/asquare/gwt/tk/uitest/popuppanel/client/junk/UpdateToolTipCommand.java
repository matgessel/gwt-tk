package asquare.gwt.tk.uitest.popuppanel.client.junk;

import asquare.gwt.tk.uitest.popuppanel.client.ToolTipModel;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;

public class UpdateToolTipCommand implements Command
{
	private final ToolTipModel m_toolTipModel;
	private final HoverModel m_hoverModel;
	private final Widget m_currentWidget;
	
	public UpdateToolTipCommand(ToolTipModel toolTipModel, HoverModel hoverModel)
	{
		m_toolTipModel = toolTipModel;
		m_hoverModel = hoverModel;
		m_currentWidget = hoverModel.getHoverWidget();
	}
	
	public void execute()
	{
		if (m_hoverModel.getHoverWidget() == m_currentWidget)
		{
			// set owner widget (position)
			m_toolTipModel.setToolTipWidget(m_currentWidget);
						
			// set content
			
			// set visible
		}
	}
}
