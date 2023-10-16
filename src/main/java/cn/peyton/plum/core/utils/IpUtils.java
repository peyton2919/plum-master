package cn.peyton.plum.core.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h3>IP 工具类</h3>
 * <pre>
 * @email: <a href="mailto:fz2919@tom.com">fz2919@tom.com</a>
 * @create date: 2018/11/16 15:57
 * @author: <a href="http://www.peyton.cn">peyton</a>
 * @version: 1.0.0
 * </pre>
 */
@Slf4j
public final class IpUtils implements Serializable {

    public final static String ERROR_IP = "127.0.0.1";

    public final static Pattern pattern = Pattern.
            compile("(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})");

    /**
     * <h4>取外网IP</h4>
     *
     * @param request
     * @return
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("x-real-ip");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        //过滤反向代理的ip
        String[] stemps = ip.split(",");
        if (stemps != null && stemps.length >= 1) {
            //得到第一个IP，即客户端真实IP
            ip = stemps[0];
        }

        ip = ip.trim();
        if (ip.length() > 23) {
            ip = ip.substring(0, 23);
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            return "127.0.0.1";
        }
        return ip;
    }

    /**
     * <h4>获取用户的真实ip</h4>
     *
     * @param request
     * @return
     */
    public static String getUserIP(HttpServletRequest request) {
        // 优先取X-Real-IP
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if ("0:0:0:0:0:0:0:1".equals(ip)) {
                ip = ERROR_IP;
            }
        }
        if ("unknown".equalsIgnoreCase(ip)) {
            ip = ERROR_IP;
            return ip;
        }
        int pos = ip.indexOf(',');
        if (pos >= 0) {
            ip = ip.substring(0, pos);
        }
        return ip;
    }

    /**
     * <h4>获取最后IP</h4>
     * @param request
     * @return
     */
    public static String getLastIpSegment(HttpServletRequest request) {
        String ip = getUserIP(request);
        if (ip != null) {
            ip = ip.substring(ip.lastIndexOf('.') + 1);
        } else {
            ip = "0";
        }
        return ip;
    }

    /**
     * <h>判断IP是否合法</h>
     * @param request
     * @return
     */
    public static boolean isValidIP(HttpServletRequest request) {
        String ip = getUserIP(request);
        return isValidIP(ip);
    }

    /**
     * <h4>判断我们获取的ip是否是一个符合规则ip</h4>
     *
     * @param ip
     * @return
     */
    public static boolean isValidIP(String ip) {
        if (StrUtils.isEmpty(ip)) {
            log.debug("ip is null. valid result is false");
            return false;
        }
        Matcher matcher = pattern.matcher(ip);
        boolean isValid = matcher.matches();
        log.debug("valid ip:" + ip + " result is: " + isValid);
        return isValid;
    }

    /**
     * <h4>获取最后服务器 部分IP</h4>
     * @return
     */
    public static String getLastServerIpSegment() {
        String ip = getServerIP();
        if (ip != null) {
            ip = ip.substring(ip.lastIndexOf('.') + 1);
        } else {
            ip = "0";
        }
        return ip;
    }

    /**
     * <h4>获取服务器IP</h4>
     * @return
     */
    public static String getServerIP() {
        InetAddress inet;
        try {
            inet = InetAddress.getLocalHost();
            String hostAddress = inet.getHostAddress();
            return hostAddress;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "127.0.0.1";
    }

    /**
     * <h4>获取客户端IP地址</h4>
     * @param request
     * @return Ip地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if(ip.equals("127.0.0.1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip= inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ip != null && ip.length() > 15){
            if(ip.indexOf(",") > 0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }

    /**
     * <h>获取物理网卡地址</h>
     * @return
     */
    public static String getMacAddress() {
        StringBuilder sb = new StringBuilder("");
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            byte[] mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();

            for(int i=0; i<mac.length; i++) {
                if(i!=0) {
                    sb.append("-");
                }
                //字节转换为整数
                int temp = mac[i]&0xff;
                String str = Integer.toHexString(temp);
                if(str.length()== 1) {
                    sb.append("0").append(str);
                }else {
                    sb.append(str);
                }
            }
            return sb.toString().toUpperCase();
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}