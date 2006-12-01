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
import java.util.Vector;

import asquare.gwt.tk.client.ui.behavior.*;
import asquare.gwt.tk.client.ui.commands.FocusCommand;
import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.impl.FocusImpl;

/**
 * A modal dialog featuring:
 * <ul>
 * <li>an optional caption</li>
 * <li>support for widgets in the caption</li>
 * <li>a background panel which blocks user interaction with the page (also
 * stylable for the "light box" effect). </li>
 * <li>automatic centering in browser's main viewport (regardless of document
 * scroll)</li>
 * <li>resizable: if you set the size of the content area the layout will maintain
 * integrity on all platforms &amp; quirks/strict modes</li>
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
 * <p>
 * Usage Notes
 * </p>
 * <ul>
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
 */
public class ModalDialog extends PopupPanel implements ControllerSupport
{
	public static final String STYLENAME_DIALOG = "tk-ModalDialog";
	public static final String STYLENAME_GLASSPANEL = "tk-ModalDialog-glassPanel";
	public static final String STYLENAME_CAPTION = "Caption";
	public static final String STYLENAME_CONTENT = "tk-ModalDialog-content";
	public static final String STYLENAME_DRAGGING = "tk-ModalDialog-dragging";
	
	protected static final FocusImpl s_focusImpl = (FocusImpl) GWT.create(FocusImpl.class);
	
	private final Element m_focusable = s_focusImpl.createFocusable();
	private final GlassPanel m_glassPanel = new GlassPanel();
	private final RowPanel m_panel = new RowPanel();
	private final Element m_contentTd;
	private final ControllerSupportDelegate m_controllerSupport = new ControllerSupportDelegate(this);
	
	private FocusModel m_focusModel = new FocusModel();
	private CaptionWrapper m_caption = null;
	private int m_minContentWidth = 200;
	private int m_minContentHeight = 75;
	private HasFocus m_focusOnCloseWidget;
	
	public ModalDialog()
	{
	    setStyleName(STYLENAME_DIALOG);
		DOM.appendChild(getElement(), m_focusable);
		m_glassPanel.addStyleName(STYLENAME_GLASSPANEL);
		m_panel.addCell();
		m_contentTd = m_panel.getCellElement(0);
		m_panel.addCellStyleName(STYLENAME_CONTENT);
		setWidget(m_panel);
		setControllers(createControllers());
	}
	
	/**
	 * A factory method which gives a subclass the opportunity to override default 
	 * controller creation.
	 * 
	 * @return a List with 0 or more controllers, or <code>null</code>
	 */
	protected List createControllers()
	{
		List result = new Vector();
		result.add(new PositionDialogController());
		result.add(new InitializeFocusController());
		TabFocusController focusController = (TabFocusController) GWT.create(TabFocusController.class);
		focusController.setModel(getFocusModel());
		result.add(focusController);
		result.add(new FocusOnCloseController());
		return result;
	}
	
	/**
	 * A factory method which gives a subclass the opportunity to override default 
	 * controller creation.
	 * 
	 * @return a List with 0 or more controllers, or <code>null</code>
	 */
	protected List createCaptionControllers()
	{
		List result = new Vector();
		result.add(PreventSelectionController.INSTANCE);
		result.add(new DragStyleController(this));
		result.add(new DragController(new DragPopupGesture(this)));
		return result;
	}
	
	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.ControllerSupport#getController(java.lang.Class)
	 */
	public Controller getController(Class id)
	{
		return m_controllerSupport.getController(id);
	}
	
	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.ControllerSupport#addController(asquare.gwt.tk.client.ui.behavior.Controller)
	 */
	public Widget addController(Controller controller)
	{
		return m_controllerSupport.addController(controller);
	}
	
	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.ControllerSupport#removeController(asquare.gwt.tk.client.ui.behavior.Controller)
	 */
	public Widget removeController(Controller controller)
	{
		return m_controllerSupport.removeController(controller);
	}
	
	/*
	 *  (non-Javadoc)
	 * @see asquare.gwt.tk.client.ui.behavior.ControllerSupport#setControllers(java.util.List)
	 */
	public void setControllers(List controllers)
	{
		m_controllerSupport.setControllers(controllers);
	}
	
	/*
	 *  (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.UIObject#sinkEvents(int)
	 */
	public void sinkEvents(int eventBits)
	{
		m_controllerSupport.sinkEvents(eventBits);
	}
	
