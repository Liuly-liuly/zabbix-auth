/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Suguru Yajima
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liuly.zabbixauth.common;

import com.liuly.zabbixauth.annotation.ZabbixToken;
import com.liuly.zabbixauth.annotation.ZabixAuthToken;
import com.liuly.zabbixauth.application.Application;
import com.liuly.zabbixauth.dto.ZabbixServer;
import com.liuly.zabbixauth.host.Host;
import com.liuly.zabbixauth.hostgroup.Hostgroup;
import com.liuly.zabbixauth.hostinteface.HostInterface;
import com.liuly.zabbixauth.item.Item;
import com.liuly.zabbixauth.service.imp.ZabbixBaseServiceImpl;
import com.liuly.zabbixauth.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ZabbixApi {
    //主机集群增删改操作
   @ZabixAuthToken
    public Hostgroup hostgroup(@ZabbixToken String auth) {
        return new Hostgroup(ZabbixBaseServiceImpl.zabbixUrl, auth);
    }
    //主机增删改操作
    @ZabixAuthToken
    public Host host(@ZabbixToken String auth) {
        return new Host(ZabbixBaseServiceImpl.zabbixUrl, auth);
    }
    //监控项增删改操作
    @ZabixAuthToken
    public Item item(@ZabbixToken String auth) {
        return new Item(ZabbixBaseServiceImpl.zabbixUrl, auth);
    }
    //主机接口增删改操作
    @ZabixAuthToken
    public HostInterface hostInterface(@ZabbixToken String auth) {
        return new HostInterface(ZabbixBaseServiceImpl.zabbixUrl, auth);
    }
    //模板增删改操作
    @ZabixAuthToken
    public Template template(@ZabbixToken String auth) {
        return new Template(ZabbixBaseServiceImpl.zabbixUrl, auth);
    }
    //应用增删改操作
    @ZabixAuthToken
    public Application application(@ZabbixToken String auth) {
        return new Application(ZabbixBaseServiceImpl.zabbixUrl, auth);
    }

}
