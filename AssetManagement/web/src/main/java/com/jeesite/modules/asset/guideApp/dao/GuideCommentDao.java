/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.guideApp.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.guideApp.entity.GuideComment;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


/**
 * 导购活动表DAO接口
 * @author len
 * @version 2018-12-07
 */
@MyBatisDao
public interface GuideCommentDao extends CrudDao<GuideComment> {
    /**
     * 根据评论Id获取评论信息
     * @param commentCode
     * @return
     */
    @Select("SELECT * FROM js_guide_comment WHERE comment_code = #{arg0}")
    GuideComment getGuideComment(String commentCode);

    /**
     * 根据评论id清空回复信息
     * @param commentCode
     */
    @Update("UPDATE js_guide_comment SET answer=null,answer_by=null,answer_id=null,answer_time=null WHERE comment_code = #{arg0}")
    void deleteAnswer(String commentCode);
}