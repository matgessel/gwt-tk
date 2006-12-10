/*
 * Copyright 2006 Mat Gessel <mat.gessel@gmail.com>
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
package asquare.gwt.tk.server;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9">Cache-control directive</a>
 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.21">Expires directive</a>
 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.32">Pragma directive</a>
 * 
 */
public class GWTCacheFilter implements Filter
{
	public static final String INITPARAM_FORCEDONTCACHE = "forceDontCache";
	public static final String INITPARAM_FORCECACHE = "forceCache";
	public static final String DEFAULT_FORCEDONTCACHE = ".+\\.nocache.html";
	public static final String DEFAULT_FORCECACHE = ".+\\.cache.html";
	
	private Pattern forceDontCachePattern;
	private Pattern forceCachePattern;
	
	public void init(FilterConfig filterConfig) throws ServletException
	{
		String forceDontCachePatternString = filterConfig.getInitParameter(INITPARAM_FORCEDONTCACHE);
		String forceCachePatternString = filterConfig.getInitParameter(INITPARAM_FORCECACHE);
		if (forceDontCachePatternString == null)
		{
			forceDontCachePatternString = DEFAULT_FORCEDONTCACHE;
		}
		if (forceCachePatternString == null)
		{
			forceCachePatternString = DEFAULT_FORCECACHE;
		}
		forceDontCachePattern = Pattern.compile(forceDontCachePatternString);
		forceCachePattern = Pattern.compile(forceCachePatternString);
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		if (request instanceof HttpServletRequest)
		{
			HttpServletRequest hRequest = (HttpServletRequest) request;
			if (forceDontCachePattern.matcher(hRequest.getRequestURL()).matches())
			{
				HttpServletResponse hResponse = (HttpServletResponse) response;
				hResponse.addHeader("Cache-Control", "no-cache no-store must-revalidate");
				hResponse.addHeader("Pragma", "no-cache"); // HTTP/1.0
				hResponse.setDateHeader("Expires", 0l);
			}
			else if (forceCachePattern.matcher(hRequest.getRequestURL()).matches())
			{
				HttpServletResponse hresponse = (HttpServletResponse) response;
				
				// the w3c spec requires a maximum age of 1 year
				hresponse.addHeader("Cache-Control", "max-age=31536000");
				hresponse.setDateHeader("Expires", System.currentTimeMillis() + 31536000000l);
			}
		}
		chain.doFilter(request, response);
	}
	
	public void destroy()
	{
	}
}
