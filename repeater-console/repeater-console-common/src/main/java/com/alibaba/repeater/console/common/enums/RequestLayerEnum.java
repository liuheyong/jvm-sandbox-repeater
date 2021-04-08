package com.alibaba.repeater.console.common.enums;

/**
 * @description: 请求层次
 */
public enum RequestLayerEnum {
    /**
     * RestTemplate请求层
     */
    REST_TEMPLATE(5, "RestTemplate请求层"),
    /**
     * Backend层
     */
    BACKEND(4, "Backend层"),
    /**
     * Backend层
     */
    REPO(3, "Repo层"),
    /**
     * Service层
     */
    SERVICE(2, "Service层"),
    /**
     * Controller层
     */
    CONTROLLER(1, "Controller层");

    /**
     * 类别
     */
    private int type;
    /**
     * 名称
     */
    private String name;

    RequestLayerEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
