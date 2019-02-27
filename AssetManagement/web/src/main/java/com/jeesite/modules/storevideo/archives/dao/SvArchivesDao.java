/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.archives.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.storevideo.archives.entity.SvArchives;

/**
 * 产品推送基础档案DAO接口
 * @author len
 * @version 2019-02-26
 */
@MyBatisDao
public interface SvArchivesDao extends CrudDao<SvArchives> {
	
}