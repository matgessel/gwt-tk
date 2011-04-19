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
package asquare.gwt.tk.server;

import junit.framework.TestCase;

public class GWTCacheFilterTC extends TestCase
{
	public void testDefaultUrlPatterns()
	{
		String bootstrap = "http://www.asquare.net/gwttk/apps/asquare.gwt.tkdemo.Demo/asquare.gwt.tkdemo.Demo.nocache.js";
		String gwtScriptHTML = "http://www.asquare.net/gwttk/apps/asquare.gwt.tkdemo.Demo/30AAD53052A50EFB6E39EDCB0A889EFC.cache.html";
		String gwtScriptJs = "http://www.asquare.net/gwttk/apps/asquare.gwt.tkdemo.Demo/30AAD53052A50EFB6E39EDCB0A889EFC.cache.js";
		String imageStrip = "http://www.asquare.net/gwttk/apps/asquare.gwt.tkdemo.Demo/7C5D9A4EAA01B0E957A5F681BBA80FCB.cache.png";
		
		assertFalse(bootstrap.matches(GWTCacheFilter.DEFAULT_FORCECACHE));
		assertTrue(bootstrap.matches(GWTCacheFilter.DEFAULT_FORCEDONTCACHE));
		
		assertTrue(gwtScriptHTML.matches(GWTCacheFilter.DEFAULT_FORCECACHE));
		assertFalse(gwtScriptHTML.matches(GWTCacheFilter.DEFAULT_FORCEDONTCACHE));
		
		assertTrue(gwtScriptJs.matches(GWTCacheFilter.DEFAULT_FORCECACHE));
		assertFalse(gwtScriptJs.matches(GWTCacheFilter.DEFAULT_FORCEDONTCACHE));
		
		assertTrue(imageStrip.matches(GWTCacheFilter.DEFAULT_FORCECACHE));
		assertFalse(imageStrip.matches(GWTCacheFilter.DEFAULT_FORCEDONTCACHE));
	}
}
