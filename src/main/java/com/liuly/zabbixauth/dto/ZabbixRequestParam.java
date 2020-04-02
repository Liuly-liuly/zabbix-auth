package com.liuly.zabbixauth.dto;

import com.liuly.zabbixauth.annotation.ZabbixToken;
import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

@Data
public class ZabbixRequestParam {

    private String jsonrpc = "2.0";

    private String method;

    private Object params;

    @ZabbixToken
    private String auth;

    private Long id;

    public ZabbixRequestParam(){}

    public ZabbixRequestParam(String method, Object params, AtomicLong id ){
        this.method = method;
        this.params = params;
        this.id = id.get();
    }

    public ZabbixRequestParam(String method, Object params, AtomicLong id , String authToken){
        this.method = method;
        this.params = params;
        this.id = id.get();
        this.auth = authToken;
    }

    public void reloadAuthentication(Long id ,String authToken){
        this.id = id;
        this.auth = authToken;
    }
}
