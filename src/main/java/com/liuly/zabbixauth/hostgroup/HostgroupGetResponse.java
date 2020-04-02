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

package com.liuly.zabbixauth.hostgroup;

import com.liuly.zabbixauth.common.ZabbixApiResponse;
import com.liuly.zabbixauth.host.HostMinimal;
import com.liuly.zabbixauth.host.HostObject;
import com.liuly.zabbixauth.template.TemplateObject;

import java.util.ArrayList;
import java.util.List;

public class HostgroupGetResponse extends ZabbixApiResponse {

    private ArrayList<Result> result = new ArrayList<Result>();

    public HostgroupGetResponse() {
    }

    public ArrayList<Result> getResult() {
        return result;
    }

    public void setResult(ArrayList<Result> result) {
        this.result = result;
    }

    public class Result extends HostgroupObject {
       private List<HostMinimal> hosts;

       private List<TemplateObject>templates;

        public List<HostMinimal> getHosts() {
            return hosts;
        }

        public void setHosts(List<HostMinimal> hosts) {
            this.hosts = hosts;
        }

        public List<TemplateObject> getTemplates() {
            return templates;
        }

        public void setTemplates(List<TemplateObject> templates) {
            this.templates = templates;
        }
    }
}
