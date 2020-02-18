package com.example.demo.service.impl;

import com.example.demo.bean.Role;
import com.example.demo.bean.User;

import java.util.List;

public interface UserServiceImpl {

    void updateUserName(User user);

    void updateUserInfo(User user);

    User getUserById(int userId);

    int insertUser(User user);

    User wxLogin(User user);

    List<Role> selectUserRole(Integer userId);
}
