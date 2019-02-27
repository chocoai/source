/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.product.dao;

import com.jeesite.common.dao.TreeDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.product.entity.ProductCategory;
import com.jeesite.modules.asset.tianmao.entity.TbProduct;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import com.taobao.api.domain.Product;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商品分类表DAO接口
 * @author Scarlett
 * @version 2018-07-23
 */
@MyBatisDao
public interface ProductCategoryDao extends TreeDao<ProductCategory> {
    void delete(String procategoryCode);
    Integer selectByProcategoryStatus(String procategoryCode);
    List<ProductCategory> findListOuter();
    int findProductCategory(String name);

    /**
     * 二级分类查询
     */
    @Select("select a.*,MIN(b.distribution_price) lowerDistrPrice from tb_product a LEFT JOIN tb_sku b ON a.num_iid=b.num_iid where procategory_code=#{arg0} AND approve_status='onsale' AND nick != 'saladliang' GROUP BY a.num_iid limit #{arg1},#{arg2}")
    List<TbProduct> findProductListByCode(String code, int start, int end);
    /**
     * 二级分类查询总数
     */
    @Select("select count(1) from tb_product where procategory_code=#{arg0} AND approve_status='onsale' AND nick != 'saladliang'")
    int findProductCountByCode(String code);

    /**
     * 查询SKU
     */
    @Select("SELECT * FROM `tb_sku` where num_iid=#{arg0} and quantity>0")
    List<TbSku> findSkuList(String numIid);
}