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
package com.google.gwt.dom.client;

/**
 * Works around an IE problem where multiple images trying to load at the same
 * time will generate a request per image. We fix this by only allowing the
 * first image of a given URL to set its source immediately, but simultaneous
 * requests for the same URL don't actually get their source set until the
 * original load is complete.
 */
class ImageSrcIE6Patched
{
	public static String getImgSrc(Element img)
	{
		return ImageSrcIE6.getImgSrc(img);
	}
	
	public static void setImgSrc(Element img, String src)
	{
		try
		{
			ImageSrcIE6.setImgSrc(img, src);
		}
		catch (Throwable e)
		{
			System.err.println("Warning: ImageSrcIE6Patched.setImgSrc(), ignoring exception: " + e.getMessage());
		}
	}
}
