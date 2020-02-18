package com.example.demo.service.impl;

import com.example.demo.bean.ResourceFile;

import java.util.List;

public interface ResourceServiceImpl {

    int insertResource(ResourceFile resource);

    List<ResourceFile> selectResourceByKeyword(String keyword, Integer begin, Integer sheet);

    List<ResourceFile> selectResourceByTwoKeywords(String keyword1, String keyword2,Integer begin,Integer sheet);

    List<ResourceFile> selectResourceByLimit(String grade, String subject, String type, Integer begin, Integer sheet);
}
