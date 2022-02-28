package org.setana.treenity.security.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

    public static String getAuthorizationToken(String header) {

        // header 의 Authorization 키 값이 Bearer <access_token> 형식이 아닐 경우
        if (header == null || !header.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid authorization header.");
        }

        // header 로부터 Bearer 제거
        String[] splits = header.split(" ");
        if (splits.length != 2) {
            throw new IllegalArgumentException("Invalid authorization header.");
        }
        return splits[1];
    }

    public static String getAuthorizationToken(HttpServletRequest request) {
        return getAuthorizationToken(request.getHeader("Authorization"));
    }

}
