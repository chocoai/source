/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.wish.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.wish.entity.WishPrimaryDetail;

import java.util.List;

/**
 * 初选提名记录表DAO接口
 * @author len
 * @version 2018-11-20
 */
@MyBatisDao
public interface WishPrimaryDetailDao extends CrudDao<WishPrimaryDetail> {

    /**
     * 插入明细表
     * @param wishPrimaryDetailList
     */
    void insertDetail(List<WishPrimaryDetail> wishPrimaryDetailList);

    /**
     * 导出初选被提名记录
     * @return
     */
    List<WishPrimaryDetail> exportData();

}