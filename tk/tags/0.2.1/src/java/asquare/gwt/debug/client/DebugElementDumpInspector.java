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
 * Dumps all properties of an element to the console. To use, put this in
 * {@link com.google.gwt.core.client.EntryPoint#onModuleLoad() onModuleLoad()}:
 * 
 * <pre>
 * new DebugElementDumpInspector().install();</pre>
 * In the browser, press 'd' twice and click on an element.
 */
public class DebugElementDumpInspector extends DebugEventListener
{
	/**
	 * The default enabler key (<code>'d'</code>).
	 */
	public static final char DEFAULT_ENABLE_KEY = (char) 27;
	
	public DebugElementDumpInspector()
	{
		this(DEFAULT_ENABLE_KEY, Event.ONMOUSEDOWN);
	}
	
	public DebugElementDumpInspector(char enableKey, int eventMask)
	{
		super(enableKey, eventMask, "Element Dump Inspector");
		
		// ensure Debug is not optimized out by compiler
		Debug.init();
	}
	
	protected void doEvent(Event event)
	{
		Element target = DOM.eventGetTarget(event);
		Debug.dump(target);
	}
}
