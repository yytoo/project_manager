package com.pm.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class BaseActionSupport extends ActionSupport implements ServletResponseAware, ServletRequestAware, SessionAware{
	private static final long serialVersionUID = 1L;
	
	
	/** Aware */
	protected HttpServletRequest request = null;
	protected HttpServletResponse response = null;
	protected Map<String, Object> session = null;
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		
	}
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		
	}
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
