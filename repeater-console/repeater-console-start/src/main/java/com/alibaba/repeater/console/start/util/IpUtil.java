package com.alibaba.repeater.console.start.util;

import com.alibaba.jvm.sandbox.repeater.plugin.core.util.LogUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashSet;

/**
 * @description:
 */
public class IpUtil {

    private static final String UNKNOWN = "unknown";

    /**
     * 获取真实ip地址
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String comma = ",";
        String localhost = "127.0.0.1";
        if (ip.contains(comma)) {
            ip = ip.split(",")[0];
        }
        if (localhost.equals(ip)) {
            // 获取本机真正的ip地址
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                LogUtil.error(e.getMessage(), e);
            }
        }
        if (localhost.equals(ip)) {
            ip = getRealIp();
        }
        return ip;
    }

    public static String getRealIp() {
        String ip = "";
        HashSet<Object> ipSet = new HashSet<>();
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface nextElement = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = nextElement.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
                        ip = inetAddress.getHostAddress();
                        ipSet.add(inetAddress.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            LogUtil.error(e.getMessage(), e);
        }
        return ip;
    }
}
