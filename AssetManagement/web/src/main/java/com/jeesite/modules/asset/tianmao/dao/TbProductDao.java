/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.tianmao.entity.TbProduct;

/**
 * 同步淘宝商品列表DAO接口
 * @author Jace
 * @version 2018-07-07
 */
@MyBatisDao
public interface TbProductDao extends CrudDao<TbProduct> {
   void updateProductCategory(String procategoryCode,String proseriesCode,String numIid);
}