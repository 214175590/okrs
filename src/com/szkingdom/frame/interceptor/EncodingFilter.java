package com.szkingdom.frame.interceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import com.szkingdom.bean.pojo.system.Users;
import com.szkingdom.frame.common.GlobalConstants;
import com.szkingdom.frame.common.RootContext;

/**
 * <pre>
 * 过滤器：字符编码过滤、未登陆时的请求过滤
 * </pre>
 * 
 * @author yisin
 * @date 2012-11-29 下午03:40:31
 * @最后修改人：admin
 * @最后修改时间：2012-11-29 下午03:41:32
 * @see com.szkingdom.frame.interceptor.EncodingFilter
 */
public class EncodingFilter implements Filter {
	/**
	 * 初始化时
	 */
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("EncodingFilter init..");
	}

	/**
	 * 销毁时
	 */
	public void destroy() {
		System.out.println("EncodingFilter destroy..");
	}

	/**
	 * 过滤
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain arg2) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		/*
		 * String uri = req.getRequestURI(); String url =
		 * req.getRequestURL().toString(); System.out.println("uri=======" +
		 * uri); System.out.println("url=======" + url);
		 */

		/************* 为了解决URL后面老跟着jsessionid问题 *****************/
		// clear session if session id in URL
		if (req.isRequestedSessionIdFromURL()) {
			HttpSession session = req.getSession();
			if (session != null)
				session.invalidate();
		}

		// wrap response to remove URL encoding
		HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(res) {
			public String encodeRedirectUrl(String url) {
				System.out.println("1.url=" + url);
				return url;
			}

			public String encodeRedirectURL(String url) {
				System.out.println("2.url=" + url);
				return url;
			}

			public String encodeUrl(String url) {
				System.out.println("3.url=" + url);
				return url;
			}

			public String encodeURL(String url) {
				System.out.println("4.url=" + url);
				return url;
			}
		};
		/******************************/
		// 首先从Session中获取用户信息
		Users Users = (Users) req.getSession().getAttribute(GlobalConstants.SESSION_KEY_USER);
		String cotName = RootContext.getRootName();
		// 判断用户是否未登陆，或登陆超时
		if (null == Users) { // 未登陆，或登陆超时
			System.out.println("user login timeout or no login..");
			// 判断是否未登陆界面的请求，是则放行
			String uri = req.getRequestURI();
			if (uri.indexOf("/login.jsp") == -1) {
				if (uri.indexOf(".jsp") != -1 || uri.indexOf(".html") != -1 || uri.indexOf(".htm") != -1) {
					res.sendRedirect(cotName + "/frame/login.action");
					return;
				}
			}
		}

		response.setCharacterEncoding("UTF-8");
		arg2.doFilter(new MyFilter(req), response);
	}

	static class MyFilter extends HttpServletRequestWrapper {
		public MyFilter(HttpServletRequest request) {
			super(request);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
		 */
		@Override
		public String getParameter(String name) {
			String str = super.getParameter(name);
			str = this.encoding(str);
			return str;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.servlet.ServletRequestWrapper#getParameterValues(java.lang.
		 * String)
		 */
		@Override
		public String[] getParameterValues(String name) {
			String[] str = super.getParameterValues(name);
			for (int i = 0; i < str.length; i++) {
				String ss = str[i];
				str[i] = this.encoding(ss);
			}
			return str;
		}

		private String encoding(String name) {
			if (null != name) {
				try {
					byte[] by = name.getBytes("ISO-8859-1");
					name = new String(by, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			return name;
		}

	}

}
