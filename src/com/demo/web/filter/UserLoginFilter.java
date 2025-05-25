package com.demo.web.filter;


import com.demo.commons.Constants;
import com.demo.pojo.Users;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.LogRecord;

@WebFilter(urlPatterns = {"*.do","*.jsp"})
public class UserLoginFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws
            IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String uri = request.getRequestURI();
        //判断当前请求的是否为 login.jsp 或者 login.do，如果请求的是用户登录
        //的资源那么需要放行。
        if(uri.indexOf("login.jsp") != -1 || uri.indexOf("login.do") != -1){
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            HttpSession session = request.getSession();
            Users users = (Users) session.getAttribute(Constants.USER_SESSION_KEY);
            if(users != null){
                filterChain.doFilter(servletRequest, servletResponse);
            }else{
                request.setAttribute(Constants.REQUEST_MSG,"不登录不好使！" );
                request.getRequestDispatcher("login.jsp").forward(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
