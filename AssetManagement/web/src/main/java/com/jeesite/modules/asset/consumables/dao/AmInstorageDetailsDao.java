/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.consumables.entity.AmInstorageDetails;

import java.util.List;

/**
 * 入库表DAO接口
 * @author dwh
 * @version 2018-05-03
 */
@MyBatisDao
public interface AmInstorageDetailsDao extends CrudDao<AmInstorageDetails> {

    List<AmInstorageDetails> getDetailsByCategoryCode(String categoryCode,String categoryCodeLike);

    int deleteDb(String instorageCode);
}