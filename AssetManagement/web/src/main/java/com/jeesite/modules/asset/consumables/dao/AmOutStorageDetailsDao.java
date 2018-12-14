/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.consumables.entity.AmConStock;
import com.jeesite.modules.asset.consumables.entity.AmInstorageDetails;
import com.jeesite.modules.asset.consumables.entity.AmOutStorageDetails;

import java.util.List;

/**
 * 耗材出库表DAO接口
 * @author czy
 * @version 2018-05-07
 */
@MyBatisDao
public interface AmOutStorageDetailsDao extends CrudDao<AmOutStorageDetails> {
    List<AmOutStorageDetails> getDetailsByCategoryCode(String categoryCode, String categoryCodeLike);
    int delStorage(String outStorageCode);
}