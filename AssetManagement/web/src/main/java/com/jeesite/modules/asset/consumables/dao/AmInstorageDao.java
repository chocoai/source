/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.amlocation.entity.AmLocation;
import com.jeesite.modules.asset.consumables.entity.AmInstorage;
import com.jeesite.modules.asset.periodstate.entity.AmPeriodState;

import java.util.List;

/**
 * 入库表DAO接口
 * @author dwh
 * @version 2018-05-03
 */
@MyBatisDao
public interface AmInstorageDao extends CrudDao<AmInstorage> {
    AmInstorage findList();

    List<AmLocation> getAmLocationListByCode(String warehouseCode );
    int deleteDb(String instorageCode);
//    int updataFlag(String instorageCode);
}