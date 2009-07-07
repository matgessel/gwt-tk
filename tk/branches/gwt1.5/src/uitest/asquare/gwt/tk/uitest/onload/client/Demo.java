/*
 * Copyright 2008 Mat Gessel <mat.gessel@gmail.com>
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
package asquare.gwt.tk.uitest.onload.client;

import asquare.gwt.debug.client.Debug;
import asquare.gwt.debug.client.DebugConsole;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class Demo implements EntryPoint
{
	public void onModuleLoad()
	{
		if (! GWT.isScript())
		{
			DebugConsole.getInstance().disable();
		}
		Debug.enable();
		
		Branch root = new Branch("0");
		populateTree("0", root, 1, 2);
		Debug.println("Attaching root");
		RootPanel.get().add(root);
	}
	
    private static void populateTree(String labelPrefix, Branch branch, int childCount, int depth)
    {
    	for (int i = 0; i < childCount; i++)
        {
        	String label = labelPrefix + i;
    		if (depth == 1)
        	{
                branch.add(new Leaf(label));
        	}
        	else
        	{
        		Branch child = new Branch(label);
        		branch.add(child);
        		populateTree(label, child, childCount, depth - 1);
        	}
        }
    }
    
	private static class Branch extends ComplexPanel
	{
		private final TraceDelegate m_traceDelegate;
		
		public Branch(String label)
		{
			m_traceDelegate = new TraceDelegate(this, label);
			Element outer = DOM.createDiv();
			Element inner = DOM.createDiv();
			DOM.setInnerText(inner, label);
			DOM.appendChild(outer, inner);
			setElement(outer);
			DOM.setStyleAttribute(getElement(), "borderLeft", "solid black 10px");
		}
		
		public void add(Widget child)
		{
			super.add(child, getElement());
		}
		
		protected void onAttach()
		{
			m_traceDelegate.onAttach0();
			super.onAttach();
			m_traceDelegate.onAttach1();
		}
		
		protected void onDetach()
		{
			m_traceDelegate.onDetach0();
			super.onDetach();
			m_traceDelegate.onDetach1();
		}
		
		protected void doAttachChildren()
		{
			m_traceDelegate.doAttachChildren0();
			super.doAttachChildren();
			m_traceDelegate.doAttachChildren1();
		}
		
		protected void doDetachChildren()
		{
			m_traceDelegate.doDetachChildren0();
			super.doDetachChildren();
			m_traceDelegate.doDetachChildren1();
		}
		
		protected void onLoad()
		{
			m_traceDelegate.onLoad0();
			super.onLoad();
			m_traceDelegate.onLoad1();
		}
		
		protected void onUnload()
		{
			m_traceDelegate.onUnload0();
			super.onUnload();
			m_traceDelegate.onUnload1();
		}
	}
	
	private static class Leaf extends Widget
	{
		private final TraceDelegate m_traceDelegate;
		
		public Leaf(String label)
		{
			m_traceDelegate = new TraceDelegate(this, label);
			Element outer = DOM.createDiv();
			DOM.setInnerText(outer, label);
			setElement(outer);
			DOM.setStyleAttribute(getElement(), "borderLeft", "solid black 10px");
		}
		
		protected void onAttach()
		{
			m_traceDelegate.onAttach0();
			super.onAttach();
			m_traceDelegate.onAttach1();
		}
		
		protected void onDetach()
		{
			m_traceDelegate.onDetach0();
			super.onDetach();
			m_traceDelegate.onDetach1();
		}
		
		protected void doAttachChildren()
		{
			m_traceDelegate.doAttachChildren0();
			super.doAttachChildren();
			m_traceDelegate.doAttachChildren1();
		}
		
		protected void doDetachChildren()
		{
			m_traceDelegate.doDetachChildren0();
			super.doDetachChildren();
			m_traceDelegate.doDetachChildren1();
		}
		
		protected void onLoad()
		{
			m_traceDelegate.onLoad0();
			super.onLoad();
			m_traceDelegate.onLoad1();
		}
		
		protected void onUnload()
		{
			m_traceDelegate.onUnload0();
			super.onUnload();
			m_traceDelegate.onUnload1();
		}
	}
	
	@SuppressWarnings("unused")
	private static class TraceDelegate
	{
		private static final String ON_ATTACH = "onAttach";
		private static final String ON_DEATACH = "onDetach";
		private static final String DO_ATTACH_CHILDREN = "doAttachChildren";
		private static final String DO_DEATACH_CHILDREN = "doDetachChildren";
		private static final String ON_LOAD = "onLoad";
		private static final String ON_UNLOAD = "onUnload";
		
		private final String m_label;
		
		public TraceDelegate(Widget widget, String label)
		{
			m_label = label;
		}
		
		private void traceMethodEntry(String methodName)
		{
			traceMethod(" -> ", methodName);
		}
		
		private void traceMethodExit(String methodName)
		{
			traceMethod(" <- ", methodName);
		}
		
		private void traceMethod(String prefix, String methodName)
		{
			Debug.println(prefix + methodName + "(" + m_label + ")");
		}
		
		public void onAttach0()
		{
			traceMethodEntry(ON_ATTACH);
		}
		
		public void onAttach1()
		{
			traceMethodExit(ON_ATTACH);
		}
		
		public void onDetach0()
		{
			traceMethodEntry(ON_DEATACH);
		}
		
		public void onDetach1()
		{
			traceMethodExit(ON_DEATACH);
		}
		
		public void doAttachChildren0()
		{
//			traceMethodEntry(DO_ATTACH_CHILDREN);
		}
		
		public void doAttachChildren1()
		{
//			traceMethodExit(DO_ATTACH_CHILDREN);
		}
		
		public void doDetachChildren0()
		{
//			traceMethodEntry(DO_DEATACH_CHILDREN);
		}
		
		public void doDetachChildren1()
		{
//			traceMethodExit(DO_DEATACH_CHILDREN);
		}
		
		public void onLoad0()
		{
			traceMethodEntry(ON_LOAD);
		}
		
		public void onLoad1()
		{
			traceMethodExit(ON_LOAD);
		}
		
		public void onUnload0()
		{
			traceMethodEntry(ON_UNLOAD);
		}
		
		public void onUnload1()
		{
			traceMethodExit(ON_UNLOAD);
		}
		
		private native void callOnAttach(Widget widget) /*-{
			widget.@com.google.gwt.user.client.ui.Widget::onAttach()();
		}-*/;
		
		private native void callOnDetach(Widget widget) /*-{
			widget.@com.google.gwt.user.client.ui.Widget::onDetach()();
		}-*/;
		
		private native void callDoAttachChildren(Widget widget) /*-{
			widget.@com.google.gwt.user.client.ui.Widget::doAttachChildren()();
		}-*/;
		
		private native void callDoDetachChildren(Widget widget) /*-{
			widget.@com.google.gwt.user.client.ui.Widget::doDetachChildren()();
		}-*/;
	
		private native void callOnLoad(Widget widget) /*-{
			widget.@com.google.gwt.user.client.ui.Widget::onLoad()();
		}-*/;
		
		private native void callOnUnload(Widget widget) /*-{
			widget.@com.google.gwt.user.client.ui.Widget::onUnload()();
		}-*/;
	}
}
