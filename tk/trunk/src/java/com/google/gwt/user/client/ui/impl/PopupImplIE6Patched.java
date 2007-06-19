/*
 * Copyright 2006 Google Inc.
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
package com.google.gwt.user.client.ui.impl;

import com.google.gwt.user.client.Element;

/**
 * Internet Explorer 6 implementation of
 * {@link com.google.gwt.user.client.ui.impl.PopupImpl}.
 * <p>
 * <strong>NOTICE</strong>: this class was modified from {@link com.google.gwt.user.client.ui.impl.PopupImplIE6 PopupImplIE6} to  
 * <ul>
 *   <li>eliminate scrollbar jumping and flicker when dialogs are shown
 *   <li>enable popup transparency
 *   <li>eliminate mixed content warning in https pages
 * </ul>
 * </p>
 * @see <a href="http://weblogs.asp.net/bleroy/archive/2005/08/09/how-to-put-a-div-over-a-select-in-ie.aspx">How to put a DIV over a SELECT in IE?</a>
 */
public class PopupImplIE6Patched extends PopupImplIE6
{
	public native void onShow(Element popup) /*-{
		var frame = $doc.createElement('iframe');
		frame.scrolling = 'no';
		frame.frameBorder = 0;
		frame.style.position = 'absolute';
		frame.src = 'javascript:\'<html></html>\''; // https fix
		frame.style['filter'] = 'alpha(opacity=0)'; // transparency fix
		
		popup.__frame = frame;
		frame.__popup = popup;
		
		// takes effect immediately (jumping iframe fix)
		frame.style['left'] = popup.offsetLeft;
		frame.style['top'] = popup.offsetTop;
		frame.style['width'] = popup.offsetWidth;
		frame.style['height'] = popup.offsetHeight;
		frame.style['zIndex'] = popup.style.zIndex;
		
		// updates position and dimensions as popup is moved & resized
		frame.style.setExpression('left', 'this.__popup.offsetLeft');
		frame.style.setExpression('top', 'this.__popup.offsetTop');
		frame.style.setExpression('width', 'this.__popup.offsetWidth');
		frame.style.setExpression('height', 'this.__popup.offsetHeight');
		frame.style.setExpression('zIndex', 'this.__popup.style.zIndex');
		popup.parentElement.insertBefore(frame, popup);
	}-*/;
}
