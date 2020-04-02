package com.liuly.zabbixauth.dto;

import java.text.Collator;
import java.util.Comparator;

public class ZabbixItemSortByHostId implements Comparator<ZabbixLastItem>{

    @Override
    public int compare(ZabbixLastItem arg1, ZabbixLastItem arg2){
        return Collator.getInstance().compare(arg1.getHostId(),arg2.getHostId());
    }

}
