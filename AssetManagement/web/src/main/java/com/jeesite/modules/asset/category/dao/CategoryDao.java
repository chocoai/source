/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.category.dao;

import com.jeesite.common.dao.TreeDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.category.entity.Category;

import java.util.List;

/**
 * 耗材分类表DAO接口
 * @author czy
 * @version 2018-04-23
 */
@MyBatisDao
public interface CategoryDao extends TreeDao<Category> {
    List<Category> getCategoryByName(String categoryName);
    int deleteDb(String categoryCode);  // 删除数据
}