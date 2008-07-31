package asquare.gwt.sb.client.fw;

public interface PropertyChangeSupport extends ExplicitUpdateModel
{
	boolean propertyChanged(String propertyName, int oldValue, int newValue);
	
	boolean propertyChanged(String propertyName, float oldValue, float newValue);
	
	boolean propertyChanged(String propertyName, boolean oldValue, boolean newValue);
	
	boolean propertyChanged(String propertyName, String oldValue, String newValue);
	
	boolean propertyChanged(String propertyName, Object oldValue, Object newValue);
}
