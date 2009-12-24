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

public class ModelChangeSupportHeavy extends ModelChangeSupportBase
{
	public ModelChangeSupportHeavy(Object source)
	{
		super(source);
	}
	
    @Override
	protected void notifyListeners(EventListener[] listeners)
	{
		Object source = getSource();
		ModelChangeEvent event = createChangeEvent(source);
		for (int i = 0; i < listeners.length; i++)
		{
			notifyListener(listeners[i], source, event);
		}
	}
	
	/**
	 * Template method to create the event 
	 */
	protected ModelChangeEvent createChangeEvent(Object source)
	{
		return new ModelChangeEvent(source);
	}

	/**
	 * Template method to cast and notify the listener
	 */
	protected void notifyListener(EventListener listener, Object source, ModelChangeEvent event)
	{
		((ModelListener) listener).modelChanged(event);
	}
}
