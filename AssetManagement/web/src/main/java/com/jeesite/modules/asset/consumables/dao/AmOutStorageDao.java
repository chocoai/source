/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.consumables.entity.AmOutStorage;
import com.jeesite.modules.asset.periodstate.entity.AmPeriodState;

/**
 * 耗材出库表DAO接口
 * @author czy
 * @version 2018-05-07
 */
@MyBatisDao
public interface AmOutStorageDao extends CrudDao<AmOutStorage> {
	String findWarehouseName(String warehouseCode);  //查询仓库名称
	int delStorage(String outStorageCode);			// 删除出库单数据

}