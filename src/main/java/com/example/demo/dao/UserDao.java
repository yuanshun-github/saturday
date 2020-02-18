package com.example.demo.dao;

import com.example.demo.bean.Role;
import com.example.demo.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {

    void updateUserName(User user);

    void updateUserInfo(User user);

    User selectById(Integer id);

    User selectByCode(String code);

    int insertUser(User user);

    List<Role> selectUserRole(Integer userId);

}
