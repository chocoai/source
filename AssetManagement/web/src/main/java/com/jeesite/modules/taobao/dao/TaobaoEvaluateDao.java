/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.taobao.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.taobao.entity.TaobaoEvaluate;
import org.apache.ibatis.annotations.Select;

/**
 * 图片评价表DAO接口
 * @author len
 * @version 2018-11-01
 */
@MyBatisDao
public interface TaobaoEvaluateDao extends CrudDao<TaobaoEvaluate> {
    @Select("SELECT * from taobao_evaluate where product_id = #{arg0} and publish_date = #{arg1} and author_name = #{arg2} ")
    TaobaoEvaluate getEvaluate(String productId, Long publishDate, String authorName);
}