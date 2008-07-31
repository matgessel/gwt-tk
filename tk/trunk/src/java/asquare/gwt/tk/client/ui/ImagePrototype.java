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
package asquare.gwt.tk.client.ui;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Image;

public class ImagePrototype extends AbstractImagePrototype
{
	private final String m_url;
	
	public ImagePrototype(String url)
	{
		m_url = url;
	}
	
	public void applyTo(Image image)
	{
		image.setUrl(m_url);
	}
	
	public Image createImage()
	{
	    return new Image(m_url);
	}
	
	public String getHTML()
	{
		return "<img src='" + m_url + "'>";
	}
}
