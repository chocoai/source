/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.tianmao.dao.TbTianmaoItemsDao;
import com.jeesite.modules.asset.tianmao.entity.TbItemImgs;
import com.jeesite.modules.asset.tianmao.entity.TbProduct;
import com.jeesite.modules.asset.tianmao.entity.TbTianmaoItems;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.ItemImg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * tb_tianmao_itemsService
 * @author jace
 * @version 2018-07-04
 */
@Service
@Transactional(readOnly=true)
public class TbTianmaoItemsService extends CrudService<TbTianmaoItemsDao, TbTianmaoItems> {

	@Autowired
	private TbTianmaoItemsDao tbTianmaoItemsDao;
	/**
	 * 获取单条数据
	 * @param tbTianmaoItems
	 * @return
	 */
	@Override
	public TbTianmaoItems get(TbTianmaoItems tbTianmaoItems) {
		return super.get(tbTianmaoItems);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbTianmaoItems
	 * @return
	 */
	@Override
	public Page<TbTianmaoItems> findPage(Page<TbTianmaoItems> page, TbTianmaoItems tbTianmaoItems) {
		return super.findPage(page, tbTianmaoItems);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbTianmaoItems
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbTianmaoItems tbTianmaoItems) {
		super.save(tbTianmaoItems);
	}
	
	/**
	 * 更新状态
	 * @param tbTianmaoItems
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbTianmaoItems tbTianmaoItems) {
		super.updateStatus(tbTianmaoItems);
	}
	
	/**
	 * 删除数据
	 * @param tbTianmaoItems
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbTianmaoItems tbTianmaoItems) {
		super.delete(tbTianmaoItems);
	}

	/**
	 * 根据商品id获取最后一张图片作为主图
	 * @param
	 * @return
	 */
	public String getLastImg (String numIid) {
		String body = get(numIid).getBody();
		JSON json = JSONObject.parseObject(body);
		Item item = JSONObject.toJavaObject(json,Item.class);

		List<ItemImg> tbItemImgsList = item.getItemImgs();
		if (ListUtils.isNotEmpty(tbItemImgsList)) {
			Collections.sort(tbItemImgsList, new Comparator<ItemImg>() {
				@Override
				public int compare(ItemImg o1, ItemImg o2) {
					if (o1.getPosition() > o2.getPosition()) {
						return 1;
					}
					if (o1.getPosition() == o2.getPosition()) {
						return 1;
					}
					return -1;
				}
			});
			return tbItemImgsList.get(tbItemImgsList.size() - 1).getUrl();
		} else {
			return "";
		}
	}


	/**
	 *
	 * @param numIidList
	 * @return
	 */
	public List<TbItemImgs> getLastImg (List<String> numIidList) {
		List<TbItemImgs> tbItemImgsList = ListUtils.newArrayList();
		List<TbTianmaoItems> tbTianmaoItemsList = tbTianmaoItemsDao.getTianmaoItems(numIidList);
		for (TbTianmaoItems tbTianmaoItems : tbTianmaoItemsList) {
			String body = tbTianmaoItems.getBody();
			JSON json = JSONObject.parseObject(body);
			Item item = JSONObject.toJavaObject(json, Item.class);

			List<ItemImg> itemImgList = item.getItemImgs();
			if (ListUtils.isNotEmpty(itemImgList)) {
				Collections.sort(itemImgList, new Comparator<ItemImg>() {
					@Override
					public int compare(ItemImg o1, ItemImg o2) {
						if (o1.getPosition() > o2.getPosition()) {
							return 1;
						}
						if (o1.getPosition() == o2.getPosition()) {
							return 1;
						}
						return -1;
					}
				});
				TbItemImgs tbItemImgs = new TbItemImgs();
				tbItemImgs.setItemId(Long.valueOf(tbTianmaoItems.getId()));
				tbItemImgs.setUrl(ListUtils.getLast(itemImgList).getUrl());
				tbItemImgsList.add(tbItemImgs);
			}
		}
		return tbItemImgsList;
	}

}