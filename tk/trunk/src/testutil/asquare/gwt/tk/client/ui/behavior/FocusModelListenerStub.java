package asquare.gwt.tk.client.ui.behavior;

import java.util.ArrayList;
import java.util.Arrays;

import asquare.gwt.tk.client.util.GwtUtil;

import com.google.gwt.user.client.ui.HasFocus;

public class FocusModelListenerStub extends ModelListenerStubBase implements FocusModelListener
{
	private final ArrayList m_added = new ArrayList();
	private final ArrayList m_removed = new ArrayList();
	
	private HasFocus m_previous = null;
	private HasFocus m_current = null;
	
	public FocusModelListenerStub()
	{
		init();
	}
	
	public void init()
	{
		super.init();
		m_added.clear();
		m_removed.clear();
	}
	
	public HasFocus[] getAdded()
	{
		return (HasFocus[]) GwtUtil.toArray(m_added, new HasFocus[m_added.size()]);
	}
	
	public HasFocus[] getRemoved()
	{
		return (HasFocus[]) GwtUtil.toArray(m_removed, new HasFocus[m_removed.size()]);
	}
	
	public HasFocus getCurrent()
	{
		return m_current;
	}
	
	public HasFocus getPrevious()
	{
		return m_previous;
	}
	
	public void widgetsAdded(FocusModel model, HasFocus[] added)
	{
		incrementNotificationCount();
		m_added.addAll(Arrays.asList(added));
	}
	
	public void widgetsRemoved(FocusModel model, HasFocus[] removed)
	{
		incrementNotificationCount();
		m_removed.addAll(Arrays.asList(removed));
	}
	
	public void focusChanged(FocusModel model, HasFocus previous, HasFocus current)
	{
		incrementNotificationCount();
		m_previous = previous;
		m_current = current;
	}
}