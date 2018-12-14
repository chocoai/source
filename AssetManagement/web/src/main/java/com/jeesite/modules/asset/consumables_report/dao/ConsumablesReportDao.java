/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables_report.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.common.validator.OptimisticLock;
import com.jeesite.modules.asset.consumables_report.entity.ConsumablesReport;

import java.util.List;

/**
 * consumables_reportDAO接口
 * @author Mclaran
 * @version 2018-05-03
 */
@MyBatisDao
public interface ConsumablesReportDao extends CrudDao<ConsumablesReport> {

/*   List findList();
   Integer findCount();*/
}