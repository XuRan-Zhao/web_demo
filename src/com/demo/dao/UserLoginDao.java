package com.demo.dao;

import com.demo.pojo.Users;

public interface UserLoginDao {

    public Users selectUsersByUserNameAndPwd(String username,String password);
}
