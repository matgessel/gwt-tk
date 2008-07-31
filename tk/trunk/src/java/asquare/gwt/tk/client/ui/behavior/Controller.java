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

import com.google.gwt.user.client.ui.Widget;

/**
 * A pluggable event handler which processes delegated events from
 * {@link com.google.gwt.user.client.ui.Widget#onBrowserEvent(com.google.gwt.user.client.Event)
 * Widget.onBrowserEvent(Event)}. Controllers are typically created via a
 * factory method in the widget. Controllers can be added to and removed from
 * the widget at any time by calling {@link #plugIn(Widget)} and
 * {@link #unplug(Widget)}.
 * <p>
 * Controllers can be used to:
 * <ul>
 * <li>encapsulate browser behavioral differences (controller implementation
 * class can be instantiated via GWT.create())</li>
 * <li>install hooks for unsupported events (e.g. onselectstart)</li>
 * <li>handle dependencies</li>
 * <li>cancel events</li>
 * <li>track state of input operations (e.g. mouse state for drag operation)</li>
 * </ul>
 * <p>
 * Usage notes:
 * <ul>
 * <li>controllers should only be notified of events which are declared by 
 * {@link asquare.gwt.tk.client.ui.behavior.EventDelegate#getEventBits() getEventBits()}</li>
 * <li>controller notification order is indeterminate</li>
 * <li>controllers instantiated via deferred binding must have a default
 * constructor</li>
 * <li>stateless controllers can be shared</li>
 * </ul>
 */
public interface Controller extends BrowserEventHandler, PluggableEventHandler
{
	/**
	 * Get the id of this controller. Used for looking up a controller in a
	 * collection. Controllers with a single implementation will return the
	 * class of the controller. Controllers with multiple implementations will
	 * return the class of the interface or base class.
	 */
	public Class getId();
}
