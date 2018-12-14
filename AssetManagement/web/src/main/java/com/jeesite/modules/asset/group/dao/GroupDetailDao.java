/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.group.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.group.entity.GroupDetail;
import org.apache.ibatis.annotations.Delete;

/**
 * 团购信息表DAO接口
 * @author len
 * @version 2018-10-23
 */
@MyBatisDao
public interface GroupDetailDao extends CrudDao<GroupDetail> {
    @Delete("DELETE FROM js_group_detail WHERE purchase_code = #{arg0}")
	void deleteDb(String purchaseCode);
}