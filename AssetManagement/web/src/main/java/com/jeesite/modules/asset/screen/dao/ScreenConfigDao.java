/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.screen.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.screen.entity.FirstPageData;
import com.jeesite.modules.asset.screen.entity.ScreenConfig;
import com.jeesite.modules.asset.tianmao.entity.TbProduct;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 屏幕配置DAO接口
 * @author len
 * @version 2018-12-21
 */
@MyBatisDao
public interface ScreenConfigDao extends CrudDao<ScreenConfig> {
    /**
     * 查询一级页面数据
     * @return
     */
    List<FirstPageData> getFirstPage(String flag);

    List<TbProduct> getGoods(@Param("seriesName") String seriesName,@Param("pageNo") int pageNo,@Param("pageSize") int pageSize);

    int getGoodsNum(String seriesName);
}