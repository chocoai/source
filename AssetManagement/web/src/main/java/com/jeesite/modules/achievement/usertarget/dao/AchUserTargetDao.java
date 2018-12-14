/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.usertarget.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.achievement.usertarget.entity.AchUserTarget;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

/**
 * 绩效指标管理DAO接口
 * @author PhilipGuan
 * @version 2018-11-21
 */
@MyBatisDao
public interface AchUserTargetDao extends CrudDao<AchUserTarget> {
    @Insert("INSERT INTO ach_user_target \n" +
            "(user_target_code,card_code,user_id,\n" +
            "target_code,target_name,target_level,target_type,standard_score,description,formula,coefficient_range,bottom,basics_down,basic_aims,basics_upstream,challenge_goal,target_setting_content,data_sources\n" +
            ",score_group,is_achievement\n" +
            ",score_coefficient,relevance,actual_score,examined_staff_remark,assessor_remark,create_date,create_by,audit_by,audit_date,`status`,`data_status`)\n" +
            "SELECT \n" +
            "UUID(),#{arg0},#{arg1},\n" +
            "target_code,target_name,target_level,target_type,standard_score,description,formula,coefficient_range,bottom,basics_down,basic_aims,basics_upstream,challenge_goal,target_setting_content,data_sources\n" +
            ",score_group,is_achievement\n" +
            ",score_coefficient,relevance,0,NULL,NULL,NOW(),#{arg2},NULL,NULL,0,0\n" +
            "FROM ach_target WHERE target_level = #{arg3} ")    //AND (#{arg2} IS NULL OR card_code = #{arg2})
    void createDefaultData(String cardCode, String staffCode, String userName, String targetLevel);

    @Delete("DELETE FROM ach_user_target WHERE user_target_code = #{arg0} AND user_id = #{arg1}")
    int deleteData(String userTargetCode, String userId);

    @Update("UPDATE ach_user_target SET `data_status` = #{arg0}, `update_by` = #{arg1}, `update_date` = NOW() WHERE `card_code` = #{arg2} AND `user_id` = #{arg3}  ")
    int updateStatus(String dataStatus, String updateBy, String cardCode, String userId);
}