/**
 * <描述>
 * @author admin
 * @date 2014-1-3 上午10:15:44
 * StrutsPrepareAndExecuteFilter
 */
package com.szkingdom.frame.interceptor;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

/**
 * <描述>
 * 
 * @author admin
 * @date 2014-1-3 上午10:15:44
 */
public class StrutsPrepareAndExecuteFilter2 extends StrutsPrepareAndExecuteFilter {
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		Enumeration<?> en = request.getParameterNames();
		Object obj = null;
		boolean isrun = true;
		while (en.hasMoreElements()) {
			obj = en.nextElement();
			if (obj != null) {
				if (obj.toString().toLowerCase().indexOf("action:") != -1 || obj.toString().toLowerCase().indexOf("redirect:") != -1) {
					isrun = false;
					return;
				}
			}
		}
		try {
			if(isrun){
				prepare.setEncodingAndLocale(request, response);
				prepare.createActionContext(request, response);
				prepare.assignDispatcherToThread();
				if (excludedPatterns != null && prepare.isUrlExcluded(request, excludedPatterns)) {
					chain.doFilter(request, response);
				} else {
					request = prepare.wrapRequest(request);
					ActionMapping mapping = prepare.findActionMapping(request, response, true);
					if (mapping == null) {
						boolean handled = execute.executeStaticResourceRequest(request, response);
						if (!handled) {
							chain.doFilter(request, response);
						}
					} else {
						execute.executeAction(request, response, mapping);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} finally {
			prepare.cleanupRequest(request);
		}
	}

}
