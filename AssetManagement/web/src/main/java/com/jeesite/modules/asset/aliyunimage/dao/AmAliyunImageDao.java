/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.aliyunimage.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.aliyunimage.entity.AmAliyunImage;

/**
 * 阿里云图片DAO接口
 * @author AlbertFeng
 * @version 2018-08-04
 */
@MyBatisDao
public interface AmAliyunImageDao extends CrudDao<AmAliyunImage> {
	
}