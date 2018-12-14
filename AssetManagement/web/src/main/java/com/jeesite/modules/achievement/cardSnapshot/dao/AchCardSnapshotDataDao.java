/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.cardSnapshot.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.achievement.cardSnapshot.entity.AchCardSnapshotData;

/**
 * 绩效卡快照DAO接口
 * @author Philip Guan
 * @version 2018-12-04
 */
@MyBatisDao
public interface AchCardSnapshotDataDao extends CrudDao<AchCardSnapshotData> {
	
}