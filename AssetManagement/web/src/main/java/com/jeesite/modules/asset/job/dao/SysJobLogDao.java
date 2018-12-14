/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.job.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.job.entity.SysJobLog;

/**
 * 定时任务调度日志表DAO接口
 * @author len
 * @version 2018-11-08
 */
@MyBatisDao
public interface SysJobLogDao extends CrudDao<SysJobLog> {
	
}