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

import asquare.gwt.tk.client.Util;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

/**
 * An in-browser debug console. By default you can press <code>'w'</code>
 * twice to disable this console without disabling other debug output. Uses a
 * {@link asquare.gwt.debug.client.DebugEventListener DebugEventListener} to
 * listen for the enabler key. See DebugEventListener for potential conflicts
 * with popup/dialog event filtering.
 * <p>
 * It is preferable to print debug statements through
 * {@link asquare.gwt.debug.client.Debug Debug} since a stub implementation is
 * provided to remove it from the deliverable.
 * </p>
 * 
 * @see asquare.gwt.debug.client.Debug#print(String)
 * @see asquare.gwt.debug.client.Debug#println(String)
 * @see asquare.gwt.debug.client.DebugEventListener
 */
public class DebugConsole extends DialogBox
{
	public static final char DEFAULT_ENABLE_KEY = 'w';
	
	private static DebugConsole s_instance = null;
	
	private final HTML m_content = new HTML();
	private final Enabler m_enabler = new Enabler(DEFAULT_ENABLE_KEY);
	private final Button m_disableButton = new Button();
	
	private boolean m_initialized = false;
	private boolean m_enabled = true;
	
	/**
	 * Creates the console, installs the enabler key listener. 
	 * The console is not attached to the DOM yet. 
	 */
	protected DebugConsole()
	{
		DOM.setStyleAttribute(getElement(), "border", "solid black 1px");
		DOM.setStyleAttribute(getElement(), "background", "white");
		
		setHTML("<div style='margin: 2px; padding: 3px; background-color: rgb(195, 217, 255); font-weight: bold; font-size: smaller; cursor: default;'>Debug Console</div>");
		
		m_content.setWordWrap(false);
		DOM.setStyleAttribute(m_content.getElement(), "margin", "2px");
		DOM.setStyleAttribute(m_content.getElement(), "padding", "3px");
		
		VerticalPanel outer = new VerticalPanel();

		ScrollPanel scrollPanel = new ScrollPanel(m_content);
		scrollPanel.setAlwaysShowScrollBars(true);
		scrollPanel.setSize("40em", "20em");
		outer.add(scrollPanel);
		
		HorizontalPanel controls = new HorizontalPanel();
		DOM.setStyleAttribute(controls.getElement(), "margin", "2px");
		updateDisableButtonText();
		m_disableButton.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				disable();
			}
		});
		controls.add(m_disableButton);
		controls.setCellWidth(m_disableButton, "100%");
		controls.setCellHorizontalAlignment(m_disableButton, HorizontalPanel.ALIGN_LEFT);
		Button clearButton = new Button("Clear", new ClickListener()
		{
			public void onClick(Widget sender)
			{
				clearMessages();
			}
		});
		controls.add(clearButton);
		Button hideButton = new Button("Hide", new ClickListener()
		{
			public void onClick(Widget sender)
			{
				hide();
			}
		});
		controls.add(hideButton);
		outer.add(controls);
		
		add(outer);
		int x = Window.getClientWidth() / 2 - 640 / 2;
		int y = Window.getClientHeight() / 2;
		setPopupPosition(x, y);
		
		m_enabler.install();
	}
	
	/**
	 * Get the sole instance of the console, creating if necessary. 
	 */
	public static DebugConsole getInstance()
	{
		if (s_instance == null)
		{
			s_instance = new DebugConsole();
		}
		return s_instance;
	}
	
	private void updateDisableButtonText()
	{
		m_disableButton.setText("Disable (" + m_enabler.getEnableKey() + ")");
	}
	
	/**
	 * Set the key which is pressed twice to enable/disable the console. 
	 * 
	 * @param keyCode
	 */
	public void setEnableKey(char keyCode)
	{
		m_enabler.setEnableKey(keyCode);
		updateDisableButtonText();
	}
	
	/**
	 * Enables the console. It will be shown when the next message is printed. 
	 */
	public void enable()
	{
		m_enabled = true;
	}
	
	/**
	 * Disables and hides the console. 
	 */
	public void disable()
	{
		m_enabled = false;
		hide();
	}
	
	/**
	 * Clears all messages from the console. 
	 */
	public void clearMessages()
	{
		m_content.setHTML("<PRE style='padding: 0px; margin: 0px'>Press &lsquo;w&rsquo; twice to disable/enable this window.\r\n</PRE>");
	}
	
	/**
	 * Prints a string to the console. 
	 * <em>Direct use of this method is discouraged.</em>
	 *  
	 * @param text
	 * @see Debug#print(String)
	 */
	public void print(String text)
	{
		if (m_enabled)
		{
			if (! m_initialized)
			{
				clearMessages();
				m_initialized = true;
			}
			Util.appendText(m_content.getElement(), text);
			show();
		}
	}
	
	/**
	 * Prints a string to the console, followed by a "\r\n".
	 * <em>Direct use of this method is discouraged.</em> 
	 *  
	 * @param text
	 * @see Debug#println(String)
	 */
	public void println(String text)
	{
		if (m_enabled)
		{
			print(text);
			print("\r\n");
		}
	}
	
	/**
	 * Overrides {@link PopupPanel#show() popup's} implementation to prevent event filtering ala
	 * {@link com.google.gwt.user.client.EventPreview EventPreview}
	 */
	public void show()
	{
		if (m_enabled && ! isAttached())
		{
			RootPanel.get().add(this);
		}
	}
	
	/**
	 * Overrides popup's implementation to prevent event filtering ala
	 * {@link com.google.gwt.user.client.EventPreview EventPreview}
	 */
	public void hide()
	{
		if (isAttached())
		{
			RootPanel.get().remove(this);
		}
	}

	/**
	 * Listens for the enable key and enables/disables the console. 
	 */
	private final class Enabler extends DebugEventListener
	{
		Enabler(char defaultEnableKey)
		{
			super(defaultEnableKey, 0);
		}
		
		protected void doDisabled()
		{
			DebugConsole.this.disable();
		}
		
		protected void doEnabled()
		{
			DebugConsole.this.enable();
			DebugConsole.this.show();
		}
		
		protected void doEvent(Event event)
		{
			// NOOP
		}
	}
}
