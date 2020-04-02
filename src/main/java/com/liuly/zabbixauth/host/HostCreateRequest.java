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

package com.liuly.zabbixauth.host;

import com.liuly.zabbixauth.common.ZabbixApiRequest;
import com.liuly.zabbixauth.dto.Macro;
import com.liuly.zabbixauth.hostgroup.HostgroupObject;
import com.liuly.zabbixauth.hostinteface.HostInterfaceObject;
import com.liuly.zabbixauth.util.ZabbixUtils;

import java.util.List;


public class HostCreateRequest extends ZabbixApiRequest {

    private Params params = new Params();

    public HostCreateRequest() {
        setMethod("host.create");
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public class Params extends HostObject {

        private List<HostgroupObject> groups;
        private List<HostInterfaceObject> interfaces;
        private List<Integer> templates;
        private List<Macro> macros;

        public Params() {
        }

        public void addGroupId(Integer id) {
            HostgroupObject obj = new HostgroupObject();
            obj.setGroupid(id);
            groups = ZabbixUtils.add(groups, obj);
        }

        public void addHostInterfaceObject(HostInterfaceObject obj) {
            interfaces = ZabbixUtils.add(interfaces, obj);
        }

        public void addTemplateId(Integer id) {
            templates = ZabbixUtils.add(templates, id);
        }

        public List<HostInterfaceObject> getInterfaces() {
            return interfaces;
        }

        public void setInterfaces(List<HostInterfaceObject> interfaces) {
            this.interfaces = interfaces;
        }

        public List<Macro> getMacros() {
            return macros;
        }

        public void setMacros(List<Macro> macros) {
            this.macros = macros;
        }

        public List<Integer> getTemplates() {
            return templates;
        }

        public void setTemplates(List<Integer> templates) {
            this.templates = templates;
        }

        /**
         * Gets groups.
         *
         * @return Value of groups.
         */
        public List<HostgroupObject> getGroups() {
            return groups;
        }

        /**
         * Sets new groups.
         *
         * @param groups New value of groups.
         */
        public void setGroups(List<HostgroupObject> groups) {
            this.groups = groups;
        }
    }

}
