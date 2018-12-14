/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.tianmao.entity.SpSalesprice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商品销售价目DAO接口
 * @author dwh
 * @version 2018-08-16
 */
@MyBatisDao
public interface SpSalespriceDao extends CrudDao<SpSalesprice> {

    @Select("select f_material_code from js_sp_salesprice GROUP BY f_material_code ")
    List<String> getMaterialCodeList();
    @Select("select * from js_sp_salesprice")
    List<SpSalesprice> getAllK3Info();
    @Delete("delete from js_sp_salesprice")
    void deleteAll();
}