/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.wish.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.wish.entity.WishShortlist;

import java.util.List;

/**
 * 入围名单DAO接口
 * @author len
 * @version 2018-11-20
 */
@MyBatisDao
public interface WishShortlistDao extends CrudDao<WishShortlist> {

    /**
     * 根据用户id获取入围信息
     * @param userIdlist
     * @return
     */
    List<WishShortlist> selectByUserId(List<String> userIdlist);

    /**
     * 更新入围者票数
     * @param wishShortlistList
     */
    void updateShortlist(List<WishShortlist> wishShortlistList);

    /**
     * 根据用户Id获取被投票人的信息
     * @param userId
     * @return
     */
    List<WishShortlist> getPollResults (String userId);

    void insertData(List<WishShortlist> list);
}