	/*
	 *  (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.UIObject#unsinkEvents(int)
	 */
	public void unsinkEvents(int eventBits)
	{
		m_controllerSupport.unSinkEvents(eventBits);
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
		m_focusModel = focusModel;
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
		DOM.setStyleAttribute(m_contentTd, "width", width);
	}
	
	/**
	 * Set the desired height of the content panel. The minimum height property
	 * will take precedence if applicable.
	 * 
	 * @param height the height in CSS measurements
	 */
	public void setContentHeight(String height)
	{
		DOM.setStyleAttribute(m_contentTd, "height", height);
	}
	
	/**
	 * Get the actual width of the content panel. This does not work until the
	 * dialog is attached to the DOM.
	 * 
	 * @return the width in pixels
	 */
	public int getContentOffsetWidth()
	{
		return DOM.getIntAttribute(m_contentTd, "offsetWidth");
	}
	
	/**
	 * Get the actual height of the content panel. This does not work until the
	 * dialog is attached to the DOM.
	 * 
	 * @return the height in pixels
	 */
	public int getContentOffsetHeight()
	{
		return DOM.getIntAttribute(m_contentTd, "offsetHeight");
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
		super.show();
	}
	
	/*
	 *  (non-Javadoc)
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
		
		m_controllerSupport.onAttach();
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
			m_controllerSupport.onDetach();
		}
	}
	
	/*
	 *  (non-Javadoc)
	 * @see com.google.gwt.user.client.EventListener#onBrowserEvent(com.google.gwt.user.client.Event)
	 */
	public void onBrowserEvent(Event event)
	{
		m_controllerSupport.onBrowserEvent(event);
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
		protected CaptionWrapper(Element captionElement, List controllers)
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
	public final class InitializeFocusController extends ControllerAdaptor
	{
		public InitializeFocusController()
		{
			super(InitializeFocusController.class);
		}
		
		public void plugIn(Widget widget)
		{
			if (getFocusModel().getFocusWidget() != null)
			{
				DeferredCommand.add(new FocusCommand(getFocusModel().getFocusWidget()));
			}
			else
			{
				DeferredCommand.add(new Command()
				{
					public void execute()
					{
						s_focusImpl.focus(m_focusable);
					}
				});
			}
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
				DeferredCommand.add(new FocusCommand(focusOnCloseWidget));
			}
		}
	}
	
	/**
	 * A controller which encapsulates dialog positioning logic. 
	 */
	public static class PositionDialogController extends ControllerAdaptor
	{
		public PositionDialogController()
		{
			super(0, PositionDialogController.class);
		}
		
		public void plugIn(Widget widget)
		{
			ModalDialog dialog = (ModalDialog) widget;
			int contentWidth = dialog.getContentOffsetWidth();
			int maxContentWidth = Window.getClientWidth() / 2;
			if (contentWidth > maxContentWidth || contentWidth < dialog.getContentMinWidth())
			{
				if (contentWidth > maxContentWidth)
				{
					contentWidth = maxContentWidth;
				}
				if (contentWidth < dialog.getContentMinWidth())
				{
					contentWidth = dialog.getContentMinWidth();
				}
				dialog.setContentWidth(contentWidth + "px");
			}
			int dialogHeight = dialog.getOffsetHeight();
			int left = DomUtil.getViewportScrollX() + DomUtil.getViewportWidth() / 2 - contentWidth / 2;
			int contentHeight = dialog.getContentOffsetHeight();
			if (contentHeight < dialog.getContentMinHeight())
			{
				contentHeight = dialog.getContentMinHeight();
				dialog.setContentHeight(contentHeight + "px");
			}
			// calculate top last because maxContentWidth constraint may change height
			int top = DomUtil.getViewportScrollY() + DomUtil.getViewportHeight() / 2 - dialogHeight / 2;
			dialog.setPopupPosition(left, top);
		}
	}
	
	/**
	 * A controller which applies the "dragging" style name to the dialog. 
	 */
	public static class DragStyleController extends ControllerAdaptor
	{
		private final ModalDialog m_dialog;
		
		public DragStyleController(ModalDialog dialog)
		{
			super(Event.ONMOUSEDOWN | Event.ONMOUSEUP, DragStyleController.class);
			m_dialog = dialog;
		}
		
		public void onBrowserEvent(Widget widget, Event event)
		{
			if (DOM.eventGetType(event) == Event.ONMOUSEDOWN)
			{
				m_dialog.addStyleName(STYLENAME_DRAGGING);
			}
			else if (DOM.eventGetType(event) == Event.ONMOUSEUP)
			{
				m_dialog.removeStyleName(STYLENAME_DRAGGING);
			}
		}
	}
}
