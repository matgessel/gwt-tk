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
package asquare.gwt.sb.client.fw;

public class StringFormatterDefault implements StringFormatter
{
	/**
	 * The default implementation invokes {@link #getString(Object)}. 
	 * 
	 * @return <code>{@link #getString(Object)}</code>
	 */
	public String getEditingString(Object modelElement)
	{
		return getString(modelElement);
	}

	/**
	 * @return <code>null</code> to indcate that <code>{@link #getString(Object)}</code> should be used. 
	 */
	public String getHtml(Object modelElement)
	{
		return null;
	}

	/**
	 * @return <code>{@link String#valueOf(Object)}</code>
	 */
	public String getString(Object modelElement)
	{
		return String.valueOf(modelElement);
	}
}
