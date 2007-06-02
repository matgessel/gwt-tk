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
package asquare.gwt.tk.client.ui;

import asquare.gwt.tk.client.util.DomUtil;

import com.google.gwt.user.client.ui.Image;

/**
 * An icon is an image which requires its dimensions be specifed at construction
 * time. Useful for static layouts. 
 */
public class Icon extends Image
{
	/**
	 * Creates a new Icon sized in pixels. 
	 * 
	 * @param url the url of the image
	 * @param width the width in pixels
	 * @param height the height in pixels
	 */
	public Icon(String url, int width, int height)
	{
		this(url, width + "px", height + "px");
	}
	
	/**
	 * Creates a new Icon sized in CSS measurements. 
	 * 
	 * @param url the url of the image
	 * @param width the width in CSS measurements
	 * @param height the height in CSS measurements
	 */
	public Icon(String url, String width, String height)
	{
		super(url);
		setWidth(width);
		setHeight(height);
	}
	
	/**
	 * Gets the CSS width of the underlying element. 
	 * 
	 * @return the width in CSS measurements
	 */
	public int getWidth()
	{
		return DomUtil.getIntAttribute(this, "width");
	}
	
	/**
	 * Gets the CSS height of the underlying element. 
	 * 
	 * @return the height in CSS measurements
	 */
	public int getHeight()
	{
		return DomUtil.getIntAttribute(this, "height");
	}
}
