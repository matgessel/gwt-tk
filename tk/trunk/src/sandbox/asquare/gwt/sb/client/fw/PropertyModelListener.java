package asquare.gwt.sb.client.fw;

import java.util.EventListener;

public interface PropertyModelListener extends EventListener
{
	public void modelChanged(PropertyModelLazy model);
}
