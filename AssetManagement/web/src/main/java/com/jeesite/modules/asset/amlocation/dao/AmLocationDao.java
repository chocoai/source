/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.amlocation.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.amlocation.entity.AmLocation;

/**
 * 库位管理DAO接口
 * @author Mclaran
 * @version 2018-05-03
 */
@MyBatisDao
public interface AmLocationDao extends CrudDao<AmLocation> {
	int deleteDb(String locationCode);  // 删除数据
}