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
package asquare.gwt.tkdemo.client.ui;

import java.util.*;

import asquare.gwt.tk.client.ui.*;
import asquare.gwt.tk.client.ui.behavior.*;

import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;

/**
 * A widget which supports
 * {@link com.google.gwt.user.client.ui.ClickListener ClickListener}s and
 * prevents selection from repeated clicking.
 * 
 * @see LegacyClickController
 */
public class ClickWidget extends CWidget implements HasText, HasHTML, SourcesClickEvents
{
	public ClickWidget()
	{
		this(DOM.createDiv());
	}
	
	public ClickWidget(Element element)
	{
		super(element);
	}
	
	protected List createControllers()
	{
        List result = new ArrayList();
		result.add(ControlSurfaceController.getInstance());
		return result;
	}
	
	public String getText()
	{
		return DOM.getInnerText(getElement());
	}
	
	public void setText(String text)
	{
		DOM.setInnerText(getElement(), text);
	}
	
	public String getHTML()
	{
		return DOM.getInnerHTML(getElement());
	}
	
	public void setHTML(String html)
	{
		DOM.setInnerHTML(getElement(), html);
	}
	
	// SourcesClickEvents methods
	public void addClickListener(ClickListener listener)
	{
		CClickListenerAdaptor.addListener(this, listener);
	}
	
	public void removeClickListener(ClickListener listener)
	{
        CClickListenerAdaptor.removeListener(this, listener);
	}
}
