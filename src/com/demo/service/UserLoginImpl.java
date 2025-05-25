package com.demo.service;

import com.demo.dao.UserLoginDao;
import com.demo.dao.UserLoginDaoImpl;
import com.demo.exception.UserNotFoundException;
import com.demo.pojo.Users;

public class UserLoginImpl implements UserLoginService{

    @Override
    public Users userLogin(String username, String password) {
        UserLoginDao userLoginDao = new UserLoginDaoImpl();
        Users users = userLoginDao.selectUsersByUserNameAndPwd(username,password);
        if(users == null){
            throw new UserNotFoundException("用户名或密码有误");
        }
        return users;
    }
}
