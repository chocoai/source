/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.util.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.util.entity.AmSeq;

/**
 * js_am_seqDAO接口
 * @author czy
 * @version 2018-04-24
 */
@MyBatisDao
public interface AmSeqDao extends CrudDao<AmSeq> {
	int insertSeq(String perfix);
	Integer getSeq(String perfix);
    Integer getInsertSeq(String perfix);

}