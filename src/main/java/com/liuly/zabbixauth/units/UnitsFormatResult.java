package com.liuly.zabbixauth.units;

import lombok.Data;

@Data
public class UnitsFormatResult {

    private String value;//值

    private String suffix;//后缀

    private String adaptedUnits;

    @Override
    public String toString() {
        return "UnitsFormatResult{" +
                "value='" + value + '\'' +
                ", suffix='" + suffix + '\'' +
                ", adaptedUnits='" + adaptedUnits + '\'' +
                '}';
    }
}
