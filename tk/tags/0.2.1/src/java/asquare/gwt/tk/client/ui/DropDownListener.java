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
package asquare.gwt.tk.client.ui;

/**
 * Implement if you wish to be notified of a DropDownPanel opening/closing.
 * Useful if you need to modify your layout to accommodate changes in the DropDownPanel.
 */
public interface DropDownListener
{
	/**
	 * Called after a DropDownPanel's content element is made visible. 
	 * 
	 * @param sender the panel which was opened
	 */
	void dropDownOpened(DropDownPanel sender);
	
	/**
	 * Called after a DropDownPanel's content element is hidden. 
	 * 
	 * @param sender the panel which was closed
	 */
	void dropDownClosed(DropDownPanel sender);
}
