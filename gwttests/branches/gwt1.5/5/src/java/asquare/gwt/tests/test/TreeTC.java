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

import com.google.gwt.junit.client.*;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;

public class TreeTC extends GWTTestCase
{
	public String getModuleName()
	{
		return "asquare.gwt.tests.UnitTests";
	}
	
	public void testFocusInvisibleElement()
	{
        DeckPanel outer = new DeckPanel();
        RootPanel.get().add(outer);
        
        outer.add(new Label("Card 1"));
        outer.showWidget(0);
        
        TreeItem treeRoot = new TreeItem("Tree");
        final TreeItem treeItem = new TreeItem("foo");
        treeRoot.addItem(treeItem);
        final Tree tree = new Tree();
        tree.addItem(treeRoot);
        /*
         * Defer selection so that Tree does not break Opera
         * http://code.google.com/p/google-web-toolkit/issues/detail?id=1784
         */
        DeferredCommand.addCommand(new Command()
        {
            public void execute()
            {
                tree.setSelectedItem(treeItem);
                tree.ensureSelectedItemVisible();
                finishTest();
            }
        });
        
        /*
         * Use case: panel is not attached right now. It may be attached later 
         * as part of an incremental loading process.
         * 
         * For some reason this bug does not happen with a SimplePanel(DIV)
         */
        HorizontalPanel hPanel = new HorizontalPanel();
        hPanel.add(tree);
        
        /*
         * Uncomment this line and the test will pass
         */
//        RootPanel.get().add(hPanel);

        delayTestFinish(1000);
	}
}
