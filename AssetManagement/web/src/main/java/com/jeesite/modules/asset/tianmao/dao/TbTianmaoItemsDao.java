/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.tianmao.entity.TbTianmaoItems;

import java.util.List;

/**
 * tb_tianmao_itemsDAO接口
 * @author jace
 * @version 2018-07-04
 */
@MyBatisDao
public interface TbTianmaoItemsDao extends CrudDao<TbTianmaoItems> {
    /**
     * 根据商品id获取详细信息
     * @param numIidList
     * @return
     */
    List<TbTianmaoItems> getTianmaoItems(List<String> numIidList);
}