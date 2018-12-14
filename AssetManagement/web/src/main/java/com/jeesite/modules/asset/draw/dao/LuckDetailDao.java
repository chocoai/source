/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.draw.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.draw.entity.LuckDetail;
import org.apache.ibatis.annotations.Delete;

/**
 * 抽奖竞猜DAO接口
 * @author len
 * @version 2018-10-05
 */
@MyBatisDao
public interface LuckDetailDao extends CrudDao<LuckDetail> {
    /**
     * 根据单据号删除明细数据
     * @param documentCode
     * @return
     */
    @Delete("DELETE FROM js_am_luck_detail WHERE document_code=#{documentCode}")
    int deleteDetailByCode(String documentCode);

}