/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.consumables.entity.Consumables;

import java.util.List;

/**
 * 耗材档案DAO接口
 * @author dwh
 * @version 2018-04-23
 */
@MyBatisDao
public interface ConsumablesDao extends CrudDao<Consumables> {

    int deleteOne(String consumables_code);
}