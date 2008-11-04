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

import asquare.gwt.sb.client.util.TypedList;
import asquare.gwt.tk.client.util.GwtUtil;

public class ModelChangeEventComplex extends ModelChangeEvent
{
    private final TypedList m_changes = new TypedList();
    
	public ModelChangeEventComplex(SourcesModelChangeEventComplex source)
	{
		super(source);
	}
	
	public SourcesModelChangeEventComplex getSourceModel()
	{
		return (SourcesModelChangeEventComplex) getSource();
	}
	
    public void addPropertyChange(String propertyName, boolean oldValue, boolean newValue)
    {
    	addChange(new PropertyChangeBoolean(propertyName, oldValue, newValue));
    }
    
    public void addPropertyChange(String propertyName, int oldValue, int newValue)
    {
    	addChange(new PropertyChangeInt(propertyName, oldValue, newValue));
    }
    
    public void addPropertyChange(String propertyName, float oldValue, float newValue)
    {
    	addChange(new PropertyChangeFloat(propertyName, oldValue, newValue));
    }
    
    public void addPropertyChange(String propertyName, String oldValue, String newValue)
    {
    	addChange(new PropertyChangeString(propertyName, oldValue, newValue));
    }
    
    public void addPropertyChange(String propertyName, Object oldValue, Object newValue)
    {
    	addChange(new PropertyChangeObject(propertyName, oldValue, newValue));
    }
    
    public void addChange(ChangeBase change)
    {
        // note: changes can consume changes of other types
    	ChangeBase result = null;
        for (int i = 0, size = m_changes.getSize(); i < size; i++)
        {
        	ChangeBase other = (ChangeBase) m_changes.getValue(i);
        	if (other.addChange(change))
            {
        		result = other;
            	break;
            }
        }
        if (result == null)
        {
            result = change;
            for (int i = m_changes.getSize() - 1; i >= 0; i--)
            {
                if (result.addChange((ChangeBase) m_changes.getValue(i)))
                {
                    m_changes.remove(i);
                    // NOBREAK
                }
            }
            m_changes.add(result.getType(), result);
        }
        
        /*
		 * If a property is reverted to its original value the change can be
		 * discarded. Unlikely to occur so no optimization.
		 */
        if (result instanceof PropertyChangeBase && ! ((PropertyChangeBase) change).isChanged())
        {
            m_changes.remove(change);
        }
    }
    
    public int getChangeCount()
    {
        return m_changes.getSize();
    }
    
    public ChangeBase getChangeAt(int index)
    {
        return (ChangeBase) m_changes.getValue(index);
    }
    
    /**
	 * Gets the first change with the specified type id. A class literal is used
	 * as a unique id.
	 * 
	 * @param type a class literal used to identify a type of change
	 * @return the change, or <code>null</code>
	 */
    public ChangeBase getChange(Class<? extends ChangeBase> type)
    {
        return (ChangeBase) m_changes.getValue(type);
    }
    
    public int getIndexOfChange(Class<? extends ChangeBase> type)
    {
    	 return getIndexOfChange(type, 0);
    }
    
    /**
	 * Gets the first index of the change with the specified type id. A class
	 * literal is used as a unique id.
	 * 
	 * @param type a class literal used to identify a type of change
	 * @return the index, or <code>-1</code>
	 */
    public int getIndexOfChange(Class<? extends ChangeBase> type, int fromIndex)
    {
    	 return m_changes.getIndexOfType(type, fromIndex);
    }
    
    public boolean hasChange(Class<? extends ChangeBase> type)
    {
    	return m_changes.containsType(type);
    }
    
    public PropertyChangeBase getPropertyChange(String name)
    {
        PropertyChangeBase candidate;
        int searchIndex = getIndexOfChange(PropertyChangeBase.class);
		while (searchIndex != -1)
		{
        	candidate = (PropertyChangeBase) getChangeAt(searchIndex);
            if (candidate.getName().equals(name))
            {
            	return candidate;
            }
            searchIndex = getIndexOfChange(PropertyChangeBase.class, searchIndex + 1);
		}
        return null;
    }
    
