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

/**
 * A controller which:
 * <ul>
 * <li>converts native DOM mouse events to library {@link EventBase Event}s</li>
 * <li>implements event handler interfaces (i.e. handler adaptor) and provides
 * empty implementations of event methods</li>
 * <li>can delegate events to child handlers</li>
 * </ul>
 * <p>
 * <em>Note: child handlers should be added/removed <strong>before</strong> the controller 
 * is added to the widget so that events are properly sunk on the widget.<em>
 * <p>
 * TODO: add support for all event types
 */
public interface EventHandler extends PluggableEventHandler
{
	void processFocusGained(FocusEvent e);
	void processFocusLost(FocusEvent e);
	void processKeyDown(KeyEvent e);
	void processKeyPress(KeyEvent e);
	void processKeyUp(KeyEvent e);
	void processMouseClick(MouseEvent e);
	void processMouseDoubleClick(MouseEvent e);
	void processMouseDown(MouseEvent e);
	void processMouseMove(MouseEvent e);
	void processMouseOut(MouseEvent e);
	void processMouseOver(MouseEvent e);
	void processMouseUp(MouseEvent e);
}
