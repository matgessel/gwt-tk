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

import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;

public class EventBaseStub implements EventBase
{
	private final int m_type;
    private final boolean m_previewPhase;
    
    private boolean m_killPreviewEvent;
    
    public EventBaseStub(int type, boolean previewPhase)
	{
        m_type = type;
        m_previewPhase = previewPhase;
	}

    public boolean didEventOccurIn(UIObject uio)
    {
        return false;
    }

    public boolean didEventOccurIn(Element e)
    {
        return false;
    }

    public Element getCurrentTarget()
    {
        return null;
    }

    public Event getDomEvent()
    {
        return null;
    }

    public Widget getSourceWidget()
    {
        return null;
    }

    public Element getTarget()
    {
        return null;
    }

    public int getType()
    {
        return m_type;
    }

    public boolean isKillPreviewEvent()
    {
        return m_killPreviewEvent;
    }

    public boolean isPreviewPhase()
    {
        return m_previewPhase;
    }

    public void killPreviewEvent()
    {
        m_killPreviewEvent = true;
    }

    public void preventDefault()
    {
    }

    public void stopPropagation()
    {
    }
}
