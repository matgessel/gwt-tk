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
