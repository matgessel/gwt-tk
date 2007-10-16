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
package asquare.gwt.tk.client.ui.behavior.impl;

import asquare.gwt.tk.client.ui.behavior.*;

import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;

public class PreventDragControllerIE6 extends PreventDragController
{
	public PreventDragControllerIE6()
	{
		super(0);
	}
	
	public void plugIn(Widget widget)
	{
		installHook(widget.getElement());
	}
	
	public void unplug(Widget widget)
	{
		uninstallHook(widget.getElement());
	}
	
	private native void installHook(Element element) /*-{
		element.ondragstart = function() { return false; };
	}-*/;
	
	private native void uninstallHook(Element element) /*-{
		element.ondragstart = null;
	}-*/;
}
