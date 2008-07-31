package asquare.gwt.sb.client.fw;

import asquare.gwt.sb.client.fw.ModelChangeEventComplex.ChangeBase;
import asquare.gwt.sb.client.util.IntRange;

public abstract class IndexedChangeBase extends ChangeBase
{
    private IntRange m_range;
	
	public IndexedChangeBase(Class type, int index, int count)
    {
        super(type);
        m_range = new IntRange(index, count);
    }
	
	public int getIndex()
	{
		return m_range.getStartIndex();
	}
	
    public int getCount()
	{
		return m_range.getLength();
	}
    
	public int getEndIndex()
	{
		return m_range.getEndIndex();
	}
	
	public abstract boolean addChange(ChangeBase change);
	
	protected boolean addListChange(IndexedChangeBase change)
    {
        if (m_range.intersectsOrNeighbors(change.m_range))
        {
    		m_range.add(change.m_range);
    		return true;
        }
        return false;
    }
	
	public boolean rangeEquals(IndexedChangeBase change)
	{
		return m_range.equals(change.m_range);
	}
	
    public int hashCode()
    {
        return super.hashCode() * 31 + m_range.hashCode();
    }
    
    public boolean equals(Object obj)
    {
    	return obj == this || obj instanceof IndexedChangeBase && equals((IndexedChangeBase) obj);
    }
    
    public boolean equals(IndexedChangeBase change)
    {
    	return super.equals(change) && m_range.equals(change.m_range);
    }
    
    public String toString()
    {
    	return super.toString() + m_range;
    }
}
