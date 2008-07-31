/*
 * Copyright 2008 Mat Gessel <mat.gessel@gmail.com>
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

public interface AdjustPositionGesture extends DragGesture
{
	public static final AdjustPositionGesture NULL = new AdjustPositionGesture()
	{
		public void start(MouseEvent event)
		{
		}
		
		public void step(DragEvent event)
		{
		}
		
		public void finish()
		{
		}
		
		public void adjustTop(int delta)
		{
			// NOOP
		}
		
		public void adjustLeft(int delta)
		{
			// NOOP
		}
	};
	
	/**
	 * @param delta the difference from the starting left to the desired left position
	 */
	void adjustLeft(int delta);
	
	/**
	 * @param delta the difference from the starting top to the desired top position
	 */
	void adjustTop(int delta);
}
