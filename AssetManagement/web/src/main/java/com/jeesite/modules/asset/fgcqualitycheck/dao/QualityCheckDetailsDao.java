/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.fgcqualitycheck.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.fgcqualitycheck.entity.QualityCheckDetails;

import java.util.List;

/**
 * 质检单DAO接口
 * @author len
 * @version 2018-08-18
 */
@MyBatisDao
public interface QualityCheckDetailsDao extends CrudDao<QualityCheckDetails> {
    QualityCheckDetails selectByEntityId(String entityId);

    /**
     * 根据质检单号删除
     * @param billNoList
     */
    void deleteDb(List<String> billNoList);
}