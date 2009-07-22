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

import asquare.gwt.sb.client.fw.StyleNames;
import asquare.gwt.tk.client.ui.BasicPanel;
import asquare.gwt.tk.client.ui.CWrapper;
import asquare.gwt.tk.client.ui.behavior.FocusModel;
import asquare.gwt.tk.client.ui.behavior.TabFocusController;
import asquare.gwt.tk.client.ui.behavior.event.FocusModelHandler;
import asquare.gwt.tk.client.util.DomUtil;
import asquare.gwt.tkdemo.client.ui.FocusCyclePanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class FocusCycleDemo extends BasicPanel
{
	public static final String NAME = "Focus";
	
	public FocusCycleDemo()
	{
		addStyleName("division");
		
		String content = "<H2>Focus Cycle</H2>" + 
			"<p>In some browsers you can programmatically override the default tab order. " +
			"Unsupported browsers use a stub implementation which allows the default behavior.</p>" +
			"<p>IE, Mozilla (Win) &amp; Safari are currently supported. <br/>" +
			"<code>asquare.gwt.tk.TabFocusController.gwt.xml</code><br/>" + 
			"<code>asquare.gwt.tk.client.ui.behavior.FocusModel</code><br/>" + 
			"<code>asquare.gwt.tk.client.ui.behavior.TabFocusController</code></p>";
		HTML header = new HTML(content);
		header.addStyleName("description");
		add(header);
		
		HorizontalPanel examples = new HorizontalPanel();
		examples.setWidth("100%");
		examples.addStyleName("example");
		
		examples.add(createFocusCycle1());
		examples.add(createFocusCycle2());

		add(examples);
	}
	
	private Widget createFocusCycle1()
	{
		FocusCyclePanel cycle1 = new FocusCyclePanel("div", "block");
		
		cycle1.add(new Label("Cycle 1"));
		
		cycle1.add(new FocusStyleDecorator(new Button("Button")));
		
		Button buttonDisabled = new Button("disabled");
		buttonDisabled.setEnabled(false);
		cycle1.add(new FocusStyleDecorator(buttonDisabled));
		
		Button buttonNegativeTabIndex = new Button("tabIndex = -1");
		buttonNegativeTabIndex.setTabIndex(-1);
		cycle1.add(new FocusStyleDecorator(buttonNegativeTabIndex));
		
		cycle1.add(new FocusStyleDecorator(new CheckBox("CheckBox")));
		
		cycle1.add(new FocusStyleDecorator(new FocusPanel(new Label("FocusPanel"))));
		
		ListBox listBox = new ListBox();
		listBox.addItem("ListBox");
		listBox.addItem("Item 1");
		listBox.addItem("Item 2");
		listBox.addItem("Item 3");
		cycle1.add(new FocusStyleDecorator(listBox));
		
		TextBox textBox = new TextBox();
		textBox.setText("TextBox");
		cycle1.add(new FocusStyleDecorator(textBox));
		
		PasswordTextBox pwBox = new PasswordTextBox();
		pwBox.setText("PasswordTextBox");
		cycle1.add(new FocusStyleDecorator(pwBox));
		
		TextArea textArea = new TextArea();
		textArea.setText("TextArea");
		cycle1.add(new FocusStyleDecorator(textArea));
		
		Tree tree = new Tree();
		TreeItem treeRoot = new TreeItem("Tree");
		for (int branchNum = 1; branchNum < 4; branchNum++)
		{
			TreeItem branch = new TreeItem("Branch " + branchNum);
			for (int item = 1; item < 4; item++)
			{
				branch.addItem("Item " + item);
			}
			treeRoot.addItem(branch);
		}
		tree.addItem(treeRoot);
		cycle1.add(new FocusStyleDecorator(tree));
		
        new WidgetFocusStyleController(cycle1.getFocusModel());
        
		return cycle1;
	}
	
	private Widget createFocusCycle2()
	{
		BasicPanel cycle2 = new BasicPanel("div", "block");
		FocusModel focusModel = new FocusModel();
		TabFocusController focusController = (TabFocusController) GWT.create(TabFocusController.class);
		focusController.setModel(focusModel);
		
		cycle2.add(new Label("Cycle 2"));
		Label label = new Label("A custom focus cycle across containers");
		DomUtil.setStyleAttribute(label, "fontSize", "smaller");
		cycle2.add(label);
		HorizontalPanel containers = new HorizontalPanel();
		
		FocusStyleDecorator[] buttons = new FocusStyleDecorator[6];
		for (int i = 0; i < buttons.length; i++)
		{
			buttons[i] = new FocusStyleDecorator(new Button("index&nbsp;" + i));
		}
		VerticalPanel container1 = new VerticalPanel();
		container1.addStyleName("focus-container");
		VerticalPanel container2 = new VerticalPanel();
		container2.addStyleName("focus-container");
		
		for (int i = 0; i < buttons.length; i+=2)
		{
			container1.add(buttons[i]);
			focusModel.add(buttons[i]);
			container2.add(buttons[i + 1]);
			focusModel.add(buttons[i + 1]);
		}
		
		containers.add(new CWrapper(container1).addController(focusController));
		containers.add(new CWrapper(container2).addController(focusController));
		cycle2.add(containers);
		
		new WidgetFocusStyleController(focusModel);
        
        return cycle2;
	}
    
    private static class FocusStyleDecorator extends SimplePanel implements Focusable, HasFocusHandlers, HasBlurHandlers
    {
        private final HandlerManager m_handlerManager = new HandlerManager(this);
    	
    	private Focusable m_wrapped;
        
        public FocusStyleDecorator(Widget toWrap)
        {
            m_wrapped = (Focusable) toWrap;
            setWidget(toWrap);
            setStyleName("FocusStyleDecorator");
            class Handler implements FocusHandler, BlurHandler
            {
				public void onFocus(FocusEvent event)
				{
					m_handlerManager.fireEvent(event);
				}
				public void onBlur(BlurEvent event)
				{
					m_handlerManager.fireEvent(event);
				}
            }
            Handler handler = new Handler();
            ((HasFocusHandlers) m_wrapped).addFocusHandler(handler);
            ((HasBlurHandlers) m_wrapped).addBlurHandler(handler);
        }

        public HandlerRegistration addFocusHandler(FocusHandler handler)
        {
        	return m_handlerManager.addHandler(FocusEvent.getType(), handler);
        }
        
        public HandlerRegistration addBlurHandler(BlurHandler handler)
        {
        	return m_handlerManager.addHandler(BlurEvent.getType(), handler);
        }
        
        public int getTabIndex()
        {
            return m_wrapped.getTabIndex();
        }

        public void setAccessKey(char key)
        {
            m_wrapped.setAccessKey(key);
        }

        public void setFocus(boolean focused)
        {
            m_wrapped.setFocus(focused);
        }

        public void setTabIndex(int index)
        {
            m_wrapped.setTabIndex(index);
        }
        
        public String toString()
        {
            return m_wrapped.toString();
        }
    }
    
    private static class WidgetFocusStyleController implements FocusModelHandler
    {
        private UIObject m_current = null;
        private UIObject m_previous = null;
        
        public WidgetFocusStyleController(FocusModel focusModel)
        {
            focusModel.addHandler(this);
            focusChanged(focusModel, focusModel.getBlurWidget(), focusModel.getCurrentWidget());
        }
        
        public void focusChanged(FocusModel model, Focusable previous, Focusable current)
        {
            if (m_current != null)
            {
                m_current.removeStyleDependentName(StyleNames.FOCUSED);
            }
            else
            {
                if (m_previous != null)
                {
                    m_previous.removeStyleDependentName(StyleNames.FOCUSEDINACTIVE);
                }
            }
            m_current = (UIObject) current;
            m_previous = (UIObject) previous;
            if (m_current != null)
            {
                m_current.addStyleDependentName(StyleNames.FOCUSED);
            }
            else
            {
                if (m_previous != null)
                {
                    m_previous.addStyleDependentName(StyleNames.FOCUSEDINACTIVE);
                }
            }
        }
        
        public void widgetsAdded(FocusModel model, Focusable[] added)
        {
        }
        
        public void widgetsRemoved(FocusModel model, Focusable[] removed)
        {
            for (int i = 0; i < removed.length; i++)
            {
                if (removed[i] == m_current)
                {
                    m_current.removeStyleDependentName(StyleNames.FOCUSED);
                    m_current = null;
                }
                if (removed[i] == m_previous)
                {
                    m_previous.removeStyleDependentName(StyleNames.FOCUSEDINACTIVE);
                    m_previous = null;
                }
            }
        }
    }
}
