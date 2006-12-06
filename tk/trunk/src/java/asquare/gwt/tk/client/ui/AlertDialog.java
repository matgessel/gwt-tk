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

import java.util.List;

import asquare.gwt.tk.client.ui.behavior.ControllerAdaptor;
import asquare.gwt.tk.client.ui.behavior.FocusModel;
import asquare.gwt.tk.client.util.DomUtil;
import asquare.gwt.tk.client.util.KeyMap;

import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;

/**
 * A modal dialog tailored to conveniently displaying alerts. 
 * <p>Features: 
 * <dl>
 * <dt>Icon</dt>
 * <dd>An image indicating the type / severity of the condition. </dd>
 * <dt>Caption Text</dt>
 * <dd>A brief summary of the condition which triggered the dialog. Displayed in the "title". </dd>
 * <dt>Message</dt>
 * <dd>A detail of the consequences of the actions presented by the dialog.</dd>
 * <dt>Buttons</dt>
 * <dd>0 or more buttons. The button text should correspond to the alert message, as if the user
 * is answering a question. </dd>
 * <dt>Hot keys</dt>
 * <dd>Hot keys can be assigned to trigger actions when pressed. </dd>
 * <dt>Button roles</dt>
 * <dd>The "Default" button is automatically focused when the dialog is shown. 
 * The "Cancel" button maps to the "Esc" hotkey. </dd>
 * </dl>
 * <p>A callback is assigned to each button in the form of a {@link com.google.gwt.user.client.Command Command}. 
 * When a button is pressed, the dialog will be hidden and the command will be executed. 
 * <h3>CSS Style Rules</h3>
 * <ul class='css'>
 * <li>.tk-AlertDialog-defaultButton { the default button (if applicable) }</li>
 * <li>.tk-AlertDialog-captionLeft { the left part of the caption (contains the icon)}</li>
 * <li>.tk-AlertDialog-captionCenter { the center part of the caption (contains the caption text) }</li>
 * <li>.tk-AlertDialog-captionRight { the right part of the caption (set width to center caption text) }</li>
 * <li>.tk-AlertDialog-captionIcon { the caption icon itself }</li>
 * <li>.tk-AlertDialog-message { the message between the caption and the buttons }</li>
 * <li>.tk-AlertDialog-buttons { the panel containing the buttons }</li>
 * <li>.tk-AlertDialog-hotKeyChar { the hotkey character in the button text (availible in factory generated dialogs) }</li>
 * </ul>
 * 
 * @see <a
 *      href="http://developer.apple.com/documentation/UserExperience/Conceptual/OSXHIGuidelines/XHIGWindows/chapter_17_section_6.html">Apple
 *      Human Interface Guidlines</a>
 * @see <a href="http://msdn.microsoft.com/library/default.asp?url=/library/en-us/dnwue/html/ch09f.asp">Windows User Interface Guidelines</a>
 */
public class AlertDialog extends ModalDialog
{
	/**
	 * Text for the "OK" button used in factory generated dialogs. Applies the
	 * <code>.tk-AlertDialog-hotKeyChar</code> style to "O".
	 */
	public static final String TEXT_OK = "<span class='tk-AlertDialog-hotKeyChar'>O</span>K";
	
	/**
	 * Text for the "Cancel" button used in factory generated dialogs. Applies the
	 * <code>.tk-AlertDialog-hotKeyChar</code> style to "C".
	 */
	public static final String TEXT_CANCEL = "<span class='tk-AlertDialog-hotKeyChar'>C</span>ancel";
	
	/**
	 * Indicates a button which has no special roles. 
	 */
	public static final int BUTTON_PLAIN = 0;
	
	/**
	 * Indicates that a button has the <em>Default</em> role. The
	 * <em>Default</em> button will have initial focus. Never make an action 
	 * default if it could have severe consequences such as data loss. The
	 * dialog may only have one button of this type.
	 */
	public static final int BUTTON_DEFAULT = 1 << 0;
	
	/**
	 * Indicates that a button has the <em>Cancel</em> role. Pressing
	 * <code>Esc</code> will execute the button's associated command. The
	 * dialog may only have one button of this type.
	 */
	public static final int BUTTON_CANCEL = 1 << 1;
	
	/**
	 * Indicates that a button has both the <em>Default</em> and
	 * <em>Cancel</em> roles. It will have initial focus and the user can press
	 * <code>Esc</code> to execute this button's command. The dialog may
	 * have no other buttons of type <code>Default</code> or
	 * <code>Cancel</code>.
	 */
	public static final int BUTTON_CANCEL_DEFAULT = BUTTON_DEFAULT | BUTTON_CANCEL;
	
