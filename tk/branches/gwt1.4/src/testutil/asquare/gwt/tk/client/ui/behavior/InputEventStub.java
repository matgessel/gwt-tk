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
package asquare.gwt.tk.client.ui.behavior;

public class InputEventStub extends EventBaseStub implements InputEvent
{
    private boolean m_altDown = false;
    private boolean m_controlDown = false;
    private boolean m_metaDown = false;
    private boolean m_shiftDown = false;
    
    public InputEventStub(int type, boolean previewPhase)
    {
    	super(type, previewPhase);
    }
    
    public boolean isAltDown()
	{
		return m_altDown;
	}

	public void setAltDown(boolean altDown)
	{
		m_altDown = altDown;
	}

	public boolean isControlDown()
	{
		return m_controlDown;
	}

	public void setControlDown(boolean controlDown)
	{
		m_controlDown = controlDown;
	}

	public boolean isMetaDown()
	{
		return m_metaDown;
	}

	public void setMetaDown(boolean metaDown)
	{
		m_metaDown = metaDown;
	}

	public boolean isShiftDown()
	{
		return m_shiftDown;
	}

	public void setShiftDown(boolean shiftDown)
	{
		m_shiftDown = shiftDown;
	}
}
