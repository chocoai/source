/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.neigou.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.fz.neigou.entity.FzNeigouRefund;
import org.apache.ibatis.annotations.Select;

import java.sql.SQLException;

/**
 * 梵钻内购退货流水表DAO接口
 * @author easter
 * @version 2018-12-03
 */
@MyBatisDao
public interface FzNeigouRefundDao extends CrudDao<FzNeigouRefund> {

    @Select("SELECT * FROM fz_neigou_refund a WHERE a.refund_id = #{arg0};")
    public FzNeigouRefund findOne(String refund_id) throws SQLException;
}