	private final ColumnPanel m_buttonPanel = new ColumnPanel();
	private final KeyMap m_keyMap = new KeyMap();
	
	private Image m_captionIcon = null;
	private String m_captionText = null;
	private boolean m_captionTextAsHtml = false;
	private Widget m_message = null;
	private Widget m_defaultButton = null;
	
	/**
	 * Creates an empty AlertDialog. 
	 */
	public AlertDialog()
	{
		addStyleName("tk-AlertDialog");
	}
	
	/**
	 * Creates a low severity modal dialog with an OK button. 
	 * Use for hints, tips, welcome messages, etc.... 
	 * 
	 * @param okCommand a command to execute after the dialog is dismissed, or null
	 * @param captionText a String to display in the dialog title, or null
	 * @param message text a String display in the content area of the dialog, or null
	 */
	public static AlertDialog createInfo(Command okCommand, String captionText, String message)
	{
		AlertDialog dialog = new AlertDialog();
		dialog.setCaptionText(captionText, false);
		dialog.setMessage(message);
		dialog.setIcon(new Icon("InfoIcon16.gif", 16, 16));
		dialog.addButton(TEXT_OK, 'o', okCommand, BUTTON_DEFAULT | BUTTON_CANCEL);
		return dialog;
	}
	
	/**
	 * Creates a medium severity modal dialog with a OK and Cancel buttons. 
	 * Use for "Do you want to continue" style dialogs. 
	 * 
	 * @param okCommand a command to execute if the user presses the OK button, or null
	 * @param captionText a String to display in the dialog title, or null
	 * @param message text a String display in the content area of the dialog, or null
	 */
	public static AlertDialog createWarning(Command okCommand, String captionText, String message)
	{
		AlertDialog dialog = new AlertDialog();
		dialog.setCaptionText(captionText, false);
		dialog.setMessage(message);
		dialog.setIcon(new Icon("AlertIcon16.gif", 16, 16));
		dialog.addButton(TEXT_OK, 'o', okCommand, BUTTON_DEFAULT);
		dialog.addButton(TEXT_CANCEL, 'c', null, BUTTON_CANCEL);
		return dialog;
	}
	
	/**
	 * Creates a high severity "stop" modal dialog with an OK button. Use when
	 * an error condition prevents the normal execution of the application.
	 * 
	 * @param okCommand a command to execute after the dialog is dismissed, or null
	 * @param captionText a String to display in the dialog title, or null
	 * @param message text a String display in the content area of the dialog, or null
	 */
	public static AlertDialog createError(Command okCommand, String captionText, String message)
	{
		AlertDialog dialog = new AlertDialog();
		dialog.setCaptionText(captionText, false);
		dialog.setMessage(message);
		dialog.setIcon(new Icon("ErrorIcon16.gif", 16, 16));
		dialog.addButton(TEXT_OK, 'o', okCommand, BUTTON_DEFAULT | BUTTON_CANCEL);
		return dialog;
	}
	
	protected List createControllers()
	{
		List result = super.createControllers();
		result.add(new HotKeyController());
		result.add(new ArrowKeyFocusController());
		return result;
	}
	
	/**
	 * Gets the map of hotkeys to commands. Alpha keycodes are represented in upper
	 * case. 
	 * 
	 * @return the keymap
	 */
	public KeyMap getKeyMap()
	{
		return m_keyMap;
	}
	
	/**
	 * Get the image which will be displayed in the caption. 
	 */
	public Image getIcon()
	{
		return m_captionIcon;
	}
	
	/**
	 * Set the image will be displayed in the caption. 
	 * 
	 * @param url a URL to an image or null
	 */
	public void setIcon(String url)
	{
		setIcon(url != null ? new Image(url) : null);
	}
	
	/**
	 * Set the image will be displayed in the caption. You can use an
	 * {@link Icon} to ensure size information is available when the dialog
	 * layout is calculated.
	 * 
	 * @param icon an image or null
	 * @see Icon
	 */
	public void setIcon(Image icon)
	{
		if (m_captionIcon != null)
		{
			m_captionIcon = null;
		}
		if (icon != null)
		{
			m_captionIcon = icon;
			m_captionIcon.addStyleName("tk-AlertDialog-captionIcon");
			Image.prefetch(icon.getUrl());
		}
	}
	
	/**
	 * Get the text which will be displayed in the caption. 
	 * 
	 * @return a String or null
	 */
	public String getCaptionText()
	{
		return m_captionText;
	}
	
