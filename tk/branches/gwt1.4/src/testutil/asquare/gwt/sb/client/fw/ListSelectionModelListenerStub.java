/*
 * Copyright 2008 Mat Gessel <mat.gessel@gmail.com>
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

import asquare.gwt.sb.client.util.RangeCollection;

public class ListSelectionModelListenerStub extends ModelListenerStub implements ListSelectionModelListener
{
	private RangeCollection m_selectedRanges;
	private RangeCollection m_deselectedRanges;

	public RangeCollection getSelectedRanges()
	{
		return m_selectedRanges;
	}
	
	public RangeCollection getDeselectedRanges()
	{
		return m_deselectedRanges;
	}
	
	public void init()
	{
		super.init();
		m_selectedRanges = null;
		m_deselectedRanges = null;
	}
	
	public void listSelectionModelChanged(ListSelectionModelEvent e)
	{
		m_selectedRanges = e.getSelectedRanges();
		m_deselectedRanges = e.getDeselectedRanges();
		modelChanged(e);
	}
}
