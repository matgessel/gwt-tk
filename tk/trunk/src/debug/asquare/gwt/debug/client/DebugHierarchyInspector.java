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
package asquare.gwt.debug.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;

/**
 * Dumps the DOM containment of an element to the console. To use, put this in
 * {@link com.google.gwt.core.client.EntryPoint#onModuleLoad() onModuleLoad()}:
 * 
 * <pre>
 * new DebugHierarchyInspector().install();</pre>
 * In the browser, press 'h' twice and click on an element.
 */
public class DebugHierarchyInspector extends DebugEventListener
{
	/**
	 * The default enabler key (<code>'h'</code>).
	 */
	public static final char DEFAULT_ENABLE_KEY = 'h';
	
	public DebugHierarchyInspector()
	{
		this(DEFAULT_ENABLE_KEY, Event.ONMOUSEDOWN);
	}
	
	public DebugHierarchyInspector(char enableKey, int eventMask)
	{
		super(enableKey, eventMask, "Hierarchy inspector");
		
		// ensure Debug is not optimized out by compiler
		Debug.init();
	}
	
	@Override
	protected void doEvent(Event event)
	{
		Element target = DOM.eventGetTarget(event);
		printHierarchy(target, this);
	}
	
	/**
	 * Override this method to change the details you want to display for each
	 * element in the hierarchy.
	 * 
	 * @param element
	 * @return a string
	 */
	protected native String createDetailString(Element element) /*-{
		return "offsetLeft=" + element.offsetLeft + 
			", offsetTop=" + element.offsetTop + 
			", offsetWidth=" + element.offsetWidth + 
			", offsetHeight=" + element.offsetHeight; 
	}-*/;
	
	private native void printHierarchy(Element element, DebugHierarchyInspector inspector) /*-{
		var ELEMENT_NODE = 1;
		var prefix = "+ ";
		
		// crawl up the DOM hierarchy, stopping at the document node
		while(element && element.nodeType == ELEMENT_NODE)
		{
			var details = inspector.@asquare.gwt.debug.client.DebugHierarchyInspector::createDetailString(Lcom/google/gwt/user/client/Element;)(element);
			Debug.println(prefix + element.tagName + "(" + element.id + " " + element.className + ") {" + details + "}");
			element = element.parentNode;
			prefix = " " + prefix;
		}
	}-*/;
}
