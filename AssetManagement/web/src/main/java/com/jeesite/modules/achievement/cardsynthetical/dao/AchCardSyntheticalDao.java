/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.cardsynthetical.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.achievement.cardsynthetical.entity.AchCardSynthetical;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

/**
 * 绩效卡综合管理DAO接口
 * @author Philip Guan
 * @version 2018-11-21
 */
@MyBatisDao
public interface AchCardSyntheticalDao extends CrudDao<AchCardSynthetical> {
    @Insert("INSERT INTO ach_card_synthetical (\n" +
            "card_synthetical_code,card_code,user_id,\n" +
            "synthetical_code,examine_type,ach_quota,examine_quota,\n" +
            "score_group,\n" +
            "standard_score,\n" +
            "high_score,\n" +
            "`status`,create_by,create_date,`data_status`)\n" +
            "SELECT \n" +
            "UUID(),#{arg0},#{arg1},\n" +
            "synthetical_code,examine_type,ach_quota,examine_quota,\n" +
            "score_group,\n" +
            "CASE #{arg2} WHEN '1' THEN manage_score ELSE no_manage_score END,\n" +
            "IFNULL(CASE #{arg2} WHEN '1' THEN manage_high_score ELSE no_manage_high_score END, 0),\n" +
            "0,create_by,NOW(),0\n" +
            "FROM ach_synthetical WHERE disable_status = '0'")    //AND (#{arg2} IS NULL OR card_code = #{arg2})
    void createDefaultData(String cardCode, String staffCode, String isManager);

    @Update("UPDATE ach_card_synthetical SET `data_status` = #{arg0}, `update_by` = #{arg1}, `update_date` = NOW() WHERE `card_code` = #{arg2} AND `user_id` = #{arg3}  ")
    int updateStatus(String dataStatus, String updateBy, String cardCode, String userId);
}