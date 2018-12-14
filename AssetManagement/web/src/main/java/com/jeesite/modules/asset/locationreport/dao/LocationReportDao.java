/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.locationreport.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.locationreport.entity.LocationReport;

/**
 * 库位管理DAO接口
 * @author Mclaran
 * @version 2018-05-07
 */
@MyBatisDao
public interface LocationReportDao extends CrudDao<LocationReport> {
	
}