package com.liuly.zabbixauth.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ZabbixApplication {
    private String applicationId;

    private String hostId;

    private String name;

    //parentId为null时hostId指向Template
    private String parentId;

    private List<ZabbixItem> items = new ArrayList<>();

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ZabbixApplication{");
        sb.append("applicationId='").append(applicationId).append('\'');
        sb.append(", hostId='").append(hostId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", parentId='").append(parentId).append('\'');
        sb.append(", items=").append(items);
        sb.append('}');
        return sb.toString();
    }
}
