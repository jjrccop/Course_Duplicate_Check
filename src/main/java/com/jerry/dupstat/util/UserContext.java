package com.jerry.dupstat.util;

import com.jerry.dupstat.domain.Login;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserContext {
    public static final String USER_IN_SESSION = "login_in_session";

    private static HttpSession getSession() {
        ServletRequestAttributes threadAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = threadAttributes.getRequest();
        HttpSession session = request.getSession();
        return session;
    }

    /*
     *  得到session,并把loginInfo放到session中
     */
    public static void putLogin(Login login) {
        getSession().setAttribute(USER_IN_SESSION, login);
    }

    /*
     * 取出session中的LoginInfo
     */
    public static Login getLogin() {
        Login loginInfo = (Login) getSession().getAttribute(USER_IN_SESSION);
        return loginInfo;
    }
}
