package com.example.demo.service;

import com.example.demo.bean.UserSuggest;
import com.example.demo.dao.UserSuggestDao;
import com.example.demo.service.impl.UserSuggestServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userSuggestImpl")
public class UserSuggestService implements UserSuggestServiceImpl {

    @Resource
    private UserSuggestDao userSuggestDao;

    @Override
    public int insertSuggest(UserSuggest userSuggest) {
        int i = userSuggestDao.insertSuggest(userSuggest);
        return i;
    }

    @Override
    public List<UserSuggest> selectSuggestByUserId(Integer userId, Integer begin, Integer sheet) {
        List<UserSuggest>  userSuggests = userSuggestDao.selectSuggestByUserId(userId, begin, sheet);
        System.out.println(userSuggests);
        return userSuggests;
    }

    @Override
    public List<UserSuggest> selectSuggest(Integer begin, Integer sheet) {
        List<UserSuggest> userSuggests = userSuggestDao.selectSuggest(begin, sheet);
        return userSuggests;
    }

    @Override
    public int selectNumByUserId(Integer userId) {
        int num = userSuggestDao.selectNumByUserId(userId);
        return num;
    }

    @Override
    public int selectNum() {
        int num = userSuggestDao.selectNum();
        return num;
    }
}
