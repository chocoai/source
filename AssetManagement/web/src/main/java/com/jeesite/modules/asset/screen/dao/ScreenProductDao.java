/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.screen.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.screen.entity.ProductData;
import com.jeesite.modules.asset.screen.entity.ScreenProduct;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 零售家产品详情DAO接口
 * @author len
 * @version 2019-01-08
 */
@MyBatisDao
public interface ScreenProductDao extends CrudDao<ScreenProduct> {
    /**
     * 根据企业编码获取企业的产品
     * @return
     */
    List<ProductData> getProduct(List<String> enterpriseList);

    /**
     * 企业的产品
     * @param enterpriseCode
     * @return
     */
    @Select("SELECT count(1) FROM js_screen_product WHERE enterprise_code=#{arg0}")
    int selectCount(String enterpriseCode);

    /**
     * 根据企业编码分页
     * @param enterpriseCode
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<ProductData> getProductList(@Param("enterpriseCode") String enterpriseCode, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);
}