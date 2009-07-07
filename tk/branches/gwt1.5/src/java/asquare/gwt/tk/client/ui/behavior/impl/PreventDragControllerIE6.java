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

import asquare.gwt.tk.client.ui.behavior.MouseEvent;
import asquare.gwt.tk.client.ui.behavior.PreventDragController;

import com.google.gwt.user.client.Event;

public class PreventDragControllerIE6 extends PreventDragController
{
	public PreventDragControllerIE6()
	{
		super(Event.ONMOUSEDOWN | Event.ONMOUSEUP);
	}
	
	public void onMouseDown(MouseEvent e)
	{
        onMouseDown0();
	}
	
	public void onMouseUp(MouseEvent e)
	{
		onMouseUp0();
	}
	
	public native void onMouseDown0() /*-{
		$doc.body.onselectstart = function() { return false; };
		$doc.body.ondragstart = function() { return false; };
	}-*/;

	public native void onMouseUp0() /*-{
		$doc.body.onselectstart = null;
		$doc.body.ondragstart = null;
	}-*/;
}
