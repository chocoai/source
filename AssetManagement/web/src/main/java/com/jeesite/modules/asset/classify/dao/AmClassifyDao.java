/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.classify.dao;

import com.jeesite.common.dao.TreeDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.classify.entity.AmClassify;

/**
 * 资产分类表DAO接口
 * @author czy
 * @version 2018-04-24
 */
@MyBatisDao
public interface AmClassifyDao extends TreeDao<AmClassify> {
    int deleteDb(String classifyCode);  // 删除数据
}