package com.alibaba.repeater.console.common.model;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * {@link Record}
 * <p>
 *
 * @author zhaoyb1990
 */
@Getter
@Setter
public class Record implements java.io.Serializable {

    private Long id;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtRecord;

    private String appName;

    private String environment;

    private String host;

    private String traceId;

    private String entranceDesc;

    private String wrapperRecord;

    private String request;

    private String response;

    private List<Replay> replays;
}
