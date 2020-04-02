package com.liuly.zabbixauth.dto;

import lombok.Data;

@Data
public class ProblemAcknowledge {

    private String id;

    private String userId;

    private String eventId;

    private String clock;

    private String message;

    private Integer action;

}
