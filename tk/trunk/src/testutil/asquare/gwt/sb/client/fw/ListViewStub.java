package asquare.gwt.sb.client.fw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import asquare.gwt.sb.client.util.Properties;

import com.google.gwt.user.client.Element;

public class ListViewStub implements ListView
{
	private static final String PROPERTY_MODELOBJECT = "modelObject";
	
	private final ArrayList mItems = new ArrayList();
	
	public Object getModelElement(int index)
	{
		return getItem(index).get(PROPERTY_MODELOBJECT);
	}
	
	public Object getProperty(int index, String property)
	{
		return getItem(index).get(property);
	}
	
	private Map getItem(int index)
	{
		return (Map) mItems.get(index);
	}
	
	public void add(Object modelElement, Properties cellProperties)
	{
		insert(new IndexedCellIdImpl(getSize()), modelElement, cellProperties);
	}

	public void insert(IndexedCellId cellId, Object modelElement, Properties cellProperties)
	{
		Map map = new HashMap();
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

	public void prepareElement(CellId cellId, Object modelElement, Properties cellProperties)
	{
		if (cellProperties != null)
		{
			Map item = getItem(((IndexedCellId) cellId).getIndex());
			item.put(ListCellRenderer.PROPERTY_EVEN, Boolean.valueOf(cellProperties.getBoolean(ListCellRenderer.PROPERTY_EVEN)));
			item.put(ListCellRenderer.PROPERTY_FIRST, Boolean.valueOf(cellProperties.getBoolean(ListCellRenderer.PROPERTY_FIRST)));
			item.put(ListCellRenderer.PROPERTY_LAST, Boolean.valueOf(cellProperties.getBoolean(ListCellRenderer.PROPERTY_LAST)));
			item.put(ListCellRenderer.PROPERTY_ODD, Boolean.valueOf(cellProperties.getBoolean(ListCellRenderer.PROPERTY_ODD)));
			item.put(ListCellRenderer.PROPERTY_ACTIVE, Boolean.valueOf(cellProperties.getBoolean(ListCellRenderer.PROPERTY_ACTIVE)));
			item.put(ListCellRenderer.PROPERTY_DISABLED, Boolean.valueOf(cellProperties.getBoolean(ListCellRenderer.PROPERTY_DISABLED)));
			item.put(ListCellRenderer.PROPERTY_HOVER, Boolean.valueOf(cellProperties.getBoolean(ListCellRenderer.PROPERTY_HOVER)));
			item.put(ListCellRenderer.PROPERTY_SELECTED, Boolean.valueOf(cellProperties.getBoolean(ListCellRenderer.PROPERTY_SELECTED)));
		}
	}

	public void renderContent(CellId cellId, Object modelElement, Properties cellProperties)
	{
		getItem(((IndexedCellId) cellId).getIndex()).put(PROPERTY_MODELOBJECT, modelElement);
	}

	public void renderCell(CellId cellId, Object modelElement, Properties cellProperties)
	{
		prepareElement(cellId, modelElement, cellProperties);
		renderContent(cellId, modelElement, cellProperties);
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