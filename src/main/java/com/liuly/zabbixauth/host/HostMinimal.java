package com.liuly.zabbixauth.host;

import lombok.Data;

@Data
public class HostMinimal {
    private String name;
    private Integer hostid;
    private Integer proxy_hostid;
    private String host;
    private Integer status;
    private Integer disable_until;
    private String error;
    private Integer available;
    private Integer errors_from;
    private Integer lastaccess;
}
