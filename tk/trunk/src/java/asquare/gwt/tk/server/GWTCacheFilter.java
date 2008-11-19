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
 * This is a filter which enforces proper caching of the generated GWT script
 * files. It requires that you serve your GWT application via a Java servlet
 * container.
 * <p>
 * To use, add the jar to <code>WEB-INF/lib</code> and add the
 * following to your deployment descriptor (web.xml):
 * 
 * <pre>
 * &lt;filter&gt;
 *   &lt;filter-name&gt;GWTCacheFilter&lt;/filter-name&gt;
 *   &lt;filter-class&gt;asquare.gwt.tk.server.GWTCacheFilter&lt;/filter-class&gt;
 *   &lt;description&gt;Enforces proper caching of GWT files&lt;/description&gt;
 * &lt;/filter&gt;
 * 
 * &lt;filter-mapping&gt;
 *   &lt;filter-name&gt;GWTCacheFilter&lt;/filter-name&gt;
 *   &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 * &lt;/filter-mapping&gt;</pre>
 * 
 * By default, files ending in <code>.cache.*</code> are cached and files
 * ending in <code>.nocache.*</code> are not cached. You can override the
 * defaults by specifying file name patterns in filter init-params. The pattern
 * is parsed as a JDK regular expression. The defaults are below: 
 * 
 * <pre>
 * &lt;init-param&gt;
 *   &lt;param-name&gt;forceDontCache&lt;/param-name&gt;
 *   &lt;param-value&gt;.+\.nocache\..+&lt;/param-value&gt;
 * &lt;/init-param&gt;
 * &lt;init-param&gt;
 *   &lt;param-name&gt;forceCache&lt;/param-name&gt;
 *   &lt;param-value&gt;.+\.cache\..+&lt;/param-value&gt;
 * &lt;/init-param&gt;</pre>
 * 
 * <p>
 * Usage notes
 * <ul>
 * <li>You can verify that the filter is being applied with Firefox's Web
 * Developer Extension. Click Tools > Web Developer > Information > View
 * Response Headers.
 * <li>If you are running an Apache httpd/Jk/Tomcat server configuration you
 * need to ensure that Tomcat is serving HTML files, otherwise the filter will
 * not be applied.
 * <li>One reason that this filter exists is that you cannot use <code>*.nocache.html</code> or
 * <code>*.cache.html</code> for url patterns. According to the 2.3 servlet
 * spec, an extension is defined as the characters after the <strong>last</strong>
 * period.
 * <li>The header is modified <em>before</em> passing control down the filter chain. 
 * </ul>
 * 
 * @see <a
 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9">Cache-control
 *      directive</a>
 * @see <a
 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.21">Expires
 *      directive</a>
 * @see <a
 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.32">Pragma
 *      directive</a>
 */
public class GWTCacheFilter implements Filter
{
	/**
	 * The name of the filter init-param which specifies files not to cache. 
	 * The name is <code>{@value}</code>. 
	 */
	public static final String INITPARAM_FORCEDONTCACHE = "forceDontCache";
	
	/**
	 * The name of the filter init-param which specifies files to cache. 
	 * The name is <code>{@value}</code>. 
	 */
	public static final String INITPARAM_FORCECACHE = "forceCache";
	
	/**
	 * The default value of the <code>forceCache</code> init-param. 
	 * The value is <code>{@value}</code>. 
	 */
	public static final String DEFAULT_FORCEDONTCACHE = ".+\\.nocache\\..+";
	
	/**
	 * The default value of the <code>forceDontCache</code> init-param. 
	 * The value is <code>{@value}</code>. 
	 */
	public static final String DEFAULT_FORCECACHE = ".+\\.cache\\..+";
	
	private Pattern forceDontCachePattern;
	private Pattern forceCachePattern;
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
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
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		if (request instanceof HttpServletRequest)
		{
			HttpServletRequest hRequest = (HttpServletRequest) request;
			if (forceDontCachePattern.matcher(hRequest.getRequestURL()).matches())
			{
				HttpServletResponse hResponse = (HttpServletResponse) response;
				hResponse.setHeader("Cache-Control", "no-cache no-store must-revalidate");
				hResponse.setHeader("Pragma", "no-cache"); // HTTP/1.0
				hResponse.setDateHeader("Expires", 0l);
			}
			else if (forceCachePattern.matcher(hRequest.getRequestURL()).matches())
			{
				HttpServletResponse hresponse = (HttpServletResponse) response;
				
				// the w3c spec requires a maximum age of 1 year
				// Firefox 3+ needs 'public' to cache this resource when received via SSL
				hresponse.setHeader("Cache-Control", "public max-age=31536000");
				
				// necessary to overwrite "Pragma: no-cache" header
				hresponse.setHeader("Pragma", "temp");
				hresponse.setHeader("Pragma", "");
				hresponse.setDateHeader("Expires", System.currentTimeMillis() + 31536000000l);
			}
		}
		chain.doFilter(request, response);
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy()
	{
	}
}
