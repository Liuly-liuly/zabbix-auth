package com.liuly.zabbixauth.service;

import com.liuly.zabbixauth.dto.*;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Description: 1367636569@qq.com
 * @Auther: Liuly
 * @Date: 2019/8/26
 * @since JDK 1.8
 */
public interface ZabbixFuctionService {


    /**
     * 查询全部主机
     * @return  map 集合主机hostId ---> host
     */
    Map<String, String> getHostAll();


    /**
     * 查询 模板关联的所有机器
     * @param params
    默认参数:
    {
    "output": ["hostid","host"]
    }
     * @return
     */
    List<ZabbixHost> getHosts(Map params);


    /**
     * 查询主机关联的所有的监控项
     * @param params {@link <a href = "https://www.zabbix.com/documentation/3.4/manual/api/reference/item/get">item.get</a>}
    选填参数：
    {
    "hostids":["10205"],
    "itemids":["27692","27707"],
    "triggerids":["12345"]
    }
    默认参数：
    {
    "output":["itemid","name","key_","delay","history","status","flags","units","lastvalue","value_type"],
    "selectApplications": ["applicationid","name","hostid","templateids"],
    "selectTriggers":["triggerid","templateid","description","expression","comments","status","priority","value"],
    "selectHosts":["hostid","host"]
    }
     * @return Item列表 或 空Array
     */
    List<ZabbixItem> getItems(Map params);


    /**
     * 查询监控项数据，和监控项关联的host，无用的字段不查
     * @param zabbixItem
    默认参数
    {
    "output":["itemid","name","lastclock","lastvalue","lastvalue","prevvalue","status","units"],
    "selectApplications": ["applicationid","name","hostid","templateids"],
    "selectHosts":["hostid","host"]
    }
     * @return Item列表key-zabbixItemId 或 空Map
     */
    List<ZabbixItem> getItemData(ZabbixItem zabbixItem ,int page , int pageSize);


    /**
     * 查询 监控数据，以主机ID 和 applicationName 作为过滤条件
     * 会过滤掉 已被禁用的监控项数据
     * @param zabbixLastItem 查询条件
     * @param appNames 为空时，查询全部数据
     * @return application-item
     */
    List<ZabbixLastItem> getLatestItemData(ZabbixLastItem zabbixLastItem, List<String> appNames);


    Boolean updateHostByGroupId(Integer id , boolean flag)throws Exception;

}
