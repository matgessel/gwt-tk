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

public class EditHistoryListenerCollection extends ArrayList<EditHistoryListener>
{
	private static final long serialVersionUID = -8965877059437741858L;

	public void fireUpdate(EditHistoryModel model)
	{
		if (size() == 0)
			return;
		
		Object[] listeners = toArray();
		for (int i = 0; i < listeners.length; i++)
		{
			((EditHistoryListener) listeners[i]).updateHistory(model);
		}
	}
	
//	public void fireUndoPosted(EditHistoryModel model)
//	{
//		if (size() == 0)
//			return;
//		
//		Object[] listeners = toArray();
//		for (int i = 0; i < listeners.length; i++)
//		{
//			((EditHistoryListener) listeners[i]).undoPosted(model);
//		}
//	}
//	
//	public void fireRedoPosted(EditHistoryModel model)
//	{
//		if (size() == 0)
//			return;
//		
//		Object[] listeners = toArray();
//		for (int i = 0; i < listeners.length; i++)
//		{
//			((EditHistoryListener) listeners[i]).redoPosted(model);
//		}
//	}
}
