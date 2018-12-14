/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.card.dao;


import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.achievement.card.entity.AchCard;
import com.jeesite.modules.achievement.card.entity.AchCardData;
import com.jeesite.modules.achievement.card.entity.AchCardGroupData;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * 绩效卡DAO接口
 * @author Philip Guan
 * @version 2018-11-19
 */
@MyBatisDao
public interface AchCardDao extends CrudDao<AchCard> {
    @Select("SELECT A.*,B.`name`,B.`avatar`,B.`extattr`,C.`name` as assessor_name \n" +
            "FROM ach_card A\n" +
            "LEFT JOIN js_ding_user B ON A.examined_staff_code = B.userid\n" +
            "LEFT JOIN js_ding_user C ON A.assessor_code = C.userid\n" +
            "WHERE examine_month = #{arg0}\n" +
            "AND examined_staff_code = #{arg1}\n" +
            "LIMIT 0,1")    //AND (#{arg2} IS NULL OR card_code = #{arg2})
    AchCardData getData(String month, String staffCode);

    @Select("SELECT A.*,B.`name`,B.`avatar`,B.`extattr`,C.`name` as assessor_name \n" +
            "FROM ach_card A\n" +
            "LEFT JOIN js_ding_user B ON A.examined_staff_code = B.userid\n" +
            "LEFT JOIN js_ding_user C ON A.assessor_code = C.userid\n" +
            "WHERE card_code = #{arg0}\n" +
            "LIMIT 0,1")    //AND (#{arg2} IS NULL OR card_code = #{arg2})
    AchCardData getDataById(String cardCode);

    @Update("UPDATE ach_card SET `data_status` = #{arg0}, `update_by` = #{arg1}, `update_date` = NOW() WHERE `card_code` = #{arg2} AND `examined_staff_code` = #{arg3}  ")
    int updateStatus(String dataStatus, String updateBy, String cardCode, String userId);

    @Select("SELECT a.userid as userId, a.`name` AS userName,\n" +
            "CASE c.data_status WHEN '100' THEN IFNULL(SUM(e.add_sub_score),0) ELSE IFNULL(#{canShowAddScore},IFNULL(SUM(e.add_sub_score),0)) END AS `addSubScore`,\n" +
            "IFNULL(SUM(c.examine_score),0) AS examineScore,\n" +
            "#{month} AS examineMonth, a.avatar, f.name AS departmentName, a.position, a.jobnumber,\n" +
            "IFNULL(c.card_code,'') AS cardCode,IFNULL(c.data_status,'-2') AS dataStatus,\n" +
            "Max(case d.examine_name when 'KPI指标' then IFNULL(d.examine_score,0) else 0 end ) AS target,\n" +
            "Max(case d.examine_name when 'KPI任务' then IFNULL(d.examine_score,0)else 0 end) AS mission,\n" +
            "Max(case d.examine_name when '职场基础素养' then IFNULL(d.examine_score,0) ELSE 0 END) AS synthetical,\n" +
            "MAX(case d.examine_name when '价值观' then IFNULL(d.examine_score,0) else 0 end) AS senseWorth\n" +
            "FROM js_ding_user a\n" +
            "INNER JOIN js_ding_user_department b on a.userid = b.user_id\n" +
            "INNER JOIN js_ding_department f on b.department_id = f.department_id\n" +
            "LEFT JOIN ach_card c on c.examined_staff_code = a.userid AND c.examine_month = #{month}\n" +
            "LEFT JOIN ach_card_score d on c.card_code = d.card_code\n" +
            "LEFT JOIN ach_card_score_modify e on e.examine_month = c.examine_month and e.user_id = a.userid\n" +
            "WHERE A.`left` = '0' ${myDeptIds} AND a.userid NOT IN (${expUserIds})\n" +
            "AND (#{userId} IS NULL OR a.direct_superior = #{userId}) AND a.jobnumber <> ''\n" +
            "AND (#{dataStatus} IS NULL OR c.data_status = #{dataStatus})\n" +
            "AND (#{userName} IS NULL OR a.name like #{userName})\n" +
            "GROUP BY a.userid,a.`name`\n" +
            "ORDER BY a.jobnumber LIMIT ${startIndex},${endIndex}")
    List<AchCardGroupData> getGroupData(@Param("month")String month, @Param("userId")String userId, @Param("userName")String userName, @Param("dataStatus") String dataStatus, @Param("myDeptIds")String myDeptIds, @Param("expUserIds")String expUserIds,@Param("canShowAddScore")String canShowAddScore, @Param("startIndex")int startIndex, @Param("endIndex")int endIndex);

