package com.liuly.zabbixauth.service.imp;

import com.liuly.zabbixauth.dto.*;
import com.liuly.zabbixauth.service.ZabbixBaseService;
import com.liuly.zabbixauth.service.ZabbixFuctionService;
import com.liuly.zabbixauth.units.UnitsFormat;
import com.liuly.zabbixauth.units.UnitsFormatResult;
import com.liuly.zabbixauth.util.DateUtils;
import com.liuly.zabbixauth.dto.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version 1.0
 * @Description: 1367636569@qq.com
 * @Auther: Liuly
 * @Date: 2019/8/26
 * @since JDK 1.8
 */
@Service
public class ZabbixFuctionServiceImpl implements ZabbixFuctionService {

    private final static Logger logger = LoggerFactory.getLogger(ZabbixFuctionServiceImpl.class);

    @Autowired
    private ZabbixBaseService zabbixBaseService;

    @Override
    public Map<String, String> getHostAll() {
        Map<String, String> IdMapIp = new HashMap<>();
        Map params = new HashMap() {{
            put("output", new ArrayList() {{
                add("hostid");
                add("host");
            }});
        }};
        ZabbixRequestParam requestParam = new ZabbixRequestParam("host.get", params, zabbixBaseService.getRequestId());
        ZabbixResponse response = zabbixBaseService.requestZabbixForObject(requestParam);
        if (zabbixBaseService.responseResultAvailable(response)) {
            try {
                List<Map> result = (List) response.getResult();
                if (!ObjectUtils.isEmpty(result)) {
                    for (Map<String, String> host : result) {
                        IdMapIp.put(host.get("hostid"), host.get("host"));
                    }
                }
            } catch (Exception e) {
                logger.error("getHostAll失败!解析result失败!param={},result={}", params, response.getResult().toString());
            }
        } else {
            if (zabbixBaseService.responseErrorAvailable(response)) {
                logger.error("getHostAll失败!error:[{}],param={}", response.getError().toString(), params);
            } else {
                logger.error("getHostAll失败!返回为空!param={}", params);
            }
        }
        return IdMapIp;
    }

    @Override
    public List<ZabbixHost> getHosts(Map params) {
        List<ZabbixHost> servers = new ArrayList<>();
        if (params == null) return servers;
        params.put("output", new ArrayList() {{
            add("hostid");
            add("host");
        }});
        ZabbixRequestParam requestParam = new ZabbixRequestParam("host.get", params, zabbixBaseService.getRequestId());
        ZabbixResponse response = zabbixBaseService.requestZabbixForObject(requestParam);
        if (zabbixBaseService.responseResultAvailable(response)) {
            try {
                List serverList = (List) response.getResult();
                for (Object zabbixServer : serverList) {
                    ZabbixHost server = new ZabbixHost();
                    server.setHostId((String) ((Map) zabbixServer).get("hostid"));
                    server.setHostIp((String) ((Map) zabbixServer).get("host"));
                    servers.add(server);
                }
            } catch (Exception e) {
                logger.error("getHosts解析返回值失败!");
            }
        } else {
            if (zabbixBaseService.responseErrorAvailable(response)) {
                logger.error("getHosts获取模板关联的机器列表失败!error:[{}]", response.getError().toString());
            } else {
                logger.debug("getHosts返回值为空");
            }
        }
        return servers;
    }

