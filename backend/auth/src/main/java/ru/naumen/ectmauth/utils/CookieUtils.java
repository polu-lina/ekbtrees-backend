package ru.naumen.ectmauth.utils;

import ru.naumen.ectmauth.constants.AuthConstants;
import ru.naumen.ectmauth.constants.CookieNames;
import ru.naumen.ectmauth.dto.JwtDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

    public static void setTokenCookies(HttpServletResponse response, JwtDto tokens) {
        Cookie accessTokenCookie = new Cookie(CookieNames.ACCESS_TOKEN, tokens.getAccessToken());
        accessTokenCookie.setHttpOnly(false);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(Math.toIntExact(AuthConstants.COOKIES_MAX_AGE));
        response.addCookie(accessTokenCookie);
        Cookie refreshTokenCookie = new Cookie(CookieNames.REFRESH_TOKEN, tokens.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(Math.toIntExact(AuthConstants.COOKIES_MAX_AGE));
        response.addCookie(refreshTokenCookie);
    }

}
