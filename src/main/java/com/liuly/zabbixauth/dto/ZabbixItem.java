package com.liuly.zabbixauth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ZabbixItem {

    private String itemId;

    private String name;

    private String hostId;

    private String host;

    private String key;

    //单位 秒 s
    private String delay;

    // 单位 天 d
    private String history;

    // 0启用 1禁用
    private Integer status;

    private ZabbixApplication application;

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

    private List<ZabbixTrigger> triggers = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startUpTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endUpTime;

    private String apName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateTime;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ZabbixItem{");
        sb.append("itemId='").append(itemId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", hostId='").append(hostId).append('\'');
        sb.append(", key='").append(key).append('\'');
        sb.append(", delay=").append(delay);
        sb.append(", history=").append(history);
        sb.append(", status=").append(status);
        sb.append(", application=").append(application);
        sb.append(", flags=").append(flags);
        sb.append(", units='").append(units).append('\'');
        sb.append(", lastClock='").append(lastClock).append('\'');
        sb.append(", lastValue='").append(lastValue).append('\'');
        sb.append(", preValue='").append(preValue).append('\'');
        sb.append(", valueType=").append(valueType);
        sb.append(", triggers=").append(triggers);
        sb.append('}');
        return sb.toString();
    }


}
