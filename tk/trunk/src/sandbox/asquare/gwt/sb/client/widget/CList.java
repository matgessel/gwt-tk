package asquare.gwt.sb.client.widget;

import java.util.List;
import java.util.Vector;

import asquare.gwt.sb.client.fw.*;
import asquare.gwt.tk.client.ui.behavior.PreventSelectionController;

public class CList extends CComponent
{
	public CList(ListModel model, ListViewBase view)
	{
		super(model, view);
		new ListUpdateController(model, view);
	}
	
	protected List createControllers()
	{
		List result = new Vector();
		result.add(PreventSelectionController.getInstance());
		return result;
	}
	
	public ListModel getListModel()
	{
		return (ListModel) getModel();
	}
	
	public ListView getListView()
	{
		return (ListView) getView();
	}
}
