package com.alibaba.repeater.console.common.params;

/**
 * @description: 日志对象
 */
public class AopLog {

    /**
     * 线程id
     */
    private String threadId;
    /**
     * 线程名称
     */
    private String threadName;
    /**
     * ip
     */
    private String ip;
    /**
     * url
     */
    //private String url;
    /**
     * http方法 GET POST PUT DELETE PATCH
     */
    private String httpMethod;
    /**
     * 类方法
     */
    private String classMethod;
    /**
     * 请求参数
     */
    //private Object requestParams;
    /**
     * 返回参数
     */
    //private Object result;
    /**
     * 接口耗时
     */
    //private String timeCost;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 浏览器
     */
    private String browser;
    /**
     * user-agent
     */
    private String userAgent;

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getClassMethod() {
        return classMethod;
    }

    public void setClassMethod(String classMethod) {
        this.classMethod = classMethod;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

}
