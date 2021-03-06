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
package asquare.gwt.sb.client.util;

/**
 * TODO: shouldn't action get a refrence to it's caller? Useful for returning
 * focus to a button after closing a dialog triggered by the action. Possibly
 * refactory UICommand.execute() to receive source reference.
 */
public interface Action extends UICommand
{
	boolean isEnabled();
	
	void setEnabled(boolean enabled);
	
	void addListener(ActionPropertyListener listener);
	
	void removeListener(ActionPropertyListener listener);
}
