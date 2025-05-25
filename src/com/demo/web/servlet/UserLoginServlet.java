package com.demo.web.servlet;
import com.demo.commons.Constants;
import com.demo.exception.UserNotFoundException;
import com.demo.pojo.Users;
import com.demo.service.UserLoginImpl;
import com.demo.service.UserLoginService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import  javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login.do")
public class UserLoginServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("userpwd");
        String code = req.getParameter("code");
        try {
            HttpSession session = req.getSession();
//           getAttribute  Servlet 转发到 JSP 时共享数据
            String codeTemp = (String) session.getAttribute(Constants.VALIDAT_CODE_KEY);
            if(codeTemp.equals(code)){
                UserLoginService userLoginService = new UserLoginImpl();
                Users users = userLoginService.userLogin(username,password);
                //建立客户端与会话端的状态

                session.setAttribute(Constants.USER_SESSION_KEY,users);

                ServletContext servletContext = this.getServletContext();
                HttpSession temp = (HttpSession) servletContext.getAttribute(users.getUserid()+"");
                if(temp != null)
                {
                    servletContext.removeAttribute(users.getUserid()+"");
                    temp.invalidate();
                }
                servletContext.setAttribute(users.getUserid()+"",session);
                //使用重定向跳转到登录页面
                resp.sendRedirect("main.jsp");
            }else {
                req.setAttribute(Constants.REQUEST_MSG,"验证码有误，请重新输入");
                req.getRequestDispatcher("login.jsp").forward(req,resp);
            }

        } catch (UserNotFoundException e) {
            //因为在service层捕获了异常，得处理
            req.setAttribute("msg",e.getMessage());
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        } catch (Exception e){
            e.printStackTrace();
            resp.sendRedirect("error.jsp");
        }
   }
}
