/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.report.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.fz.report.entity.FzLoginReport;

/**
 * 用户登录记录报表DAO接口
 * @author len
 * @version 2018-10-19
 */
@MyBatisDao
public interface FzLoginReportDao extends CrudDao<FzLoginReport> {
	
}