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
package asquare.gwt.tests.history.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.*;

public class Demo implements EntryPoint
{
	public void onModuleLoad()
	{
		TabCollection tabSet = new TabCollection();
		tabSet.add("grains", "Grains", createGrains());
		tabSet.add("vegetables", "Vegetables", createVegetables());
		tabSet.add("fruit", "Fruit", createFruit());
		tabSet.add("stimulants", "Stimulants", createStimulants());
		
		TabPanel tabPanel = new TabPanel();
		for (int i = 0; i < tabSet.size(); i++)
		{
			tabPanel.add(tabSet.getWidget(i), tabSet.getDescription(i));
		}
		
		String initialTabToken = History.getToken();
		if (initialTabToken.length() == 0)
		{
			initialTabToken = tabSet.getToken(0);
		}
		TabController controller = new TabController(tabSet, tabPanel, initialTabToken);
		tabPanel.addSelectionHandler(controller);
		History.addValueChangeHandler(controller);
		
		RootPanel.get().add(tabPanel);
	}
	
	private static Widget createGrains()
	{
		HTML html = new HTML("<h4>Grains</h4><ul><li>wheat</li><li>rice</li><li>quinoa</li><li>beer</li></ul>");
		DOM.setElementProperty(html.getElement(), "id", "_grains");
		return html;
	}

	private static Widget createVegetables()
	{
		HTML html = new HTML("<h4>Vegetables</h4><ul><li>crucifers</li><li>carrots</li><li>spinach</li><li>bacon</li></ul>");
		DOM.setElementProperty(html.getElement(), "id", "_vegetables");
		return html;
	}
	
	private static Widget createFruit()
	{
		HTML html = new HTML("<h4>Fruit</h4><ul><li>cherries</li><li>rasberries</li><li>strawberries</li></ul>");
		/*
		 * This causes the bug. 
		 * The history token and the element id conflict. 
		 */
		DOM.setElementProperty(html.getElement(), "id", "fruit");
		return html;
	}
	
	private static Widget createStimulants()
	{
		HTML html = new HTML("<h4>Stimulants</h4><ul><li>coffee</li><li>tea</li><li>Jolt</li><li>yerba mat&eacute;</li></ul>");
		DOM.setElementProperty(html.getElement(), "id", "_stimulants");
		return html;
	}
}
