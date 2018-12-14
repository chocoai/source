/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.qualitycheck.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.qualitycheck.entity.FgcQualityCheck;

import java.util.List;

/**
 * 质检单DAO接口
 * @author Albert
 * @version 2018-07-25
 */
@MyBatisDao
public interface FgcQualityCheckDao extends CrudDao<FgcQualityCheck> {
    FgcQualityCheck getByBillNo(String billNo);     // 根据质检单获取单据信息
    String getMaxQualityTime();
}