    public boolean hasPropertyChange(String name)
    {
    	return getPropertyChange(name) != null;
    }
    
    /**
	 * @throws ClassCastException if the property specified by <code>name</code>
	 *             is a different type
	 */
    public PropertyChangeBoolean getPropertyChangeBoolean(String name)
    {
    	return (PropertyChangeBoolean) getPropertyChange(name);
    }
    
    /**
	 * @throws ClassCastException if the property specified by <code>name</code>
	 *             is a different type
	 */
    public PropertyChangeInt getPropertyChangeInt(String name)
    {
    	return (PropertyChangeInt) getPropertyChange(name);
    }
    
    /**
	 * @throws ClassCastException if the property specified by <code>name</code>
	 *             is a different type
	 */
    public PropertyChangeFloat getPropertyChangeFloat(String name)
    {
    	return (PropertyChangeFloat) getPropertyChange(name);
    }
    
    /**
	 * @throws ClassCastException if the property specified by <code>name</code>
	 *             is a different type
	 */
    public PropertyChangeString getPropertyChangeString(String name)
    {
    	return (PropertyChangeString) getPropertyChange(name);
    }
    
    /**
	 * @throws ClassCastException if the property specified by <code>name</code>
	 *             is a different type
	 */
    public PropertyChangeObject getPropertyChangeObject(String name)
    {
    	return (PropertyChangeObject) getPropertyChange(name);
    }
    
    public String toString()
    {
        return "ModelChangeEventComplex" + m_changes;
    }

    public static abstract class ChangeBase
    {
        /**
		 * Design note: using the Class to specify the type ensures quick ==
		 * comparison (in GWT 1.5 anyway) and consistency across networked VMs.
		 */
    	private final Class<? extends ChangeBase> m_type;
        
    	public ChangeBase(Class<? extends ChangeBase> type)
        {
            m_type = type;
        }
        
        /**
         * Attempts to combine the specified change can be added to this one.
         * <p>
         * The default implementation always returns false.
         * 
         * @return <code>true</code> if <code>change</code> was combined
         *         with this change
         */
        public boolean addChange(ChangeBase change)
        {
            return false;
        }
        
        /**
		 * Gets a Class which is used to identify the type of change.
		 */
        public Class<? extends ChangeBase> getType()
        {
            return m_type;
        }
        
        public String toString()
        {
            return GwtUtil.getClassSimpleName(this);
        }
        
        public int hashCode()
        {
        	return m_type.hashCode();
        }
        
        public boolean equals(Object obj)
        {
        	return obj instanceof ChangeBase && equals((ChangeBase) obj);
        }
        
        public boolean equals(ChangeBase change)
        {
        	return m_type == change.m_type;
        }
    }
	
	/**
	 * Indicates that the model changed and the entire view needs to be
	 * revalidated.
	 */
    public static class ModelChanged extends ChangeBase
    {
    	public ModelChanged()
		{
    		super(ModelChanged.class);
		}
    }

    public static abstract class PropertyChangeBase extends ChangeBase
    {
		private final String m_name;
    	
		protected PropertyChangeBase(String name)
    	{
    		super(PropertyChangeBase.class);
    		
    		if (name == null)
    			throw new IllegalArgumentException();
    		
    		m_name = name;
    	}
    	
    	public String getName()
    	{
    		return m_name;
    	}
    	
    	/**
		 * Determines if the values are different. Used to discard unnecessary
		 * changes.
		 * 
		 * @return <code>true</code> if the new value is different than the
		 *         old value
		 */
    	public abstract boolean isChanged();
    	
    	public boolean addChange(ChangeBase change)
    	{
    		if (equals((Object) change))
    		{
    			copyNewValueFrom((PropertyChangeBase) change);
    			return true;
    		}
    		return false;
    	}
    	
    	protected abstract void copyNewValueFrom(PropertyChangeBase change);
    	
    	public int hashCode()
    	{
    		return super.hashCode() * 31 + m_name.hashCode();
    	}
    	
    	public boolean equals(Object obj)
    	{
        	return obj instanceof PropertyChangeBase && equals((PropertyChangeBase) obj);
    	}
    	
