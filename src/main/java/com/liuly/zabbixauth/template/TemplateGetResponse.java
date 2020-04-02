package com.liuly.zabbixauth.template;

import com.liuly.zabbixauth.common.ZabbixApiResponse;

import java.util.List;


public class TemplateGetResponse extends ZabbixApiResponse {
    public TemplateGetResponse() {
        super();
    }

    private List<Result> result;

    public class Result extends TemplateObject {

    }

    /**
     * Sets new result.
     *
     * @param result New value of result.
     */
    public void setResult(List<Result> result) {
        this.result = result;
    }

    /**
     * Gets result.
     *
     * @return Value of result.
     */
    public List<Result> getResult() {
        return result;
    }
}
