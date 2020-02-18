package com.example.demo.dao;

import com.example.demo.bean.ResourceFile;
import com.example.demo.common.tool.StringCode;

import java.util.List;

public interface ResourceFileDao {

    int insertResource(ResourceFile resource);

    List<ResourceFile> selectResourceByKeyword(String keyword, Integer begin, Integer sheet);

    List<ResourceFile> selectResourceByTwoKeywords(String keyword1, String keyword2,Integer begin,Integer sheet);

    List<ResourceFile> selectResourceByLimit(String grade, String subject, String type, Integer begin, Integer sheet);
}