	/**
	 * Set the text which will be displayed in the caption.
	 * 
	 * @param captionText a String or null
	 * @param asHtml true to treat <code>captionText</code> as HTML, false to
	 *            treat <code>captionText</code> as plain text
	 */
	public void setCaptionText(String captionText, boolean asHtml)
	{
		m_captionText = captionText;
		m_captionTextAsHtml = asHtml;
	}
	
	/**
	 * Factory method which creates the caption. Called just before the dialog is
	 * shown. 
	 * 
	 * @return the caption, or <code>null</code>
	 */
	protected Widget buildCaption()
	{
		ColumnPanel captionPanel = new ColumnPanel();
		captionPanel.setWidth("100%"); // necessary so that descendent TD can have 100% width in Opera
		captionPanel.addCell();
		captionPanel.setCellStyleName("tk-AlertDialog-captionLeft");
		captionPanel.addCell();
		captionPanel.setCellStyleName("tk-AlertDialog-captionCenter");
		captionPanel.addCell();
		captionPanel.setCellStyleName("tk-AlertDialog-captionRight");
		
		if (m_captionIcon != null)
		{
			captionPanel.addWidgetTo(m_captionIcon, 0);
		}
		
		if (m_captionText != null)
		{
			if (m_captionTextAsHtml)
			{
				DOM.setInnerHTML(captionPanel.getCellElement(1), m_captionText);
			}
			else
			{
				DOM.setInnerText(captionPanel.getCellElement(1), m_captionText);
			}
		}
		
		return captionPanel;
	}
	
	private void buildContent()
	{
		if (m_message != null)
		{
			m_message.addStyleName("tk-AlertDialog-message");
			add(m_message);
		}
		m_buttonPanel.setStyleName("tk-AlertDialog-buttons");
		DomUtil.setAttribute(m_buttonPanel, "cellSpacing", "");
		DomUtil.setAttribute(m_buttonPanel, "cellPadding", "");
		add(m_buttonPanel);
	}
	
	/**
	 * Get the message which will be displayed in the dialog. 
	 * 
	 * @return a String, or <code>null</code>
	 */
	public Widget getMessage()
	{
		return m_message;
	}
	
	/**
	 * Set the message which will be displayed in the dialog. 
	 * 
	 * @param text a String, or <code>null</code>
	 */
	public void setMessage(String text)
	{
		setMessage(text, false);
	}
	
	/**
	 * Set the message which will be displayed in the dialog. 
	 * 
	 * @param text a String, or <code>null</code>
	 * @param asHtml true to treat <code>captionText</code> as HTML, false to
	 *            treat <code>captionText</code> as plain text
	 */
	public void setMessage(String text, boolean asHtml)
	{
		if (text == null)
		{
			setMessage((Widget) null);
		}
		else
		{
			if (asHtml)
			{
				setMessage(new HTML(text));
			}
			else
			{
				setMessage(new Label(text));
			}
		}
	}
	
	/**
	 * Set a widget to be displayed in the message area of the dialog. 
	 * 
	 * @param widget a Widget, or <code>null</code>
	 */
	public void setMessage(Widget widget)
	{
		m_message = widget;
	}
	
	/**
	 * Gets the widget in the button panel corresponding to <code>index</code>. 
	 * 
	 * @param index an integer >= 0
	 * @return the button widget
	 */
	public Widget getButton(int index)
	{
		return (Button) m_buttonPanel.getWidgetAt(index, 0);
	}
	
	/**
	 * Gets the number of widgets in the button panel. 
	 */
	public int getButtonCount()
	{
		return m_buttonPanel.getWidgetCount();
	}
	
	/**
	 * Adds a button to button panel.
	 * 
	 * @param text the text to display in the button
	 * @param hotKey the keycode of a key which will execute the widget's
	 *            associated command when pressed
	 * @param command a command to execute if the button is clicked, or
	 *            <code>null</code>
	 * @param type a constant representing special button behavior
	 */
	public void addButton(String text, char hotKey, Command command, int type)
	{
		addButton(new Button(text), hotKey, command, type);
	}
	
