package com.alibaba.repeater.console.common.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * {@link PageResult}
 * <p>
 *
 * @author zhaoyb1990
 */
@Getter
@Setter
public class PageResult<T> implements java.io.Serializable {

    /**
     * 分页数据
     */
    private List<T> data;
    /**
     * 分页返回结果标识数据
     */
    private boolean success;
    /**
     * 分页返回结果信息
     */
    private String message;

    private Long count;
    private Integer totalPage;
    private Integer pageSize;
    private Integer pageIndex;

    public boolean hasPrevious() {
        return pageIndex > 1;
    }

    public boolean hasNext() {
        return pageIndex < totalPage;
    }

}
