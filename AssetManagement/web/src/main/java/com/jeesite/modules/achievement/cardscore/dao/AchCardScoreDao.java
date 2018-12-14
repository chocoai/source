/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.cardscore.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.achievement.cardscore.entity.AchCardScore;
import org.apache.ibatis.annotations.Update;

/**
 * 绩效卡评分DAO接口
 * @author Philip Guan
 * @version 2018-11-22
 */
@MyBatisDao
public interface AchCardScoreDao extends CrudDao<AchCardScore> {
    @Update("UPDATE ach_card_score as A\n" +
            "LEFT JOIN(\n" +
            "SELECT card_code,left(score_group,4) score_group,SUM(IFNULL(actual_score,0)) examine_score, SUM(IFNULL(standard_score,0)) standard_score FROM ach_user_target GROUP BY card_code,left(score_group,4)\n" +
            ") B ON a.card_code = B.card_code and B.score_group = a.score_group\n" +
            "SET A.examine_score = B.examine_score, A.standard_score = B.standard_score\n" +
            "WHERE a.card_code = #{arg0} AND B.card_code IS NOT NULL AND (B.examine_score > 0 OR B.standard_score > 0)")
    int updateTargerScore(String cardCode);
    @Update("UPDATE ach_card_score as A\n" +
            "LEFT JOIN(\n" +
            "SELECT card_code,left(score_group,4) score_group,\n" +
            "SUM(IFNULL(standard_score,0)) standard_score,\n" +
            "SUM(IFNULL(examine_evaluation_score,0)) examine_score,\n" +
            "SUM(IFNULL(self_evaluation_score,0)) self_evaluation_score\n" +
            "FROM ach_card_mission GROUP BY card_code,left(score_group,4)\n" +
            ") B ON a.card_code = B.card_code and B.score_group = a.score_group\n" +
            "SET A.standard_score = B.standard_score, A.examine_score = B.examine_score,a.evaluation_score = B.self_evaluation_score\n" +
            "WHERE a.card_code = #{arg0} AND B.card_code IS NOT NULL AND (B.examine_score > 0 OR B.self_evaluation_score > 0 OR B.standard_score > 0)")
    int updateMissionScore(String cardCode);
    @Update("UPDATE ach_card_score as A\n" +
            "LEFT JOIN(\n" +
            "SELECT card_code,left(score_group,4) score_group,\n" +
            "SUM(IFNULL(standard_score,0)) standard_score,\n" +
            "SUM(IFNULL(examine_evaluation_score,0)) examine_score,\n" +
            "SUM(IFNULL(self_evaluation_score,0)) self_evaluation_score\n" +
            "FROM ach_card_synthetical GROUP BY card_code,left(score_group,4)\n" +
            ") B ON a.card_code = B.card_code and B.score_group = a.score_group\n" +
            "SET A.standard_score = B.standard_score, a.examine_score = B.examine_score,a.evaluation_score = B.self_evaluation_score\n" +
            "WHERE a.card_code = #{arg0} AND B.card_code IS NOT NULL AND (B.examine_score > 0 OR B.self_evaluation_score > 0 OR B.standard_score > 0)")
    int updateSyntheticalScore(String cardCode);

    @Update("UPDATE ach_card_score A\n" +
            "JOIN ach_card C ON A.card_code = C.card_code\n" +
            "LEFT JOIN(\n" +
            "SELECT examine_month, user_id,\n" +
            "SUM(IFNULL(add_sub_score,0)) examine_score\n" +
            "FROM ach_card_score_modify WHERE add_sub_score > 0 AND data_type = '2' GROUP BY examine_month,user_id\n" +
            ") B ON C.examine_month = B.examine_month AND A.user_id = B.user_id\n" +
            "SET A.examine_score = B.examine_score\n" +
            "WHERE ((#{arg0} IS NULL OR C.card_code = #{arg0}) AND C.examine_month = #{arg1}) AND A.score_group = #{arg2}")
    int updateScoreModifyScore(String cardCode, String month, String scoreGroup);



    @Update("UPDATE ach_card_score A\n" +
            "JOIN ach_card C ON A.card_code = C.card_code\n" +
            "LEFT JOIN(\n" +
            "SELECT examine_month, user_id,\n" +
            "SUM(IFNULL(add_sub_score,0)) examine_score\n" +
            "FROM ach_card_score_modify WHERE add_sub_score > 0 AND data_type = '2' GROUP BY examine_month,user_id\n" +
            ") B ON C.examine_month = B.examine_month AND A.user_id = B.user_id\n" +
            "SET A.examine_score = 0\n" +
            "WHERE ((#{arg0} IS NULL OR C.card_code = #{arg0}) AND C.examine_month = #{arg1}) AND A.score_group = #{arg2}")
    int clearScoreModifyScore(String cardCode, String month, String scoreGroup);

    @Update("UPDATE ach_card_score as a\n" +
            "LEFT JOIN(\n" +
            "SELECT card_code,score_group,\n" +
            "SUM(IFNULL(actual_score,0)) examine_score\n" +
            "FROM ach_user_target GROUP BY card_code,score_group\n" +
            ") B ON a.card_code = B.card_code and instr(B.score_group,a.score_group) = 1\n" +
            "LEFT JOIN(\n" +
            "SELECT card_code,score_group,\n" +
            "SUM(IFNULL(examine_evaluation_score,0)) examine_score,\n" +
            "SUM(IFNULL(self_evaluation_score,0)) self_evaluation_score\n" +
            "FROM ach_card_mission GROUP BY card_code,score_group\n" +
            ") C ON a.card_code = C.card_code and instr(C.score_group,a.score_group) = 1\n" +
            "LEFT JOIN(\n" +
            "SELECT card_code,score_group,\n" +
            "SUM(IFNULL(examine_evaluation_score,0)) examine_score,\n" +
            "SUM(IFNULL(self_evaluation_score,0)) self_evaluation_score\n" +
            "FROM ach_card_synthetical GROUP BY card_code,score_group\n" +
            ") D ON a.card_code = D.card_code and instr(D.score_group,a.score_group) = 1\n" +
            "SET a.examine_score = IFNULL(B.examine_score,IFNULL(C.examine_score,IFNULL(D.examine_score,0)))\n" +
            ",a.evaluation_score = IFNULL(C.self_evaluation_score,IFNULL(D.self_evaluation_score,0))\n" +
            "WHERE a.card_code = #{arg0} AND (\n" +
            "(B.card_code IS NOT NULL AND B.examine_score > 0) \n" +
            "OR (C.card_code IS NOT NULL AND (C.examine_score > 0 OR C.self_evaluation_score > 0))\n" +
            "OR (D.card_code IS NOT NULL AND (D.examine_score > 0 OR D.self_evaluation_score > 0)))")
    int updateAllScore(String cardCode);


}