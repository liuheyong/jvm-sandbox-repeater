package com.alibaba.repeater.console.common.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * {@link Replay}
 * <p>
 *
 * @author zhaoyb1990
 */
@Getter
@Setter
public class Replay implements java.io.Serializable {

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String appName;

    private String ip;

    private String environment;

    private String repeatId;

    private Integer status;

    private Record record;

    /**
     * replay traceId
     */
    private String traceId;

    private String response;

    private String mockInvocation;

    private Boolean success;

    private Long cost;

    private String diffResult;
}
