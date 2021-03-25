package com.alibaba.repeater.console.common.params;

import lombok.*;

/**
 * {@link ReplayParams}
 * <p>
 *
 * @author zhaoyb1990
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplayParams extends BaseParams {

    /**
     * ip
     */
    private String ip;

    /**
     * 回放id
     */
    private String repeatId;

    /**
     * 端口
     */
    private String port;

    /**
     * 是否mock
     */
    private boolean mock;

}
