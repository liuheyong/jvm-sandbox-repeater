package com.alibaba.repeater.console.common.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * {@link ModuleInfoBO}
 * <p>
 * 在线模块信息
 *
 * @author zhaoyb1990
 */
@Getter
@Setter
public class ModuleInfoBO extends BaseBO {
    /**
     * 主键
     */
    private Long id;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;
    /**
     * 应用名
     */
    private String appName;
    /**
     * 环境信息
     */
    private String environment;
    /**
     * 机器IP
     */
    private String ip;
    /**
     * 端口
     */
    private String port;
    /**
     * 模块版本号
     */
    private String version;
    /**
     * 模块状态
     */
    private ModuleStatus status;

    @Override
    public String toString() {
        return super.toString();
    }
}
