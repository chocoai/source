/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.group.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.group.entity.GroupPurchase;
import com.jeesite.modules.asset.group.entity.PurchaseData;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import com.jeesite.modules.taobao.entity.TaobaoOrderRds;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 团购信息表DAO接口
 * @author len
 * @version 2018-10-23
 */
@MyBatisDao
public interface GroupPurchaseDao extends CrudDao<GroupPurchase> {
    /**
     * 根据旺旺id或者电话获取团长信息
     * @param wangCode
     */
    List<GroupPurchase> getGroupPurchase(@Param("wangCode") String wangCode, @Param("phone")String phone);
    /**
     * 根据旺旺id + 电话获取团长信息
     * @param wangCode
     */
    GroupPurchase getPurchase(String wangCode, String phone);
    /**
     * 查询团员旺旺ID是否存在小区拼团表中
     * @param memberWangCode
     * @return
     */
    List<PurchaseData> getMember(String memberWangCode);

    /**
     * 根据旺旺id匹配买家昵称
     */
    List<TaobaoOrderRds> getOrderRds(List<String> buyerNickList);

    /**
     * 根据旺旺id匹配买家昵称
     */
    List<TbSku> selectBySkuList(List<String> skuList);

    /**
     * 根据电话查询团购表和明细表中是否存在这个电话
     * @param phone
     * @return
     */
    List<PurchaseData> selectByPhone(String phone);

    List<PurchaseData> getMembers(List<String> detailList);
}