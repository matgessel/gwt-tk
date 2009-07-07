package asquare.gwt.sb.client.ui;

public class BitmapBuilder
{
    /**
     * @param spec a 2D array describing a bitmap. true = black, false = transparent. 
     * @return a html string
     */
    public static String makeBitmap(boolean[][] spec, String onColor, String onStyleName)
    {
        StringBuilder result = new StringBuilder();
        for (int row = 0; row < spec.length; row++)
        {
            // optimized to combine adjacent columns w/ same value
            boolean[] cols = spec[row];
            int col = 0;
            while(col < cols.length)
            {
                int count = 1;
                boolean on = cols[col];
                while(col + count + 1 < cols.length && cols[col + count] == on)
                {
                    count++;
                }
                if (on)
                {
                    result.append("<div");
                    if (onStyleName != null)
                    {
                        result.append(" class='");
                        result.append(onStyleName);
                        result.append("'");
                    }
                    result.append(" style='position:absolute;left:").append(row).append(";top:").append(col).append(";width:").append(count).append(";height:1;");
                    if (onColor != null)
                    {
                        result.append("background-color:");
                        result.append(onColor);
                        result.append(';');
                    }
                    result.append("'></div>");
                }
                col += count;
            }
        }
        return result.toString();
    }
}
