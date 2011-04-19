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

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;

/**
 * A UI event monitor. Event descriptions are output via
 * {@link asquare.gwt.debug.client.Debug#println(String)}. Tracing is
 * enabled/disabled by pressing the enabler key twice sequentially. You can
 * specify which kinds of events you are interested in when you create the
 * monitor.
 * <p>
 * Popups and dialogs will block the listener if they are shown after it is
 * installed. Likewise, the listener will prevent with event filtering by popups
 * if initialized after the popup is shown. This is due to the design of
 * {@link com.google.gwt.user.client.EventPreview EventPreview}.
 * </p>
 * 
 * @see #DEFAULT_ENABLE_KEY
 * @see #DEFAULT_MASK
 * @see com.google.gwt.user.client.EventPreview
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
	
	private final Handler m_handler = new Handler();
	
	private final String m_name;
	
	private int m_eventMask;
	
	private char m_enableKey;
	
	private boolean m_enabled = false;
	
	private char m_lastKeyDown = 0;
	
	/**
	 * Create a new instance with the default key code and event mask. 
	 */
	public DebugEventListener()
	{
		this((char) 0, -1, null);
	}
	
	/**
	 * Create a new instance with the specified enable key and the default event mask. 
	 * 
	 * @param enableKey a key code, or -1 for the default (<code>'e'</code>)
	 */
	public DebugEventListener(char enableKey)
	{
		this(enableKey, -1, null);
	}
	
	/**
	 * Create a new instance with the specified event mask and the default enable key. 
	 * 
	 * @param eventMask an event mask or -1 for the default
	 * @see Event
	 */
	public DebugEventListener(int eventMask)
	{
		this((char) 0, eventMask, null);
	}
	
	/**
	 * Create a new instance with the specified enable key and event mask. 
	 * 
	 * @param enableKey a key code, or 0 for the default (<code>'e'</code>)
	 * @param eventMask an event mask or -1 for the default
	 * @param name a name to identify this event listener instance in debug statements
	 * @see Event
	 */
	public DebugEventListener(char enableKey, int eventMask, String name)
	{
		m_enableKey = (enableKey > 0) ? enableKey : DEFAULT_ENABLE_KEY;
		m_enableKey = (Character.toUpperCase(m_enableKey));
		m_eventMask = (eventMask != -1) ? eventMask : DEFAULT_MASK; // negative values are possible
		m_name = (name != null) ? name : "Event monitor";
	}
	
	/**
	 * Installs this listener so that it listens to dispatched events. It will
	 * not process events or produce output until it is enabled by pressing the
	 * enable key twice or via {@link #enable(boolean)}.
	 * 
	 *  @throws IllegalStateException if already installed
	 */
	public void install()
	{
		m_handler.init();
	}
	
	/**
	 *  @throws IllegalStateException if not installed
	 */
	public void uninstall()
	{
		m_handler.dispose();
	}
	
	/**
	 * Get the name of this tool
	 */
	public String getName()
	{
		return m_name;
	}
	
	/**
	 * Get the key used to enable processing of events. 
	 * 
	 * @return a unicode character code corresponding the key
	 */
	public char getEnableKey()
	{
		return m_enableKey;
	}
	
	/**
	 * Set the key used to enable processing of events. 
	 * 
	 * @param keyCode a unicode character code corresponding the key
	 */
	public void setEnableKey(char keyCode)
	{
		m_enableKey = keyCode;
	}
	
	/**
	 * Get the bitmask representing the events which this listener will process.
	 * 
	 * @return the bitmask
	 * @see Event
	 */
	public int getEventMask()
	{
		return m_eventMask;
	}
	
	/**
	 * Specifies the events which the listener will process when it is enabled. 
	 * Pass <code>0</code> to ignore all events.
	 * <p>
	 * Example: 
	 * <pre>setEventMask(Event.ONMOUSEDOWN | Event.ONCLICK)</pre> 
	 * 
	 * @param mask a bitmask of event types
	 * @see Event
	 */
	public void setEventMask(int mask)
	{
		m_eventMask = mask;
	}
	
	/**
	 * Enables/disables this listener. When enabled it will process events
	 * via {@link #doEvent(Event)}
	 * 
	 * @param enable
	 */
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
	
	/**
	 * Sets this listener as enabled/disabled but does not perform the
	 * {@link #doEnabled()}/{@link #doDisabled()} action.
	 * 
	 * @param enabled
	 */
	public void setEnabled(boolean enabled)
	{
		m_enabled = enabled;
	}
	
	/**
	 * Called by when an event is previewed.
	 */
	public void eventDispatched(NativeEvent event)
	{
		Event event0 = Event.as(event);
		
		/* 
		 * onKeyDown works with invisible characters (e.g. caps lock)
		 * onKeyPress works with visible characters (i.e. [a-zA-Z0-9~!@#$%^&*()_+])
		 */
		int eventType = Event.getTypeInt(event0.getType());
		if (eventType == Event.ONKEYDOWN)
		{
			char keyCode = (char) event0.getKeyCode();
			if (isSameKey(keyCode, m_lastKeyDown) && isSameKey(keyCode, m_enableKey))
			{
				m_lastKeyDown = 0;
				enable(! m_enabled);
			}
			else
			{
				m_lastKeyDown = keyCode;
			}
		}
		
		if (m_enabled && (m_eventMask & eventType) != 0)
		{
			doEvent(event0);
		}
	}
	
	/**
	 * Compares two character codes, accounting for case. 
	 * Calling <code>isSameKey('a', 'A')</code> would return true. 
	 * 
	 * @param a
	 * @param b
	 * @return true if both keycodes are generated from the same key
	 */
	private boolean isSameKey(char a, char b)
	{
		if (a == b)
			return true;
		
		if (Character.isLetter(a) && Character.isLetter(b))
		{
			return (Character.toLowerCase(a) == Character.toLowerCase(b));
		}
		
		return false; 
	}
	
	/**
	 * Override to perform a custom action whenever this listener is disabled. 
	 */
	protected void doEnabled()
	{
		Debug.println("(" + m_enableKey + ") " + m_name + " enabled for " + DebugUtil.prettyPrintEventMask(m_eventMask));
	}
	
	/**
	 * Override to perform a custom action whenever this listener is enabled. 
	 */
	protected void doDisabled()
	{
		Debug.println("(" + m_enableKey + ") " + m_name + " disabled");
	}
	
	/**
	 * Override to perform a custom action for each event. 
	 * 
	 * @param event
	 */
	protected void doEvent(Event event)
	{
		Debug.println(DebugUtil.prettyPrintEvent(event));
	}
	
	private class Handler implements NativePreviewHandler
	{
		private HandlerRegistration m_handlerRegistration;
		
		public void init()
		{
			if (m_handlerRegistration != null)
				throw new IllegalStateException();
			
			m_handlerRegistration = Event.addNativePreviewHandler(this);
		}
		
		public void dispose()
		{
			if (m_handlerRegistration != null)
				throw new IllegalStateException();
			
			m_handlerRegistration.removeHandler();
		}
		
		public void onPreviewNativeEvent(NativePreviewEvent event)
		{
			eventDispatched(event.getNativeEvent());
		}
	}
}
