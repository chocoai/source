/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 商品-SKU管理DAO接口
 * @author jace
 * @version 2018-07-09
 */
@MyBatisDao
public interface TbSkuDao extends CrudDao<TbSku> {
	List<TbSku> getSku(Long numIid);

	int updateSku(String outerId,String k3name);

	@Select("select * from tb_sku where outer_id != '' and outer_id is not null")
	List<TbSku> selectSkuList();

	@Select("SELECT * FROM `tb_sku`  where num_iid=#{arg0} ORDER BY real_price LIMIT 1")
	TbSku getSkuPrice(Long numIid);
	/**
	 按numIid进行查询
	 */
	List<TbSku> findByNumIid(String numIid);
	@Select("SELECT b.* FROM tb_product a LEFT JOIN tb_sku b ON a.num_iid = b.num_iid WHERE a.nick = 'saladliang'")
	List<TbSku> selectSku();
	@Update("update tb_sku SET real_price = #{arg1} WHERE sku_id = #{arg0}")
	int updatePrice(Long skuId, String realPrice);

	@Select("SELECT * FROM tb_sku where sku_id = #{skuId}")
	TbSku selectBySkuId(String skuId);

	/**
	 * 获取店铺（卖家昵称），SKU，真实售价
	 * @return
	 */
	@Select("SELECT a.outer_id,a.real_price,b.nick shop from tb_sku a LEFT JOIN tb_product b on a.num_iid = b.num_iid where b.approve_status ='onsale' and b.nick !='saladliang' and a.real_price != 0 and quantity>0")
	List<TbSku> getSkuAndPrice();

	/**
	 * 插入多条数据到数据库
	 * @param skuList
	 */
	void insertSkuList(List<TbSku> skuList);

	/**
	 * 更新多条数据到数据库
	 * @param skuList
	 */
	void updateSkuList(List<TbSku> skuList);

	/**
	 * 更新库存为0
	 * @param skuList
	 */
	void updateQuantity(List<TbSku> skuList);

	/**
	 * 根据skuId更新分销价
	 * @param tbSkuList
	 */
	void updateDistributionPrice(List<TbSku> tbSkuList);

	/**
	 * 根据sku查询sku
	 * @param skuIdList
	 * @return
	 */
	List<TbSku> selectBySkuIdList(List<String> skuIdList);

	/**
	 * 根据sku更新分销价
	 * @param tbSkuList
	 */
	void updateDistributionPriceBySku(List<TbSku> tbSkuList);
}