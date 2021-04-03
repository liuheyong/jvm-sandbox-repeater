package com.alibaba.repeater.console.start.config;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.alibaba.repeater.console.start.util.IpUtil.getRealIp;

/**
 * @description: logback日志添加ip信息
 */
public class LogbackIpConfig extends ClassicConverter {

    private static final Logger logger = LoggerFactory.getLogger(LogbackIpConfig.class);

    @Override
    public String convert(ILoggingEvent event) {
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            if ("127.0.0.1".equals(ip)) {
                return getRealIp();
            }
            return ip;
        } catch (UnknownHostException e) {
            logger.error("获取日志Ip异常", e);
        }
        return null;
    }
}