    @Override
    public List<ZabbixItem> getItems(Map params) {
        List<ZabbixItem> zabbixItems = new ArrayList<>();
        params.put("output", new ArrayList() {{
            add("itemid");
            add("name");
            add("key_");
            add("delay");
            add("history");
            add("status");
            add("flags");
            add("units");
            add("lastvalue");
            add("value_type");
        }});
        params.put("selectApplications", new ArrayList() {{
            add("applicationid");
            add("name");
            add("hostid");
            add("templateids");
        }});
        params.put("selectTriggers", new ArrayList() {{
            add("triggerid");
            add("templateid");
            add("description");
            add("expression");
            add("comments");
            add("status");
            add("priority");
            add("value");
        }});
        params.put("selectHosts", new ArrayList() {{
            add("hostid");
            add("host");
        }});
        ZabbixRequestParam requestParam = new ZabbixRequestParam("item.get", params, zabbixBaseService.getRequestId());
        ZabbixResponse response = zabbixBaseService.requestZabbixForObject(requestParam);
        if (zabbixBaseService.responseResultAvailable(response)) {
            try {
                List resultList = (List) response.getResult();
                if (!ObjectUtils.isEmpty(resultList)) {
                    for (Object itemObject : resultList) {
                        ObjectMap itemMap = new ObjectMap((Map) itemObject);
                        ZabbixItem item = new ZabbixItem();
                        item.setItemId(itemMap.getString("itemid"));
                        item.setName(itemMap.getString("name"));
                        item.setKey(itemMap.getString("key_"));
                        item.setDelay(itemMap.getString("delay"));
                        item.setHistory(itemMap.getString("history"));
                        item.setStatus(Integer.valueOf(itemMap.getString("status")));
                        item.setFlags(Integer.valueOf(itemMap.getString("flags")));
                        item.setUnits(itemMap.getString("units"));
                        item.setLastValue(itemMap.getString("lastvalue"));
                        item.setValueType(itemMap.getInteger("value_type"));

                        List applications = itemMap.getList("applications");
                        if (ObjectUtils.isEmpty(applications)) continue;
                        ObjectMap applicationMap = new ObjectMap((Map) applications.get(0));
                        ZabbixApplication application = new ZabbixApplication();
                        application.setApplicationId(applicationMap.getString("applicationid"));
                        application.setName(applicationMap.getString("name"));
                        application.setHostId(applicationMap.getString("hostid"));
                        List templateIds = applicationMap.getList("templateids");
                        if (!ObjectUtils.isEmpty(templateIds)) {
                            application.setParentId((String) templateIds.get(0));
                        } else {
                            application.setParentId(null);
                        }
                        item.setApplication(application);

                        List triggers = itemMap.getList("triggers");
                        if (!ObjectUtils.isEmpty(triggers)) {
                            for (Object trigger : triggers) {
                                Map<String, String> triggerMap = (Map) trigger;
                                ZabbixTrigger zabbixTrigger = new ZabbixTrigger();
                                zabbixTrigger.setTriggerId(triggerMap.get("triggerid"));
                                zabbixTrigger.setDescription(triggerMap.get("description"));
                                zabbixTrigger.setTemplateId(triggerMap.get("templateid"));
                                zabbixTrigger.setExpression(triggerMap.get("expression"));
                                zabbixTrigger.setStatus(Integer.valueOf(triggerMap.get("status")));
                                zabbixTrigger.setComments(triggerMap.get("comments"));
                                zabbixTrigger.setPriority(Integer.valueOf(triggerMap.get("priority")));
                                zabbixTrigger.setValue(Integer.valueOf(triggerMap.get("value")));
                                item.getTriggers().add(zabbixTrigger);
                            }

                        }
                        List<Map> hosts = itemMap.getList("hosts");
                        if (!ObjectUtils.isEmpty(hosts)) {
                            ObjectMap hostMap = new ObjectMap(hosts.get(0));
                            item.setHostId(hostMap.getString("hostid"));
                            item.setHost(hostMap.getString("host"));
                        }
                        zabbixItems.add(item);
                    }
                }

            } catch (Exception e) {
                logger.error("查询zabbixItems失败!解析返回值异常!params={},result={},exception={}", params, response.getResult().toString(), e);
            }
        } else {
            if (zabbixBaseService.responseErrorAvailable(response)) {
                logger.error("查询zabbixItems失败!params={},error:[{}]", params, response.getError().toString());
            } else {
                logger.error("查询zabbixItems失败!,返回为空!params={}", params);
            }
        }
        return zabbixItems;
    }

