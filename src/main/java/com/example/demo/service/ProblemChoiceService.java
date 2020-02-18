package com.example.demo.service;

import com.example.demo.bean.ProblemChoice;
import com.example.demo.dao.ProblemChoiceDao;
import com.example.demo.service.impl.ProblemChoiceServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("ProblemChoiceServiceImpl")
public class ProblemChoiceService implements ProblemChoiceServiceImpl {

    @Resource
    private ProblemChoiceDao problemChoiceDao;

    @Override
    public int insertProblemChoice(ProblemChoice problemChoice) {
        int problemId = problemChoiceDao.insertProblemChoice(problemChoice);
        return problemId;
    }

    @Override
    public List<ProblemChoice> selectProblemChoiceSheetByType(String type, int begin, int sheet) {

        List<ProblemChoice> problemChoices = problemChoiceDao.selectProblemChoiceSheetByType(type, begin, sheet);
        return problemChoices;
    }

    /**
     * 查询各类题型的数量
     * @param type
     * @return
     */
    @Override
    public int selectNumByType(String type) {
        int num = problemChoiceDao.selectNumByType(type);
        return num;
    }

    /**
     * 查询用户收藏的题型
     * @param userId
     * @param begin
     * @param sheet
     * @return
     */
    @Override
    public List<ProblemChoice> selectCollectChoice(Integer userId,Integer begin,Integer sheet) {
        List<ProblemChoice> problemChoices = problemChoiceDao.selectCollectChoice(userId,begin,sheet);
        return problemChoices;
    }


    /**
     * 获取用户收藏题的数量
     * @param userId
     * @return
     */
    @Override
    public int selectCollectionNumByUserId(Integer userId) {
        int num = problemChoiceDao.selectCollectionNumByUserId(userId);
        return num;
    }

    @Override
    public List<ProblemChoice> selectWritePeByUserId(Integer userId,Integer begin,Integer sheet) {
        List<ProblemChoice> problemChoices = problemChoiceDao.selectWritePeByUserId(userId, begin, sheet);
        return problemChoices;
    }

    @Override
    public int selectWritePeNumByUserId(Integer userId) {
        int num = problemChoiceDao.selectCollectionNumByUserId(userId);
        return num;
    }

    @Override
    public List<ProblemChoice> selectProblem() {
        List<ProblemChoice> problemChoices = problemChoiceDao.selectProblem();
        System.out.println(problemChoices);
        return problemChoices;
    }

    @Override
    public List<ProblemChoice> selectWritePe(Integer userId,Integer begin,Integer sheet) {
        List<ProblemChoice> problemChoices = problemChoiceDao.selectWritePe(userId, begin,sheet);
        return problemChoices;
    }

    @Override
    public void updateProblemChoiceIsFlag(ProblemChoice problemChoice) {
        problemChoiceDao.updateProblemChoiceIsFlag(problemChoice);
    }

    @Override
    public void updateProblemChoiceUserId(Integer id) {
        problemChoiceDao.updateProblemChoiceUserId(id);
    }

    @Override
    public void updapteProblemChoiceById(ProblemChoice problemChoice) {
        problemChoiceDao.updapteProblemChoiceById(problemChoice);
    }

    @Override
    public void deleteProblemChoiceById(Integer id) {
        problemChoiceDao.deleteProblemChoiceById(id);
    }

    @Override
    public ProblemChoice selectProblemChoiceById(Integer id){
        ProblemChoice problemChoice = problemChoiceDao.selectProblemChoiceById(id);
        return  problemChoice;
    }

    @Override
    public List<ProblemChoice> selectProblemChoiceByIsFlag(Integer isFlag, Integer begin, Integer sheet) {
        List<ProblemChoice> problemChoices = problemChoiceDao.selectProblemChoiceByIsFlag(isFlag, begin, sheet);
        return problemChoices;
    }

}
