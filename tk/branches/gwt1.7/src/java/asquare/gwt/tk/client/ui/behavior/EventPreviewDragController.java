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

/**
 * A controller which interprets drag events and delegates the mouse drag
 * operation to a delegate.
 * 
 * @see asquare.gwt.tk.client.ui.behavior.DragGesture
 */
public class EventPreviewDragController extends EventController
{
	private final EventPreviewControllerTemporary m_preview;
	
	/**
	 * Creates a new EventPreviewDragController which delegates to the specified
	 * handler.
	 * 
	 * @param dragHandler a delegate object
	 */
	public EventPreviewDragController(EventHandler dragHandler)
	{
		super(DragController.class, Event.ONMOUSEDOWN, dragHandler);
		m_preview = new PreviewController();
		addHandler(ControlSurfaceController.getInstance());
	}
	
	@Override
	public void onMouseDown(MouseEvent e)
	{
		m_preview.start(getPluggedInWidget());
	}
	
	private class PreviewController extends EventPreviewControllerTemporary
	{
		public PreviewController()
		{
			super(MouseEvent.MOUSE_UP);
		}
		
		@Override
		public int getEventBits()
		{
			return super.getEventBits() | EventPreviewDragController.this.getEventBits();
		}
		
		@Override
		public void onMouseUp(MouseEvent e)
		{
			stop();
		}
		
		@Override
		protected void processEvent(EventBase event)
		{
			try
			{
				EventPreviewDragController.this.processEvent(event);
			}
			finally
			{
				super.processEvent(event);
			}
		}
	}
}
