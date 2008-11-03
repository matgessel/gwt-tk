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

import java.util.ArrayList;
import java.util.List;

import asquare.gwt.sb.client.fw.ListModelEvent.ListItemPropertyChange;
import asquare.gwt.tk.client.util.GwtUtil;

public class ListModelListenerStub extends ModelListenerComplexStub implements ListModelListener
{
	public void modelChanged(ListModelEvent event)
	{
		modelChanged((ModelChangeEventComplex) event);
	}
	
	public int getItemPropertyChangeCount(String itemPropertyName)
	{
        return getItemPropertyChanges(itemPropertyName).length;
	}
	
	public ListItemPropertyChange[] getItemPropertyChanges(String itemPropertyName)
	{
		List changes = new ArrayList();
		int searchIndex = getChangeList().getIndexOfType(ListItemPropertyChange.class);
		while (searchIndex != -1)
		{
            ListItemPropertyChange candidate = (ListItemPropertyChange) getChangeList().getValue(searchIndex);
			if (candidate.getName().equals(itemPropertyName))
            {
            	changes.add(candidate);
            }
            searchIndex = getChangeList().getIndexOfType(ListItemPropertyChange.class, searchIndex + 1);
		}
		ListItemPropertyChange[] result = new ListItemPropertyChange[changes.size()];
		GwtUtil.toArray(changes, result);
		return result;
	}
	
	public ListItemPropertyChange getItemPropertyChange(String itemPropertyName)
	{
		ListItemPropertyChange[] changes = getItemPropertyChanges(itemPropertyName);
		return changes != null ? changes[0] : null;
	}
}
