package com.example.demo.service;

import com.example.demo.bean.ResourceFile;
import com.example.demo.dao.ResourceFileDao;
import com.example.demo.service.impl.ResourceServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("ResourceServiceImpl")
public class ResourceService implements ResourceServiceImpl {

    @Resource
    private ResourceFileDao resourceFileDao;

    @Override
    public int insertResource(ResourceFile resource) {
        int i = resourceFileDao.insertResource(resource);
        return i;
    }

    @Override
    public List<ResourceFile> selectResourceByKeyword(String keyword, Integer begin, Integer sheet) {
        System.out.println("收到关键---------"+keyword);
        List<ResourceFile> resourceFiles = resourceFileDao.selectResourceByKeyword(keyword, begin, sheet);
        return resourceFiles;
    }

    @Override
    public List<ResourceFile> selectResourceByTwoKeywords(String keyword1, String keyword2, Integer begin, Integer sheet) {
        List<ResourceFile> resourceFiles = resourceFileDao.selectResourceByTwoKeywords(keyword1, keyword2, begin, sheet);
        return resourceFiles;
    }

    @Override
    public List<ResourceFile> selectResourceByLimit(String grade, String subject, String type, Integer begin, Integer sheet) {
        List<ResourceFile> resourceFiles = resourceFileDao.selectResourceByLimit(grade, subject, type, begin, sheet);
        return resourceFiles;
    }
}
