/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.distribution.pricelog.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.distribution.pricelog.entity.DistrPriceLog;

/**
 * 分销价修改日志DAO接口
 * @author len
 * @version 2019-01-08
 */
@MyBatisDao
public interface DistrPriceLogDao extends CrudDao<DistrPriceLog> {
	
}