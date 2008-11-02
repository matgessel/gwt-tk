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

import com.google.gwt.junit.client.*;

public class ClassCastTestCase1 extends GWTTestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.tests.UnitTests";
	}
    
    public void testDownCastArrayVariant()
    {
        /* 
         * Fails in web mode in GWT 1.3.3 with: 
         * com.google.gwt.core.client.JavaScriptException: JavaScript TypeError exception: 'null._nullField' is null or not an object
         */
        SortRule[] sortBy = (SortRule[]) toArray(new SortRule[0]);
        assertNotNull(sortBy);
        assertEquals(0, sortBy.length);
    }
    
    public static Object[] toArray(Object[] dest)
    {
        return dest;
    }
    
    public static class SortRule
    {
    }
}
