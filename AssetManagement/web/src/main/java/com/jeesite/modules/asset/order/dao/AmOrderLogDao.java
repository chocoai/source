/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.order.entity.AmOrderLog;

/**
 * 订单管理日志异常日志表DAO接口
 * @author len
 * @version 2018-11-12
 */
@MyBatisDao
public interface AmOrderLogDao extends CrudDao<AmOrderLog> {
	
}