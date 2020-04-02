

package com.liuly.zabbixauth.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liuly.zabbixauth.adapter.DoubleDefault0Adapter;
import com.liuly.zabbixauth.adapter.IntegerDefault0Adapter;
import com.liuly.zabbixauth.adapter.LongDefault0Adapter;

import java.util.ArrayList;
import java.util.List;

public class ZabbixUtils {

    static Gson gson;

    private ZabbixUtils() {
    }

    public static <E> List<E> add(List<E> list, E e) {
        if (list == null) {
            list = new ArrayList<E>();
        }

        list.add(e);

        return list;
    }

    public static Gson buildGson() {
        if(gson == null) {
            gson =new GsonBuilder()
                    .registerTypeAdapter(Integer.class,new IntegerDefault0Adapter())
                    .registerTypeAdapter(int.class,new IntegerDefault0Adapter())
                    .registerTypeAdapter(Double.class,new DoubleDefault0Adapter())
                    .registerTypeAdapter(double.class,new DoubleDefault0Adapter())
                    .registerTypeAdapter(Long.class,new LongDefault0Adapter())
                    .registerTypeAdapter(long.class,new LongDefault0Adapter())
                    .create();
        }
        return gson;
    }

}
