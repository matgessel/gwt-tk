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
package asquare.gwt.tests.test;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.junit.client.GWTTestCase;

public class ListenerCollectionTC extends GWTTestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.tests.UnitTests";
	}
	
	public void testConcurrentModification()
	{
		final HandlerManager listeners = new HandlerManager(this);
		listeners.addHandler(EventStub.TYPE, new EventHandlerStub());
		listeners.addHandler(EventStub.TYPE, new EventHandlerStub());
		final HandlerRegistration[] registration = new HandlerRegistration[1];
		registration[0] = listeners.addHandler(EventStub.TYPE, new EventHandlerStub()
		{
			@Override
			public void handle()
			{
//				listeners.removeHandler(EventStub.TYPE, this); // deprecated method
				registration[0].removeHandler();
			}
		});
		listeners.addHandler(EventStub.TYPE, new EventHandlerStub());
		listeners.addHandler(EventStub.TYPE, new EventHandlerStub());
		
		listeners.fireEvent(new EventStub());
		// side-effect: listener removal
		listeners.fireEvent(new EventStub());
	}
	
	private static class EventHandlerStub implements EventHandler
	{
		public void handle()
		{
		}
	}
	
	private static class EventStub extends GwtEvent<EventHandlerStub>
	{
		private static final Type<EventHandlerStub> TYPE = new Type<EventHandlerStub>();
		
		@Override
		protected void dispatch(EventHandlerStub handler)
		{
			handler.handle();
		}

		@Override
		public Type<EventHandlerStub> getAssociatedType()
		{
			return null;
		}
	}
}
