package com.alibaba.repeater.console.common.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    private String appName;

    private String environment;

    private String config;
}
