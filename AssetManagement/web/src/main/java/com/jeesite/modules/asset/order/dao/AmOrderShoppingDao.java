/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.order.entity.AmOrderShopping;
import com.jeesite.modules.asset.order.entity.Shopping;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 导购购物车DAO接口
 * @author len
 * @version 2018-11-13
 */
@MyBatisDao
public interface AmOrderShoppingDao extends CrudDao<AmOrderShopping> {
    /**
     * 根据skuid查询商品信息
     * @param skuId
     * @return
     */
    Shopping getSkuInfo(String skuId);

    /**
     * 根据skuId获取真实售价库存数
     * @param skuIdList
     * @return
     */
    List<TbSku> selectBySkuId(List<String> skuIdList);
    /**
     * 根据skuId 登录用户查询状态为有效的商品
     * @param skuIdList
     * @param userCode
     * @return
     */
    int updateBySkuIdList(@Param("skuIdList") List<String> skuIdList,@Param("userCode") String userCode);
}