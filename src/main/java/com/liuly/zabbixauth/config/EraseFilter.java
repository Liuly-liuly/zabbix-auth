package com.liuly.zabbixauth.config;

import com.liuly.zabbixauth.annotation.DataStaticWiring;
import com.liuly.zabbixauth.util.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.ObjectUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EraseFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(DataStaticWiring.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if(request.getRequestURI().equals("/logout")){
            HttpSession session =request.getSession(false);
            if(!ObjectUtils.isEmpty(session)){
                String id = session.getId();
                logger.info(id,"{}","logout");
                eraseRedisTemplate().delete(SessionUtils.SESSION_+id);
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }


    public RedisTemplate eraseRedisTemplate(){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        GenericJackson2JsonRedisSerializer redisSerializer= new GenericJackson2JsonRedisSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}