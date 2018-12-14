/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.warehioreport.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;

import com.jeesite.modules.asset.warehioreport.entity.SummaryReport;

import java.util.Date;
import java.util.List;

/**
 * 耗材仓库进出明细表DAO接口
 * @author czy
 * @version 2018-05-26
 */
@MyBatisDao
public interface SummaryReportDao extends CrudDao<SummaryReport> {
   SummaryReport getCountByDate(Date time,String consumablesCode,String WarehouseCode);
	
}