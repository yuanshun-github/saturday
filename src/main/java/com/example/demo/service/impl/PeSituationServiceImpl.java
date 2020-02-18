package com.example.demo.service.impl;

import com.example.demo.bean.PeSituation;

public interface PeSituationServiceImpl {

    PeSituation selectPeSituationByUserId(int userId);

    void insertSituation(PeSituation peSituation);

    int updateSituationByUserId(PeSituation peSituation);
}
