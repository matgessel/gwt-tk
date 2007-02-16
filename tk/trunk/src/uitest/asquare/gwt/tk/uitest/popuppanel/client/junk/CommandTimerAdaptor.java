package asquare.gwt.tk.uitest.popuppanel.client.junk;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;

public class CommandTimerAdaptor extends Timer
{
	private final Command m_command;
	
	public CommandTimerAdaptor(Command command)
	{
		m_command = command;
	}
	
	public void run()
	{
		m_command.execute();
	}
}
