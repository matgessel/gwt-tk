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

import java.util.*;

import com.google.gwt.junit.client.*;

@SuppressWarnings("unused")
public class Compiler14AssertionError extends GWTTestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.tests.UnitTests";
	}
	
	static abstract class Food {}
	
	static interface CanEatRaw {}
	
	static class Apple extends Food implements CanEatRaw {}
	
	private final Food m_foodItem = new Apple();
	
	private final CanEatRaw m_rawFoodItem = new Apple();
	
	private Food getFoodItem()
	{
		return m_foodItem;
	}
	
	private CanEatRaw getRawFoodItem()
	{
		return m_rawFoodItem;
	}
	
	private Food getRawFoodAsFood_field()
	{
		return (Food) m_rawFoodItem;
	}
	
	private Food getRawFoodAsFood_method()
	{
		return (Food) getRawFoodItem();
	}
	
	private CanEatRaw getFoodAsRawFood_field()
	{
		return (CanEatRaw) m_foodItem;
	}
	
	private CanEatRaw getFoodAsRawFood_method()
	{
		return (CanEatRaw) getFoodItem();
	}
	
	public void testDownCastClass()
	{
		Apple apple = (Apple) m_foodItem;
	}
	
	public void testDownCastClass_method()
	{
		Apple apple = (Apple) getFoodItem();
	}
	
	public void testDownCastInterface()
	{
		Apple apple = (Apple) m_rawFoodItem;
	}
	
	public void testDownCastInterface_method()
	{
		Apple apple = (Apple) getRawFoodItem();
	}
	
	public void testInterfaceToBaseToConcrete_inline()
	{
		Apple apple = (Apple) (Food) m_rawFoodItem;
	}
	
	public void testInterfaceToBaseToConcrete_field()
	{
		Apple apple = (Apple) getRawFoodAsFood_field();
	}
	
	public void testInterfaceToBaseToConcrete_method()
	{
		Apple apple = (Apple) getRawFoodAsFood_method();
	}
	
	public void testBaseToInterface()
	{
		Apple apple = (Apple) m_foodItem;
	}

	public void testBaseToInterface_method()
	{
		Apple apple = (Apple) getFoodItem();
	}
	
	public void testBaseToInterfaceToConcrete_crazyInline()
	{
		Apple apple = (Apple) (CanEatRaw) (Food) new Apple();
	}

	public void testBaseToInterfaceToConcrete_inline()
	{
		Apple apple = (Apple) (CanEatRaw) m_foodItem;
	}

	public void testBaseToInterfaceToConcrete_field()
	{
		Apple apple = (Apple) getFoodAsRawFood_field();
	}

	public void testBaseToInterfaceToConcrete_method()
	{
		Apple apple = (Apple) getFoodAsRawFood_method();
	}
    
    public void testTemp()
    {
        List<Apple> apples = new ArrayList<Apple>();
        apples.add(new Apple());
        apples.add(new Apple());
        apples.add(new Apple());
        Apple[] appleArray = getApples(apples);
    }
    
    public Apple[] getApples(List<Apple> apples)
    {
        return (Compiler14AssertionError.Apple[]) toArray(apples, new Compiler14AssertionError.Apple[apples.size()]);
    }
    
    public static Object[] toArray(List<Apple> src, Object[] dest)
    {
        // necessary in web mode
        if (src == null || dest == null)
            throw new NullPointerException();
        
        int size = src.size();
        
        if (size > 0)
        {
            for (int i = 0; i < size; i++)
            {
                dest[i] = src.get(i);
            }
        }
        
        if (dest.length > size)
        {
            dest[size] = null;
        }
        
        return dest;
    }
}
