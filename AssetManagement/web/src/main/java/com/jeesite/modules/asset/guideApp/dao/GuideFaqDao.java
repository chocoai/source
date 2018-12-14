/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.guideApp.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.guideApp.entity.GuideFaq;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 导购活动表DAO接口
 * @author len
 * @version 2018-12-07
 */
@MyBatisDao
public interface GuideFaqDao extends CrudDao<GuideFaq> {
    /**
     * 关键字查询常见问题接口
     *
     * 后台根据常见问题的问题模糊匹配问题包含关键字的q&a；返回给前台；
     * @param activityCode
     * @param keyword
     * @return
     */
    @Select("SELECT * FROM js_guide_faq WHERE activity_code=#{arg0} AND (faq_question LIKE concat('%',#{arg1},'%') OR faq_answer LIKE concat('%',#{arg1},'%'))")
    List<GuideFaq> selectFAQ(String activityCode, String keyword);
}