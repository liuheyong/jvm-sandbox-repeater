package com.alibaba.repeater.console.common.params;

import lombok.Getter;
import lombok.Setter;

/**
 * {@link ModuleConfigParams}
 * <p>
 *
 * @author zhaoyb1990
 */
@Getter
@Setter
public class ModuleConfigParams extends BaseParams {

    /**
     * 应用名
     */
    private String appName;
    /**
     * 环境信息
     */
    private String environment;
    /**
     * 配置信息
     */
    private String config;
}
