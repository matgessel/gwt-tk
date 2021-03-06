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

import java.util.ArrayList;
import java.util.List;

import asquare.gwt.tk.client.ui.behavior.*;
import asquare.gwt.tk.client.ui.commands.FocusCommand;
import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.HasFocus;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.impl.FocusImpl;

/**
 * A modal dialog featuring:
 * <ul>
 * <li>an optional caption</li>
 * <li>support for widgets in the caption</li>
 * <li>a background "{@link GlassPanel}" which blocks user interaction with
 * the page (also stylable for the "light box" effect). </li>
 * <li>automatic centering in browser's main viewport (regardless of document
 * scroll)</li>
 * <li>resizable: if you set the size of the content area the layout will
 * maintain integrity on all platforms &amp; quirks/strict modes</li>
 * <li>minimum size specification for content area (optional, default is 200 x
 * 75px)</li>
 * <li>focus containment &amp; management</li>
 * <li>option to restore focus to triggering widget when dialog is hidden</li>
 * </ul>
 * The caption has the following properties:
 * <ul>
 * <li>inserted at top of dialog</li>
 * <li>can be dragged to move the dialog</li>
 * <li>disallows text selection</li>
 * <li>has the <code>Caption</code> style name</li>
 * </ul>
 * <h3>Usage Notes</h3>
 * <ul>
 * <li>When adding a Panel, the panel's child widgets are not automatically
 * added to the {@link #getFocusModel() focus model}. </li>
 * <li>Call {@link #removeController(Controller)} with
 * <code>{@link TabFocusController}.class</code> to disable built-in focus management.</li>
 * <li>{@link #setWidth(String)} can result in a dialog which is wider than the
 * caption. Use {@link #setContentWidth(String)} instead. </li>
 * <li>IE6 ignores table cell heights in strict mode. This means that you can't
 * set the dialog height and use "1px" to force minimum caption height.<br/>
 * Workaround: set the height of the content cell and let the dialog auto-size.</li>
 * <li>Opera: dialog does not receive keystrokes unless the cursor is over the
 * dialog or a child of the dialog is focused.</li>
 * </ul>
 * <h3>CSS Style Rules</h3>
 * <ul class='css'>
 * <li>.tk-ModalDialog { the dialog itself }</li>
 * <li>.tk-ModalDialog-glassPanel { the background panel behind the dialog }</li>
 * <li>.tk-ModalDialog .Caption { the caption }</li>
 * <li>.tk-ModalDialog-content { the content area }</li>
 * <li>.tk-ModalDialog-dragging { applied to the dialog whilst dragging }</li>
 * </ul>
 * <h3>Example</h3>
 * 
 * <pre>
 * final Button showDialogButton = new Button(&quot;Focus management&quot;);
 * showDialogButton.addClickListener(new ClickListener()
 * {
 * 	public void onClick(Widget sender)
 * 	{
 * 		final ModalDialog inputDialog = new ModalDialog();
 * 		inputDialog.setCaption(&quot;Input&quot;, false);
 * 		inputDialog.add(new Label(&quot;Enter a value&quot;));
 * 		inputDialog.add(new TextBox());
 * 		inputDialog.add(new Button(&quot;OK&quot;, new ClickListener()
 * 		{
 * 			public void onClick(Widget sender)
 * 			{
 * 				inputDialog.hide();
 * 			}
 * 		}));
 * 
 * 		inputDialog.show(showDialogButton);
 * 	}
 * });
 * </pre>
 */
public class ModalDialog extends CPopupPanel
{
	public static final String STYLENAME_DIALOG = "tk-ModalDialog";
	public static final String STYLENAME_GLASSPANEL = "tk-ModalDialog-glassPanel";
	public static final String STYLENAME_CAPTION = "Caption";
	public static final String STYLENAME_CONTENT = "tk-ModalDialog-content";
	
	protected static final FocusImpl s_focusImpl = FocusImpl.getFocusImplForPanel();
	
	private final Element m_focusable = s_focusImpl.createFocusable();
	private final GlassPanel m_glassPanel = new GlassPanel();
	private final RowPanel m_panel = new RowPanel();
	private final Element m_contentTd;
	
	private FocusModel m_focusModel;
	private CaptionWrapper m_caption = null;
	private int m_minContentWidth = 200;
	private int m_minContentHeight = 75;
	private HasFocus m_focusOnCloseWidget;
	
