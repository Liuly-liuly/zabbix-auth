package com.liuly.zabbixauth.util;


import com.liuly.zabbixauth.service.ZabbixBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionUtils {

    public static final String SESSION_ = "zabbix:session:";

    private static final String SPRING_SESSION_="spring:session:sessions:expires:";

    private static Logger logger = LoggerFactory.getLogger(SessionUtils.class);

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ZabbixBaseService zabbixBaseService;

    public String acquireToken(String id ,Long timeOut){

        String token = (String)redisUtils.get(SESSION_ + id);
            if(token == null){
                token = zabbixBaseService.acToken();
                this.putToken(id ,token ,timeOut);
            }else {
                Boolean aBoolean = zabbixBaseService.onLine(token);
                if(!aBoolean){
                    token = zabbixBaseService.acToken();
                    this.putToken(id ,token ,timeOut);
                }else {
                    this.refresh(id ,timeOut);
                }
        }
        return token;
    }

    public void putToken(String id , String token ){
        redisUtils.set(SESSION_+id , token);
    }

    public void putToken(String id , String token , Long timeOut){
        redisUtils.set(SESSION_+id , token , timeOut);
    }

    public void refresh(String id ,long time){
        long expire = redisUtils.getExpire(SESSION_ + id);
        if(expire != time){
            redisUtils.expire(SESSION_+id ,time);
        }
    }

    public void  del(String id){
        redisUtils.del(SESSION_+id);
    }

    public RedisUtils getRedisUtils() {
        return redisUtils;
    }

    public void setRedisUtils(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    public ZabbixBaseService getZabbixBaseService() {
        return zabbixBaseService;
    }

    public void setZabbixBaseService(ZabbixBaseService zabbixBaseService) {
        this.zabbixBaseService = zabbixBaseService;
    }
}
