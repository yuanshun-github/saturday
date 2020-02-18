package com.example.demo.service;

import com.example.demo.bean.PeSituation;
import com.example.demo.dao.PeSituationDao;
import com.example.demo.service.impl.PeSituationServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("PeSituationServiceImpl")
public class PeSituationService implements PeSituationServiceImpl {

    @Resource
    private PeSituationDao peSituationDao;

    @Override
    public PeSituation selectPeSituationByUserId(int userId) {
        PeSituation peSituation = peSituationDao.selectSituationByUserId(userId);
        if(peSituation == null){
            peSituation = new PeSituation();
            peSituation.setUserId(userId);
            peSituation.setYyljybd(0);
            peSituation.setCspd(0);
            peSituation.setPdtl(0);
            peSituation.setSlgx(0);
            peSituationDao.insertSituation(peSituation);
        }
        System.out.println("公考做题情况:"+peSituation);
        return peSituation;
    }

    @Override
    public void insertSituation(PeSituation peSituation) {
        peSituationDao.insertSituation(peSituation);
    }

    @Override
    public int updateSituationByUserId(PeSituation peSituation) {
        int userId = peSituationDao.updateSituationByUserId(peSituation);
        return userId;
    }
}