	public ModalDialog()
	{
	    setFocusModel(new FocusModel());
	    setStyleName(STYLENAME_DIALOG);
		DOM.appendChild(getContainerElement(), m_focusable);
		m_glassPanel.addStyleName(STYLENAME_GLASSPANEL);
		m_panel.addCell();
		m_panel.addCellStyleName(STYLENAME_CONTENT);
		m_contentTd = m_panel.getCellElement(0);
		super.setWidget(m_panel);
	}
	
	/*
	 * (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.CPopupPanel#createControllers()
	 */
	protected List<Controller> createControllers()
	{
		List<Controller> result = new ArrayList<Controller>();
		result.add(GWT.<Controller>create(PositionDialogController.class));
		result.add(new InitializeFocusController());
		result.add(GWT.<Controller>create(TabFocusController.class));
		result.add(new FocusOnCloseController());
		return result;
	}
	
	/**
	 * A factory method which gives a subclass the opportunity to override default 
	 * controller creation.
	 * 
	 * @return a List with 0 or more controllers, or <code>null</code>
	 */
	protected List<Controller> createCaptionControllers()
	{
		List<Controller> result = new ArrayList<Controller>();
		result.add(new DragController(new MouseMoveFilter(new MouseDragHandler(new AdjustObjectGesture.Move(this)))));
		result.add(new DragStyleController(this));
		return result;
	}
	
	/**
	 * Get the focus model for this dialog. 
	 */
	public FocusModel getFocusModel()
	{
		return m_focusModel;
	}
	
	/**
	 * Set the focus model for this dialog. 
	 */
	public void setFocusModel(FocusModel focusModel)
	{
		// TODO: apply IoC? I.e. let interested object register for change notifications
		m_focusModel = focusModel;
		TabFocusController tabFocusController = (TabFocusController) getController(TabFocusController.class);
		if (tabFocusController != null)
		{
			tabFocusController.setModel(focusModel);
		}
	}
	
	/**
	 * Get the minimum height of the content panel. 
	 * 
	 * @return the minimum height in pixels
	 */
	public int getContentMinHeight()
	{
		return m_minContentHeight;
	}
	
	/**
	 * Set the minimum height of the content panel. The default is <code>75 px</code>.
	 * Set to <code>0</code> to disable this feature.
	 * 
	 * @param minHeight the minimum height in pixels
	 */
	public void setContentMinHeight(int minHeight)
	{
		this.m_minContentHeight = minHeight;
	}
	
	/**
	 * Get the minimum width of the content panel.
	 * 
	 * @return the minimum width in pixels
	 */
	public int getContentMinWidth()
	{
		return m_minContentWidth;
	}
	
	/**
	 * Set the minimum width of the content panel. The default is <code>200 px</code>.
	 * Set to <code>0</code> to disable this feature.
	 * 
	 * @param minWidth the minimum width in pixels
	 */
	public void setContentMinWidth(int minWidth)
	{
		this.m_minContentWidth = minWidth;
	}
	
	/**
	 * Set the desired width of the content panel. The minimum width property
	 * will take precedence if applicable.
	 * 
	 * @param width the width in CSS measurements
	 */
	public void setContentWidth(String width)
	{
		DOM.setElementProperty(m_contentTd, "width", width);
	}
	
	/**
	 * Set the desired height of the content panel. The minimum height property
	 * will take precedence if applicable.
	 * 
	 * @param height the height in CSS measurements
	 */
	public void setContentHeight(String height)
	{
		DOM.setElementProperty(m_contentTd, "height", height);
	}
	
	/**
	 * Get the actual width of the content panel. This does not work until the
	 * dialog has been shown.
	 * 
	 * @return the width in pixels
	 */
	public int getContentOffsetWidth()
	{
		return DOM.getElementPropertyInt(m_contentTd, "offsetWidth");
	}
	
	/**
	 * Get the actual height of the content panel. This does not work until the
	 * dialog has been shown.
	 * 
	 * @return the height in pixels
	 */
	public int getContentOffsetHeight()
	{
		return DOM.getElementPropertyInt(m_contentTd, "offsetHeight");
	}
	
	/**
	 * Adds a widget to the content area of this dialog. Multiple widgets may be
	 * added. The widget will be added to the focus model if it implements
	 * HasFocus and does not have a tabIndex < 0.
	 * 
	 * @param w a widget
	 */
	public void add(Widget w)
	{
		// pre: content row is created and is last row
		m_panel.addWidget(w, false);
		
		if (w instanceof HasFocus)
		{
			m_focusModel.add((HasFocus) w);
		}
	}
	
