package com.liuly.zabbixauth.annotation;

import java.lang.annotation.*;

/**
 * @version 1.0
 * @Description: 1367636569@qq.com :该注解作用作为用户自动登录zabbix
 * @Auther: Liuly
 * @Date: 2019/8/20
 * @since JDK 1.8
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZabixAuthToken {
}
