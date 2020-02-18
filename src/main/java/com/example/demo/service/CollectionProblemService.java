package com.example.demo.service;

import com.example.demo.bean.CollectionProblem;
import com.example.demo.dao.CollectionProblemDao;
import com.example.demo.service.impl.CollectionProblemImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("CollectionProblemImpl")
public class CollectionProblemService implements CollectionProblemImpl {

    @Resource
    private CollectionProblemDao collectionProblemDao;


    @Override
    public int insertCollectionProblem(CollectionProblem collectionProblem) {
        int i = collectionProblemDao.insertCollectionProblem(collectionProblem);
        return i;
    }

    @Override
    public void updateCollectionProblem(CollectionProblem collectionProblem) {
        collectionProblemDao.updateCollectionProblem(collectionProblem);
    }

    @Override
    public void deleteCollectionProblem(CollectionProblem collectionProblem) {
        collectionProblemDao.deleteCollectionProblem(collectionProblem);
    }
}
