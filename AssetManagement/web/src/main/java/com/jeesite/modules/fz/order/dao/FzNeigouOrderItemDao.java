/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.order.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.fz.order.entity.FzNeigouOrderItem;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 梵赞内购订单商品明细表DAO接口
 * @author easter
 * @version 2018-12-12
 */
@MyBatisDao
public interface FzNeigouOrderItemDao extends CrudDao<FzNeigouOrderItem> {

    void updateBatch(List<FzNeigouOrderItem> list);

    @Select("SELECT * FROM fz_neigou_order_item a WHERE a.`order_id`= #{arg0}")
    List<FzNeigouOrderItem> getFzNeigouOrderItemByOrderId(String order_id);
}