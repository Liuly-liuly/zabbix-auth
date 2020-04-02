package com.liuly.zabbixauth.service;

import com.liuly.zabbixauth.dto.ZabbixRequestParam;
import com.liuly.zabbixauth.dto.ZabbixResponse;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @version 1.0
 * @Description: 1367636569@qq.com
 * @Auther: Liuly
 * @Date: 2019/8/26
 * @since JDK 1.8
 */
public interface ZabbixBaseService {

    AtomicLong requestId = new AtomicLong(1);

    /**
     * @return 测试自动获取token
     */
    String loginInAndAcToken(String p  ,int page ,
                             ZabbixRequestParam param , Boolean fla);
    /**
     * @return 获取token
     */
    String acToken();

    /**
     * @return 判断是否登陆zabbix后台 true/false
     */
    Boolean login();

    /**
     * 判断zabbix 的token是否可用
     * @return true/false
     */
    Boolean onLine(String token);

    /**
     * 封装了 RestTemplate 请求
     * @param requestParam 包含zabbixMethod  请求方法 zabbixParams  请求参数
     * @return result Object
     */
    ZabbixResponse requestZabbixForObject(ZabbixRequestParam requestParam);

    /**
     * 效验 response 返回结果包含正确结果
     * @param response
     * @return true/false
     */
    Boolean responseResultAvailable(ZabbixResponse response);

    /**
     * 效验 response 返回结果包含错误结果
     * @param response
     * @return true/false
     */
    Boolean responseErrorAvailable(ZabbixResponse response);

    /**
     * 获取requestId
     * @param
     * @return AtomicLong
     */
    AtomicLong getRequestId();

}
