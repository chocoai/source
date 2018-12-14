/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.report.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.fz.report.entity.FzPraiserReport;

/**
 * 获赞者获赞数统计表DAO接口
 * @author len
 * @version 2018-10-22
 */
@MyBatisDao
public interface FzPraiserReportDao extends CrudDao<FzPraiserReport> {
	
}