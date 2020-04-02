package com.liuly.zabbixauth.annotation;


import com.liuly.zabbixauth.util.ReflectUtils;
import com.liuly.zabbixauth.util.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;

public class DataStaticWiring {

    private static Logger logger = LoggerFactory.getLogger(DataStaticWiring.class);

    private static String session_id = "1t6j2sf5w6u5y53k7r25th6h901hu";

    private static Long time_out = 3600L;

    public static void wiring(Object object, Field field, HttpSession session, SessionUtils sessionUtils) {
        Object fieldObj = ReflectUtils.getFieldValue(object, field.getName());
        if (fieldObj != null) {
            return;
        }
        Object token = acObjectValue(session ,sessionUtils);
        ReflectUtils.setFieldValue(object, field.getName(), token);
    }

    public static Object acObjectValue(HttpSession session ,SessionUtils sessionUtils){
        Object token = null;
        if (session != null) {
            String id = session.getId();
            int interval = session.getMaxInactiveInterval();
            token = sessionUtils.acquireToken(id, Long.valueOf(interval));
        } else {
            token = sessionUtils.acquireToken(session_id, time_out);
        }
        return token;
    }

}
