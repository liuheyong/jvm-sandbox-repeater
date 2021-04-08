package com.alibaba.repeater.console.common.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * {@link RecordBO}
 * <p>
 *
 * @author zhaoyb1990
 */
@Getter
@Setter
public class RecordBO extends BaseBO implements java.io.Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
    /**
     * 录制时间
     */
    private LocalDateTime gmtRecord;
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
    private String host;
    /**
     * 链路追踪ID
     */
    private String traceId;
    /**
     * 链路追踪ID
     */
    private String entranceDesc;

    @Override
    public String toString() {
        return super.toString();
    }
}
