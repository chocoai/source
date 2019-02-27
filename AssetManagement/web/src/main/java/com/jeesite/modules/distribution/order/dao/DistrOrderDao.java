/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.distribution.order.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.distribution.order.entity.DistrOrder;
import org.apache.ibatis.annotations.Update;

/**
 * 分销订单DAO接口
 * @author len
 * @version 2019-01-07
 */
@MyBatisDao
public interface DistrOrderDao extends CrudDao<DistrOrder> {

    /**
     * 根据订单号更新订单状态 清空确认信息
     * @param documentCode
     */
    @Update("UPDATE distr_order SET document_status = '创建',confirm_by=null,confirm_date=null,confirm_code=null WHERE document_code=#{arg0}")
    void cancleConform (String documentCode);
}