package com.liuly.zabbixauth.dto;

import lombok.Data;

@Data
public class ZabbixError {

    private Integer code;

    private String message;

    private String data;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ZabbixError{");
        sb.append("code=").append(code);
        sb.append(", message='").append(message).append('\'');
        sb.append(", data='").append(data).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
