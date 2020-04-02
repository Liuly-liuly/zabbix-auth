package com.liuly.zabbixauth.config;

import com.liuly.zabbixauth.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.core.Ordered;

/**
 * @version 1.0
 * @Description: 1367636569@qq.com
 * @Auther: Liuly
 * @Date: 2019/8/20
 * @since JDK 1.8
 */
@Configuration
@ComponentScan(value = "com.yanqiancloud.zabbixauth")
@EnableAspectJAutoProxy
public class ZabbixConfig {

//    @Autowired
//    private SessionUtils sessionUtils;

//    @Bean
//    public FilterRegistrationBean filterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new EraseFilter());
//        registration.addUrlPatterns("/*");
//        registration.setName("eraseFilter");
//        registration.setOrder(Ordered.LOWEST_PRECEDENCE);
//        return registration;
//    }

//    @Bean
//    @Primary
//    public CustomLogoutSuccessHandler logoutSuccessHandler() {
//        return new CustomLogoutSuccessHandler(sessionUtils);
//    }

}
