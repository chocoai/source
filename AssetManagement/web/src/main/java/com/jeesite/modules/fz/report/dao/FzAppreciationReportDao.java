/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.report.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.fz.report.entity.FzAppreciationReport;

/**
 * 赠赞数量统计表DAO接口
 * @author scarlett
 * @version 2018-10-22
 */
@MyBatisDao
public interface FzAppreciationReportDao extends CrudDao<FzAppreciationReport> {
	
}