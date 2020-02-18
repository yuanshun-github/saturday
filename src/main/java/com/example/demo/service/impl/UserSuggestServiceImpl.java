package com.example.demo.service.impl;

import com.example.demo.bean.UserSuggest;

import java.util.List;

public interface UserSuggestServiceImpl {

    int insertSuggest(UserSuggest userSuggest);

    List<UserSuggest> selectSuggestByUserId(Integer userId, Integer begin, Integer sheet);

    List<UserSuggest> selectSuggest(Integer begin, Integer sheet);

    int selectNumByUserId(Integer userId);

    int selectNum();

}
