/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.cardscoremodify.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.achievement.cardscoremodify.entity.AchCardScoreModify;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 加减分管理DAO接口
 * @author PhilipGuan
 * @version 2018-11-22
 */
@MyBatisDao
public interface AchCardScoreModifyDao extends CrudDao<AchCardScoreModify> {
    @Delete("DELETE FROM ach_card_score_modify WHERE score_modify_code=#{arg0}")
    void deleteDb(String scoreModifyCode);

    @Select("SELECT a.* FROM ach_card_score_modify a JOIN ach_card b on a.examine_month = b.examine_month AND A.user_id = B.examined_staff_code WHERE a.user_id = #{arg0} AND a.examine_month = #{arg1} AND (b.data_status = '60' OR b.data_status = '100') AND a.data_type = '2'")
    List<AchCardScoreModify> getMyList(String userId, String examineMonth);

    @Select("SELECT * FROM ach_card_score_modify limit 1")
    List<AchCardScoreModify> selectTemp();

    @Update("UPDATE ach_card_score_modify SET `data_status` = #{arg0}, `update_by` = #{arg1}, `update_date` = NOW() WHERE `card_code` = #{arg2} AND `user_id` = #{arg3}  ")
    int updateStatus(String dataStatus, String updateBy, String cardCode, String userId);
}