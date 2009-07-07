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

import java.util.List;

import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Widget;

/**
 * An interface implemented by widgets which support event handling via
 * controllers.
 */
public interface ControllerSupport extends EventListener
{
	/**
	 * Sets the controllers which will process events on this widget. Clears out
	 * the current controllers, if any. When the widget is added to the DOM,
	 * received events will be delegated to each controller which declares the
	 * event's type via 
	 * {@link asquare.gwt.tk.client.ui.behavior.EventDelegate#getEventBits() getEventBits()}.
	 * 
	 * @param controllers a list of 0 or more controllers, or <code>null</code>
	 */
	void setControllers(List<Controller> controllers);
	
	/**
	 * Gets a controller with the specified id. The id is often an interface or
	 * base class, as controllers can have multiple implementations. Used to
	 * modify the behavior of existing widgets. 
	 * 
	 * @param id a Class identifying the type of controller to get
	 * @return the first controller matching the id, or <code>null</code>
	 */
	Controller getController(Class<? extends Controller> id);
	
	/**
	 * Adds a controller to process events on this widget. Multiple controllers
	 * may be added.
	 * 
	 * @param controller a controller to add
	 * @return the widget this call was made on (for convenience)
	 */
	Widget addController(Controller controller);
	
	/**
	 * Removes a controller
	 * 
	 * @param controller a controller to remove
	 * @return the widget this call was made on (for convenience)
	 * @throws IllegalArgumentException if <code>controller</code> is not present
	 */
	Widget removeController(Controller controller);
}
