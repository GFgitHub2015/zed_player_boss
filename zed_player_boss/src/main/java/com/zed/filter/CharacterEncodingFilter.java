package com.zed.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zed.listener.Log;

public class CharacterEncodingFilter implements Filter {

	protected String encoding = null;
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain fc) throws IOException, ServletException {
		Log.getLogger(CharacterEncodingFilter.class).info("获取request的encoding。。。");
		((HttpServletRequest) request).setCharacterEncoding(this.encoding);
		Log.getLogger(CharacterEncodingFilter.class).info("设置response的contentType。。。");
		((HttpServletResponse) response).setContentType("text/html; charset=" + this.encoding);
		fc.doFilter(request, response);
	}
	
	public void init(FilterConfig filterConfig) throws ServletException {
		Log.getLogger(CharacterEncodingFilter.class).info("初始化encoding。。。");
		this.encoding = filterConfig.getInitParameter("encoding");	
	}
	
	public void destroy() {
		
	}
}
