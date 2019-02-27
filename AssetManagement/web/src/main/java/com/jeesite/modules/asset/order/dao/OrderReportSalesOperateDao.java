/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.order.entity.OrderReportSalesOperate;

/**
 * 梵导购操作统计报表DAO接口
 * @author Albert
 * @version 2019-01-11
 */
@MyBatisDao
public interface OrderReportSalesOperateDao extends CrudDao<OrderReportSalesOperate> {
	
}