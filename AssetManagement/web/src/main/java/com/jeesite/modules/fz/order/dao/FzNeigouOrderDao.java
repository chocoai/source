/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.order.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.fz.order.entity.FzNeigouOrder;

import java.util.List;

/**
 * 梵赞兑换订单表DAO接口
 * @author easter
 * @version 2018-11-26
 */
@MyBatisDao
public interface FzNeigouOrderDao extends CrudDao<FzNeigouOrder> {

    void updateBatch(List<FzNeigouOrder> list);
}