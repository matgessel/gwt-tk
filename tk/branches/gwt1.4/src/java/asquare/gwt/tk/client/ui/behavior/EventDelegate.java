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
package asquare.gwt.tk.client.ui.behavior;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

/**
 * An EventDelegate processes events from a widget.
 * <p>
 * Example: <br/> To override default focus behavior you need to preview and
 * cancel tab events. The KeyboardListener interface does not receive the actual
 * events. EventPreview is really intended for popups and dialogs and will not
 * function if another popup/dialog is shown (think a non-modal dialog or popup
 * without autoclose). A solution is to have widget can delegate events to a
 * focus manager implementing EventDelegate.
 * </p>
 * @deprecated use {@link BrowserEventHandler}
 */
public interface EventDelegate extends EventInterest
{
	/**
	 * Call this from your widget's onBrowserEvent() method 
	 * to to delegate events. 
	 * 
	 * <p>Example: <p> 
	 * <pre>
	 * public void onBrowserEvent(Event event)
	 * {
	 *   super.onBrowserEvent(event);
	 *   delegate.onBrowserEvent(this, event);
	 * }
	 * </pre>
	 * 
	 * @param widget the wiget that originated the event
	 * @param event the event
	 */
	void onBrowserEvent(Widget widget, Event event);
}
