/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.qualitycheck.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.qualitycheck.entity.FgcQualityCheckDetails;

import java.util.List;

/**
 * 质检单DAO接口
 * @author Albert
 * @version 2018-07-25
 */
@MyBatisDao
public interface FgcQualityCheckDetailsDao extends CrudDao<FgcQualityCheckDetails> {
    FgcQualityCheckDetails selectById (String id);
    // 根据单据号获取明细
    List<FgcQualityCheckDetails> selectByQualityId(String qualityId);
}