	/**
	 * Adds a widget to button panel. The widget will be added to the focus
	 * cycle if it implements {@link HasFocus} and does not have a tabIndex < 0.
	 * The widget must implement {@link SourcesClickEvents}. When a button is
	 * clicked, the dialog will be closed and the specified command will be
	 * executed.
	 * 
	 * @param widget the widget to add
	 * @param hotKey the keycode of a key which will execute the widget's
	 *            associated command when pressed
	 * @param command a command to execute if the button is clicked, or
	 *            <code>null</code>
	 * @param type a constant representing special button behavior
	 * @throws ClassCastException if <code>widget</code> does not implement
	 *             {@link HasFocus}
	 */
	public void addButton(Widget widget, char hotKey, final Command command, int type)
	{
		SourcesClickEvents clickable = (SourcesClickEvents) widget;
		boolean focusable = widget instanceof HasFocus;
		
		clickable.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				new HideAndExecuteCommand(AlertDialog.this, command).execute();
			}
		});
		m_buttonPanel.add(widget);
		if (focusable)
		{
			getFocusModel().add((HasFocus) widget);
		}
		if ((type & BUTTON_DEFAULT) != 0)
		{
			widget.addStyleName("tk-AlertDialog-defaultButton");
			m_defaultButton = widget;
			if (focusable)
			{
				getFocusModel().setFocusWidget((HasFocus) widget);
			}
		}
		if ((type & BUTTON_CANCEL) != 0)
		{
			m_keyMap.put((char) KeyboardListener.KEY_ESCAPE, command);
		}
		if (hotKey > 0)
		{
			m_keyMap.put(Character.toUpperCase(hotKey), command);
		}
	}
	
	/**
	 * Removes the specified button from the button panel.
	 * <em>Note: this will not remove commands that were put in the keymap when the button was added. </em>
	 * 
	 * @param button
	 * @see #getKeyMap()
	 */
	public void removeButton(Widget button)
	{
		m_buttonPanel.remove(button);
		if (button == m_defaultButton)
		{
			m_defaultButton = null;
			m_defaultButton.removeStyleName("tk-AlertDialog-defaultButton");
		}
		if (button instanceof HasFocus)
		{
			if (getFocusModel().getFocusWidget() == button)
			{
				getFocusModel().setFocusWidget(null);
			}
			getFocusModel().remove(((HasFocus) button));
		}
	}
	
	/*
	 *  (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.PopupPanel#show()
	 */
	public void show()
	{
		show(null);
	}
	
	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.ModalDialog#show(com.google.gwt.user.client.ui.HasFocus)
	 */
	public void show(HasFocus focusOnCloseWidget)
	{
		setCaption(buildCaption());
		buildContent();
		super.show(focusOnCloseWidget);
	}
	
	/**
	 * A command wrapper which hides the dialog then executes a wrapped command.
	 */
	public static class HideAndExecuteCommand implements Command
	{
		private final ModalDialog m_dialog;
		private final Command m_command;
		
		public HideAndExecuteCommand(ModalDialog dialog, Command command)
		{
			m_dialog = dialog;
			m_command = command;
		}
		
		public void execute()
		{
			m_dialog.hide();
			if (m_command != null)
			{
				DeferredCommand.add(m_command);
			}
		}
	}
	
	/**
	 * A controller which listens for the onkeydown event of a registered hotkey
	 * and executes the associated command.
	 */
	public static class HotKeyController extends ControllerAdaptor
	{
		public HotKeyController()
		{
			super(Event.ONKEYDOWN, HotKeyController.class);
		}
		
		protected boolean doBrowserEvent(Widget widget, Event event)
		{
			final AlertDialog dialog = (AlertDialog) widget;
			char keyCode = (char) DomUtil.eventGetKeyCode(event);
			if (dialog.getKeyMap().containsKey(keyCode))
			{
				Command command = dialog.getKeyMap().get(keyCode);
				new HideAndExecuteCommand(dialog, command).execute();
				return false;
			}
			return true;
		}
	}
	
	/**
	 * A controller which cycles the focus when the arrow keys are pressed.
	 */
	public static class ArrowKeyFocusController extends ControllerAdaptor
	{
		public ArrowKeyFocusController()
		{
			super(Event.ONKEYDOWN, ArrowKeyFocusController.class);
		}
		
		protected boolean doBrowserEvent(Widget widget, Event event)
		{
			boolean result = true;
			FocusModel focusModel = ((ModalDialog) widget).getFocusModel();
			if (focusModel != null && focusModel.getSize() > 1)
			{
				char keyCode = (char) DomUtil.eventGetKeyCode(event);
				if (keyCode == KeyboardListener.KEY_RIGHT || keyCode == KeyboardListener.KEY_DOWN)
				{
					// increment focus
					focusModel.getNextWidget().setFocus(true);
					result = false;
				}
				else if (keyCode == KeyboardListener.KEY_LEFT || keyCode == KeyboardListener.KEY_UP)
				{
					// decrement focus
					focusModel.getPreviousWidget().setFocus(true);
					result = false;
				}
			}
			return result;
		}
	}
}