    @Select("SELECT COUNT(*) FROM (SELECT a.userid as userId, a.`name` AS userName,IFNULL(SUM(e.add_sub_score),0) AS `addSubScore`,IFNULL(SUM(c.examine_score),0) AS examineScore,\n" +
            "IFNULL(c.card_code,'') AS cardCode,IFNULL(c.data_status,'-2') AS dataStatus,\n" +
            "Max(case d.examine_name when 'KPI指标' then IFNULL(d.examine_score,0) else 0 end ) AS target,\n" +
            "Max(case d.examine_name when 'KPI任务' then IFNULL(d.examine_score,0)else 0 end) AS mission,\n" +
            "Max(case d.examine_name when '职场基础素养' then IFNULL(d.examine_score,0) ELSE 0 END) AS synthetical,\n" +
            "MAX(case d.examine_name when '价值观' then IFNULL(d.examine_score,0) else 0 end) AS senseWorth\n" +
            "FROM js_ding_user a\n" +
            "INNER JOIN js_ding_user_department b on a.userid = b.user_id\n" +
            "LEFT JOIN ach_card c on c.examined_staff_code = a.userid AND c.examine_month = #{month}\n" +
            "LEFT JOIN ach_card_score d on c.card_code = d.card_code\n" +
            "LEFT JOIN ach_card_score_modify e on e.examine_month = c.examine_month and e.user_id = a.userid\n" +
            "WHERE A.`left` = '0' ${myDeptIds} AND a.userid NOT IN (${expUserIds})\n" +
            "AND (#{userId} IS NULL OR a.direct_superior = #{userId}) AND a.jobnumber <> ''\n" +
            "AND (#{dataStatus} IS NULL OR c.data_status = #{dataStatus})\n" +
            "AND (#{userName} IS NULL OR a.name like #{userName})\n" +
            "GROUP BY a.userid,a.`name`\n" +
            "ORDER BY a.jobnumber) T")
    long getGroupDataCount(@Param("month")String month, @Param("userId")String userId, @Param("userName")String userName, @Param("dataStatus") String dataStatus, @Param("myDeptIds")String myDeptIds, @Param("expUserIds")String expUserIds);

    @Update("UPDATE ${table} SET data_status = #{newStatus}\n" +
            "WHERE card_code IN (\n" +
            "SELECT C.card_code FROM js_ding_user A\n" +
            "LEFT JOIN ach_card C ON c.examined_staff_code = a.userid AND c.examine_month = #{month}\n" +
            "LEFT JOIN js_ding_user_department b on b.user_id = a.userid\n" +
            "WHERE A.`left` = '0' ${myDeptIds} AND a.userid NOT IN (${expUserIds})\n" +
            "AND (#{userId} IS NULL OR a.direct_superior = #{userId}) AND a.jobnumber <> ''\n" +
            "AND (#{dataStatus} IS NULL OR c.data_status = #{dataStatus})\n" +
            "AND (#{userName} IS NULL OR a.name like #{userName})\n" +
            "AND C.card_code IS NOT NULL AND data_status = #{oldStatus})")
    int batchUpdateStatus(@Param("table")String table, @Param("month")String month, @Param("userId")String userId, @Param("userName")String userName, @Param("dataStatus") String dataStatus, @Param("myDeptIds")String myDeptIds, @Param("expUserIds")String expUserIds, @Param("newStatus")String newStatus, @Param
            ("oldStatus")String oldStatus);

