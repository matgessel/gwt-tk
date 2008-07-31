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
	
	public CellRenderer getRenderer(CellId cellId)
	{
		return m_renderer;
	}
	
	public void setRenderer(CellRenderer renderer)
	{
		m_renderer = renderer;
	}
	
	public void renderCell(CellId cellId, Object item, Properties cellProperties)
	{
		getRenderer(cellId).renderCell(getCellRootElement(cellId), item, cellProperties);
	}
	
	public void prepareElement(CellId cellId, Object item, Properties cellProperties)
	{
		getRenderer(cellId).prepareElement(getCellRootElement(cellId), item, cellProperties);
	}
	
	public void renderContent(CellId cellId, Object item, Properties cellProperties)
	{
		getRenderer(cellId).renderContent(getCellRootElement(cellId), item, cellProperties);
	}
}
