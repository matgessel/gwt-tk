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
 * Implement if you wish to be notified of events in a {@link UrlLocation}
 * widget.
 */
public interface URLListener
{
	/**
	 * Called when a url has been submitted in the {@link UrlLocation}. This
	 * does not necessarily indicate a change in the url value has (e.g. the
	 * user presses Enter, waits and presses Enter in the field again because
	 * connection is too slow).
	 * 
	 * @param source the UrlLocation
	 */
	void urlEntered(UrlLocation source);
}
