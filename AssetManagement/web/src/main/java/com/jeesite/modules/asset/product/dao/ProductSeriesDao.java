/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.product.dao;

import com.jeesite.common.dao.TreeDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.product.entity.ProductSeries;
import com.jeesite.modules.asset.tianmao.entity.TbProduct;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商品系列表DAO接口
 * @author Scarlett
 * @version 2018-07-24
 */
@MyBatisDao
public interface ProductSeriesDao extends TreeDao<ProductSeries> {
    Integer selectByProseriesStatus(String proseriesCode);
    void delete(String proseriesCode);
    Integer findProseries(String name);

    /**
     * 二级分类查询
     */
    @Select("select * from tb_product where proseries_code=#{arg0} AND approve_status='onsale' limit #{arg1},#{arg2}")
    List<TbProduct> findProductListByCode(String code, int start, int end);
    /**
     * 二级分类查询总数
     */
    @Select("select count(1) from tb_product where proseries_code=#{arg0} AND approve_status='onsale'")
    int findProductCountByCode(String code);

    /**
     * 根据传入的系列名模糊查询
     * @param series
     * @return
     */
    List<ProductSeries> selectByName(@Param("series") String series);

}