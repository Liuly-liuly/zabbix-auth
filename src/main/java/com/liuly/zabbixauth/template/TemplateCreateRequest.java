package com.liuly.zabbixauth.template;

import com.liuly.zabbixauth.common.ZabbixApiRequest;
import com.liuly.zabbixauth.dto.Macro;
import com.liuly.zabbixauth.host.HostObject;
import com.liuly.zabbixauth.hostgroup.HostgroupObject;
import com.liuly.zabbixauth.util.ZabbixUtils;

import java.util.List;

public class TemplateCreateRequest extends ZabbixApiRequest {
    private Params params = new Params();

    public TemplateCreateRequest() {
        setMethod("template.create");
    }

    /**
     * Gets params.
     *
     * @return Value of params.
     */
    public Params getParams() {
        return params;
    }

    /**
     * Sets new params.
     *
     * @param params New value of params.
     */
    public void setParams(Params params) {
        this.params = params;
    }

    public class Params extends TemplateObject {

        private List<HostgroupObject> groups;
        private List<TemplateObject> templates;
        private List<Macro> macros;
        private List<HostObject> hosts;

        public void addGroupId(Integer id) {
            HostgroupObject obj = new HostgroupObject();
            obj.setGroupid(id);
            groups = ZabbixUtils.add(groups, obj);
        }

        public void addTemplateId(Integer id) {
            TemplateObject obj = new TemplateObject();
            obj.setTemplateid(id);
            templates = ZabbixUtils.add(templates, obj);
        }

        public void addMacroId(String macro, String value) {
            Macro obj = new Macro();
            obj.setMacro(macro);
            obj.setValue(value);
            macros = ZabbixUtils.add(macros, obj);
        }

        public void addHostId(Integer id) {
            HostObject obj = new HostObject();
            obj.setHostid(id);
            hosts = ZabbixUtils.add(hosts, obj);
        }

        /**
         * Gets hosts.
         *
         * @return Value of hosts.
         */
        public List<HostObject> getHosts() {
            return hosts;
        }

        /**
         * Sets new hosts.
         *
         * @param hosts New value of hosts.
         */
        public void setHosts(List<HostObject> hosts) {
            this.hosts = hosts;
        }

        /**
         * Gets templates.
         *
         * @return Value of templates.
         */
        public List<TemplateObject> getTemplates() {
            return templates;
        }

        /**
         * Sets new templates.
         *
         * @param templates New value of templates.
         */
        public void setTemplates(List<TemplateObject> templates) {
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

        /**
         * Gets macros.
         *
         * @return Value of macros.
         */
        public List<Macro> getMacros() {
            return macros;
        }

        /**
         * Sets new macros.
         *
         * @param macros New value of macros.
         */
        public void setMacros(List<Macro> macros) {
            this.macros = macros;
        }
    }
}
