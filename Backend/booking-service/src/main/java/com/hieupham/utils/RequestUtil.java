package com.hieupham.utils;

import jakarta.servlet.http.HttpServletRequest;

public class RequestUtil {
    public static String getIpAddress(HttpServletRequest req){
        String xForwardedForHeader = req.getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null) {
            var remoteAddr = req.getRemoteAddr();
            if (remoteAddr == null) {
                remoteAddr = "127.0.0.1";   // TODO: the ip of this BE app
            }

            return remoteAddr;
        }

        return xForwardedForHeader.split(",")[0].trim();
    }
}
