/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.tianmao.entity.SpSelling;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 出售中的商品列表DAO接口
 * @author dwh
 * @version 2018-08-18
 */
@MyBatisDao
public interface SpSellingDao extends CrudDao<SpSelling> {
    @Select("select item_id from js_sp_selling ")
    List<String> getListItenId();
    @Select("select * from js_sp_selling ")
    List<SpSelling> getAllYzyInfo();
    @Select("select * from js_sp_selling where item_id=#{itemId}")
    SpSelling getSpSellingByItemId(String itemId);
    void deleteAllData();
    void deleteAllDataBySourceType();
}