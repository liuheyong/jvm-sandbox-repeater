package com.alibaba.repeater.console.common.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * {@link ModuleConfig}
 * <p>
 *
 * @author zhaoyb1990
 */
@Getter
@Setter
public class ModuleConfig implements java.io.Serializable {

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String appName;

    private String environment;

    private String config;
}