	/**
	 * Not supported. Use {@link #add(Widget)} instead. 
	 * 
	 * @throws UnsupportedOperationException
	 */
	public void setWidget(Widget w)
	{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Removes a widget from the content area of this dialog. Do not use for
	 * caption widget. Use {@link #setCaption(Widget) setCaption(null)} instead.
	 * 
	 * @param w the widget to remove
	 * @throws IllegalArgumentException if <code>w</code> is in the caption. 
	 */
	public boolean remove(Widget w)
	{
		if (m_caption != null && w == m_panel.getWidgetAt(0, 0))
			throw new IllegalArgumentException();
		
		if (w instanceof HasFocus)
		{
			m_focusModel.remove((HasFocus) w);
		}
		
		// pre: content row is created and is last row
		return m_panel.remove(w, false);
	}
	
	/**
	 * Sets the contents of the caption to the specified text, clearing any
	 * previous contents from the caption.
	 * 
	 * @param text the caption text
	 * @param asHtml true to treat <code>text</code> as html
	 * @see #setCaption(Widget)
	 */
	public void setCaption(String text, boolean asHtml)
	{
		if (m_caption != null)
		{
			clearCaption();
		}
		createCaption();
		if (asHtml)
		{
			DOM.setInnerHTML(m_panel.getCellElement(0), text);
		}
		else
		{
			DOM.setInnerText(m_panel.getCellElement(0), text);
		}
	}
	
	/**
	 * Set a widget as the sole child of the caption, clearing any
	 * previous contents from the caption.
	 * 
	 * @param w a widget
	 */
	public void setCaption(Widget w)
	{
		if (m_caption != null)
		{
			clearCaption();
		}
		if (w != null)
		{
			createCaption();
			m_panel.addWidgetTo(w, 0);
		}
	}
	
	/*
	 * pre: m_caption == null
	 */
	private void createCaption()
	{
		m_panel.insertCell(0);
		m_caption = new CaptionWrapper(m_panel.getCellElement(0), createCaptionControllers());
		if (isAttached())
		{
			m_caption.onAttach();
		}
	}
	
	/*
	 * pre: m_caption != null
	 */
	private void clearCaption()
	{
		if (isAttached())
		{
			m_caption.onDetach();
		}
		m_caption = null;
		m_panel.removeCell(0);
	}
	
	/**
	 * Get the element that forms the content area of the dialog. 
	 */
	public Element getContentElement()
	{
		return m_contentTd;
	}
	
	/**
	 * Get the GlassPanel which is displayed behind the dialog. 
	 */
	public GlassPanel getGlassPanel()
	{
		return m_glassPanel;
	}
	
	/**
	 * Get the widget which will be focused after the dialog is closed. This
	 * property is only available while the dialog is visible.
	 * 
	 * @return a widget or <code>null</code>
	 */
	public HasFocus getFocusOnCloseWidget()
	{
		return m_focusOnCloseWidget;
	}
	
	/**
	 * Shows the glasspanel and dialog then focuses the widget selected in 
	 * the focus model.
	 */
	public void show()
	{
		show(null);
	}
	
	/**
	 * Shows the glasspanel and dialog then focuses the widget selected in 
	 * the focus model.
	 * 
	 * @param focusOnCloseWidget a widget to focus after this dialog is closed
	 */
	public void show(HasFocus focusOnCloseWidget)
	{
		m_focusOnCloseWidget = focusOnCloseWidget;
		m_glassPanel.show();
		Controller positionDialogController = getController(PositionDialogController.class);
		if (positionDialogController != null)
		{
			(( PositionDialogController) positionDialogController).beforeAttach(this);
		}
		super.show();
	}
	
	/**
	 * Detaches the dialog from the DOM (it will be garbage collected if there
	 * are no references to it). Has no effect if the dialog is not showing. 
	 * 
	 * @see com.google.gwt.user.client.ui.PopupPanel#hide()
	 */
	public void hide()
	{
		super.hide();
		m_glassPanel.hide();
		m_focusOnCloseWidget = null;
	}
	
	/*
	 *  (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Widget#onAttach()
	 */
	protected void onAttach()
	{
		if (isAttached())
			return;
		
		super.onAttach();
		
		if (m_caption != null)
			m_caption.onAttach();	
	}
	
	/*
	 *  (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Widget#onDetach()
	 */
	protected void onDetach()
	{
		if(! isAttached())
			return;
		
		try
		{
			if (m_caption != null)
				m_caption.onDetach();
		}
		finally
		{
			super.onDetach();
		}
	}
	
	/**
	 * Provides event support for the caption element. 
	 */
	protected class CaptionWrapper extends CWidget
	{
		/*
		 * The fun thing about GWT is that elements are not encapsulated. We can
		 * take an element (e.g. td, div) from another container and create a
		 * fly-weight widget with it to handle events. The parent container is none
		 * the wiser. Of course, we have to call onAttach() to receive events and
		 * onDetach() to prevent memory leaks.
		 */
		protected CaptionWrapper(Element captionElement, List<Controller> controllers)
		{
			super(captionElement, controllers);
			setStyleName(STYLENAME_CAPTION);
		}
		
		protected void onAttach()
		{
			super.onAttach();
		}
		
		protected void onDetach()
		{
			super.onDetach();
		}
	}
	
	/**
	 * Sets the initial focus when the dialog is shown.
	 * <p>
	 * This class is not extensible because it depends on private data in the
	 * dialog. It can, however, be instantiated, added to and removed from it's
	 * parent dialog without causing problems.
	 */
	public static final class InitializeFocusController extends ControllerAdaptor
	{
		public InitializeFocusController()
		{
			super(InitializeFocusController.class);
		}
		
		public void plugIn(Widget widget)
		{
			final ModalDialog dialog = (ModalDialog) widget;
			HasFocus focusWidget = dialog.getFocusModel().getCurrentWidget();
			Command focusCommand;
			if (focusWidget != null)
			{
				focusCommand = new FocusCommand(focusWidget);
			}
			else
			{
				focusCommand = new Command()
				{
					public void execute()
					{
						s_focusImpl.focus(dialog.m_focusable);
					}
				};
			}
			DeferredCommand.addCommand(focusCommand);
		}
	}
	
	/**
	 * A controller which focuses a widget when the dialog is hidden. 
	 */
	public static class FocusOnCloseController extends ControllerAdaptor
	{
		public FocusOnCloseController()
		{
			super(FocusOnCloseController.class);
		}
		
		public void unplug(Widget widget)
		{
			HasFocus focusOnCloseWidget = ((ModalDialog) widget).getFocusOnCloseWidget();
			if (focusOnCloseWidget != null)
			{
				DeferredCommand.addCommand(new FocusCommand(focusOnCloseWidget));
			}
		}
	}
	
	/**
	 * A controller which encapsulates dialog sizing and positioning logic.
	 * Although this class doesn't react to events, we're going to implement
	 * Controller to enable dynamic configuration via
	 * {@link ControllerSupport#getController(Class)}.
	 */
	public static class PositionDialogController extends ControllerAdaptor
	{
		private int m_viewportWidth;
		private int m_viewportHeight;
		
		public PositionDialogController()
		{
			super(PositionDialogController.class);
		}
		
		protected int getViewportWidth()
		{
			return m_viewportWidth;
		}
		
		protected void setViewportWidth(int width)
		{
			m_viewportWidth = width;
		}
		
		protected int getViewportHeight()
		{
			return m_viewportHeight;
		}
		
		protected void setViewportHeight(int height)
		{
			m_viewportHeight = height;
		}
		
		public void plugIn(Widget widget)
		{
			afterAttach((ModalDialog) widget);
		}
		
		protected int applyMinWidthConstraint(ModalDialog dialog, int contentWidth)
		{
			return Math.max(dialog.getContentMinWidth(), contentWidth);
		}
		
		protected int applyMaxWidthConstraint(ModalDialog dialog, int contentWidth)
		{
			return Math.min(getViewportWidth() / 2, contentWidth);
		}
		
		protected int applyMinHeightConstraint(ModalDialog dialog, int contentHeight)
		{
			return Math.max(dialog.getContentMinHeight(), contentHeight);
		}
		
		/**
		 * Template method for updating the content width.
		 * <ul>
		 * <li>post: the dialog content width (style attribute) will be updated
		 * <li>post: the <code>dialogWidth</code> property will be finalized
		 * </ul>
		 */
		protected int updateContentWidth(ModalDialog dialog, int contentWidth)
		{
			int dialogWidth = dialog.getOffsetWidth();
			int contentWidthInitial = dialog.getContentOffsetWidth();
			
			if (contentWidth != contentWidthInitial)
			{
				/*
				 * Note: setting the content width does *not* result in
				 * immediate re-layout of parent dialog. The dialog width
				 * property will be unchanged if we refetch it. 
				 */
				dialog.setContentWidth(contentWidth + "px");
				
				/*
				 * Refetch the content width. The non-reflowable content (e.g. a
				 * wide image or long TextBox) may force a width greater than
				 * the intended value. This returns the old value in IE6.
				 */
				contentWidth = dialog.getContentOffsetWidth();
				
				/*
				 * The change to the content width will result in a change in the
				 * overall dialog's width. Try to predict the width after the
				 * pending re-layout.
				 */
				final int padding = dialogWidth - contentWidthInitial;
				dialogWidth = contentWidth + padding;
			}
			
			return dialogWidth;
		}
		
		/**
		 * Template method for updating the content height.
		 * <ul>
		 * <li>post: the dialog content height (style attribute) will be updated
		 * <li>post: the <code>dialogHeight</code> property will be finalized
		 * </ul>
		 */
		protected int updateContentHeight(ModalDialog dialog, int contentHeight)
		{
			int dialogHeight = dialog.getOffsetHeight();
			int contentHeightInitial = dialog.getContentOffsetHeight();
			
			if (contentHeight != contentHeightInitial)
			{
				/*
				 * Refetch the content height. The non-reflowable content 
				 * may force a height greater than the intended value.
				 */
				contentHeight = dialog.getContentOffsetHeight();
				
				/*
				 * The change to the content height will result in a change in the
				 * overall dialog's height. Try to predict the height after the
				 * pending re-layout.
				 */
				final int padding = dialogHeight - contentHeightInitial;
				dialogHeight = contentHeight + padding;
			}
			
			return dialogHeight;
		}
		
		/**
		 * Template method for setting the dialog's final position. This
		 * implementation prevents the dialog being positioned above or left of
		 * the origin.
		 * 
		 * @param dialog
		 * @param dialogWidth
		 * @param dialogHeight
		 */
		protected void setDialogPosition(ModalDialog dialog, int dialogWidth, int dialogHeight)
		{
			int left = Window.getScrollLeft() + getViewportWidth() / 2 - dialogWidth / 2;
			int top = Window.getScrollTop() + getViewportHeight() / 2 - dialogHeight / 2;
			
			// set the position
			dialog.setPopupPosition((left < 0) ? 0 : left, (top < 0) ? 0 : top);
		}
		
		public void beforeAttach(ModalDialog dialog)
		{
			/*
			 * Attaching the dialog sometimes temporarily add a scroll bar,
			 * throwing off the viewport dimensions. This can happen even if a
			 * scroll bar is never displayed. 
			 */
			setViewportWidth(DomUtil.getViewportWidth());
			setViewportHeight(DomUtil.getViewportHeight());
			
			/*
			 * Guard against flicker when repositioning dialog. 
			 * This may not be necessary, but it can't hurt. 
			 */
			dialog.setVisible(false);
			
			/*
			 * This should eliminate scrollbar flicker in Opera/FF[Mac] unless
			 * the dialog height > viewport height.
			 */
			dialog.setPopupPosition(0, 0);
		}
		
		public void afterAttach(ModalDialog dialog)
		{
			int dialogWidth, dialogHeight;
			
			/**
			 * Apply width constraints
			 * Get/estimate dialog width. 
			 */
			dialogWidth = (updateContentWidth(dialog, applyMinWidthConstraint(dialog, applyMaxWidthConstraint(dialog, dialog.getContentOffsetWidth()))));
			
			/*
			 * Apply height constraint last because width constraints 
			 * may have changed content height. 
			 * Get/estimate dialog height. 
			 */
			dialogHeight = updateContentHeight(dialog, applyMinHeightConstraint(dialog, dialog.getContentOffsetHeight()));
			
			setDialogPosition(dialog, dialogWidth, dialogHeight);
			dialog.setVisible(true);
		}
	}
	
	public static class PositionDialogControllerIE6 extends PositionDialogController
	{
		protected int updateContentWidth(ModalDialog dialog, int contentWidth)
		{
			int dialogWidth = dialog.getOffsetWidth();
			
			if (contentWidth != dialog.getContentOffsetWidth())
			{
				dialog.setContentWidth(contentWidth + "px");
				
				/*
				 * This magic call forces IE to update the layout. 
				 */
				dialogWidth = dialog.getOffsetWidth();
			}
			
			return dialogWidth;
		}
	}
}
