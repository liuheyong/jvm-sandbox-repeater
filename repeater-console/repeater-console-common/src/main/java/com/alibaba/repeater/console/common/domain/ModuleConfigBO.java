package com.alibaba.repeater.console.common.domain;

import com.alibaba.jvm.sandbox.repeater.plugin.domain.RepeaterConfig;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * {@link ModuleConfigBO}
 * <p>
 *
 * @author zhaoyb1990
 */
@Getter
@Setter
public class ModuleConfigBO extends BaseBO {

    /**
     * 应用名
     */
    private Long id;
    /**
     * 应用名
     */
    private LocalDateTime gmtCreate;
    /**
     * 应用名
     */
    private LocalDateTime gmtModified;
    /**
     * 应用名
     */
    private String appName;
    /**
     * 应用名
     */
    private String environment;
    /**
     * 应用名
     */
    private RepeaterConfig configModel;
    /**
     * 应用名
     */
    private String config;

    @Override
    public String toString() {
        return super.toString();
    }
}
