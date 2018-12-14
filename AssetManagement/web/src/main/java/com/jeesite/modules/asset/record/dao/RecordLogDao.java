/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.record.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.record.entity.RecordLog;

/**
 * 日志管理DAO接口
 * @author scarlett
 * @version 2018-09-17
 */
@MyBatisDao
public interface RecordLogDao extends CrudDao<RecordLog> {
	
}