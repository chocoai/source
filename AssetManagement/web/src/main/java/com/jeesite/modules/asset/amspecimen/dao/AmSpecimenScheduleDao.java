/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.amspecimen.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.amspecimen.entity.AmSpecimenSchedule;

/**
 * 构建样品进度DAO接口
 * @author mclaran
 * @version 2018-06-29
 */
@MyBatisDao
public interface AmSpecimenScheduleDao extends CrudDao<AmSpecimenSchedule> {
    int deleteDb(String specimenCode);
}