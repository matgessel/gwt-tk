/*
 * Copyright 2007 Mat Gessel <mat.gessel@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package asquare.gwt.sb.client.fw;

import java.util.EventListener;
import asquare.gwt.sb.client.fw.ModelChangeEventComplex.*;
import asquare.gwt.tk.client.util.GwtUtil;

public class ModelChangeSupportComplex extends ChangeSupportBase implements PropertyChangeSupport
{
	private ModelChangeEventComplex m_event = null;
    
    public ModelChangeSupportComplex(SourcesModelChangeEventComplex source)
	{
    	super(source);
	}
    
	public SourcesModelChangeEventComplex getSourceModel()
	{
		return (SourcesModelChangeEventComplex) getSource();
	}
	
	public boolean isChanged()
    {
        return m_event != null && m_event.getChangeCount() > 0;
    }
	
	/**
	 * @throws ClassCastException if listener is not an instance of {@link ModelListenerComplex}
	 */
	public void addListener(ModelListenerComplex listener)
	{
		addListenerImpl(listener);
	}
	
	/**
	 * @throws ClassCastException if listener is not an instance of {@link ModelListenerComplex}
	 */
	public void removeListener(ModelListenerComplex listener)
	{
		removeListenerImpl(listener);
	}
	
    public boolean propertyChanged(String propertyName, boolean oldValue, boolean newValue)
    {
    	if (oldValue != newValue)
    	{
    		getEvent().addPropertyChange(propertyName, oldValue, newValue);
        	return true;
    	}
    	return false;
    }
    
    public boolean propertyChanged(String propertyName, int oldValue, int newValue)
    {
    	if (oldValue != newValue)
    	{
    		getEvent().addPropertyChange(propertyName, oldValue, newValue);
        	return true;
    	}
    	return false;
    }
    
    public boolean propertyChanged(String propertyName, float oldValue, float newValue)
    {
    	if (oldValue != newValue)
    	{
    		getEvent().addPropertyChange(propertyName, oldValue, newValue);
        	return true;
    	}
    	return false;
    }
    
    public boolean propertyChanged(String propertyName, String oldValue, String newValue)
    {
    	if (! GwtUtil.equals(oldValue, newValue))
    	{
    		getEvent().addPropertyChange(propertyName, oldValue, newValue);
        	return true;
    	}
    	return false;
    }
    
    public boolean propertyChanged(String propertyName, Object oldValue, Object newValue)
    {
    	if (! GwtUtil.equals(oldValue, newValue))
    	{
    		getEvent().addPropertyChange(propertyName, oldValue, newValue);
        	return true;
    	}
    	return false;
    }
    
    public void addChange(ChangeBase change)
    {
    	getEvent().addChange(change);
    }
    
    public void resetChanges()
    {
        m_event = null;
    }
    
	public void update()
	{
		if (isChanged())
		{
			if (hasListeners())
			{
				notifyListeners(getListeners());
			}
			resetChanges();
		}
	}

	protected ModelChangeEventComplex getEvent()
	{
        if (m_event == null)
        {
            m_event = createChangeEvent(getSourceModel());
        }
        return m_event;
	}
    
    protected ModelChangeEventComplex createChangeEvent(SourcesModelChangeEventComplex source)
    {
    	return new ModelChangeEventComplex(getSourceModel());
    }
	
	protected void notifyListeners(EventListener[] listeners)
	{
		Object source = getSource();
		for (int i = 0; i < listeners.length; i++)
		{
			notifyListener(listeners[i], source, m_event);
		}
	}

	/**
	 * Template method to cast and notify the listener
	 */
	protected void notifyListener(EventListener listener, Object source, ModelChangeEventComplex event)
	{
		((ModelListenerComplex) listener).modelChanged(event);
	}
}
