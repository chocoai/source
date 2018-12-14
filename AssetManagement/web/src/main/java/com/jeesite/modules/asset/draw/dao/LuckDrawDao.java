/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.draw.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.draw.entity.LuckDraw;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 抽奖竞猜DAO接口
 * @author len
 * @version 2018-10-05
 */
@MyBatisDao
public interface LuckDrawDao extends CrudDao<LuckDraw> {
    /**
     * 根据单据表头【中奖人数】， 获取梵赞-用户管理档案数据， 【是否中奖】=0 并且 状态=【启用】的用户清单，随机获取
     * @param winnersNum
     * @return
     */
    @Select("SELECT * from js_ding_user WHERE winning_prize!='1' and user_status != '2' and name not in( '美梵发票','会议室')  ORDER BY RAND() LIMIT #{arg0}")
//    @Select("SELECT * from js_ding_user WHERE userid='18392547561934016313' or userid='1856293363663750138'")
    List<DingUser> selectByRand(int winnersNum);

    int saveDingUser(List<DingUser> dingUserList);

    @Select("SELECT config_value FROM js_sys_config WHERE config_key='deadline_for_quiz'")
    String selectByKey();
}