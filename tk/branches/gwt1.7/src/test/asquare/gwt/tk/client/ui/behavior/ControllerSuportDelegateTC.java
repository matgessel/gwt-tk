package asquare.gwt.tk.client.ui.behavior;

import java.util.ArrayList;
import java.util.List;

import asquare.gwt.debug.client.DebugUtil;
import asquare.gwt.tk.client.Tests;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class ControllerSuportDelegateTC extends GWTTestCase
{
	private WidgetStub m_widget;
	private ControllerSupportDelegate m_delegate;
	private ControllerStub m_controllerStub2;
	private ControllerStub m_controllerStub4;
	private ControllerStub m_controllerStub8;
	private List<ControllerStub> m_controllers2;
	private List<ControllerStub> m_controllers12;
	
    @Override
	public String getModuleName()
	{
		return Tests.getModuleName();
	}
	
	private void setupImpl(int eventBitsForWidget)
	{
		m_widget = new WidgetStub(eventBitsForWidget);
		m_delegate = new ControllerSupportDelegate(m_widget);
		m_controllerStub2 = new ControllerStub(2);
		m_controllerStub4 = new ControllerStub(4);
		m_controllerStub8 = new ControllerStub(8);
		m_controllers2 = new ArrayList<ControllerStub>();
		m_controllers2.add(m_controllerStub2);
		m_controllers12 = new ArrayList<ControllerStub>();
		m_controllers12.add(m_controllerStub4);
		m_controllers12.add(m_controllerStub8);
	}
	
	private void assertBits(int expected)
	{
		assertEquals(expected, DOM.getEventsSunk(m_widget.getElement()));
	}
	
	private void attach()
	{
		RootPanel.get().add(m_widget);
		m_delegate.onAttach();
	}
	
	private void detach()
	{
		m_delegate.onDetach();
		RootPanel.get().remove(m_widget);
	}
	
	public void testConstruction()
	{
		setupImpl(1);
		assertBits(0);
		try
		{
			m_delegate.getBitsForOnBrowserEvent();
			fail();
		}
		catch (IllegalStateException e)
		{
			// EXPECTED
		}
	}
	
	public void testAttach()
	{
		setupImpl(1);
		attach();
		assertBits(1);
	}
	
	public void testGwtSinkEvents()
	{
		setupImpl(1);
		attach();
		detach();
		m_delegate.addController(m_controllerStub2);
		assertBits(3);
		m_delegate.sinkEvents(8);
		assertBits(11);
		
		setupImpl(1);
		m_delegate.addController(m_controllerStub2);
		attach();
		assertBits(3);
		m_delegate.sinkEvents(8);
		assertBits(11);
		
		setupImpl(1);
		m_delegate.addController(m_controllerStub2);
		m_delegate.sinkEvents(8);
		attach();
		assertBits(11);
	}
	
	public void testAddRemoveController()
	{
		setupImpl(1);
		attach();
		m_delegate.addController(m_controllerStub2);
		assertBits(3);
		
		setupImpl(1);
		m_delegate.addController(m_controllerStub2);
		m_delegate.removeController(m_controllerStub2);
		attach();
		assertBits(1);
		m_delegate.addController(m_controllerStub2);
		assertBits(3);
		m_delegate.removeController(m_controllerStub2);
		assertBits(1);
		m_delegate.addController(m_controllerStub2);
		detach();
		assertBits(3);
		m_delegate.removeController(m_controllerStub2);
		assertBits(1);
		m_delegate.addController(m_controllerStub2);
		assertBits(3);
	}
	
	public void testSetControllers()
	{
		setupImpl(1);
		m_delegate.setControllers(m_controllers2);
		attach();
		assertBits(3);
		m_delegate.setControllers(m_controllers12);
		assertBits(13);
		detach();
		m_delegate.setControllers(m_controllers2);
		assertBits(3);
	}
	
	private static class ControllerStub extends ControllerAdaptor
	{
		public ControllerStub(int eventBits)
		{
			super(ControllerStub.class, eventBits);
		}
	}

	public class WidgetStub extends Widget
	{
	    public WidgetStub(int eventBits)
	    {
	        setElement(DOM.createDiv());
	        sinkEvents(eventBits);
	    }
	    
	    @Override
		protected void onAttach()
	    {
	    	super.onAttach();
	    }
	    
	    @Override
	    public void sinkEvents(int eventBitsToAdd)
	    {
	    	super.sinkEvents(eventBitsToAdd);
	    }
	    
	    @Override
	    public String toString()
	    {
	        return DebugUtil.prettyPrintEventMask(DOM.getEventsSunk(getElement()));
	    }
	}
}
