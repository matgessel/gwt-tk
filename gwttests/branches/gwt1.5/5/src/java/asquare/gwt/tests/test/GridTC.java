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

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;

public class GridTC extends GWTTestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.tests.UnitTests";
	}
	
	public void testSetWidget()
	{
		Grid grid = new Grid(10, 10);
		Label a1 = new Label("a");
		Label a2 = new Label("a");
		Label b = new LabelCustomEquals("b");
		Label c1 = new LabelCustomEquals("c");
		Label c2 = new LabelCustomEquals("c");
		
		grid.setWidget(0, 0, a1);
		grid.setWidget(1, 0, a2);
		grid.setWidget(2, 0, b);
		grid.setWidget(3, 0, c1);
		grid.setWidget(4, 0, c2);
		
		assertSame(a1, grid.getWidget(0, 0));
		assertSame(a2, grid.getWidget(1, 0));
		assertSame(b, grid.getWidget(2, 0));
		assertSame(c1, grid.getWidget(3, 0));
		assertSame(c2, grid.getWidget(4, 0));
		
		grid.remove(a1);
		grid.remove(a2);
		grid.remove(b);
		grid.remove(c1);
		grid.remove(c2);
	}
    
	private static class LabelCustomEquals extends Label
	{
		public LabelCustomEquals(String text)
		{
			setText(text);
		}
		
		public int hashCode()
		{
			return String.valueOf(getText()).hashCode();
		}
		
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			
			if (obj instanceof LabelCustomEquals)
			{
				String thisText = getText();
				String otherText = ((LabelCustomEquals) obj).getText();
				return thisText == otherText || thisText != null && thisText.equals(otherText);
			}
			return false;
		}
	}
}
