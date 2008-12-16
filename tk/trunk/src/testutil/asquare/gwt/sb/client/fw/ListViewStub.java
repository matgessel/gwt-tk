package asquare.gwt.sb.client.fw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.Element;

public class ListViewStub implements ListView
{
	private static final String PROPERTY_MODELOBJECT = "modelObject";
	
	private final ArrayList<Map<String, Object>> mItems = new ArrayList<Map<String, Object>>();
	
	public Object getModelElement(int index)
	{
		return getItem(index).get(PROPERTY_MODELOBJECT);
	}
	
	public Object getProperty(int index, String property)
	{
		return getItem(index).get(property);
	}
	
	private Map<String, Object> getItem(int index)
	{
		return mItems.get(index);
	}
	
	public void add(Object modelElement, CellProperties cellProperties)
	{
		insert(new IndexedCellIdImpl(getSize()), modelElement, cellProperties);
	}

	public void insert(IndexedCellId cellId, Object modelElement, CellProperties cellProperties)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		mItems.add(cellId.getIndex(), map);
		renderCell(cellId, modelElement, cellProperties);
	}
	
	public void remove(IndexedCellId cellId)
	{
		mItems.remove(cellId.getIndex());
	}

	public void clear()
	{
		mItems.clear();
	}

	public int getSize()
	{
		return mItems.size();
	}

	public void prepareElement(CellId cellId, Object modelElement, CellProperties cellProperties)
	{
		if (cellProperties != null)
		{
			Map<String, Object> item = getItem(((IndexedCellId) cellId).getIndex());
			String[] booleanProperties = {
				ListCellProperties.EVEN, 
				ListCellProperties.FIRST, 
				ListCellProperties.LAST, 
				ListCellProperties.ODD, 
				ListCellProperties.ACTIVE, 
				ListCellProperties.DISABLED, 
				ListCellProperties.HOVER, 
				ListCellProperties.SELECTED
			};
			
			for (String property : booleanProperties)
			{
				item.put(property, Boolean.valueOf(cellProperties.getBoolean(property)));
			}
		}
	}

	public void renderContent(CellId cellId, Object modelElement, CellProperties cellProperties)
	{
		getItem(((IndexedCellId) cellId).getIndex()).put(PROPERTY_MODELOBJECT, modelElement);
	}

	public void renderCell(CellId cellId, Object modelElement, CellProperties cellProperties)
	{
		ListCellProperties properties0 = (ListCellProperties) cellProperties;
		prepareElement(cellId, modelElement, properties0);
		renderContent(cellId, modelElement, properties0);
	}
	
	/**
	 * @deprecated
	 */
	public boolean isEnabled()
	{
		return false;
	}
	
	/**
	 * @deprecated
	 */
	public void setEnabled(boolean enabled)
	{
	}
	
	/**
	 * @deprecated
	 */
	public CellRenderer getRenderer()
	{
		return null;
	}

	/**
	 * @deprecated
	 */
	public void setRenderer(CellRenderer renderer)
	{
	}

	/**
	 * @deprecated
	 */
	public Element getCellRootElement(CellId cellId)
	{
		return null;
	}

	/**
	 * @deprecated
	 */
	public CellId getCellId(Element eventTarget)
	{
		return null;
	}
}
