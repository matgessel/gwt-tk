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
package asquare.gwt.tests.test;

import java.util.*;

import com.google.gwt.junit.client.GWTTestCase;

public class StringTC extends GWTTestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.tests.UnitTests";
	}
	
	public void testMatches()
	{
		String content = "text:text";
		assertTrue(content.matches(".*:.*"));
	}
	
	public void testIndexOf()
	{
		String content = "text:text";
		assertTrue(content.indexOf(':') != -1);
	}
	
	public void testIdentityCompare()
	{
        // taken from Java Language Specifictaion 3.10.5: String Literals
        String hello = "Hello", lo = "lo";
        assertSame("1", hello, "Hello");
		assertSame("2", hello, ("Hel"+"lo"));
		assertNotSame("3", hello, ("Hel"+lo));
	}
    
    public void testReplace()
    {
        ArrayList<ReplaceTest> tests = new ArrayList<ReplaceTest>();
        tests.add(new ReplaceTest("example", "example", new char[][] {{'\r', ' '}, {'\n', ' '}}));
        tests.add(new ReplaceTest("xd", "xd", new char[][] {{'\r', ' '}, {'\n', ' '}}));
        tests.add(new ReplaceTest("a  b", "a\r\nb", new char[][] {{'\r', ' '}, {'\n', ' '}}));
        tests.add(new ReplaceTest("a\r b", "a\r\nb", new char[][] {{'\n', ' '}}));
        tests.add(new ReplaceTest("a \nb", "a\r\nb", new char[][] {{'\r', ' '}}));
        tests.add(new ReplaceTest("a \nb", "a\r\nb", new char[][] {{'\r', ' '}}));
        for (int i = 0, size = tests.size(); i < size; i++)
        {
            tests.get(i).execute(String.valueOf(i));
        }
    }
    
    private static class ReplaceTest
    {
        private final String mExpected;
        private final String mIn;
        private final char[][] mReplaceArgs;
        
        public ReplaceTest(String expected, String in, char[][] replaceArgs)
        {
            mExpected = expected;
            mIn = in;
            mReplaceArgs = replaceArgs;
        }
        
        public void execute(String traceInfo)
        {
            String result = mIn;
            for (int i = 0; i < mReplaceArgs.length; i++)
            {
                result = result.replace(mReplaceArgs[i][0], mReplaceArgs[i][1]);
            }
            assertEquals(traceInfo + '(' + "e" + dumpChars(mExpected) + ", a" + dumpChars(result) + ')' , mExpected, result);
        }
        
        private String dumpChars(String string)
        {
            String result = "";
            char[] chars = string.toCharArray();
            for (int i = 0; i < chars.length; i++)
            {
                result += '[' + String.valueOf((int) chars[i]) + ']';
            }
            return result;
        }
    }
}
