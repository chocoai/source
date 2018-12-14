/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.wish.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.wish.entity.WishVotedDetail;

/**
 * 入围名单DAO接口
 * @author len
 * @version 2018-11-20
 */
@MyBatisDao
public interface WishVotedDetailDao extends CrudDao<WishVotedDetail> {
	
}