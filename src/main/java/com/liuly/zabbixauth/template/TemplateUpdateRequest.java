package com.liuly.zabbixauth.template;

import com.liuly.zabbixauth.common.ZabbixApiRequest;
import com.liuly.zabbixauth.dto.Macro;
import com.liuly.zabbixauth.host.HostObject;
import com.liuly.zabbixauth.hostgroup.HostgroupObject;
import com.liuly.zabbixauth.util.ZabbixUtils;

import java.util.List;


public class TemplateUpdateRequest extends ZabbixApiRequest {
    private Params params = new Params();

    public TemplateUpdateRequest() {
        setMethod("template.update");
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
        private List<HostObject> hosts;
        private List<Macro> macros;
        private List<TemplateObject> templates;
        private List<TemplateObject> templates_clear;

        public void addGroupId(Integer id) {
            HostgroupObject obj = new HostgroupObject();
            obj.setGroupid(id);
            groups = ZabbixUtils.add(groups, obj);
        }

        public void addHostId(Integer id) {
            HostObject obj = new HostObject();
            obj.setHostid(id);
            hosts = ZabbixUtils.add(hosts, obj);
        }

        public void addMacro(String macro, String value) {
            Macro obj = new Macro();
            obj.setMacro(macro);
            obj.setValue(value);
            macros = ZabbixUtils.add(macros, obj);
        }

        public void addTemplateId(Integer id) {
            TemplateObject obj = new TemplateObject();
            obj.setTemplateid(id);
            templates = ZabbixUtils.add(templates, obj);
        }

        public void addClearTemplate(Integer id) {
            TemplateObject obj = new TemplateObject();
            obj.setTemplateid(id);
            templates_clear = ZabbixUtils.add(templates_clear, obj);
        }

        /**
         * Gets templates_clear.
         *
         * @return Value of templates_clear.
         */
        public List<TemplateObject> getTemplates_clear() {
            return templates_clear;
        }

        /**
         * Sets new templates_clear.
         *
         * @param templates_clear New value of templates_clear.
         */
        public void setTemplates_clear(List<TemplateObject> templates_clear) {
            this.templates_clear = templates_clear;
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
