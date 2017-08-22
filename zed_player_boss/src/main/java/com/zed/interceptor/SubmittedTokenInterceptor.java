package com.zed.interceptor;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.TokenInterceptor;
import org.apache.struts2.util.TokenHelper;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptorUtil;
import com.zed.domain.system.Admin;
import com.zed.system.GConstant;
import com.zed.system.GConstantAlert;

public class SubmittedTokenInterceptor extends TokenInterceptor {

	private static final long serialVersionUID = -6193210026038934721L;

	/*
	 * (non-Javadoc)
	 * @see org.apache.struts2.interceptor.TokenInterceptor#doIntercept(com.opensymphony.xwork2.ActionInvocation)
	 * 
	 * We need to override this method for special handling Error message
	 */
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		
        //see WW-2902: we need to use the real HttpSession here, as opposed to the map
        //that wraps the session, because a new wrap is created on every request
        HttpSession session = ServletActionContext.getRequest().getSession(false);

    	// Just handle the token when user is still in session,
    	// otherwise we go on invoke the action
        if(session==null){
        	//System.err.println("SubmittedTokenInterceptor: session is null");
        	return invocation.invoke();
        }else{
	        synchronized (session) {
	        	if(session.getAttribute(GConstant.GS_SESSION_ADMIN_KEY) instanceof Admin) {
	        		//System.err.println("SubmittedTokenInterceptor: user is ok");
		            if (applyInterceptor(invocation) && !TokenHelper.validToken()) {
		            	ServletActionContext.getRequest().setAttribute("errorCode", GConstantAlert.GS_TOKEN_ERROR);
		                return handleInvalidToken(invocation);
		            }
		            return handleValidToken(invocation);
	        	}
	        	//System.err.println("SubmittedTokenInterceptor: no user");
	        	return invocation.invoke();
	        }
        }
	}
	
	protected boolean applyInterceptor(ActionInvocation invocation) {  
        String method = invocation.getProxy().getMethod();  
        // ValidationInterceptor  
        boolean applyMethod = MethodFilterInterceptorUtil.applyMethod(includeMethods, includeMethods, method);  
       return applyMethod;
    }  
}
