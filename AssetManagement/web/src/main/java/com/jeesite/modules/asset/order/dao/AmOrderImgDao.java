/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.order.entity.AmOrderImg;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 订单图片表DAO接口
 * @author len
 * @version 2018-11-26
 */
@MyBatisDao
public interface AmOrderImgDao extends CrudDao<AmOrderImg> {
    /**
     * 根据图片id更新状态为1（删除）
     * @param imgCode
     */
    @Update("UPDATE js_am_order_img SET img_status=1,update_date=now() WHERE img_code=#{imgCode}")
    void updateImgStatus(@Param("imgCode") String imgCode);

    /**
     * 根据图片id更新状态为1（删除）
     * @param list
     */
    void updateBatch(List<String> list);
}