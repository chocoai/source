/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.consumables.entity.AmConLocationStock;

/**
 * 库位库存表DAO接口
 * @author dwh
 * @version 2018-05-08
 */
@MyBatisDao
public interface AmConLocationStockDao extends CrudDao<AmConLocationStock> {
    String findLocationName(String locationCode);
}