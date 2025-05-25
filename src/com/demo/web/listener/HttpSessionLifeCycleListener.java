package com.demo.web.listener;

import com.demo.commons.Constants;
import com.demo.pojo.Users;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class HttpSessionLifeCycleListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }
//解决session被反复销毁的问题
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        ServletContext servletContext = session.getServletContext();
        Users users = (Users) session.getAttribute(Constants.USER_SESSION_KEY);
        servletContext.removeAttribute(users.getUserid()+"");

    }
}
