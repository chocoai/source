/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.order.entity.StatisticalReport;

/**
 * 订单业绩统计DAO接口
 * @author len
 * @version 2018-12-13
 */
@MyBatisDao
public interface StatisticalReportDao extends CrudDao<StatisticalReport> {
	
}