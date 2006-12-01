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
package asquare.gwt.tk.client.ui;

import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A panel which wraps a single widget. This differs from
 * {@link com.google.gwt.user.client.ui.Composite Composite} in that BorderPanel
 * wraps the widget's element with its own element.
 * 
 * <h3>CSS Style Rules</h3>
 * <ul class='css'>
 * <li>.tk-BorderPanel { }</li>
 * </ul>
 */
public class BorderPanel extends SimplePanel
{
	/**
	 * Creates an empty BorderPanel. Call
	 * {@link SimplePanel#setWidget(com.google.gwt.user.client.ui.Widget) setWidget()} to
	 * set the widget.
	 */
	public BorderPanel()
	{
		this(null);
	}
	
	/**
	 * Creates an empty BorderPanel with the specified child widget. 
	 */
	public BorderPanel(Widget child)
	{
		addStyleName("tk-BorderPanel");
		if (child != null)
		{
			setWidget(child);
		}
	}
	
	/**
	 * Sets a unique id for referencing this specific panel. 
	 * 
	 * @param id a unique id
	 * @see DomUtil#setId(com.google.gwt.user.client.ui.UIObject, String)
	 */
	public void setId(String id)
	{
		DomUtil.setId(this, id);
	}
}
