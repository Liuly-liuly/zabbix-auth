package com.liuly.zabbixauth.config;

import com.liuly.zabbixauth.util.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    private static Logger logger = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);

    private SessionUtils sessionUtils;

    public CustomLogoutSuccessHandler(SessionUtils sessionUtils) {
        this.sessionUtils = sessionUtils;
    }

    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session =request.getSession(false);
        if(!ObjectUtils.isEmpty(session)){
            String id = session.getId();
            logger.info(id,"{}","logout");
            this.sessionUtils.del(id);
        }
        super.handle(request, response, authentication);
    }

}