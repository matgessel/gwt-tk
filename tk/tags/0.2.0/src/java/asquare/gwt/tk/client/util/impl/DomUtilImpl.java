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
package asquare.gwt.tk.client.util.impl;

public class DomUtilImpl
{
	public native int getViewportWidth() /*-{
		if ($doc.compatMode == "BackCompat")
		{
			return $doc.body.clientWidth;
		}
		return $doc.documentElement.clientWidth;
	}-*/;
	
	public native int getViewportHeight() /*-{
		if ($doc.compatMode == "BackCompat")
		{
			return $doc.body.clientHeight;
		}
		return $doc.documentElement.clientHeight;
	}-*/;
	
	public native int getDocumentScrollWidth() /*-{
		if ($doc.compatMode == "BackCompat")
		{
			return $doc.body.scrollWidth;
		}
		return $doc.documentElement.scrollWidth;
	}-*/;
	
	public native int getDocumentScrollHeight() /*-{
		if ($doc.compatMode == "BackCompat")
		{
			return $doc.body.scrollHeight;
		}
		return $doc.documentElement.scrollHeight;
	}-*/;
	
	public native boolean isQuirksMode() /*-{
		return $doc.compatMode == "BackCompat";
	}-*/;
	
	public native boolean isMac() /*-{
		return navigator.userAgent.toLowerCase().indexOf("mac") != -1;
	}-*/;
	
	public native boolean isWin() /*-{
		return navigator.userAgent.toLowerCase().indexOf("win") != -1;
	}-*/;
}
