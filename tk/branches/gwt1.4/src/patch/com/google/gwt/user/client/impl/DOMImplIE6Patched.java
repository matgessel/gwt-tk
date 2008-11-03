package com.google.gwt.user.client.impl;

import com.google.gwt.user.client.Element;

public class DOMImplIE6Patched extends DOMImplIE6
{
	public String getImgSrc(Element img)
	{
		return ImageSrcIE6Patched.getImgSrc(img);
	}
	
	public void setImgSrc(Element img, String src)
	{
		ImageSrcIE6Patched.setImgSrc(img, src);
	}
}
