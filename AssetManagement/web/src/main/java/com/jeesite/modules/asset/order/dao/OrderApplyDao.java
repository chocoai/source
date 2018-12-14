/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.order.entity.AmOrder;
import com.jeesite.modules.asset.order.entity.OrderApply;
import com.jeesite.modules.sys.entity.EmpUser;
import com.jeesite.modules.sys.entity.Employee;
import com.jeesite.modules.sys.entity.User;

import java.util.List;

/**
 * 订单变更申请表DAO接口
 * @author czy
 * @version 2018-07-14
 */
@MyBatisDao
public interface OrderApplyDao extends CrudDao<OrderApply> {
    List<OrderApply> getSalesOrder (String salesOrder); // 根据销售订单查询是否有相同的销售订单
    int deleteDb(String documentCode);        // 根据编号删除订单
    List<User> getUser(String userName);
    List<AmOrder> getOrder(String userName);
    String getOffice(String loginCode);
}