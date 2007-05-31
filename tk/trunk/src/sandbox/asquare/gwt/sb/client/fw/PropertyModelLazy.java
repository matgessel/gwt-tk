package asquare.gwt.sb.client.fw;

import java.util.Vector;

import asquare.gwt.sb.client.util.Properties;

public class PropertyModelLazy
{
	private final Properties m_impl = new Properties();
	
	private boolean m_changed = false;
	private Vector m_listeners = null;
	
	public void addListener(PropertyModelListener listener)
	{
		if (m_listeners == null)
		{
			m_listeners = new Vector();
		}
		m_listeners.add(listener);
	}
	
	public void removeListener(PropertyModelListener listener)
	{
		if (m_listeners != null)
		{
			m_listeners.remove(listener);
		}
	}
	
	public void clear()
	{
		m_impl.clear();
	}
	
	public Object get(String name)
	{
		return m_impl.get(name);
	}
	
	public boolean getBoolean(String name)
	{
		return m_impl.getBoolean(name);
	}
	
	public String getString(String name)
	{
		return m_impl.getString(name);
	}
	
	public void set(String name, boolean value)
	{
		if (m_impl.getBoolean(name) != value)
		{
			m_impl.set(name, value);
			m_changed = true;
		}
	}
	
	public void set(String name, Object value)
	{
		Object old = m_impl.get(name);
		if (old == value || old != null && old.equals(value))
			return;
		
		m_impl.set(name, value);
		m_changed = true;
	}
	
	protected void fireChanged()
	{
		if (m_changed && m_listeners.size() > 0)
		{
			Object[] listeners = m_listeners.toArray();
			for (int i = 0; i < listeners.length; i++)
			{
				((PropertyModelListener) listeners[i]).modelChanged(this);
			}
			m_changed = false;
		}
	}
}
