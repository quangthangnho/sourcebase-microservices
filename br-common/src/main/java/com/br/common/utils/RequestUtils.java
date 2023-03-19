package com.br.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import javax.servlet.http.HttpServletRequest;

/**
 * @author an.cantuong
 */
public final class RequestUtils {

    private static final String X_REAL_IP_HEADER = "x-real-ip";
    private static final String USER_AGENT = "User-Agent";
    private static final String X_FORWARDED_FOR_HEADER = "x-forwarded-for";
    private static final String PROXY_CLIENT_IP_HEADER = "proxy-client-ip";
    private static final String WL_PROXY_CLIENT_IP_HEADER = "wl-proxy-client-ip";
    private static final String ANONYMOUS = "anonymous";
    private static final String UNKNOWN_IP = "unknown";

    private RequestUtils() {
    }

    public static String getRequestUrl(HttpServletRequest request) {
        var url = request.getRequestURL().toString().toLowerCase();
        return getRequestUrl(url);
    }

    public static String getRequestUrl(ServerHttpRequest request) {
        var url = request.getURI().toString().toLowerCase();
        return getRequestUrl(url);
    }

    private static String getRequestUrl(String rawUrl) {
        int idx = rawUrl.indexOf("password");
        if (idx > 0) return rawUrl.substring(0, idx) + "[SENSITIVE DATA - PWD]";

        idx = rawUrl.indexOf("token=");
        if (idx > 0) return rawUrl.substring(0, idx) + "[SENSITIVE DATA - TOKEN]";

        idx = rawUrl.indexOf("session=");
        if (idx > 0) return rawUrl.substring(0, idx) + "[SENSITIVE DATA - SESSION]";

        return rawUrl;
    }

    public static String getRealIp(HttpServletRequest request) {
        var ip = request.getHeader(X_FORWARDED_FOR_HEADER);
        if (StringUtils.isEmpty(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader(X_REAL_IP_HEADER);
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader(PROXY_CLIENT_IP_HEADER);
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader(WL_PROXY_CLIENT_IP_HEADER);
        }
        if (StringUtils.isNotEmpty(ip) && ip.length() > 15 && ip.contains(",")) {
            ip = ip.substring(0, ip.indexOf(","));
        }
        return ip;
    }

    public static String getRealIp(ServerHttpRequest request) {
        var ip = getFirstHeaderValue(request, X_FORWARDED_FOR_HEADER);
        if (StringUtils.isEmpty(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = getFirstHeaderValue(request, X_REAL_IP_HEADER);
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = getHostAddress(request);
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = getFirstHeaderValue(request, PROXY_CLIENT_IP_HEADER);
        }
        if (StringUtils.isEmpty(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = getFirstHeaderValue(request, WL_PROXY_CLIENT_IP_HEADER);
        }
        if (StringUtils.isNotEmpty(ip) && ip.length() > 15 && ip.contains(",")) {
            ip = ip.substring(0, ip.indexOf(","));
        }
        return ip;
    }

    public static String getHostAddress(ServerHttpRequest request) {
        var remoteAddress = request.getRemoteAddress();
        return remoteAddress == null ? null : remoteAddress.getAddress().getHostAddress();
    }

    public static String getUserAgent(ServerHttpRequest request) {
        return request.getHeaders().getFirst(USER_AGENT);
    }

    public static String getHostName(ServerHttpRequest request) {
        var remoteAddress = request.getRemoteAddress();
        return remoteAddress != null ? remoteAddress.getHostName() : null;
    }


    public static String getRequestUser(HttpServletRequest request) {
        return StringUtils.isNotEmpty(request.getRemoteUser()) ? request.getRemoteUser() : ANONYMOUS;
    }

    public static String getHeader(HttpServletRequest request, String headerName) {
        return request.getHeader(headerName);
    }

    public static String getParameter(HttpServletRequest request, String paramName) {
        return request.getParameter(paramName);
    }

    public static String getFirstHeaderValue(ServerWebExchange exchange, String headerName) {
        return getFirstHeaderValue(exchange.getRequest(), headerName);
    }

    public static String getFirstHeaderValue(ServerHttpRequest request, String headerName) {
        var headers = request.getHeaders();
        var headerValues = headers.get(headerName);
        return CollectionUtils.isEmpty(headerValues) ? null : headerValues.get(0);
    }

    public static String getFirstRequestParamValue(ServerWebExchange exchange, String paramName) {
        return getFirstRequestParamValue(exchange.getRequest(), paramName);
    }

    public static String getFirstRequestParamValue(ServerHttpRequest request, String paramName) {
        var queryParams = request.getQueryParams();
        var params = queryParams.get(paramName);

        return CollectionUtils.isEmpty(params) ? null : params.get(0);
    }

}
