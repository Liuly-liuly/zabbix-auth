package com.liuly.zabbixauth.annotation;

import com.liuly.zabbixauth.aop.Constant;
import com.liuly.zabbixauth.dto.ZabbixServer;
import com.liuly.zabbixauth.service.ZabbixBaseService;
import com.liuly.zabbixauth.service.imp.ZabbixBaseServiceImpl;
import com.liuly.zabbixauth.util.CommonUtils;
import com.liuly.zabbixauth.util.RedisUtils;
import com.liuly.zabbixauth.util.SessionUtils;
import com.liuly.zabbixauth.util.WebUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * @version 1.0
 * @Description: 1367636569@qq.com
 * @Auther: Liuly
 * @Date: 2019/8/20
 * @since JDK 1.8
 */
@Component
public class DataBeanProcessor implements BeanPostProcessor, PriorityOrdered {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        Class<?> cls = bean.getClass();
        HttpSession session = WebUtils.getSession();
        SessionUtils sessionUtils = sessionUtil();
        for (Field field : cls.getDeclaredFields()) {
            if (field.isAnnotationPresent(ZabbixToken.class)) {
                DataStaticWiring.wiring(bean, field ,session ,sessionUtils);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }


    private RedisTemplate getRedisTemplate(){
        GenericJackson2JsonRedisSerializer redisSerializer= new GenericJackson2JsonRedisSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    private ZabbixBaseService zabbixService(){
        Properties properties = CommonUtils.getProperties(Constant.FILE_PATH);
        ZabbixServer zabbixServer = new ZabbixServer();
        zabbixServer.setIp(properties.getProperty(Constant.ZAABIX_IP));
        zabbixServer.setUsername(properties.getProperty(Constant.ZABBIX_USER_NAME));
        zabbixServer.setPassword(properties.getProperty(Constant.ZABBIX_PASSWORD));
        ZabbixBaseServiceImpl zabbixService = new ZabbixBaseServiceImpl();
        zabbixService.setZabbixServer(zabbixServer);
        return zabbixService;
    }

    private RedisUtils redisUtils(){
        RedisUtils redisUtils = new RedisUtils();
        RedisTemplate redisTemplate = getRedisTemplate();
        redisUtils.setRedisTemplate(redisTemplate);
        return redisUtils;
    }

    private SessionUtils sessionUtil(){
        SessionUtils sessionUtils = new SessionUtils();
        sessionUtils.setRedisUtils(redisUtils());
        sessionUtils.setZabbixBaseService(zabbixService());
        return sessionUtils;
    }

}