/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.distribution.material.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.tianmao.entity.TbItemImgs;
import com.jeesite.modules.asset.tianmao.entity.TbProduct;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import com.jeesite.modules.distribution.material.entity.DistrMaterial;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 分销素材DAO接口
 * @author len
 * @version 2019-01-05
 */
@MyBatisDao
public interface DistrMaterialDao extends CrudDao<DistrMaterial> {
    @Select("SELECT a.*,GROUP_CONCAT(b.file_url) img FROM distr_material a LEFT JOIN js_am_file_upload b on a.material_code = b.biz_key and b.biz_type = 'distrMaterial_detailsImg' WHERE a.material_code=#{arg0} GROUP BY a.material_code")
    DistrMaterial selectByMaterial(String materialCode);

    /**
     * 根据商品Id查询商品信息
     * @param numIidList
     * @return
     */
    List<TbProduct> selectByNumIid(List<String> numIidList);

    /**
     * 根据商品Id查询sku信息
     * @param numIidList
     * @return
     */
    List<TbSku> selectSkuByNumIid(List<String> numIidList);

    /**
     *
     * @param numIidList
     * @return
     */
    List<TbItemImgs> selectImgByNumIid(List<String> numIidList);
}

