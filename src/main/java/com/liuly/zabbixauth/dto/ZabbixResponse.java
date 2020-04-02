package com.liuly.zabbixauth.dto;

import lombok.Data;

@Data
public class ZabbixResponse {

    private String jsonrpc;

    private Long id;

    private Object result;

    private ZabbixError error;

}
