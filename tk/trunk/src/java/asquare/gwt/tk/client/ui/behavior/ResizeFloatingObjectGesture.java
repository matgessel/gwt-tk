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
import com.google.gwt.user.client.ui.Widget;

public class ResizeFloatingObjectGesture
{
	private ResizeFloatingObjectGesture()
	{
		// non-instantiable
	}
	
	public static class NW extends AdjustObjectGesture
	{
		public NW(UIObject floater, UIObject content)
		{
			super(floater, content);
		}
		
		public void step(DragEvent e)
		{
			setWidth(-e.getDeltaX());
			setLeft(e.getDeltaX());
			setHeight(-e.getDeltaY());
			setTop(e.getDeltaY());
		}
	}
	
	public static class N extends AdjustObjectGesture
	{
		public N(Widget floater, UIObject content)
		{
			super(floater, content);
		}
		
		public void step(DragEvent e)
		{
			setHeight(-e.getDeltaY());
			setTop(e.getDeltaY());
		}
	}
	
	public static class NE extends AdjustObjectGesture
	{
		public NE(Widget floater, UIObject content)
		{
			super(floater, content);
		}
		
		public void step(DragEvent e)
		{
			setHeight(-e.getDeltaY());
			setTop(e.getDeltaY());
			setWidth(e.getDeltaX());
		}
	}
	
	public static class W extends AdjustObjectGesture
	{
		public W(Widget floater, UIObject content)
		{
			super(floater, content);
		}
		
		public void step(DragEvent e)
		{
			setWidth(-e.getDeltaX());
			setLeft(e.getDeltaX());
		}
	}
	
	public static class E extends AdjustObjectGesture
	{
		public E(Widget floater, UIObject content)
		{
			super(floater, content);
		}
		
		public void step(DragEvent e)
		{
			setWidth(e.getDeltaX());
		}
	}
	
	public static class SW extends AdjustObjectGesture
	{
		public SW(Widget floater, UIObject content)
		{
			super(floater, content);
		}
		
		public void step(DragEvent e)
		{
			setLeft(e.getDeltaX());
			setWidth(-e.getDeltaX());
			setHeight(e.getDeltaY());
		}
	}
	
	public static class S extends AdjustObjectGesture
	{
		public S(Widget floater, UIObject content)
		{
			super(floater, content);
		}
		
		public void step(DragEvent e)
		{
			setHeight(e.getDeltaY());
		}
	}
	
	public static class SE extends AdjustObjectGesture
	{
		public SE(Widget floater, UIObject content)
		{
			super(floater, content);
		}
		
		public void step(DragEvent e)
		{
			setWidth(e.getDeltaX());
			setHeight(e.getDeltaY());
		}
	}
}
