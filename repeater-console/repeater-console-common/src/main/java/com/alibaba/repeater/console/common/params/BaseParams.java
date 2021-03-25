package com.alibaba.repeater.console.common.params;

import lombok.Getter;
import lombok.Setter;

/**
 * {@link BaseParams}
 * <p>
 *
 * @author zhaoyb1990
 */
@Getter
@Setter
public class BaseParams implements java.io.Serializable {

    /**
     * 页码
     */
    private Integer page = 1;
    /**
     * 每页数量
     */
    private Integer size = 10;
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 记录id
     */
    private String traceId;
    /**
     * 环境
     */
    private String environment;
}
