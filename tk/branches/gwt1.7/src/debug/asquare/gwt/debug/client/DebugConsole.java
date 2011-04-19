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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.*;
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
 * <p>
 * Opera stops displaying text after a while. This may be due to a maximum text node size limitation: 
 * <a href="http://www.quirksmode.org/bugreports/archives/2004/12/text_node_maxim.html">QuirksMode issue</a>. 
 * </p>
 * 
 * <h3>CSS Style Rules</h3>
 * <ul class='css'>
 * <li>.tk-DebugConsole { }</li>
 * </ul>
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
	private int m_left, m_top;
	
	/**
	 * Creates the console, installs the enabler key listener. 
	 * The console is not yet attached to the DOM. 
	 */
	protected DebugConsole()
	{
		super(false, false);
		setStyleName("tk-DebugConsole");
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
		controls.setWidth("100%");
		outer.add(controls);
		
		HorizontalPanel controlsLeft = new HorizontalPanel();
		controls.add(controlsLeft);
		controls.setCellHorizontalAlignment(controlsLeft, HorizontalPanel.ALIGN_LEFT);
		
		HorizontalPanel controlsRight = new HorizontalPanel();
		controls.add(controlsRight);
		controls.setCellHorizontalAlignment(controlsRight, HorizontalPanel.ALIGN_RIGHT);
		
		final Button toggleDebugButton = new Button("Toggle&nbsp;Debug");
		DOM.setElementProperty(toggleDebugButton.getElement(), "title", "Toggles output of debug statements");
		controlsLeft.add(toggleDebugButton);
		
		updateDisableButtonText();
		DOM.setElementProperty(m_disableButton.getElement(), "title", "Prevents this console from appearing when debug statements are printed");
		controlsLeft.add(m_disableButton);
		
		final Button clearButton = new Button("Clear");
		DOM.setElementProperty(clearButton.getElement(), "title", "Clears all messages in the console");
		controlsRight.add(clearButton);
		
		final Button hideButton = new Button("Hide");
		DOM.setStyleAttribute(hideButton.getElement(), "textAlign", "right");
		controlsRight.add(hideButton);
		
		setWidget(outer);
		m_left = Window.getClientWidth() / 2 - 640 / 2;
		m_top = Window.getClientHeight() / 2;
		
		m_enabler.install();
		
		ClickHandler handler = new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				Widget sender = (Widget) event.getSource();
				if (sender == clearButton)
				{
					clearMessages();
				}
				else if (sender == hideButton)
				{
					hide();
				}
				else if (sender == m_disableButton)
				{
					disable();
				}
				else if (sender == toggleDebugButton)
				{
					if (Debug.isEnabled())
					{
						Debug.disable();
					}
					else
					{
						Debug.enable();
					}
				}
				else
				{
					assert false;
				}
			}
		};
		toggleDebugButton.addClickHandler(handler);
		m_disableButton.addClickHandler(handler);
		clearButton.addClickHandler(handler);
		hideButton.addClickHandler(handler);
		
		sinkEvents(Event.ONMOUSEDOWN);
		preventSelectionInIE(getElement());
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
	
	private static native void preventSelectionInIE(Element element) /*-{
		element.onselectstart = function() { return false; };
	}-*/;
	
	private void updateDisableButtonText()
	{
		m_disableButton.setHTML("Disable&nbsp;Console (" + m_enabler.getEnableKey() + ")");
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
		m_content.setHTML("<PRE style='padding: 0px; margin: 0px'></PRE>");
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
			appendText(m_content.getElement(), text, true);
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
	
	/*
	 * Copied from JSUtil to eliminate dependency
	 */
	private static void appendText(Element element, String text, boolean create)
	{
		/*
		 * Catch as many error conditions as possible in Java since JSNI error handling sucks
		 */
		if (element == null)
			throw new NullPointerException("element cannot be null");
		
		if (! hasChildNodes(element) && ! create)
			throw new IllegalArgumentException("element has no child nodes");
		
		// assume element is now a #text node
		appendText0(element, text, create);
	}
	
	/*
	 * Copied from JSUtil to eliminate dependency
	 */
	private static native boolean hasChildNodes(Element element) /*-{
		return element != null && element.hasChildNodes();
	}-*/;

	/*
	 * Copied from JSUtil to eliminate dependency
	 */
	private static native void appendText0(Element element, String text, boolean create) /*-{
		var TEXT_NODE = 3;
		var node = element;
		var textNode = null;
		while(node.firstChild)
		{
			if (node.firstChild.nodeType == TEXT_NODE)
			{
				textNode = node.firstChild;
				break;
			}
			node = node.firstChild;
		}
		
		if (textNode == null)
		{
			if (create)
			{
				textNode = node.ownerDocument.createTextNode(text);
				node.appendChild(textNode);
				return;
			}
			else
			{
				throw new Error("Couldn't find node of type #text");
			}
		}
		
		textNode.appendData(text);
	}-*/;
	
	@Override
	public void onBrowserEvent(Event event)
	{
		super.onBrowserEvent(event);
		
		// prevent selection in standard browsers
		if (DOM.eventGetType(event) == Event.ONMOUSEDOWN)
		{
			DOM.eventPreventDefault(event);
		}
	}
	
	/**
	 * Overrides {@link PopupPanel#show() popup's} implementation to prevent event filtering ala
	 * {@link com.google.gwt.user.client.EventPreview EventPreview}
	 */
	@Override
	public void show()
	{
		if (m_enabled && ! isAttached())
		{
		    // have to do this every time b/c AbsolutePanel likes to clear our position
			DOM.setStyleAttribute(getElement(), "position", "absolute");
			setPopupPosition(m_left, m_top);
			RootPanel.get().add(this);
		}
	}
	
	/**
	 * Overrides popup's implementation to prevent event filtering ala
	 * {@link com.google.gwt.user.client.EventPreview EventPreview}
	 */
	@Override
	public void hide()
	{
		if (isAttached())
		{
			m_left = getAbsoluteLeft();
			m_top = getAbsoluteTop();
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
			super(defaultEnableKey, 0, "Debug Console enabler");
		}
		
		@Override
		protected void doDisabled()
		{
			DebugConsole.this.disable();
		}
		
		@Override
		protected void doEnabled()
		{
			DebugConsole.this.enable();
			DebugConsole.this.show();
		}
		
		@Override
		protected void doEvent(Event event)
		{
			// NOOP
		}
	}
}
