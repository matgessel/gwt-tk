/*
 * Copyright 2007 Mat Gessel <mat.gessel@gmail.com>
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
package asquare.gwt.tkdemo.client.demos;

import java.util.List;
import java.util.Vector;

import asquare.gwt.tk.client.ui.*;
import asquare.gwt.tk.client.ui.behavior.FocusModel;
import asquare.gwt.tk.client.ui.behavior.PreventSelectionController;
import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;

public class DialogPanel extends Composite
{
	public DialogPanel()
	{
		HorizontalPanel outer = new HorizontalPanel();
		outer.setSize("100%", "100%");
		
		initWidget(outer);
		
		outer.add(createDoc());
		BasicPanel demos = new BasicPanel();
		demos.add(createModalDialogDemo());
		demos.add(createAlertDialogDemo());
		outer.add(demos);
	}
	
	private Widget createDoc()
	{
		String content = 
			"<H2>ModalDialog</H2>" + 
			"<h4>Features</h4>" + 
			"<ul>" + 
			"<li>automatic centering in browser's main viewport</li>" + 
			"<li>light box effect (using <span id='glassPanelRef'></span>)</li>" + 
			"<li>caption allows child widgets</li>" + 
			"<li>caption prevents text selection</li>" + 
			"<li>focus management and containment (internal tab cycle)</li>" + 
			"<li>can focus a widget upon dismissal</li>" + 
			"<li>minimim size enforcement for content panel (optional, default is 200 x 75 px)</li>" + 
			"</ul>" + 
			"<H2>AlertDialog</H2>" + 
			"<h4>Features</h4>" + 
			"<ul>" + 
			"<li>icon in the caption indicates severity</li>" + 
			"<li>default button is automatically focused</li>" + 
			"<li>cancel button is mapped to Esc key</li>" + 
			"<li>button hot keys + Esc + Enter</li>" + 
			"<li>focus cycle traversable with arrow keys</li>" + 
			"</ul>"; 
		HTMLPanel description = new HTMLPanel(content);
		Hyperlink glassPanelLink = new Hyperlink("GlassPanel", false, GlassPanelDemo.NAME);
		DomUtil.setStyleAttribute(glassPanelLink, "display", "inline");
		description.add(glassPanelLink, "glassPanelRef");
		description.setStyleName("description division");
		return description;
	}
	
	private Widget createModalDialogDemo()
	{
		BasicPanel panel = new BasicPanel("div", "block");
		panel.setStyleName("example division");
		DomUtil.setStyleAttribute(panel, "whiteSpace", "nowrap");
		
		panel.add(new HTML("<h4>ModalDialog examples</h4>"));
		
		class CloseListener implements ClickListener
		{
			private final ModalDialog m_dialog;
			
			public CloseListener(ModalDialog dialog)
			{
				m_dialog = dialog;
			}
			
			public void onClick(Widget sender)
			{
				m_dialog.hide();
			}
		}
		
		class CloseButton extends Button
		{
			public CloseButton(ModalDialog dialog)
			{
				super("Close");
				addClickListener(new CloseListener(dialog));
			}
			
			public CloseButton(ModalDialog dialog, String text)
			{
				super(text);
				addClickListener(new CloseListener(dialog));
			}
		}
		
		final Button plainDialog = new Button("Plain");
		plainDialog.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				final ModalDialog dialog = new ModalDialog();
				dialog.setCaption("Caption area", false);
				dialog.add(new Label("Content area"));
				dialog.add(new CloseButton(dialog));
				dialog.show(plainDialog);
			}
		});
		panel.add(plainDialog);
		
		final Button verboseDialog = new Button("Verbose");
		verboseDialog.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				final ModalDialog dialog = new ModalDialog();
				dialog.setCaption("Verbose dialog", false);
				dialog.add(new Label("Twas brillig, and the slithy toves " + 
						"  Did gyre and gimble in the wabe: " + 
						"All mimsy were the borogoves, " + 
						"  And the mome raths outgrabe " +
						"Beware the Jabberwock, my son! " +
						"The jaws that bite, the claws that catch! " +
						"Beware the Jubjub bird, and shun " +
						"The frumious Bandersnatch!"));
				dialog.add(new CloseButton(dialog));
				dialog.show(verboseDialog);
			}
		});
		panel.add(verboseDialog);
		
		final Button captionLessDialog = new Button("No caption");
		captionLessDialog.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				final ModalDialog dialog = new ModalDialog();
				dialog.add(new Label("Captionless dialog"));
				dialog.add(new CloseButton(dialog));
				dialog.show(captionLessDialog);
			}
		});
		panel.add(captionLessDialog);
		
		final Button undraggableDialog = new Button("Drag disabled");
		undraggableDialog.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				final ModalDialog dialog = new ModalDialog()
				{
					protected List createCaptionControllers()
					{
						List result = new Vector();
						result.add(PreventSelectionController.getInstance());
						return result;
					}
				};
				dialog.setCaption("Drag disabled", false);
				dialog.add(new Label("This dialog uses a custom controller in the header which does not provide drag support."));
				dialog.add(new CloseButton(dialog));
				dialog.show(undraggableDialog);
			}
		});
		panel.add(undraggableDialog);
		
		final Button styledDragDialog = new Button("Drag style");
		styledDragDialog.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				final ModalDialog dialog = new ModalDialog();
				DomUtil.setId(dialog, "dialog-dragstyle");
				dialog.setCaption("Drag me", false);
				dialog.add(new Label("This dialog employs the \"tk-ModalDialog-dragging\" style which is applied while dragging. "));
				dialog.add(new CloseButton(dialog));
				dialog.show(styledDragDialog);
			}
		});
		panel.add(styledDragDialog);
		
		final Button focusManagementDialog = new Button("Focus management");
		focusManagementDialog.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				final ModalDialog dialog = new ModalDialog();
				dialog.setCaption("Register", false);
				FocusModel fModel = dialog.getFocusModel();
				
				final int FIELD_COUNT = 3;
				Grid table = new Grid(FIELD_COUNT, 2);
				dialog.add(table);
				Widget[] labels = new Widget[FIELD_COUNT];
				labels[0] = new Label("User name: ");
				labels[1] = new Label("Password: ");
				labels[2] = new Label("Retype password: ");
				
				FocusWidget[] fields = new FocusWidget[FIELD_COUNT];
				fields[0] = new TextBox();
				fields[1] = new PasswordTextBox();
				fields[2] = new PasswordTextBox();
				
				CellFormatter formatter = table.getCellFormatter();
				for (int i = 0; i < labels.length; i++)
				{
					table.setWidget(i, 0, labels[i]);
					formatter.setHorizontalAlignment(i, 0, HasHorizontalAlignment.ALIGN_LEFT);
					table.setWidget(i, 1, fields[i]);
					fModel.add(fields[i]);
				}
				fModel.setFocusWidget(fields[0]);
				
				ColumnPanel buttonPanel = new ColumnPanel();
				buttonPanel.setWidth("100%");
				dialog.add(buttonPanel);
				
				Button closeButton = new CloseButton(dialog, "Register!");
				fModel.add(closeButton);
				buttonPanel.add(closeButton);
				
				Button cancelButton = new CloseButton(dialog, "Cancel");
				fModel.add(cancelButton);
				buttonPanel.addWidget(cancelButton, false);
				buttonPanel.setCellHorizontalAlignment(ColumnPanel.ALIGN_RIGHT);
				
				dialog.show(focusManagementDialog);
			}
		});
		panel.add(focusManagementDialog);
		
		final Button explicitlyPositionedDialog = new Button("Explicitly positioned");
		explicitlyPositionedDialog.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				final ModalDialog dialog = new ModalDialog();
				dialog.removeController(dialog.getController(ModalDialog.PositionDialogController.class));
				int contentWidth = 300;
				int contentHeight = 100;
				dialog.setContentWidth(contentWidth + "px");
				dialog.setContentHeight(contentHeight + "px");
				dialog.setPopupPosition(100, 100);
				dialog.setCaption("Explicitly positioned dialog", false);
				dialog.add(new Label("Automatic positioning is disabled. Dimensions and position are set explicitly. "));
				dialog.add(new CloseButton(dialog));
				dialog.show(explicitlyPositionedDialog);
			}
		});
		panel.add(explicitlyPositionedDialog);
		
		final Button multipleDialogs = new Button("Multiple dialogs");
		multipleDialogs.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				ModalDialog dialog = new ModalDialog();
				dialog.setCaption("First dialog", false);
				FocusModel fModel = dialog.getFocusModel();
				RowPanel outer = new RowPanel();
				
				dialog.add(new HTML(""));
				
				final UrlLocation urlBox = new UrlLocation();
				urlBox.setText("http://www.asquare.net");
				urlBox.setWidth("350px");
				fModel.add(urlBox);
				outer.add(urlBox);
				
				Button goButton = new Button("Go");
				fModel.add(goButton);
				fModel.setFocusWidget(goButton);
				outer.addWidget(goButton, false);
				
				ListBox addressList = new ListBox();
				addressList.addItem("Select an address");
				addressList.addItem("http://www.asquare.net");
				addressList.addItem("http://www.google.com");
				addressList.addItem("http://www.sourceforge.net");
				addressList.addItem("http://www.apache.org");
				fModel.add(addressList);
				outer.add(addressList);
				
				final Frame frame = new Frame();
				frame.setSize("400px", "200px");
				outer.add(frame);
				urlBox.addChangeListener(new ChangeListener()
				{
					public void onChange(Widget sender)
					{
						frame.setUrl(urlBox.getURL());
					}
				});
				goButton.addClickListener(new ClickListener()
				{
					public void onClick(Widget sender)
					{
						frame.setUrl(urlBox.getURL());
					}
				});
				addressList.addChangeListener(new ChangeListener()
				{
					public void onChange(Widget sender)
					{
						ListBox list = (ListBox) sender;
						if (list.getSelectedIndex() > 0)
						{
							urlBox.setText(list.getItemText(list.getSelectedIndex()));
							frame.setUrl(list.getItemText(list.getSelectedIndex()));
						}
					}
				});
				final Button secondDialog = new Button("Show second dialog");
				secondDialog.addClickListener(new ClickListener()
				{
					public void onClick(Widget sender)
					{
						final ModalDialog dialog = new ModalDialog();
						dialog.setCaption("Second dialog", false);
						dialog.add(new Label("Note that you cannot manipulate the widgets in the first dialog. "));
						dialog.add(new CloseButton(dialog));
						dialog.show(secondDialog);
					}
				});
				fModel.add(secondDialog);
				outer.add(secondDialog);
				Button closeButton = new CloseButton(dialog);
				fModel.add(closeButton);
				outer.add(closeButton);
				dialog.add(outer);
				dialog.show(multipleDialogs);
			}
		});
		panel.add(multipleDialogs);
		
		final Button styledDialog = new Button("Styled");
		styledDialog.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				final ModalDialog dialog = new ModalDialog();
				DomUtil.setId(dialog, "dialog-styled");
				HorizontalPanel caption = new HorizontalPanel();
				caption.setWidth("100%");
				Label captionText = new Label("Oopsie!");
				caption.add(captionText);
				caption.setCellWidth(captionText, "100%");
				Image close = new Image("close.gif");
				close.addClickListener(new CloseListener(dialog));
				caption.add(close);
				dialog.setCaption(caption);
				dialog.add(new Label("I've been a bad, bad browser."));
				dialog.add(new Button("Deny ice cream", new CloseListener(dialog)));
				dialog.show(styledDialog);
			}
		});
		panel.add(styledDialog);
		
		return panel;
	}
	
	private Widget createAlertDialogDemo()
	{
		BasicPanel panel = new BasicPanel("div", "block");
		panel.setStyleName("example division");
		DomUtil.setStyleAttribute(panel, "whiteSpace", "nowrap");
		
		panel.add(new HTML("<h4>AlertDialog examples</h4>"));
		
		final Button showInfo = new Button("Info");
		showInfo.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				AlertDialog.createInfo(null, "Caption text", "Message text").show(showInfo);
			}
		});
		panel.add(showInfo);
		
		final Button showWarning = new Button("Warning");
		showWarning.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				AlertDialog.createWarning(null, "Caption text", "Message text").show(showWarning);
			}
		});
		panel.add(showWarning);
		
		final Button showError = new Button("Error");
		showError.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				AlertDialog.createError(null, "Caption text", "Message text").show(showError);
			}
		});
		panel.add(showError);
		
		final Button showModified = new Button("Modified");
		showModified.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				AlertDialog dialog = AlertDialog.createWarning(null, "Modified alert", "This dialog was created by a factory method. It was modified to remove the Cancel button");
				dialog.removeButton(dialog.getButton(1));
				dialog.getKeyMap().remove('C'); // hotkeys are mapped in upper case
				dialog.show(showModified);
			}
		});
		panel.add(showModified);
		
		final Button showImagesAsButtons = new Button("Images as buttons");
		showImagesAsButtons.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				AlertDialog dialog = new AlertDialog();
				DomUtil.setId(dialog, "alert-imageButtons");
				dialog.setCaptionText("Caption text", false);
				dialog.setMessage("Plain images are used instead of buttons. Since images cannot be focused, they are omitted from the focus cycle. ");
				dialog.setIcon(new Icon("AlertIcon16.gif", 16, 16));
				Icon check = new Icon("CheckIcon32.png", 32, 32);
				check.setTitle("OK");
				dialog.addButton(check, (char) KeyboardListener.KEY_ENTER, null, AlertDialog.BUTTON_DEFAULT);
				Icon x = new Icon("XIcon32.png", 32, 32);
				x.setTitle("Cancel");
				dialog.addButton(x, 'x', null, AlertDialog.BUTTON_CANCEL);
				dialog.show(showImagesAsButtons);
			}
		});
		panel.add(showImagesAsButtons);
		
		final Button showImagesInButtons = new Button("Images in buttons");
		showImagesInButtons.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				AlertDialog dialog = new AlertDialog();
				dialog.setCaptionText("Caption text", false);
				dialog.setMessage("Buttons can contain images and text. ");
				dialog.setIcon(new Icon("AlertIcon16.gif", 16, 16));
				Button ok = new Button("<img src='CheckIcon16.png' title='OK' style='width: 16; height: 16; vertical-align: middle;'/>&nbsp;OK");
				dialog.addButton(ok, 'o', null, AlertDialog.BUTTON_DEFAULT);
				Button cancel = new Button("<img src='XIcon16.png' title='Cancel' style='width: 16; height: 16; vertical-align: middle;'/>&nbsp;Cancel");
				dialog.addButton(cancel, 'c', null, AlertDialog.BUTTON_CANCEL);
				dialog.show(showImagesInButtons);
			}
		});
		panel.add(showImagesInButtons);
		
		final Button showStyled = new Button("Styled");
		showStyled.addClickListener(new ClickListener()
		{
			public void onClick(Widget sender)
			{
				AlertDialog dialog = new AlertDialog();
				DomUtil.setId(dialog, "alert-styled");
				DomUtil.setId(dialog.getGlassPanel(), "alert-styled-glassPanel");
				dialog.setCaptionText("Caption text", false);
				ColumnPanel message = new ColumnPanel();
				message.setWidth("100%");
				message.add(new Icon("AlertIcon32.gif", 32, 32));
				message.add(new HTML("&nbsp"));
				message.add(new HTML("<b>Message summary</b><br/>Message detail"));
				message.addCell();
				message.setCellWidth("32px");
				dialog.setMessage(message);
				dialog.addButton(AlertDialog.TEXT_OK, 'o', null, AlertDialog.BUTTON_DEFAULT);
				dialog.addButton(AlertDialog.TEXT_CANCEL, 'c', null, AlertDialog.BUTTON_CANCEL);
				dialog.show(showStyled);
			}
		});
		panel.add(showStyled);
		return panel;
	}
}
