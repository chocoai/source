/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.distribution.order.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.distribution.order.entity.DistrOrderDetail;

import java.util.List;

/**
 * 分销订单DAO接口
 * @author len
 * @version 2019-01-07
 */
@MyBatisDao
public interface DistrOrderDetailDao extends CrudDao<DistrOrderDetail> {
    /**
     * 根据订单号查询明细信息
     * @param orderList
     * @return
     */
    List<DistrOrderDetail> selectById(List<String> orderList);
}