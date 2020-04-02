package com.liuly.zabbixauth.dto;

import java.text.Collator;
import java.util.Comparator;

public class ZabbixItemSortByItemId implements Comparator<ZabbixItem>{

    @Override
    public int compare(ZabbixItem arg1, ZabbixItem arg2){
        return Collator.getInstance().compare(arg1.getItemId(),arg2.getItemId());
    }

}
