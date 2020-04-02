package com.liuly.zabbixauth.service.imp;

import com.liuly.zabbixauth.annotation.ZabbixToken;
import com.liuly.zabbixauth.annotation.ZabixAuthToken;
import com.liuly.zabbixauth.dto.ZabbixRequestParam;
import com.liuly.zabbixauth.dto.ZabbixResponse;
import com.liuly.zabbixauth.dto.ZabbixServer;
import com.liuly.zabbixauth.service.ZabbixBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
/**
 * @version 1.0
 * @Description: 1367636569@qq.com
 * @Auther: Liuly
 * @Date: 2019/8/26
 * @since JDK 1.8
 */
@Service
public class ZabbixBaseServiceImpl implements ZabbixBaseService {

    private final static Logger logger = LoggerFactory.getLogger(ZabbixBaseServiceImpl.class);

    public static String zabbixUrl = "";

    public static Map<String, String> loginParam = new HashMap<>();

    @Autowired
    private ZabbixServer zabbixServer;

    @Override
    @ZabixAuthToken
    public String loginInAndAcToken(@ZabbixToken String p, @ZabbixToken int page,
                                    ZabbixRequestParam param, Boolean flag) {
        logger.info("ZabbixToken--->" + page);
        return p;
    }

    @Override
    public String acToken() {
        zabbixUrl = String.format("http://%s/zabbix/api_jsonrpc.php", zabbixServer.getIp());
        loginParam.put("user", zabbixServer.getUsername());
        loginParam.put("password", zabbixServer.getPassword());
        ZabbixRequestParam requestParam = new ZabbixRequestParam("user.login", loginParam, requestId);

        RestTemplate restTemplate = new RestTemplate();
        ZabbixResponse response = null;
        do {
            try {
                response = restTemplate.postForObject(zabbixUrl, requestParam, ZabbixResponse.class);
            } catch (Exception e) {
                logger.error("http请求异常" + e.getMessage());
                break;
            }
            if (responseResultAvailable(response)) {
                String authenticationToken = (String) response.getResult();
                if (!StringUtils.isEmpty(authenticationToken)) {
                    logger.info("zabbix后台登陆成功! url= {}", zabbixUrl);
                    return authenticationToken;
                } else {
                    logger.error("返回token为空串!");
                    break;
                }
            } else {
                if (responseErrorAvailable(response)) {
                    logger.error(response.getError().toString());
                } else {
                    logger.error("返回结果为空!");
                }
            }
        } while (false);

        return "";
    }

    @Override
    public Boolean login() {
        String back = acToken();
        if (StringUtils.isEmpty(back)) {
            logger.error("zabbix login failed! url= {} cause:{}", zabbixUrl);
            return false;
        }
        return true;
    }

    @Override
    public Boolean onLine(String token) {

        if (ObjectUtils.isEmpty(token)) {
            return false;
        } else {
            //测试是否过期
            Map<String, Object> param = new HashMap<>();
            List<String> output = new ArrayList<>();
            zabbixUrl = String.format("http://%s/zabbix/api_jsonrpc.php", zabbixServer.getIp());

            output.add("groupid");
            output.add("name");
            param.put("output", output);

            ZabbixRequestParam requestParam = new ZabbixRequestParam("hostgroup.get",
                    param,
                    requestId,
                    token);

            RestTemplate restTemplate = new RestTemplate();

            ZabbixResponse response = restTemplate.postForObject(zabbixUrl,
                    requestParam, ZabbixResponse.class);

            if (!responseResultAvailable(response)) {
                logger.info("zabbix token not available");
                return false;
            } else {
                return true;
            }

        }
    }

    @Override
    @ZabixAuthToken
    public ZabbixResponse requestZabbixForObject(ZabbixRequestParam requestParam) {

        RestTemplate restTemplate = new RestTemplate();

        ZabbixResponse response = null;

        try {

            response = restTemplate.postForObject(zabbixUrl, requestParam, ZabbixResponse.class);

        } catch (Exception e) {

            logger.error("RestClientException cause ", e.getMessage());

        }

        if (responseErrorAvailable(response)) {
            if (response.getError().getData().contains("Not authorised")
                    || response.getError().getData().contains("Session terminated")) {

                logger.warn("session验证失败!error:{}", response.getError().toString());

            }
        }

        return response;

    }

    @Override
    public Boolean responseResultAvailable(ZabbixResponse response) {
        if (response != null && response.getResult() != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean responseErrorAvailable(ZabbixResponse response) {
        if (response != null && response.getError() != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public AtomicLong getRequestId(){
        return requestId;
    }

    public ZabbixServer getZabbixServer() {
        return zabbixServer;
    }

    public void setZabbixServer(ZabbixServer zabbixServer) {
        this.zabbixServer = zabbixServer;
    }


}