    	public boolean equals(PropertyChangeBase change)
    	{
        	return super.equals(change) && m_name.equals(change.m_name);
    	}
    	
    	public String toString()
    	{
    		return super.toString() + '[' + m_name + ']';
    	}
    }
    
    public static class PropertyChangeBoolean extends PropertyChangeBase
    {
		private final boolean m_oldValue;
		
		private boolean m_newValue;
    	
		public PropertyChangeBoolean(String name, boolean oldValue, boolean newValue)
		{
    		super(name);
    		m_oldValue = oldValue;
    		m_newValue = newValue;
		}
    	
    	public boolean getOldValue()
		{
			return m_oldValue;
		}
    	
    	public boolean getNewValue()
		{
			return m_newValue;
		}
    	
    	protected void copyNewValueFrom(PropertyChangeBase change)
    	{
    		m_newValue = ((PropertyChangeBoolean) change).m_newValue;
    	}
    	
    	public boolean isChanged()
    	{
    		return m_oldValue != m_newValue;
    	}
    }
    
    public static class PropertyChangeInt extends PropertyChangeBase
    {
		private final int m_oldValue;
		
		private int m_newValue;
    	
		public PropertyChangeInt(String name, int oldValue, int newValue)
		{
    		super(name);
    		m_oldValue = oldValue;
    		m_newValue = newValue;
		}
    	
    	public int getOldValue()
		{
			return m_oldValue;
		}
    	
    	public int getNewValue()
		{
			return m_newValue;
		}
    	
    	protected void copyNewValueFrom(PropertyChangeBase change)
    	{
    		m_newValue = ((PropertyChangeInt) change).m_newValue;
    	}
    	
    	public boolean isChanged()
    	{
    		return m_oldValue != m_newValue;
    	}
    }
    
    public static class PropertyChangeFloat extends PropertyChangeBase
    {
		private final float m_oldValue;
		
		private float m_newValue;
    	
		public PropertyChangeFloat(String name, float oldValue, float newValue)
		{
    		super(name);
    		m_oldValue = oldValue;
    		m_newValue = newValue;
		}
    	
    	public float getOldValue()
		{
			return m_oldValue;
		}
    	
    	public float getNewValue()
		{
			return m_newValue;
		}
    	
    	protected void copyNewValueFrom(PropertyChangeBase change)
    	{
    		m_newValue = ((PropertyChangeFloat) change).m_newValue;
    	}
    	
    	public boolean isChanged()
    	{
    		return m_oldValue != m_newValue;
    	}
    }
    
    public static class PropertyChangeString extends PropertyChangeBase
    {
		private final String m_oldValue;
		
		private String m_newValue;
    	
		public PropertyChangeString(String name, String oldValue, String newValue)
		{
    		super(name);
    		m_oldValue = oldValue;
    		m_newValue = newValue;
		}
    	
    	public String getOldValue()
		{
			return m_oldValue;
		}
    	
    	public String getNewValue()
		{
			return m_newValue;
		}
    	
    	protected void copyNewValueFrom(PropertyChangeBase change)
    	{
    		m_newValue = ((PropertyChangeString) change).m_newValue;
    	}
    	
    	public boolean isChanged()
    	{
    		return ! GwtUtil.equals(m_oldValue, m_newValue);
    	}
    }
    
    public static class PropertyChangeObject extends PropertyChangeBase
    {
		private final Object m_oldValue;
		
		private Object m_newValue;
    	
		public PropertyChangeObject(String name, Object oldValue, Object newValue)
		{
    		super(name);
    		m_oldValue = oldValue;
    		m_newValue = newValue;
		}
    	
    	public Object getOldValue()
		{
			return m_oldValue;
		}
    	
    	public Object getNewValue()
		{
			return m_newValue;
		}
    	
    	protected void copyNewValueFrom(PropertyChangeBase change)
    	{
    		m_newValue = ((PropertyChangeObject) change).m_newValue;
    	}
    	
    	public boolean isChanged()
    	{
    		return ! GwtUtil.equals(m_oldValue, m_newValue);
    	}
    }
}
