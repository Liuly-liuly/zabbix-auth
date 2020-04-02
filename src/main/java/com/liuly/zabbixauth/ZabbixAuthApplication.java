package com.liuly.zabbixauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;

import java.util.Date;

@SpringBootApplication
public class ZabbixAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZabbixAuthApplication.class, args);
    }

}
