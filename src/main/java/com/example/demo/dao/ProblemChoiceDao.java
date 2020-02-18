package com.example.demo.dao;

import com.example.demo.bean.ProblemChoice;

import java.util.List;

public interface ProblemChoiceDao {

    int insertProblemChoice(ProblemChoice problemChoice);

    List<ProblemChoice> selectProblemChoiceSheetByType(String type,int begin,int sheet);

    int selectNumByType(String type);

    List<ProblemChoice> selectCollectChoice(Integer userId,Integer begin,Integer sheet);

    int selectCollectionNumByUserId(Integer userId);

    List<ProblemChoice> selectWritePeByUserId(Integer userId,Integer begin,Integer sheet);

    int selectWritePeNumByUserId(Integer userId);

    List<ProblemChoice> selectProblem();

    List<ProblemChoice> selectWritePe(Integer userId,Integer begin,Integer sheet);

    void updateProblemChoiceIsFlag(ProblemChoice problemChoice);

    void updateProblemChoiceUserId(Integer id);

    void updapteProblemChoiceById(ProblemChoice problemChoice);

    void deleteProblemChoiceById(Integer id);

    ProblemChoice selectProblemChoiceById(Integer id);

    List<ProblemChoice> selectProblemChoiceByIsFlag(Integer isFlag,Integer begin,Integer sheet);
}
