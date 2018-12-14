/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.consumables.entity.AmConStock;

/**
 * 耗材库存表DAO接口
 * @author Mclaran
 * @version 2018-05-05
 */
@MyBatisDao
public interface AmConStockDao extends CrudDao<AmConStock> {
	
}