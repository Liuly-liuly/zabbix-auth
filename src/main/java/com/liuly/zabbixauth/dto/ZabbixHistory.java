package com.liuly.zabbixauth.dto;

import lombok.Data;

@Data
public class ZabbixHistory {
    private String clock;

    private String itemId;

    private String nanoseconds;

    private String value;
}
