package com.liuly.zabbixauth.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Properties;

public class CommonUtils {
    private static Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);

    public CommonUtils() {
    }

    public static Properties getProperties(String filePath) {
        Properties prop = new Properties();

        try {
            InputStream inputStream = new BufferedInputStream(CommonUtils.class.getResourceAsStream(filePath));
            prop.load(inputStream);
            inputStream.close();
        } catch (Exception var3) {
            LOGGER.error("application-zabbix.properties not found");
        }

        return prop;
    }
}
