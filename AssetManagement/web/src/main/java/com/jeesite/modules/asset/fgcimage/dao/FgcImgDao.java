/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.fgcimage.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.fgcimage.entity.FgcImg;

/**
 * 梵工厂图片表DAO接口
 * @author len
 * @version 2018-08-13
 */
@MyBatisDao
public interface FgcImgDao extends CrudDao<FgcImg> {
	
}