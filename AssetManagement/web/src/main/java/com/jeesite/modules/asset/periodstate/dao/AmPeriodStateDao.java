/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.periodstate.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.periodstate.entity.AmPeriodState;

/**
 * 数据期间表DAO接口
 * @author dwh
 * @version 2018-05-12
 */
@MyBatisDao
public interface AmPeriodStateDao extends CrudDao<AmPeriodState> {
    int deleteDb(String periodStateCode);
}