    @Override
    public List<ZabbixItem> getItemData(ZabbixItem zabbixItem ,int page , int pageSize) {
        List<ZabbixItem> zabbixItems = new ArrayList<>();
        List data = new Info();
        Map params = new HashMap() {{
            put("output", new ArrayList() {{
                add("itemid");
                add("prevvalue");
                add("status");
                add("name");
                add("delay");
                add("value_type");
                add("type");
                add("lastclock");
                add("lastvalue");
                add("units");
            }});

            put("selectApplications", new ArrayList() {{
                add("applicationid");
                add("name");
                add("hostid");
                add("templateids");
            }});

            put("selectHosts", new ArrayList() {{
                add("hostid");
                add("host");
            }});

            put("sortfield","itemid");
            put("sortorder","DESC");

            Map<String, String> hostAll = getHostAll();
            Set<String> hostIds = hostAll.keySet();
            if (!ObjectUtils.isEmpty(zabbixItem)) {
                if (!StringUtils.isEmpty(zabbixItem.getHostId())) {
                    put("hostids", zabbixItem.getHostId());
                } else {
                    put("hostids", hostIds);
                }

                if (!StringUtils.isEmpty(zabbixItem.getHost()))
                    put("host", zabbixItem.getHost());

                Map searches = new HashMap();

                if (!StringUtils.isEmpty(zabbixItem.getName()))
                    searches.put("name", zabbixItem.getName());

                if (!StringUtils.isEmpty(zabbixItem.getValueType()))
                    searches.put("value_type", zabbixItem.getValueType());

                if (!StringUtils.isEmpty(zabbixItem.getType()))
                    searches.put("type", zabbixItem.getType());

                if (!ObjectUtils.isEmpty(searches)) {
                    put("search", searches);
                    put("searchByAny", true);
                }

            } else {
                put("hostids", hostIds);
            }
        }};

        ZabbixRequestParam requestParam = new ZabbixRequestParam("item.get", params, zabbixBaseService.getRequestId());
        ZabbixResponse response = zabbixBaseService.requestZabbixForObject(requestParam);
        if (zabbixBaseService.responseResultAvailable(response)) {
            try {
                Pattern pattern = null;
                Integer type = zabbixItem.getType();
                String apName = zabbixItem.getApName();
                Date startUpTime = zabbixItem.getStartUpTime();
                Date endUpTime = zabbixItem.getEndUpTime();
                boolean apNameEp = !StringUtils.isEmpty(apName);
                boolean startUp = !ObjectUtils.isEmpty(startUpTime);
                boolean endUp = !ObjectUtils.isEmpty(endUpTime);
                boolean isType = !StringUtils.isEmpty(type);
                if(apNameEp)pattern = Pattern.compile(apName,Pattern.CASE_INSENSITIVE);

                List resultList = (List) response.getResult();
                if (!ObjectUtils.isEmpty(resultList)) {
                    for (Object itemObject : resultList) {
                        ObjectMap itemMap = new ObjectMap((Map) itemObject);
                        ZabbixItem item = new ZabbixItem();
                        if (itemMap.getInteger("status").equals(1)) continue;
                        item.setItemId(itemMap.getString("itemid"));
                        item.setName(itemMap.getString("name"));
                        item.setValueType(itemMap.getInteger("value_type"));
                        item.setUnits(itemMap.getString("units"));
                        String lastclock = itemMap.getString("lastclock");
                        long lock = Long.parseLong(lastclock)*1000L;
                        if(endUp){
                            if(lock > endUpTime.getTime()) continue;
                        }
                        if(startUp){
                            if(lock <startUpTime.getTime()) continue;
                        }
                        item.setLastUpdateTime(DateUtils.secToDate(lastclock));
                        item.setLastClock(lastclock);
                        item.setDelay(itemMap.getString("delay"));
                        Integer _type = itemMap.getInteger("type");
                        if(isType){
                            if(type != _type)continue;
                        }
                        item.setType(_type);
                        UnitsFormatResult result = UnitsFormat.
                                transformValueByUnits(
                                itemMap.getString("lastvalue"),
                                item.getUnits());
                        item.setLastValue(result.getValue());
                        List<Map> hosts = itemMap.getList("hosts");
                        if (!ObjectUtils.isEmpty(hosts)) {
                            ObjectMap hostMap = new ObjectMap(hosts.get(0));
                            item.setHostId(hostMap.getString("hostid"));
                            item.setHost(hostMap.getString("host"));
                        }
                        List applications = itemMap.getList("applications");
                        if (!ObjectUtils.isEmpty(applications)) {
                            ObjectMap applicationMap = new ObjectMap((Map) applications.get(0));
                            String name = applicationMap.getString("name");
                            if(apNameEp){
                                if(ObjectUtils.isEmpty(name))continue;
                                Matcher matcher = pattern.matcher(name);
                                if(!matcher.find()) continue;
                            }
                            item.setApName(name);
                        }
                        zabbixItems.add(item);
                    }
                }
                int size = zabbixItems.size();
                List<ZabbixItem> datas = new ArrayList<>();
                ((Info) data).setTotalElements(size);
                //((Info) data).setTotalPages(size % pageSize == 0 ? size / pageSize : size / pageSize + 1);
                List<ZabbixItem> infos1 = null;
                if (page * pageSize < size) {
                    infos1 = zabbixItems.subList(page * pageSize,
                            ((page + 1) * pageSize < size ? (page + 1) * pageSize : size));
                    datas.addAll(infos1);
                }
                ((Info) data).setRows(datas);

            } catch (Exception e) {
                e.printStackTrace();
                logger.error("getItemData失败!解析返回值异常!params={},result={},exception={}", params, response.getResult().toString(), e);
            }
        } else {
            if (zabbixBaseService.responseErrorAvailable(response)) {
                logger.error("getItemData失败!params={},error:[{}]", params, response.getError().toString());
            } else {
                logger.error("getItemData失败!,返回为空!params={}", params);
            }
        }
        return data;
    }

