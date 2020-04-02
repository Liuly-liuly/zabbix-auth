package com.liuly.zabbixauth.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GraphConfig {
    private String graphId;

    private String graphName;

    private Integer graphType;

    private Integer categoryId;

    private List<GraphItem> itemList = new ArrayList<>();
}
