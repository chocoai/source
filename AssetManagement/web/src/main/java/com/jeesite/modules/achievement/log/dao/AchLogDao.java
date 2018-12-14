/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.log.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.achievement.log.entity.AchLog;

/**
 * 绩效卡操作日志DAO接口
 * @author Philip Guan
 * @version 2018-11-28
 */
@MyBatisDao
public interface AchLogDao extends CrudDao<AchLog> {
	
}