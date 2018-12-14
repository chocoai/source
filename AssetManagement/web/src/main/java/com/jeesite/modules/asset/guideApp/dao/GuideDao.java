package com.jeesite.modules.asset.guideApp.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.guideApp.entity.GuideGoods;
import com.jeesite.modules.asset.guideApp.entity.GuideOrder;
import com.jeesite.modules.sys.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface GuideDao extends CrudDao<GuideOrder> {
    User getLoginCode(String loginCode);    // 根据登录帐号获取用户信息
    List<GuideGoods> getDetail(String documentCode);  // 根据订单号获取商品信息
    String selectShop(@Param("userCode") String userCode);     // 根据用户账号获取部门里的tree_names
}
