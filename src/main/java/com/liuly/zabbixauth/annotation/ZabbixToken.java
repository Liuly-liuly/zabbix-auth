package com.liuly.zabbixauth.annotation;

import com.liuly.zabbixauth.service.imp.ZabbixBaseServiceImpl;

import java.lang.annotation.*;

/**
 * @version 1.0
 * @Description: 1367636569@qq.com:该注解作用是：
 * 已登录的zabbix token赋值到有该注解的变量，该变量为String类型
 *@see ZabbixBaseServiceImpl#loginInAndAcToken(java.lang.String, int, com.liuly.zabbixauth.dto.ZabbixRequestParam, java.lang.Boolean)
 * @Auther: Liuly
 * @Date: 2019/8/20
 * @since JDK 1.8
 */
@Target({ElementType.FIELD ,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZabbixToken {
    /**
     * 形参为NULL ，加上该注解自动初始化
     */
    boolean notNull() default true;
}