    @Override
    public List<ZabbixLastItem> getLatestItemData(ZabbixLastItem zabbixLastItem, List<String> appNames) {

        Map<String, String> hostAll = getHostAll();
        Set<String> hostIds = hostAll.keySet();
//        List<String>  hostIds = new ArrayList<>(strings);
        if (ObjectUtils.isEmpty(hostIds))
            return new ArrayList<>();
        List<ZabbixLastItem> lastItemList = new ArrayList<>();
        Map params = new HashMap() {
            {
                String hostId = zabbixLastItem.getHostId();
                String applicationName = zabbixLastItem.getApplicationName();

                if (!ObjectUtils.isEmpty(zabbixLastItem)) {

                    if (!StringUtils.isEmpty(hostId))
                        put("hostids", hostId);
                    else
                        put("hostids", hostIds);

                    if (!StringUtils.isEmpty(applicationName)) {
                        put("search", new HashMap() {
                            {
                                put("name", applicationName);
                            }
                        });
                        put("searchByAny", true);
                    }

                } else {

                    put("hostids", hostIds);

                }

                put("selectItems", new ArrayList() {{
                    add("itemid");
                    add("name");
                    add("delay");
                    add("value_type");
                    add("type");
                    add("lastclock");
                    add("lastvalue");
                    add("prevvalue");
                    add("status");
                    add("units");
                }});

                if (!ObjectUtils.isEmpty(appNames)) {
                    put("filter", new HashMap() {{
                        put("name", new ArrayList() {{
                            for (String name : appNames) {
                                add(name);
                            }
                        }});
                    }});
                }
            }
        };

        ZabbixRequestParam requestParam = new ZabbixRequestParam("application.get", params, zabbixBaseService.getRequestId());
        ZabbixResponse response = zabbixBaseService.requestZabbixForObject(requestParam);
        if (zabbixBaseService.responseResultAvailable(response)) {
            try {
                List<Map> result = (List) response.getResult();
                for (Map appReturn : result) {
                    ObjectMap appMap = new ObjectMap(appReturn);
                    String applicationid = appMap.getString("applicationid");
                    String hostid = appMap.getString("hostid");
                    String apName = appMap.getString("name");
                    String templateId = null;
                    List templateIds = appMap.getList("templateids");
                    if (!ObjectUtils.isEmpty(templateIds)) {
                        templateId = (String) templateIds.get(0);
                    }
                    List<Map> items = (List) appReturn.get("items");
                    if (!ObjectUtils.isEmpty(items)) {
                        for (Map itemReturn : items) {
                            ObjectMap itemMap = new ObjectMap(itemReturn);
                            if (itemMap.getInteger("status").equals(1)) continue;//过滤掉 已被禁用的监控项数据
                            ZabbixLastItem item = new ZabbixLastItem();
                            item.setItemId(itemMap.getString("itemid"));
                            item.setItemName(itemMap.getString("name"));
                            item.setValueType(itemMap.getInteger("value_type"));
                            item.setUnits(itemMap.getString("units"));
                            item.setLastClock(itemMap.getString("lastclock"));
                            item.setLastValue(itemMap.getString("lastvalue"));
                            item.setPreValue(itemMap.getString("prevvalue"));
                            item.setDelay(itemMap.getString("delay"));
                            item.setType(itemMap.getInteger("type"));
                            item.setApplicationName(apName);
                            item.setApplicationId(applicationid);
                            item.setTemplateId(templateId);
                            item.setHostId(hostid);
                            item.setHost(hostAll.get(hostid));
                            lastItemList.add(item);
                        }
                    }
                }

//                if (!ObjectUtils.isEmpty(lastItemList)) {
//                    lastItemList.sort(new ZabbixItemSortByHostId());
//                }

            } catch (Exception e) {
                logger.error("请求application-item-data失败!解析返回值异常!params={},result={}", params, response.getResult().toString());
            }
        } else {
            if (zabbixBaseService.responseErrorAvailable(response)) {
                logger.error("请求application-item-data失败!params={},error:[{}]", params, response.getError().toString());
            } else {
                logger.error("请求application-item-data失败!,返回为空!params={}", params);
            }
        }
        return lastItemList;
    }

    @Override
    public  Boolean updateHostByGroupId(Integer id , boolean flag)throws Exception{
        Map params = new HashMap() {{
            put("output","extend");

            put("selectHosts", new ArrayList() {{
                add("hostid");
                add("host");
            }});

        }};

        ZabbixRequestParam requestParam = new ZabbixRequestParam("item.get", params, zabbixBaseService.getRequestId());
        ZabbixResponse response = zabbixBaseService.requestZabbixForObject(requestParam);


        return true;
    }


    public Integer getIntegerBefore(String source, String flag) {
        return Integer.valueOf(source.substring(0, source.indexOf(flag)));
    }

}
