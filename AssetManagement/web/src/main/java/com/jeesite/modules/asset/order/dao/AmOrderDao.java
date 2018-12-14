/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.order.entity.AmOrder;
import com.jeesite.modules.sys.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 订单管理DAO接口
 * @author czy
 * @version 2018-07-09
 */
@MyBatisDao
public interface AmOrderDao extends CrudDao<AmOrder> {
    List<User> getUser(String userName);
    List<AmOrder> getOrder(String userName);
    String getOffice(String loginCode);

    int deleteOrder(String documentCode);   // 删除订单

    List<AmOrder> getGuideOrder(String userName);

    /**
     * 根据特权定金单号查询订单
     * @param set
     * @return
     */
    List<AmOrder> selectByPrivilege(@Param("set") Set<String> set);
}