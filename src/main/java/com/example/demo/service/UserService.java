package com.example.demo.service;

import com.example.demo.bean.Role;
import com.example.demo.bean.User;
import com.example.demo.dao.UserDao;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("UserServiceImpl")
public class UserService implements UserServiceImpl {

    @Resource
    private UserDao userDao;

    @Override
    public void updateUserName(User user){
        userDao.updateUserName(user);
    }

    /**
     * 授权页面
     * @param user
     */
    @Override
    public void updateUserInfo(User user) {
        userDao.updateUserInfo(user);
    }

    @Override
    public User getUserById(int userId) {
        return userDao.selectById(userId);
    }

    @Override
    public int insertUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public User wxLogin(User user) {
        int id = 0;
        User user1 = userDao.selectByCode(user.getUserCode());
        if(user1 == null){
            id = userDao.insertUser(user);
            user1 = userDao.selectByCode(user.getUserCode());
        }else{
            id = user1.getId();
        }
        return user1;
    }

    @Override
    public List<Role> selectUserRole(Integer userId) {
        List<Role> roles = userDao.selectUserRole(userId);
        return roles;
    }


}
