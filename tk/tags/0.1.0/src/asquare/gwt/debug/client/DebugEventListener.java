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
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.KeyboardListenerCollection;

/**
 * A UI event monitor. Event descriptions are output via
 * {@link asquare.gwt.debug.client.Debug#println(String)}. Tracing is
 * enabled/disabled by pressing the specified key twice in a row. You can
 * specify which kinds of events you are interested when you create the monitor.
 * <p>
 * Popups and dialogs will block the listener if they are shown after it is
 * installed. Likewise, the listener will prevent with event filtering by popups
 * if initialized after the popup is shown.
 * </p>
 * 
 * @see #DEFAULT_ENABLE_KEY
 * @see #DEFAULT_MASK
 */
public class DebugEventListener
{
	/**
	 * A mask matching all events
	 */
	public static final int MASK_ALL = ~0;
	
	/**
	 * A mask matching all but onmousemove, onmouseover, onmouseout and onscroll
	 */
	public static final int DEFAULT_MASK = MASK_ALL & ~Event.ONMOUSEMOVE & ~Event.ONSCROLL & 
												~Event.ONMOUSEOVER & ~Event.ONMOUSEOUT;
	
	/**
	 * The default enabler key, which is <code>'e'</code>
	 */
	public static final char DEFAULT_ENABLE_KEY = 'e';
	
	private int m_eventMask;

	private char m_enableKey;

	private boolean m_enabled = false;

	private char m_lastKeyDown = 0;

	private char m_lastKeyPress = 0;
	
	/**
	 * Create a new instance with the default key code and event mask. 
	 */
	public DebugEventListener()
	{
		this((char) -1, -1);
	}
	
	/**
	 * Create a new instance with the specified key code and the default event mask. 
	 * 
	 * @param enableKey a key code, or -1 for the default (<code>'e'</code>)
	 */
	public DebugEventListener(char enableKey)
	{
		this(enableKey, -1);
	}
	
	/**
	 * Create a new instance with the specified event mask and the default key. 
	 * 
	 * @param eventMask an event mask or -1 for the default
	 * @see Event
	 */
	public DebugEventListener(int eventMask)
	{
		this((char) -1, eventMask);
	}
	
	/**
	 * Create a new instance with the specified key code and event mask. 
	 * 
	 * @param enableKey a key code, or -1 for the default (<code>'e'</code>)
	 * @param eventMask an event mask or -1 for the default
	 * @see Event
	 */
	public DebugEventListener(char enableKey, int eventMask)
	{
		m_enableKey = (enableKey > -1) ? enableKey : DEFAULT_ENABLE_KEY;
		m_eventMask = (eventMask != -1) ? eventMask : DEFAULT_MASK; // negative values are possible
	}
	
	public void install()
	{
		EventPreviewDispatcher.addListener(this);
	}
	
	public void uninstall()
	{
		EventPreviewDispatcher.removeListener(this);
	}
	
	public char getEnableKey()
	{
		return m_enableKey;
	}
	
	public void setEnableKey(char keyCode)
	{
		m_enableKey = keyCode;
	}
	
	public int getEventMask()
	{
		return m_eventMask;
	}
	
	public void setEventMask(int mask)
	{
		m_eventMask = mask;
	}
	
	public void enable(boolean enable)
	{
		if (m_enabled != enable)
		{
			m_enabled = enable;
			if (m_enabled)
			{
				doEnabled();
			}
			else
			{
				doDisabled();
			}
		}
	}
	
	public void eventDispatched(Event event)
	{
		/* 
		 * onKeyDown works with invisible characters (e.g. caps lock)
		 * onKeyPress works with visible characters (i.e. [a-zA-Z0-9~!@#$%^&*()_+...])
		 */
		int eventType = DOM.eventGetType(event);
		if (eventType == Event.ONKEYDOWN || eventType == Event.ONKEYPRESS)
		{
			char keyCode = DOM.eventGetKeyCode(event);
			if (keyCode == m_lastKeyDown && keyCode == m_enableKey || 
				keyCode == m_lastKeyPress && keyCode == m_enableKey)
			{
				m_lastKeyDown = 0;
				m_lastKeyPress = 0;
				enable(! m_enabled);
			}
			else
			{
				m_lastKeyDown = (eventType == Event.ONKEYDOWN) ? keyCode : m_lastKeyDown;
				m_lastKeyPress = (eventType == Event.ONKEYPRESS) ? keyCode : m_lastKeyPress;
			}
		}
		
		if (m_enabled && (m_eventMask & eventType) != 0)
		{
			doEvent(event);
		}
	}
	
