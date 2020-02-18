package com.example.demo.dao;

import com.example.demo.bean.UserSuggest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserSuggestDao {

    int insertSuggest(UserSuggest userSuggest);

    List<UserSuggest> selectSuggestByUserId(Integer userId, Integer begin, Integer sheet);

    List<UserSuggest> selectSuggest(Integer begin, Integer sheet);

    int selectNumByUserId(Integer userId);

    int selectNum();

}
