package com.demo.dao;

import com.demo.commons.JdbcUtils;
import com.demo.pojo.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLoginDaoImpl implements UserLoginDao {
    /**
     * @param username
     * @param password
     * @return
     */
    @Override
    public Users selectUsersByUserNameAndPwd(String username, String password) {
        Users user = null;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "select * from users where username = ? and userpwd = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                user = new Users();
                user.setUsername(resultSet.getString("username"));
                user.setPwd(resultSet.getString("userpwd"));
                user.setPhonenumber(resultSet.getString("phonenumber"));
                user.setUsersex(resultSet.getString("usersex"));
                user.setUserid(resultSet.getInt("userid"));
                user.setQqnumber(resultSet.getString("qqnumber"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}

