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

import asquare.gwt.sb.client.util.Properties;
import asquare.gwt.tk.client.ui.CComposite;

public abstract class CompositeCellViewBase extends CComposite implements CompositeCellView
{
	private CellRenderer m_renderer;
	
	public CompositeCellViewBase()
	{
		this(null);
	}
	
	public CompositeCellViewBase(CellRenderer cellRenderer)
	{
		m_renderer = ((cellRenderer != null) ? cellRenderer : createRenderer());
	}
	
	protected CellRenderer createRenderer()
	{
		return new CellRendererDefault();
	}
	
	public CellRenderer getRenderer()
	{
		return m_renderer;
	}
	
	public void setRenderer(CellRenderer renderer)
	{
		m_renderer = renderer;
	}
	
	/**
	 * Template method which allows the view to override the default renderer
	 * for a particular cell.
	 */
	protected CellRenderer getRenderer(CellId cellId, Object modelElement, Properties cellProperties)
	{
		return m_renderer;
	}
	
	public void renderCell(CellId cellId, Object modelElement, Properties cellProperties)
	{
		getRenderer(cellId, modelElement, cellProperties).renderCell(getCellRootElement(cellId), modelElement, cellProperties);
	}
	
	public void prepareElement(CellId cellId, Object modelElement, Properties cellProperties)
	{
		getRenderer(cellId, modelElement, cellProperties).prepareElement(getCellRootElement(cellId), modelElement, cellProperties);
	}
	
	public void renderContent(CellId cellId, Object modelElement, Properties cellProperties)
	{
		getRenderer(cellId, modelElement, cellProperties).renderContent(getCellRootElement(cellId), modelElement, cellProperties);
	}
}
