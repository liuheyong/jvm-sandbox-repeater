package com.alibaba.repeater.console.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * {@link RecordDetailBO}
 * <p>
 *
 * @author zhaoyb1990
 */
@Getter
@Setter
@ToString
public class RecordDetailBO extends RecordBO {
    /**
     * 请求参数JSON
     */
    private String request;
    /**
     * 返回值JSON
     */
    private String response;
    /**
     * 子调用记录
     */
    private String subInvocations;
}
