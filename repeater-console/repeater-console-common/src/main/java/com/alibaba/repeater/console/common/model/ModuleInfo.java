package com.alibaba.repeater.console.common.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * {@link ModuleInfo}
 * <p>
 * 在线模块信息
 *
 * @author zhaoyb1990
 */
@Getter
@Setter
public class ModuleInfo implements java.io.Serializable {

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String appName;

    private String environment;

    private String ip;

    private String port;

    private String version;

    private String status;
}
