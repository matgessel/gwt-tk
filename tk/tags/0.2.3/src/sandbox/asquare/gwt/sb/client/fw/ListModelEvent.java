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

public class ListModelEvent extends Object
{
	private final ListModelRead m_source;
	private final int m_beginInterval;
	private final int m_endInterval;
	
	public ListModelEvent(ListModelRead source, int beginInterval, int endInterval)
	{
		m_source = source;
		m_beginInterval = beginInterval;
		m_endInterval = endInterval;
	}

	public int getBeginInterval()
	{
		return m_beginInterval;
	}

	public int getEndInterval()
	{
		return m_endInterval;
	}

	public ListModelRead getSource()
	{
		return m_source;
	}
}
