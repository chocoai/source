/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.warehioreport.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.warehioreport.entity.WarehIoReport;

/**
 * 耗材仓库进出明细表DAO接口
 * @author czy
 * @version 2018-05-25
 */
@MyBatisDao
public interface WarehIoReportDao extends CrudDao<WarehIoReport> {
    WarehIoReport findSum(WarehIoReport warehIoReport);   // 合计取得
}