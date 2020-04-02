package com.liuly.zabbixauth.template;

import com.liuly.zabbixauth.common.ZabbixApiRequest;

import java.util.ArrayList;
import java.util.List;


public class TemplateDeleteRequest extends ZabbixApiRequest {
    private List<Integer> params = new ArrayList<Integer>();

    public TemplateDeleteRequest() {
        setMethod("template.delete");
    }

    public void addTemplateId(Integer id) {
        params.add(id);
    }

    /**
     * Gets params.
     *
     * @return Value of params.
     */
    public List<Integer> getParams() {
        return params;
    }

    /**
     * Sets new params.
     *
     * @param params New value of params.
     */
    public void setParams(List<Integer> params) {
        this.params = params;
    }
}
