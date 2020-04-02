package com.liuly.zabbixauth;

import com.liuly.zabbixauth.common.ZabbixApi;
import com.liuly.zabbixauth.common.ZabbixApiException;
import com.liuly.zabbixauth.common.ZabbixApiParamter;
import com.liuly.zabbixauth.dto.ZabbixApplication;
import com.liuly.zabbixauth.dto.ZabbixHost;
import com.liuly.zabbixauth.dto.ZabbixItem;
import com.liuly.zabbixauth.dto.ZabbixLastItem;
import com.liuly.zabbixauth.host.HostCreateRequest;
import com.liuly.zabbixauth.host.HostCreateResponse;
import com.liuly.zabbixauth.host.HostGetRequest;
import com.liuly.zabbixauth.host.HostGetResponse;
import com.liuly.zabbixauth.hostgroup.*;
import com.liuly.zabbixauth.hostinteface.HostInterfaceObject;
import com.liuly.zabbixauth.service.ZabbixBaseService;
import com.liuly.zabbixauth.service.ZabbixFuctionService;
import com.liuly.zabbixauth.template.TemplateGetRequest;
import com.liuly.zabbixauth.template.TemplateGetResponse;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZabbixAuthApplicationTests {

    @Autowired
    private ZabbixBaseService zabbixBaseService;

    @Autowired
    private ZabbixFuctionService fuctionService;

    @Autowired
    private ZabbixApi zabbixApi;

    @Test
	public void contextLoads() {
//        List  data = new ArrayList<ZabbixRequestParam>();
//        ZabbixRequestParam one = new ZabbixRequestParam();
//        one.setJsonrpc("2345");
//        ZabbixRequestParam two = new ZabbixRequestParam();
//        two.setId(234L);
//        data.add(one);
//        data.add(two);
//        String test = zabbixBaseService.loginInAndAcToken(null ,1 ,new ZabbixRequestParam(), true);
//        System.out.println("--->"+test);
//        HashMap<Object, Object> param = new HashMap<>();
//        List<ZabbixHost> hosts = fuctionService.getHosts(param);
//        System.out.println("-->"+hosts);
        Map<String, String> hostAll = fuctionService.getHostAll();
//        ZabbixLastItem item = new ZabbixLastItem();
//        item.setHostId("10279");
//        List<ZabbixLastItem> latestItemData = fuctionService.getLatestItemData(item, new ArrayList<>());
//        System.out.println("---->"+latestItemData);
//        List<ZabbixItem> items = fuctionService.getItems(param);
        //ZabbixItem zabbixItem = new ZabbixItem();
        //List< ZabbixItem> itemData = fuctionService.getItemData(zabbixItem,0 ,1000);
//       // System.out.println("---->"+items);
        System.out.println("---->"+hostAll);
    }

    @Test
    public void hostGroupTest(){
        String name = "host_group_test_one";
        HostgroupCreateRequest request = new HostgroupCreateRequest();
        request.getParams().setName(name);
        try {
            HostgroupCreateResponse hostgroupCreateResponse = zabbixApi.hostgroup(null).create(request);
            assertNotNull(hostgroupCreateResponse);
            System.out.println(hostgroupCreateResponse.getResult().getGroupids());
            assertTrue(0 < hostgroupCreateResponse.getResult().getGroupids().size());
        } catch (ZabbixApiException e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void getHostGroup(){
        try {
            HostgroupGetRequest request = new HostgroupGetRequest();
            HostgroupGetRequest.Params params = request.getParams();
            params.setSelectHosts(new ArrayList<String>(){{
                add("host");
                add("hostid");
                add("name");
                add("available");
            }});
            params.setSelectTemplates(new ArrayList<String>(){{
                add("templateid");
                add("host");
                add("name");
            }});
            HostgroupGetResponse response = zabbixApi.hostgroup(null).get(request);
            assertNotNull(response);
            response.getResult().forEach(e->{
                System.out.println(e.getName() +"--->"+e.getGroupid());
            });

        } catch (ZabbixApiException e) {
            fail();
        }
    }

    public void delHostGroup(int id){
        int targetGroupid = id;

        HostgroupDeleteRequest request = new HostgroupDeleteRequest();
        request.setParams(new ArrayList<Integer>(Arrays.asList(new Integer[]{targetGroupid})));

        try {
            HostgroupDeleteResponse response = zabbixApi.hostgroup(null).delete(request);

            assertNotNull(response);

            assertNotNull(response.getResult().getGroupids());
            int groupId = response.getResult().getGroupids().get(0);

            assertEquals(targetGroupid, groupId);
        } catch (ZabbixApiException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getTemp()throws ZabbixApiException{

            TemplateGetRequest request = new TemplateGetRequest();
            TemplateGetResponse response = zabbixApi.template(null).get(request);
            assertNotNull(response);
            response.getResult().forEach(e->{
                String s = e.getName() + " : " + e.getTemplateid();
                System.out.println("---->"+s);
            });

    }

    @Test
    public void createHost(){

        int newHostId = 10300;
        final Integer groupId = 21;
        final Integer templateId = 10297;

        try {
            HostCreateRequest request = new HostCreateRequest();
            HostCreateRequest.Params params = request.getParams();

            params.addTemplateId(templateId);
            params.addGroupId(groupId);


            HostInterfaceObject hostInterface = new HostInterfaceObject();
            hostInterface.setIp("127.0.0.1");
            params.addHostInterfaceObject(hostInterface);

            params.setHost("test host created");
            params.setName("test host created name");

            HostCreateResponse response = zabbixApi.host(null).create(request);
            assertNotNull(response);
            System.out.println(response.getResult().getHostids());
            int hostId = response.getResult().getHostids().get(0);
            assertTrue(0 < hostId);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getHost()throws ZabbixApiException{

            HostGetRequest request = new HostGetRequest();
//            HostGetRequest.Params params = request.getParams();
//            params.setSelectGroups(ZabbixApiParamter.QUERY.extend.name());
//            params.setSelectItems(ZabbixApiParamter.QUERY.count.name());
//            params.setSelectInterfaces(ZabbixApiParamter.QUERY.extend.name());
            HostGetResponse response = zabbixApi.host(null).get(request);
            assertNotNull(response);
            List<HostGetResponse.Result> result = response.getResult();
            System.out.println();

    }


}
