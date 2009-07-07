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
package asquare.gwt.tests.selecttreeitem.client;

import asquare.gwt.debug.client.*;

import com.google.gwt.core.client.*;
import com.google.gwt.user.client.ui.*;

public class Demo implements EntryPoint
{
	public void onModuleLoad()
	{
		Debug.enableSilently();
        
        TreeItem treeRoot = new TreeItem("Tree");
        final TreeItem treeItem = new TreeItem("foo");
        treeRoot.addItem(treeItem);
        treeRoot.addItem("bar");
        treeRoot.addItem("baz");
        final Tree tree = new Tree();
        tree.addItem(treeRoot);
//        DeferredCommand.addCommand(new Command()
//        {
//            public void execute()
//            {
                tree.setSelectedItem(treeItem);
                tree.ensureSelectedItemVisible();
//            }
//        });
//        
        RootPanel.get().add(tree);
        
        // this is not reached in Opera
        RootPanel.get().add(new Label("Entry point was sucessfully executed"));
	}
}
