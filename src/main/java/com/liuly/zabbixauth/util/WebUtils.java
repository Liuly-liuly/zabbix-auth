package com.liuly.zabbixauth.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class WebUtils {
	
	public static HttpSession getSession() {
        HttpSession session = null;
        try {
            session = getRequest().getSession(false);
        } catch (Exception e) {}
        return session;
    }
 
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs!=null?attrs.getRequest():null;
    }
 
    public static HttpServletResponse getResponse(){
        ServletRequestAttributes attrs = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes());
        return attrs!=null?attrs.getResponse():null;
    }
 
}