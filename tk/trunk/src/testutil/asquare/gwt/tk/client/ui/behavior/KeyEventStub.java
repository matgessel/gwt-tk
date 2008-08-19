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
package asquare.gwt.tk.client.ui.behavior;

public class KeyEventStub extends InputEventStub implements KeyEvent
{
	private final int m_keyCode;
    private final boolean m_autoRepeat;
    
    public KeyEventStub(int type, int keyCode, boolean autoRepeat, boolean previewPhase)
	{
        super(type, previewPhase);
        m_keyCode = keyCode;
        m_autoRepeat = autoRepeat;
	}

    public int getKeyCode()
    {
        return m_keyCode;
    }

    public char getKeyPressChar()
    {
        return (char) m_keyCode;
    }

    public boolean isAutoRepeat()
    {
        return m_autoRepeat;
    }
}
