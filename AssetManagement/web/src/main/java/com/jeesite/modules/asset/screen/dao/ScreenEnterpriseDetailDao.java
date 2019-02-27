/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.screen.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.screen.entity.CoverData;
import com.jeesite.modules.asset.screen.entity.ScreenEnterpriseDetail;

import java.util.List;

/**
 * 屏幕配置DAO接口
 * @author len
 * @version 2018-12-21
 */
@MyBatisDao
public interface ScreenEnterpriseDetailDao extends CrudDao<ScreenEnterpriseDetail> {
    /**
     * 获取屏幕配置的企业
     * @return
     */
    List<CoverData> getConfigEnterprise();
}