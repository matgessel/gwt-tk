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

/**
 * An interface for implementing mouse actions. 
 */
public abstract interface DragGesture
{
	/**
	 * Called at beginning of the input operation. This may be triggered by a
	 * mousedown, or may be triggered by a hotkey during the middle of a drag.
	 */
	void start(MouseEvent e);
	
	/**
	 * Called for each mouse movement during the input operation.
	 */
	void step(DragEvent e);
	
	/**
	 * Called at the end of the input operation, after the last movement. This
	 * may be triggered by a mouseup or may be triggered by the release of a
	 * hotkey during a drag.
	 */
	void finish();
}
