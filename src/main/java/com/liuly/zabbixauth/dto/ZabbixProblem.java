package com.liuly.zabbixauth.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ZabbixProblem {

    private String eventId;

    /**
     * Type of the problem event.
     Possible values:
     0 - event created by a trigger;
     3 - internal event.
     */
    private Integer source;

    /**
     *Type of object that is related to the problem event.

     Possible values for trigger events:
     0 - trigger.

     Possible values for internal events:
     0 - trigger;
     4 - item;
     5 - LLD rule.
     */
    private Integer object;

    /**
     * ID of the related object.
     */
    private String objectId;

    /**
     * Time when the problem event was created.
     */
    private String clock;

    /**
     * Nanoseconds when the problem event was created.
     */
    private String ns;

    private String recoveryEventId;

    /**
     * Time when the recovery event was created.
     */
    private String recoveryEventClock;

    /**
     * Nanoseconds when the recovery event was created.
     */
    private String recoveryEventNs;

    /**
     * Correlation rule ID if this event was recovered by global correlation rule.
     */
    private String correlationId;

    /**
     * User ID if the problem was manually closed.
     */
    private String userId;

    /**
     * State of the related object.

     Possible values for trigger events:
     0 - OK;
     1 - problem.
     */
    private Integer value;

    private ZabbixTrigger relatedObject;//关联的触发器

    private List<ProblemAcknowledge> acknowledges = new ArrayList<>();

  }
