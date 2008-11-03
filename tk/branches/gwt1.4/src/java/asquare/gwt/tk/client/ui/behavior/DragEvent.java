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
 * Contains information used by drag operations to position elements in the UI.
 * Different drag operations require different coordinates, so we pass all
 * coordinates to operations using this object. Also encapsulates coordinate
 * calulations.
 */
/*
 * Extends MouseEvent so that both types may be used polymorphically. For
 * example, a slider will process both mouse down and mouse drag (but not mouse
 * move).
 */
public interface DragEvent extends MouseEvent
{
	/**
	 * Get the distance moved horizontally, relative to the last event. Returns
	 * a useful value even if the document is scrolled or the widget has moved
	 * since the last event.
	 */
	int getDeltaX();

	/**
	 * Get the distance moved vertically, relative to the last event. Returns
	 * a useful value even if the document is scrolled or the widget has moved
	 * since the last event.
	 */
	int getDeltaY();

	int getCumulativeX();

	int getCumulativeY();
}
