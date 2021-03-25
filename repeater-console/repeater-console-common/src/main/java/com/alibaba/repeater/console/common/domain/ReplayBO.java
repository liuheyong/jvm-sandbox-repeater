package com.alibaba.repeater.console.common.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * {@link ReplayBO}
 * <p>
 *
 * @author zhaoyb1990
 */
@Getter
@Setter
public class ReplayBO extends BaseBO {
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 应用名
     */
    private String appName;
    /**
     * ip
     */
    private String ip;
    /**
     * 环境信息
     */
    private String environment;
    /**
     * 回放ID
     */
    private String repeatId;
    /**
     * 链路追踪ID
     */
    private String traceId;
    /**
     * 回放结果
     */
    private String response;
    /**
     * 是否回放成功
     */
    private Boolean success;
    /**
     * 回放耗时
     */
    private Long cost;
    /**
     * 回放状态
     */
    private ReplayStatus status;
    /**
     * 回放的请求记录
     */
    private RecordDetailBO record;
    /**
     * 子调用
     */
    private List<MockInvocationBO> mockInvocations;
    /**
     * diff结果
     */
    private List<DifferenceBO> differences;

    @Override
    public String toString() {
        return super.toString();
    }
}
