/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.consumables.entity.AmTransferDetails;

/**
 * 调拨单DAO接口
 * @author dwh
 * @version 2018-05-21
 */
@MyBatisDao
public interface AmTransferDetailsDao extends CrudDao<AmTransferDetails> {
    int deleteDb(String documentsCode);
}