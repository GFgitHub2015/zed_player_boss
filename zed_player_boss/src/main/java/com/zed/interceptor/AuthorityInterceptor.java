package com.zed.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.zed.controller.sitead.AdStationAction;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zed.controller.system.BaseAction;
import com.zed.controller.system.CaptchaAction;
import com.zed.controller.system.FrameAction;
import com.zed.controller.system.LoginAction;
import com.zed.controller.system.LoginProcessAction;
import com.zed.controller.system.LogoffAction;
import com.zed.system.GConstant;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;

public class AuthorityInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	
	

	@Override
	public void init() {
	}
	
	@Override
	public void destroy() {
	}
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();      
	
		
		if(action instanceof LoginAction) {
			return invocation.invoke();
		}
		
		else if(action instanceof LogoffAction) {
			return invocation.invoke();
		}
		
		else if(action instanceof LoginProcessAction) {
			return invocation.invoke();
		}
		
		else if(action instanceof FrameAction) {
			return invocation.invoke();
		}
		
		else if(action instanceof CaptchaAction) {
			return invocation.invoke();
		}

		else if(action instanceof AdStationAction) {
			return invocation.invoke();
		}
		
		else if(action instanceof BaseAction) {
			//check request info
			HttpServletRequest request = ServletActionContext.getRequest();
			
			BaseAction baseAction = (BaseAction)action;
			baseAction.setErrorDispatch(GConstantAlert.GS_LTE0004, GConstantRedirect.GS_LOGOFF_ACTION);
			
			if((request.getHeader("Referer") == null && request.getRequestURI().indexOf("/frame.action") == -1)){
				baseAction.setErrorDispatch(GConstantAlert.GS_LTE0004, GConstantRedirect.GS_LOGOFF_ACTION);
		        return Action.ERROR;
			}else{
				//System.out.println("refre="+request.getHeader("Referer"));
				request.getHeader("frame="+request.getRequestURI().indexOf("/frame.action"));
				HttpSession session = request.getSession(false);
				if(session == null || session.getAttribute(GConstant.GS_SESSION_MENU_LIST) == null){
					if(request.getHeader("Referer").contains("left.action") || request.getHeader("Referer").contains("top.action")){
						return baseAction.autoLogOffBySessionTimeOut();
					}else{
						baseAction.setErrorDispatch(GConstantAlert.GS_LTE0005, GConstantRedirect.GS_LOGOFF_ACTION);
						return Action.ERROR;
					}			
					
				}else{
					return invocation.invoke();
				}
			}			
		}
		else{
			return invocation.invoke();
		}
		
	}
	
}
