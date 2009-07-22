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

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.junit.client.GWTTestCase;

public class ThrowExceptionInJS extends GWTTestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.tests.UnitTests";
	}
	
	@SuppressWarnings("unused")
	private static IllegalArgumentException newIllegalArgumentException(String message)
	{
		return new IllegalArgumentException(message);
	}
	
	@SuppressWarnings("unused")
	private static void throwIllegalArgumentException(String message)
	{
		throw new IllegalArgumentException(message);
	}
	
	private native void initJSNI() /*-{
		if (typeof JSNI == "undefined" || ! JSNI.initialized)
		{
			JSNI = function() {};
			JSNI.newIllegalArgumentException = function(message)
			{
				return @asquare.gwt.tests.test.ThrowExceptionInJS::newIllegalArgumentException(Ljava/lang/String;)(message);
			};
			JSNI.throwIllegalArgumentException = function(message)
			{
				return @asquare.gwt.tests.test.ThrowExceptionInJS::throwIllegalArgumentException(Ljava/lang/String;)(message);
			};
			JSNI.initialized = true;
		}
	}-*/;
	
	private native void createThrowJavaException() /*-{
		throw JSNI.newIllegalArgumentException("exception created via Java factory method");
	}-*/;

	private native void throwExceptionInJava() /*-{
		throw JSNI.throwIllegalArgumentException("exception thrown by a Java method");
	}-*/;

	private native void throwStringLiteral() /*-{
		throw "string literal";
	}-*/;
	
	private native void throwInteger() /*-{
		throw 1;
	}-*/;
	
	private native void throwFloat() /*-{
		throw 1.1;
	}-*/;
	
	private native void throwBoolean() /*-{
		throw false;
	}-*/;
	
	private native void throwObject() /*-{
		function AnObject() {}
		throw new AnObject();
	}-*/;
	
	private native void throwError() /*-{
		throw new Error("JS Error Object");
	}-*/;
	
	public void testCreateThrowJavaException()
	{
		initJSNI();
		try
		{
			createThrowJavaException();
			fail("expected IllegalArgumentException");
		}
		catch (IllegalArgumentException e)
		{
			// expected
		}
		catch (RuntimeException e)
		{
			fail("expected IllegalArgumentException");
		}
	}
	
	public void testThrowExceptionInJava()
	{
		initJSNI();
		try
		{
			throwExceptionInJava();
			fail("expected IllegalArgumentException");
		}
		catch (IllegalArgumentException e)
		{
			// expected
		}
		catch (RuntimeException e)
		{
			fail("expected IllegalArgumentException");
		}
	}
	
	public void testThrowStringLiteral()
	{
		try
		{
			throwStringLiteral();
			fail("expected JavaScriptException");
		}
		catch (JavaScriptException e)
		{
			// expected
		}
		catch (RuntimeException e)
		{
			fail("expected JavaScriptException");
		}
	}
	
	public void testThrowInteger()
	{
		try
		{
			throwInteger();
			fail("expected JavaScriptException");
		}
		catch (JavaScriptException e)
		{
			// expected
		}
		catch (RuntimeException e)
		{
			fail("expected JavaScriptException");
		}
	}
	
	public void testThrowFloat()
	{
		try
		{
			throwFloat();
			fail("expected JavaScriptException");
		}
		catch (JavaScriptException e)
		{
			// expected
		}
		catch (RuntimeException e)
		{
			fail("expected JavaScriptException");
		}
	}
	
	public void testThrowBoolean()
	{
		try
		{
			throwBoolean();
			fail("expected JavaScriptException");
		}
		catch (JavaScriptException e)
		{
			// expected
		}
		catch (RuntimeException e)
		{
			fail("expected JavaScriptException");
		}
	}
	
	public void testThrowObject()
	{
		try
		{
			throwObject();
			fail("expected JavaScriptException");
		}
		catch (JavaScriptException e)
		{
			// expected
		}
		catch (RuntimeException e)
		{
			fail("expected JavaScriptException");
		}
	}
	
	public void testThrowError()
	{
		try
		{
			throwError();
			fail("expected JavaScriptException");
		}
		catch (JavaScriptException e)
		{
			// expected
		}
		catch (RuntimeException e)
		{
			fail("expected JavaScriptException");
		}
	}
}
