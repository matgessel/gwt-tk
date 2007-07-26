package asquare.gwt.sb.client.util;

public interface SourcesCommandQueueEvents
{
	void addListener(CommandQueueListener listener);

	void removeListener(CommandQueueListener listener);
}
