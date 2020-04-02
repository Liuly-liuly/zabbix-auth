package com.liuly.zabbixauth.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource({"classpath:/application-zabbix.properties"})
public class ZabbixServer {
    @Value("${zabbix.ip}")
    private String ip;
    @Value("${zabbix.username}")
    private String username;
    @Value("${zabbix.password}")
    private String password;
}
