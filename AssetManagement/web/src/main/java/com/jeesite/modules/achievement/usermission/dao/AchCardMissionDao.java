/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.usermission.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.achievement.usermission.entity.AchCardMission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;

/**
 * 绩效卡关键任务DAO接口
 * @author PhilipGuan
 * @version 2018-11-21
 */
@MyBatisDao
public interface AchCardMissionDao extends CrudDao<AchCardMission> {
    @Delete("DELETE FROM ach_card_mission WHERE mission_code = #{arg0} AND user_id = #{arg1}")
    int deleteData(String missionCode, String userId);

    @Update("UPDATE ach_card_mission SET `data_status` = #{arg0}, `update_by` = #{arg1}, `update_date` = NOW() WHERE `card_code` = #{arg2} AND `user_id` = #{arg3}  ")
    int updateStatus(String dataStatus, String updateBy, String cardCode, String userId);
}