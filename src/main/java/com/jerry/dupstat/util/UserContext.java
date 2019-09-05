package com.jerry.dupstat.util;

import com.jerry.dupstat.domain.Login;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserContext {
    private static final String USER_IN_SESSION = "login_in_session";

    private static HttpSession getSession() {
        ServletRequestAttributes threadAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = threadAttributes.getRequest();
        return request.getSession();
    }

    /*
     *  得到session,并把login放到session中
     */
    public static void putLogin(Login login) {
        getSession().setAttribute(USER_IN_SESSION, login);
    }

    /*
     * 取出session中的Login
     */
    public static Login getLogin() {
        return (Login) getSession().getAttribute(USER_IN_SESSION);
    }
}
