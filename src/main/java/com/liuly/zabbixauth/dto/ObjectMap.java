package com.liuly.zabbixauth.dto;

import java.util.List;
import java.util.Map;

public class ObjectMap {

    private Map objectMap;

    public ObjectMap(Map object){
        this.objectMap = object;
    }

    private Map getObjectMap() {
        return objectMap;
    }

    public void setObjectMap(Map objectMap) {
        this.objectMap = objectMap;
    }

    public String getString(String name){
        return (String)objectMap.get(name);
    }

    public Integer getInteger(String name){
        return Integer.valueOf((String)objectMap.get(name));
    }

    public Object getObject(String name){
        return (Object)objectMap.get(name);
    }

    public List getList(String name){
        return (List)objectMap.get(name);
    }

    public Map getMap(String name){
        return (Map)objectMap.get(name);
    }

    public Integer acNumbericBefore(String name, String flag){
        String source = (String)objectMap.get(name);
        return Integer.valueOf(source.substring(0,source.indexOf(flag)));
    }

}
