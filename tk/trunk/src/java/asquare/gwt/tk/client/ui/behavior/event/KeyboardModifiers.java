package asquare.gwt.tk.client.ui.behavior.event;

import com.google.gwt.user.client.Event;

public class KeyboardModifiers
{
	public static final int MODIFIER_SHIFT = 1;
	public static final int MODIFIER_CTRL = 2;
	public static final int MODIFIER_ALT = 4;
	public static final int MODIFIER_META = 8;
	
	private KeyboardModifiers()
	{
	}
	
	/**
	 * Gets the keyboard modifiers associated with an {@link Event}.
	 * 
	 * @param event the event.
	 * @return the modifiers
	 */
	public static int getModifiers(Event event)
	{
		return (event.getShiftKey() ? MODIFIER_SHIFT : 0)
				| (event.getMetaKey() ? MODIFIER_META : 0)
				| (event.getCtrlKey() ? MODIFIER_CTRL : 0)
				| (event.getAltKey() ? MODIFIER_ALT : 0);
	}
}
