/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.order.entity.AmOrderDiscount;

/**
 * 订单管理DAO接口
 * @author czy
 * @version 2018-07-09
 */
@MyBatisDao
public interface AmOrderDiscountDao extends CrudDao<AmOrderDiscount> {
    int deleteDiscount(String documentCode);            // 根据单据号删除数据
}