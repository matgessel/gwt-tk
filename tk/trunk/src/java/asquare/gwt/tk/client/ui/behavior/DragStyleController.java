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
package asquare.gwt.tk.client.ui.behavior;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

/**
 * A controller which applies the specified style name to the widget while the
 * mouse is down on the widget. The stylename <code>-dragging</code> will be
 * automatically applied if no style name is specified. E.g.
 * <code>.ModalDialog</code> will become
 * <code>.ModalDialog .ModalDialog-dragging</code>.
 */
public class DragStyleController extends EventController
{
	private static final String DEFAULT_STYLENAME = "dragging";
	
	private final Widget m_target;
	private final String m_styleName;
	
	/**
	 * @param target the widget to apply the style name to. 
	 */
	public DragStyleController(Widget target)
	{
		this(target, null);
	}
	
	/**
	 * @param target the widget to apply the style name to.
	 * @param styleName a style name to add to use, or <code>null</code> to
	 *            append a dependent style name with a suffix of
	 *            <code>-dragging</code>
	 */
	public DragStyleController(Widget target, String styleName)
	{
		super(DragStyleController.class, Event.ONMOUSEDOWN | Event.ONMOUSEUP);
		m_target = target;
		m_styleName = styleName;
	}
	
	public void onMouseDown(MouseEvent e)
	{
		if (m_styleName != null && m_styleName.length() > 0)
		{
			m_target.addStyleName(m_styleName);
		}
		else
		{
			m_target.addStyleDependentName(DEFAULT_STYLENAME);
		}
	}
	
	public void onMouseUp(MouseEvent e)
	{
		if (m_styleName != null && m_styleName.length() > 0)
		{
			m_target.removeStyleName(m_styleName);
		}
		else
		{
			m_target.removeStyleDependentName(DEFAULT_STYLENAME);
		}
	}
}
