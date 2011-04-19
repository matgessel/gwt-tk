package asquare.gwt.tk.client.ui.behavior.event;

import asquare.gwt.tk.client.ui.behavior.FocusModel;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.ui.Focusable;

public interface FocusModelHandler extends EventHandler
{
	/**
	 * Called when widgets are added to the focus cycle. 
	 * 
	 * @param model the focus model that originated the event
	 * @param added an array of 1 or more widgets
	 */
	void widgetsAdded(FocusModel model, Focusable[] added);

	/**
	 * Called when widgets are removed from the focus cycle. 
	 * 
	 * @param model the focus model that originated the event
	 * @param removed an array of 1 or more widgets
	 */
	void widgetsRemoved(FocusModel model, Focusable[] removed);
	
	/**
	 * Called when the focused widget property changes. 
	 * 
	 * @param model the focus model that originated the event
	 * @param previous the widget that was previously focused, or <code>null</code>
	 * @param current the widget that is currently focused, or <code>null</code>
	 */
	void focusChanged(FocusModel model, Focusable previous, Focusable current);
}