    @Update("UPDATE ${table} SET data_status = #{newStatus}\n" +
            "WHERE card_code IN (\n" +
            "SELECT C.card_code FROM js_ding_user A\n" +
            "LEFT JOIN ach_card C ON c.examined_staff_code = a.userid AND c.examine_month = #{month}\n" +
            "LEFT JOIN js_ding_user_department b on b.user_id = a.userid\n" +
            "WHERE A.`left` = '0' ${myDeptIds} AND a.userid NOT IN (${expUserIds})\n" +
            "AND (#{userId} IS NULL OR a.direct_superior = #{userId}) AND a.jobnumber <> ''\n" +
            //"AND (#{dataStatus} IS NULL OR c.data_status = #{dataStatus})\n" +
            "AND (#{userName} IS NULL OR a.name like #{userName})\n" +
            "AND C.card_code IS NOT NULL AND C.data_status = #{oldStatus})")
    int batchUpdateCardStatus(@Param("table")String table, @Param("month")String month, @Param("userId")String userId, @Param("userName")String userName, @Param("dataStatus") String dataStatus, @Param("myDeptIds") String myDeptIds, @Param("expUserIds")String expUserIds, @Param("newStatus")String newStatus, @Param("oldStatus")String oldStatus);

    /**
     * HR>0->
     * @return
     */
    @Update("UPDATE ach_card C\n" +
            "LEFT JOIN (SELECT card_code,SUM(IFNULL(examine_score,0)) examine_score FROM ach_card_score GROUP BY card_code) D ON C.card_code = D.card_code\n" +
            "LEFT JOIN js_ding_user A ON A.userid = C.examined_staff_code\n" +
            "LEFT JOIN js_ding_user_department B on B.user_id = C.examined_staff_code\n" +
            "SET C.final_score = (\n" +
            " CASE WHEN C.hr_score > 0 THEN \n" +
            "  (\n" +
            "   CASE WHEN C.second_superior_score > 0 THEN (C.second_superior_score * C.hr_score)\n" +
            "   ELSE C.examine_score * C.hr_score END\n" +
            "  )\n" +
            " WHEN C.second_superior_score > 0 THEN (C.second_superior_score)\n" +
            " ELSE D.examine_score END\n" +
            ")\n" +
            "WHERE A.`left` = '0' ${myDeptIds} AND C.examined_staff_code NOT IN (${expUserIds})\n" +
            "AND (#{userId} IS NULL OR A.direct_superior = #{userId}) AND A.jobnumber <> ''\n" +
            "AND (#{dataStatus} IS NULL OR C.data_status = #{dataStatus})\n" +
            "AND (#{userName} IS NULL OR A.name like #{userName})\n" +
            "AND C.card_code IS NOT NULL")
    int batchUpdateFinalScore(@Param("month")String month, @Param("userId")String userId, @Param("userName")String userName, @Param("dataStatus") String dataStatus, @Param("myDeptIds") String myDeptIds, @Param("expUserIds")String expUserIds);


    @Update("UPDATE ach_card A\n" +
            "#SELECT B.* FROM ach_card A\n" +
            "LEFT JOIN (SELECT card_code,SUM(IFNULL(examine_score,0)) examine_score FROM ach_card_score GROUP BY card_code) B ON A.card_code = B.card_code\n" +
            "SET A.final_score = (\n" +
            "\tCASE WHEN A.hr_score > 0 THEN \n" +
            "\t\t(\n" +
            "\t\t\tCASE WHEN A.second_superior_score > 0 THEN (A.second_superior_score * A.hr_score)\n" +
            "\t\t\tELSE A.examine_score * A.hr_score END\n" +
            "\t\t)\n" +
            "\tWHEN A.second_superior_score > 0 THEN (A.second_superior_score)\n" +
            "\tELSE B.examine_score END\n" +
            ")\n" +
            "WHERE A.card_code = #{arg0}")
    int updateFinalScore(String cardCode);
}