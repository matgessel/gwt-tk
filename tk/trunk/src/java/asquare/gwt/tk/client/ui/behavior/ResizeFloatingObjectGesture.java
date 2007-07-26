package asquare.gwt.tk.client.ui.behavior;

import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class ResizeFloatingObjectGesture
{
	private ResizeFloatingObjectGesture()
	{
	}
	
	public static class NW extends AdjustObjectGesture
	{
		public NW(UIObject floater, UIObject content)
		{
			super(floater, content);
		}
		
		public void step(int x, int y)
		{
			setWidth(-getDeltaX(x));
			setLeft(getDeltaX(x));
			setHeight(-getDeltaY(y));
			setTop(getDeltaY(y));
		}
	}
	
	public static class N extends AdjustObjectGesture
	{
		public N(Widget floater, UIObject content)
		{
			super(floater, content);
		}
		
		public void step(int x, int y)
		{
			setHeight(-getDeltaY(y));
			setTop(getDeltaY(y));
		}
	}
	
	public static class NE extends AdjustObjectGesture
	{
		public NE(Widget floater, UIObject content)
		{
			super(floater, content);
		}
		
		public void step(int x, int y)
		{
			setHeight(-getDeltaY(y));
			setTop(getDeltaY(y));
			setWidth(getDeltaX(x));
		}
	}
	
	public static class W extends AdjustObjectGesture
	{
		public W(Widget floater, UIObject content)
		{
			super(floater, content);
		}
		
		public void step(int x, int y)
		{
			setWidth(-getDeltaX(x));
			setLeft(getDeltaX(x));
		}
	}
	
	public static class E extends AdjustObjectGesture
	{
		public E(Widget floater, UIObject content)
		{
			super(floater, content);
		}
		
		public void step(int x, int y)
		{
			setWidth(getDeltaX(x));
		}
	}
	
	public static class SW extends AdjustObjectGesture
	{
		public SW(Widget floater, UIObject content)
		{
			super(floater, content);
		}
		
		public void step(int x, int y)
		{
			setLeft(getDeltaX(x));
			setWidth(-getDeltaX(x));
			setHeight(getDeltaY(y));
		}
	}
	
	public static class S extends AdjustObjectGesture
	{
		public S(Widget floater, UIObject content)
		{
			super(floater, content);
		}
		
		public void step(int x, int y)
		{
			setHeight(getDeltaY(y));
		}
	}
	
	public static class SE extends AdjustObjectGesture
	{
		public SE(Widget floater, UIObject content)
		{
			super(floater, content);
		}
		
		public void step(int x, int y)
		{
			setWidth(getDeltaX(x));
			setHeight(getDeltaY(y));
		}
	}
}
