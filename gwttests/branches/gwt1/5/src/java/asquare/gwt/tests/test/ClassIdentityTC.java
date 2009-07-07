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
package asquare.gwt.tests.test;

import com.google.gwt.junit.client.*;

@SuppressWarnings("deprecation")
public class ClassIdentityTC extends GWTTestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.tests.UnitTests";
	}
	
	public void testIdentityComparison()
    {
        Class<?> id1 = ClassA.class;
        Class<?> id2 = ClassA.class;
        Class<?> id3 = ClassB.class;
        
        assertTrue(id1 == id2);
        assertTrue(id2 == id1);
        assertFalse(id1 == ClassB.class);
        assertFalse(ClassB.class == id1);
        assertFalse(id1 == id3);
        assertFalse(id3 == id1);
        
        
        BaseClass a = new ClassA();
        BaseClass a2 = new ClassA();
        BaseClass b = new ClassB();
        BaseClass c = new ClassC();
        
        addCheckpoint("a.equals(a2)");
        assertTrue(a.equals(a2));
        addCheckpoint("a2.equals(a)");
        assertTrue(a2.equals(a));
        addCheckpoint("a.equals(b)");
        assertFalse(a.equals(b));
        addCheckpoint("b.equals(a)");
        assertFalse(b.equals(a));
        addCheckpoint("a.equals(c)");
        assertFalse(a.equals(c)); // fails in GWT 1.4.61
        addCheckpoint("c.equals(a)");
        assertFalse(c.equals(a));
    }
    
    public static class BaseClass
    {
        private final Class<?> m_type;
        
        public BaseClass(Class<?> type)
        {
            m_type = type;
        }
        
        public boolean equals(Object obj)
        {
//            Window.alert("'" + ((BaseClass) obj).m_type + "'=='" + m_type + "'");
            return obj instanceof BaseClass && ((BaseClass) obj).m_type == m_type;
        }
    }
    
    public static class ClassA extends BaseClass
    {
        public static final Class<?> IDA = ClassA.class;
        
        public ClassA()
        {
            super(IDA);
        }
    }
    
    public static class ClassB extends BaseClass
    {
        public ClassB()
        {
            super(ClassB.class);
        }
    }
    
    public static class ClassC extends BaseClass
    {
        public static final Class<?> IDC = ClassC.class;
        
        public ClassC()
        {
            super(IDC);
        }
    }
}
