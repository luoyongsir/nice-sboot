package com.nice.sboot.base.utils;

import com.nice.sboot.base.comm.Const;
import com.nice.sboot.base.exception.RunException;
import com.nice.sboot.base.utils.text.StringBud;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * IP公用类
 *
 * @author luoyong
 * @date 2019/6/19 18:34
 */
public final class IpUtil {

    private static String hostAddress;
    static {
        init();
    }

    /**
     * int ip 还原成字符串
     * 
     * @param ip
     * @return
     */
    public static String toString(final int ip) {
        final StringBuilder buf = StringBud.current();
        int tmp;

        tmp = (ip >> 24) & 0x000000FF;
        buf.append(tmp);
        buf.append(Const.DOT);

        tmp = (ip >> 16) & 0x000000FF;
        buf.append(tmp);
        buf.append(Const.DOT);

        tmp = (ip >> 8) & 0x000000FF;
        buf.append(tmp);
        buf.append(Const.DOT);

        tmp = ip & 0x000000FF;
        buf.append(tmp);

        return buf.toString();
    }

    /**
     * ip字符串转成int
     * 
     * @param ip
     *            字符串ip
     * @return
     */
    public static int parseInt(final String ip) {
        int result = 0;
        if (ip == null) {
            return result;
        }

        // ip地址长度
        int len = ip.length();
        // 临时存储数字
        int num = 0;
        // 点号间256进制
        int offset = 24;
		for (int i = 0; i < len; i++) {
			char c = ip.charAt(i);
			if (c == '.') {
				if (num < 0 || num > 255) {
					throw new RunException("ip数字错误！");
				}
				result += (num << offset);
				num = 0;
				offset -= 8;
			} else {
				// char 转成数字
				int tmp = c - '0';
				if (tmp > -1 && tmp < 10) {
					num = (num * 10) + tmp;
				} else {
					throw new RunException("ip包含非法字符！");
				}
			}
		}

        // ip必须包含3个"." 并且ip的最后位必须大于等于0小于256
        if (offset == 0 && num > -1 && num < 256) {
            result += num;
        } else {
            throw new RunException("ip地址格式错误！");
        }

        return result;
    }

    /**
     * 获取本地ip地址
     * 
     * @return
     */
    public static String getHostAddress() {
        return hostAddress;
    }

    private static void init() {
        String osName = System.getProperty("os.name");
        if (osName != null && osName.contains("Windows")) {
            IpUtil.hostAddress = getIpForWindows();
        } else {
            IpUtil.hostAddress = getIpForLinux();
        }
    }

    /**
     * Windows系统获取本机ip
     * 
     * @return
     */
    private static String getIpForWindows() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            throw new RunException("getIpForWindows:", e);
        }
    }

    /**
     * Linux系统获取本机ip
     * 
     * @return
     */
    private static String getIpForLinux() {
        // 内网IP，如果没有配置外网IP则返回它
        String tempip = null;
        // 外网IP
        String ip = null;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            // 是否找到外网IP
            boolean flag = false;
            while (interfaces.hasMoreElements() && !flag) {
                NetworkInterface netInterface = interfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
					InetAddress addr = addresses.nextElement();
                    if (addr != null) {
                        // 尽量不返回空值
                        if (tempip == null) {
                            tempip = addr.getHostAddress();
                        }
                        if (!addr.isSiteLocalAddress() && !addr.isLoopbackAddress() && addr.getHostAddress().indexOf(":") == -1) {
                            // 外网IP
                            ip = addr.getHostAddress();
                            flag = true;
                            break;
                        } else if (addr.isSiteLocalAddress() && !addr.isLoopbackAddress() && addr.getHostAddress().indexOf(":") == -1) {
                            // 内网IP
                            tempip = addr.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RunException("getIpForLinux:", e);
        }

        if (StringUtils.isNotEmpty(ip)) {
            return ip;
        } else {
            return tempip;
        }
    }

    // X-Client-IP
    // X-Forwarded-For header may return multiple IP addresses in the format:
    // "client IP, proxy 1 IP, proxy 2 IP", so we take the the first one.
    // X-Real-IP (nginx proxy/FastCGI)
    // X-Cluster-Client-IP (Rackspace LB, Riverbed Stingray)
    // Permuations of #2 such as: X-Forwarded, Forwarded-For and Forwarded
    // req.connection.remoteAddress
    // req.socket.remoteAddress
    // req.connection.socket.remoteAddress
    // req.info.remoteAddress

    private static final String[] HEADERS = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "X-Real-IP", "", "X-Client-IP",
            "X-Cluster-Client-IP", "X-Forwarded", "Forwarded-For", "Forwarded" };

    /**
     * 从request对象中获取用户IP地址
     * 
     * @param request
     * @return IP地址
     */
    public static String getRequestIp(final HttpServletRequest request) {
        String ips = null;
        for (int i = 0; i < HEADERS.length; i++) {
            if (i != 4) {
                ips = request.getHeader(HEADERS[i]);
            } else {
                ips = request.getRemoteAddr();
            }
            if (!isEmpty(ips)) {
                break;
            }
        }
        if (ips != null) {
            String[] arr = ips.split(Const.COMMA);
            for (String s : arr) {
                if (!isEmpty(s)) {
                    return s;
                }
            }
        }
        return ips;
    }

    /**
     * 判断ip是否为空
     * 
     * @param ip
     * @return
     */
    private static boolean isEmpty(String ip) {
        return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
    }

    private IpUtil() {}
}
