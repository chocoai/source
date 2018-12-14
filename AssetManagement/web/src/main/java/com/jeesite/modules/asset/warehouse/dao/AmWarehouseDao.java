/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.warehouse.dao;

import com.jeesite.common.dao.TreeDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.warehouse.entity.AmWarehouse;

import java.util.List;

/**
 * 仓库DAO接口
 * @author dwh
 * @version 2018-04-27
 */
@MyBatisDao
public interface AmWarehouseDao extends TreeDao<AmWarehouse> {

    List<AmWarehouse> getWarehouseListByLeaf(String treeLeaf);
    int deleteDb(String warehouseCode);   // 删除数据
}