	protected void doEnabled()
	{
		Debug.println("(" + m_enableKey + ") event monitoring enabled for mask: " + m_eventMask);
	}
	
	protected void doDisabled()
	{
		Debug.println("(" + m_enableKey + ") event monitoring disabled for mask: " + m_eventMask);
	}
	
	protected void doEvent(Event event)
	{
		int eventType = DOM.eventGetType(event);
		String string;
		switch(eventType)
		{
			case Event.ONKEYDOWN: 
				string = "event[type=onKeyDown,keyCode=" + DOM.eventGetKeyCode(event) + ",modifiers=" + KeyboardListenerCollection.getKeyboardModifiers(event) + "]";
				break;
				
			case Event.ONKEYUP: 
				string = "event[type=onKeyUp,keyCode=" + DOM.eventGetKeyCode(event) + ",modifiers=" + KeyboardListenerCollection.getKeyboardModifiers(event) + "]";
				break;
				
			case Event.ONKEYPRESS: 
				string = "event[type=onKeyPress,keyCode=" + DOM.eventGetKeyCode(event) + ",modifiers=" + KeyboardListenerCollection.getKeyboardModifiers(event) + "]";
				break;
				
			case Event.ONCHANGE: 
				string = "event[type=onChange,widget=" + DOM.eventGetTarget(event) + "]";
				break;
				
			case Event.ONFOCUS: 
				string = "event[type=onFocus,widget=" + DOM.eventGetTarget(event) + "]";
				break;
				
			case Event.ONBLUR: 
				string = "event[type=onBlur,widget=" + DOM.eventGetTarget(event) + "]";
				break;
				
			case Event.ONCLICK: 
				string = "event[type=onClick,widget=" + DOM.eventGetTarget(event) + "]";
				break;
				
			case Event.ONDBLCLICK: 
				string = "event[type=onDblClick,widget=" + DOM.eventGetTarget(event) + "]";
				break;
				
			case Event.ONERROR: 
				string = "event[type=onError,widget=" + DOM.eventGetTarget(event) + "]";
				break;
				
			case Event.ONLOAD: 
				string = "event[type=onLoad,widget=" + DOM.eventGetTarget(event) + "]";
				break;
				
			case Event.ONLOSECAPTURE: 
				string = "event[type=onLoseCapture,widget=" + DOM.eventGetTarget(event) + "]";
				break;
				
			case Event.ONMOUSEDOWN: 
				string = "event[type=onMouseDown,widget=" + DOM.eventGetTarget(event) + ",buttons=" + DOM.eventGetButton(event) + "]";
				break;
				
			case Event.ONMOUSEUP: 
				string = "event[type=onMouseUp,widget=" + DOM.eventGetTarget(event) + ",buttons=" + DOM.eventGetButton(event) + "]";
				break;
				
			case Event.ONMOUSEOVER: 
				string = "event[type=onMouseOver,widget=" + DOM.eventGetTarget(event) + "]";
				break;
				
			case Event.ONMOUSEOUT: 
				string = "event[type=onMouseOut,widget=" + DOM.eventGetTarget(event) + "]";
				break;
				
			case Event.ONMOUSEMOVE: 
				string = "event[type=onMouseMove,widget=" + DOM.eventGetTarget(event) + "]";
				break;
				
			case Event.ONSCROLL: 
				string = "event[type=onScroll,widget=" + DOM.eventGetTarget(event) + "]";
				break;
				
			default: 
				string = DOM.eventToString(event);
		}
		Debug.println(string);
	}
}
