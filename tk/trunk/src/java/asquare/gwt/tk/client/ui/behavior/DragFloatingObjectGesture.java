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

import com.google.gwt.user.client.ui.UIObject;

/**
 * A DragGesture which absoutely positions a target widget.
 */
public class DragFloatingObjectGesture extends AdjustObjectGesture
{
	public DragFloatingObjectGesture()
	{
		super();
	}
	
	/**
	 * @param target the widget to be dragged
	 */
	public DragFloatingObjectGesture(UIObject target)
	{
		super(target, target);
	}
	
	public void step(DragEvent e)
	{
		setLeft(e.getDeltaX());
		setTop(e.getDeltaY());
	}
}
