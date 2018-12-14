/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.tianmao.entity.SpWarehouse;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 仓库中商品列表DAO接口
 * @author dwh
 * @version 2018-08-18
 */
@MyBatisDao
public interface SpWarehouseDao extends CrudDao<SpWarehouse> {
    @Select("select item_id from js_sp_warehouse ")
    List<String> getListItemId();
}