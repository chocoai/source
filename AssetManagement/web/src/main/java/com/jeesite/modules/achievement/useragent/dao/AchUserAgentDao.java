/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.useragent.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.achievement.useragent.entity.AchUserAgent;
import org.apache.ibatis.annotations.Delete;

/**
 * 绩效卡用户代理DAO接口
 * @author Philip Guan
 * @version 2018-11-23
 */
@MyBatisDao
public interface AchUserAgentDao extends CrudDao<AchUserAgent> {
    @Delete("DELETE FROM ach_user_agent WHERE `user_id` = #{arg2}")
    int delete(String userId);
}