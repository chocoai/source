/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.amspecimen.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.amspecimen.entity.AmSpecimenRecord;

/**
 * 构建样品进度DAO接口
 * @author dwh
 * @version 2018-07-11
 */
@MyBatisDao
public interface AmSpecimenRecordDao extends CrudDao<AmSpecimenRecord> {
    int deleteDb(String specimenCode);
}