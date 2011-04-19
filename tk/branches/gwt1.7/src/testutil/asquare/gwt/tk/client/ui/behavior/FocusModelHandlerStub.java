package asquare.gwt.tk.client.ui.behavior;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gwt.user.client.ui.Focusable;

import asquare.gwt.tk.client.ui.behavior.event.FocusModelHandler;

public class FocusModelHandlerStub extends ModelListenerStubBase implements FocusModelHandler
{
	private final ArrayList<Focusable> m_added = new ArrayList<Focusable>();
	private final ArrayList<Focusable> m_removed = new ArrayList<Focusable>();
	
	private Focusable m_previous = null;
	private Focusable m_current = null;
	
	public FocusModelHandlerStub()
	{
		init();
	}
	
    @Override
	public void init()
	{
		super.init();
		m_added.clear();
		m_removed.clear();
	}
	
	public Focusable[] getAdded()
	{
		return m_added.toArray(new Focusable[m_added.size()]);
	}
	
	public Focusable[] getRemoved()
	{
		return m_removed.toArray(new Focusable[m_removed.size()]);
	}
	
	public Object getCurrent()
	{
		return m_current;
	}
	
	public Object getPrevious()
	{
		return m_previous;
	}
	
	public void widgetsAdded(FocusModel model, Focusable[] added)
	{
		incrementNotificationCount();
		m_added.addAll(Arrays.asList(added));
	}
	
	public void widgetsRemoved(FocusModel model, Focusable[] removed)
	{
		incrementNotificationCount();
		m_removed.addAll(Arrays.asList(removed));
	}
	
	public void focusChanged(FocusModel model, Focusable previous, Focusable current)
	{
		incrementNotificationCount();
		m_previous = previous;
		m_current = current;
	}
}
