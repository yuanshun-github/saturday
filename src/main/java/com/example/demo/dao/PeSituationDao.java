package com.example.demo.dao;

import com.example.demo.bean.PeSituation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PeSituationDao {

    PeSituation selectSituationByUserId(int userId);

    void insertSituation(PeSituation peSituation);

    int updateSituationByUserId(PeSituation peSituation);
}
