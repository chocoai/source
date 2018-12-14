/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.report.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.fz.report.entity.FzStatisticalItem;

/**
 * 一级部门赞赏统计项目DAO接口
 * @author easter
 * @version 2018-11-14
 */
@MyBatisDao
public interface FzStatisticalItemDao extends CrudDao<FzStatisticalItem> {
	
}