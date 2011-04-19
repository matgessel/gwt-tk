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
package asquare.gwt.tk.uitest.isvisible.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;

public class Demo implements EntryPoint
{
	public void onModuleLoad()
	{
		RootPanel outer = RootPanel.get();
		
		TextBox rowInput = new TextBox();
		TextBox colInput = new TextBox();
		Grid input = new Grid(2, 2);
		input.setText(0, 0, "Row: ");
		input.setWidget(0, 1, rowInput);
		input.setText(1, 0, "Col: ");
		input.setWidget(1, 1, colInput);
		outer.add(input);
		
		final int ROWS = 20;
		final int COLS = 20;
		Grid grid = new Grid(ROWS, COLS);
		grid.setCellPadding(0);
		grid.setCellSpacing(0);
		for (int row = 0; row < ROWS; row++)
		{
			for (int col = 0; col < COLS; col++)
			{
				grid.setWidget(row, col, new Label("(" + row + "," + col + ")"));
			}
		}
		
		ScrollPanel scrollInner = new ScrollPanel();
		scrollInner.setAlwaysShowScrollBars(true);
		scrollInner.setPixelSize(400, 400);
		scrollInner.setWidget(grid);
		ScrollPanel scrollOuter = new ScrollPanel();
		scrollOuter.add(scrollInner);
		scrollOuter.setAlwaysShowScrollBars(true);
		scrollOuter.setPixelSize(600, 200);
		outer.add(scrollOuter);
		scrollInner.setScrollPosition(100);
		scrollInner.setHorizontalScrollPosition(100);
	}
}
