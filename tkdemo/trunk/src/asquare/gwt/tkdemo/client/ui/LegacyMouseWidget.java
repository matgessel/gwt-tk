/*
 * Copyright 2006 Mat Gessel <mat.gessel@gmail.com>
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
package asquare.gwt.tkdemo.client.ui;

import asquare.gwt.tk.client.ui.CWidget;
import asquare.gwt.tkdemo.client.ui.behavior.LegacyMouseController;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.SourcesMouseEvents;

/**
 * A widget which supports GWT mouse listeners via a
 * {@link asquare.gwt.tk.client.ui.behavior.Controller Controller}
 * implementation.
 * 
 * @see LegacyMouseController
 */
public class LegacyMouseWidget extends CWidget implements SourcesMouseEvents
{
	public LegacyMouseWidget(Element element)
	{
		super(element);
	}
	
	public void addMouseListener(MouseListener listener)
	{
		LegacyMouseController controller = (LegacyMouseController) getController(LegacyMouseController.class);
		if (controller == null)
			addController(new LegacyMouseController());
		controller.addMouseListener(listener);
	}

	public void removeMouseListener(MouseListener listener)
	{
		LegacyMouseController controller = (LegacyMouseController) getController(LegacyMouseController.class);
		if (controller != null)
			controller.removeMouseListener(listener);
	}
}
