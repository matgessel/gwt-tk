/*
 * Copyright 2009 Mat Gessel <mat.gessel@gmail.com>
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

import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.Widget;

public class MouseWheelEventImpl extends MouseEventImpl implements MouseWheelEvent
{
	private static final long serialVersionUID = 1L;
	
	protected MouseWheelEventImpl(MouseWheelEventImpl e)
	{
		super(e);
	}
	
	/**
	 * @param source the widget which is receiving the mouse event
	 * @param mouseWheelEvent a <code>mousewheel</code> event
	 * @param type the DOM event type
	 * @param previewPhase <code>true</code> if the event was caught in event preview
	 */
	public MouseWheelEventImpl(Widget source, Event mouseWheelEvent, int type, boolean previewPhase)
	{
		super(source, mouseWheelEvent, type, previewPhase);
	}
	
	public static boolean isMouseWheelEvent(Event domEvent)
	{
		return isMouseWheelEvent(DOM.eventGetType(domEvent));
	}
	
	public static boolean isMouseWheelEvent(int domEventType)
	{
		return (domEventType & MOUSE_WHEEL) != 0;
	}
	
	public int getMouseWheelVelocityY()
	{
	    return getDomEvent().getMouseWheelVelocityY();
	}

    protected String dumpProperties()
    {
        return "velocityY=" + getMouseWheelVelocityY() + ',' + super.dumpProperties();
    }

    /*
     * We're not really a mouse event. The following methods are not exposed in the
     * interface and should never be called.
     */
    @Deprecated
    public void preventDefault() { throw new UnsupportedOperationException(); }

    @Deprecated
    public void stopPropagation() { throw new UnsupportedOperationException(); }

    @Deprecated
    public boolean isAltDown() { throw new UnsupportedOperationException(); }

    @Deprecated
    public boolean isControlDown() { throw new UnsupportedOperationException(); }

    @Deprecated
    public boolean isMetaDown() { throw new UnsupportedOperationException(); }

    @Deprecated
    public boolean isShiftDown() { throw new UnsupportedOperationException(); }

    @Deprecated
    public Element getFrom() { throw new UnsupportedOperationException(); }

    @Deprecated
    public Element getTo() { throw new UnsupportedOperationException(); }
}
