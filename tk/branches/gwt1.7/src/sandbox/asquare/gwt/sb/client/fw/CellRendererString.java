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

/**
 * A cell renderer supporting String display. By default, the model element for
 * the cell is converted to a String via {@link String#valueOf(Object)} and
 * written to the cell. The default conversion can be overriden by setting a
 * custom formatter.
 * 
 * @see #setFormatter(StringFormatter)
 */
public interface CellRendererString extends CellRenderer
{
	StringFormatter getFormatter();

	void setFormatter(StringFormatter formatter);
}
