/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.order.entity.AmOrderDetail;

import java.util.List;

/**
 * 订单管理DAO接口
 * @author czy
 * @version 2018-07-09
 */
@MyBatisDao
public interface AmOrderDetailDao extends CrudDao<AmOrderDetail> {
	int deleteDb(String documentCode);
	List<AmOrderDetail> getDetail(String documentCode);
}