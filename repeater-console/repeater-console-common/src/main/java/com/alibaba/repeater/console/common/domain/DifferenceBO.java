package com.alibaba.repeater.console.common.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * {@link DifferenceBO}
 * <p>
 *
 * @author zhaoyb1990
 */
@Getter
@Setter
public class DifferenceBO extends BaseBO {
    /**
     * 之际值
     */
    private String actual;
    /**
     * 期望值
     */
    private String expect;
    /**
     * 类型
     */
    private String type;
    /**
     * 节点名称
     */
    private String nodeName;

    @Override
    public String toString() {
        return super.toString();
    }
}
