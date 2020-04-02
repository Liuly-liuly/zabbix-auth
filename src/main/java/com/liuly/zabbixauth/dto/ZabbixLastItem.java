package com.liuly.zabbixauth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
public class ZabbixLastItem {

    private String itemId;

    private String itemName;

    private String hostId;

    private String host;

    //单位 秒 s
    private String delay;

    //0 - a plain item; 4 - a discovered item.
    private Integer flags;

    //监控数据 度量单位
    private String units;

    //上次数据更新时间  2017-12-18 14:23:04
    private String lastClock;

    //最近一条数据值
    private String lastValue;

    //之前一条数据值
    private String preValue;

    //Possible values: 0 - numeric float;
    // 1 - character; 2 - log;
    // 3 - numeric unsigned; 4 - text.
    private Integer valueType;

    private Integer type;

    private String applicationId;

    private String applicationName;

    private String templateId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    private Date startUpTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    private Date endUpTime;

    public ZabbixLastItem(){}

}
