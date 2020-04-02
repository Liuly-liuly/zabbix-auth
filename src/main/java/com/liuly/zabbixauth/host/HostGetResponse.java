package com.liuly.zabbixauth.host;

import com.liuly.zabbixauth.common.ZabbixApiResponse;
import com.liuly.zabbixauth.hostgroup.HostgroupObject;
import com.liuly.zabbixauth.hostinteface.HostInterfaceObject;
import com.liuly.zabbixauth.item.ItemObject;
import com.liuly.zabbixauth.template.TemplateObject;

import java.util.ArrayList;
import java.util.List;


public class HostGetResponse extends ZabbixApiResponse {

    private List<Result> result = new ArrayList<Result>();

    public HostGetResponse() {
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result extends HostObject {

        private Integer groups;
        private Integer items;
        private Integer discoveries;
        private Integer triggers;
        private Integer graphs;
        private Integer httpTests;
        private Integer applications;
        private Integer macros;
        private List<HostInterfaceObject> interfaces;
        private Integer screens;
        private Integer discoveryRule;
        private List<TemplateObject> parentTemplates;

        public Integer getGroups() {
            return groups;
        }

        public void setGroups(Integer groups) {
            this.groups = groups;
        }

        public Integer getItems() {
            return items;
        }

        public void setItems(Integer items) {
            this.items = items;
        }

        public Integer getDiscoveries() {
            return discoveries;
        }

        public void setDiscoveries(Integer discoveries) {
            this.discoveries = discoveries;
        }

        public Integer getTriggers() {
            return triggers;
        }

        public void setTriggers(Integer triggers) {
            this.triggers = triggers;
        }

        public Integer getGraphs() {
            return graphs;
        }

        public void setGraphs(Integer graphs) {
            this.graphs = graphs;
        }

        public Integer getHttpTests() {
            return httpTests;
        }

        public void setHttpTests(Integer httpTests) {
            this.httpTests = httpTests;
        }

        public Integer getApplications() {
            return applications;
        }

        public void setApplications(Integer applications) {
            this.applications = applications;
        }

        public Integer getMacros() {
            return macros;
        }

        public void setMacros(Integer macros) {
            this.macros = macros;
        }

        public List<HostInterfaceObject> getInterfaces() {
            return interfaces;
        }

        public void setInterfaces(List<HostInterfaceObject> interfaces) {
            this.interfaces = interfaces;
        }

        public List<TemplateObject> getParentTemplates() {
            return parentTemplates;
        }

        public void setParentTemplates(List<TemplateObject> parentTemplates) {
            this.parentTemplates = parentTemplates;
        }

        public Integer getScreens() {
            return screens;
        }

        public void setScreens(Integer screens) {
            this.screens = screens;
        }

        public Integer getDiscoveryRule() {
            return discoveryRule;
        }

        public void setDiscoveryRule(Integer discoveryRule) {
            this.discoveryRule = discoveryRule;
        }

   }
}
