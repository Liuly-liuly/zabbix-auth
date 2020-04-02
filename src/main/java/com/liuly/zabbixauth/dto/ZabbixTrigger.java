package com.liuly.zabbixauth.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ZabbixTrigger {

    private String triggerId;

    //触发器名称
    private String description;

    /*触发器的严重等级 0 - default
     **1 - information;
     **2 - warning;
     **3 - average;
     **4 - high;
     **5 - disaster.
     **/
    private Integer priority;

    //触发器表达式
    private String expression;

    //0启用  1禁用
    private Integer status;

    //自定义信息（对应模板导入时的trigger-description字段）
    //以$S为分隔符，依次为 expression  description-name  description-value
    private String comments;

    //触发器状态 0 正常， 1异常
    private Integer value;

    //若为"0" ，表示该触发器为自定义的触发器，而不是通过关联监控模板生成的
    private String templateId;

    //自定义标签
    private List<TriggerTags> tags = new ArrayList<>();

    //所属机器 ip
    private String hostIp;

    //所属机器 id
    private String hostId;

    //所属监控项 id
    private String itemId;

    //监控项数据
    private String itemLastValue;

    //监控项数据 单位
    private String itemUnits;

}
